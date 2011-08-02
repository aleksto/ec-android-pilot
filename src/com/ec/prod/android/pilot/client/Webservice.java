package com.ec.prod.android.pilot.client;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import com.ec.prod.android.pilot.service.MarshalService;

public abstract class Webservice implements Runnable{


	private String namespace;
	private SoapSerializationEnvelope envelope;
	private HttpTransportBasicAuth httpTransport;
	private String method;
	private String[] args;
	private Thread thread;
	private Object bodyIn;

	/**
	 * This abstract class executes webservice calls. The parameters are used to
	 * set up an envelope for holding responses from the webservice, and   
	 * for creating an {@link HttpTransportBasicAuth} object for login at the server
	 * @param username
	 * @param password
	 * @param namespace
	 * @param url
	 */
	public Webservice(String username, String password, String namespace, String url){
		//Init
		this.namespace = namespace;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		httpTransport = new HttpTransportBasicAuth(url, username, password);
		executeWebservice(url, args);
	}
	
	/**
	 * This is the method which does the actual execution of a webservice call. It takes in 
	 * a webservice method (which is defined in the webservice used) and certain arguments which
	 * this method requires. The method starts a separate thread for the call to be made on. This 
	 * thread is executed in the run method. It returns a bodyIn object which must be unMarshalled.
	 * This is done in the {@link MarshalService} class.
	 * @see MarshalService Marshall/unMarshall
	 * @param method
	 * @param args
	 * @return Object
	 */
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
	
	/**
	 * This is the thread the runs the actual execution of the webservice call. It makes a request object 
	 * of type {@link SoapObject}. It passes the webservice a namespace, method and arguments (properties).
	 * The request received is then put in the envelope created in {@link Webservice} (this) constructor.
	 * If the call is does not succeed the bodyIn (returned by executeWebservice) will be null. After the thread
	 * is finished it notifies waiting threads. 
	 */
	public synchronized void run() {
		//Init
		
		SoapObject request = new SoapObject(namespace, method); 

		//PARAMS
		if(args != null && args[0] != ""){
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
