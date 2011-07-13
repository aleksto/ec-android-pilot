package com.tieto.ec.webServices;

import java.io.IOException;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;
import com.tieto.ec.logic.HttpTransportBasicAuth;

public class Webservice implements Runnable{


	private String namespace, url;
	private SoapSerializationEnvelope envelope;
	private HttpTransportBasicAuth httpTransport;
	private String method;
	private PropertyInfo[] args;
	private Thread thread;
	private Object bodyIn;

	public Webservice(String username, String password, String namespace, String url){
		//Init
		this.namespace = namespace;
		this.url = url;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		httpTransport = new HttpTransportBasicAuth(url, username, password);
	}
	
	protected void addMarshal(Marshal marshal) {
		marshal.register(envelope);
	}

	protected synchronized Object executeWebservice(String method, PropertyInfo ... args){
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
		if(args != null){
			for (int i = 0; i < args.length; i++) {
				request.addProperty(args[i]);
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
