package com.ec.prod.android.pilot.client;

import java.io.IOException;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;
import com.ec.prod.android.pilot.client.HttpTransportBasicAuth;

public class Webservice implements Runnable{


	private String namespace;
	private SoapSerializationEnvelope envelope;
	private HttpTransportBasicAuth httpTransport;
	private String method;
	private String[] args;
	private Thread thread;
	private Object bodyIn;

	public Webservice(String username, String password, String namespace, String url){
		//Init
		this.namespace = namespace;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		httpTransport = new HttpTransportBasicAuth(url, username, password);
	}
	
	protected void addMarshal(Marshal marshal) {
		marshal.register(envelope);
	}

	protected synchronized Object executeWebservice(String method, String ... args){
		this.method = method;
		this.args = args;
		
		thread = new Thread(this);
		thread.start();
		
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return bodyIn;
	}
	
	public synchronized void run() {
		//Init
		
		SoapObject request = new SoapObject(namespace, method); 

		//PARAMS
		if(args[0] != ""){
			for (int i = 0; i < args.length; i+=2) {
				request.addProperty(args[i], args[i+1]);
			}
		}

 		//Envelope
		envelope.setOutputSoapObject(request);

		//Submiting
		try {
			httpTransport.call(namespace + "/" + method, envelope);
			bodyIn = envelope.bodyIn;
		} catch (IOException e) {
			e.printStackTrace();
		} catch(java.lang.ClassCastException e){
			e.printStackTrace();
			bodyIn = null;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			bodyIn = new Object();
		}

		notify();
	}
}
