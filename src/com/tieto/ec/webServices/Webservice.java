package com.tieto.ec.webServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import com.tieto.ec.logic.HttpTransportBasicAuth;

import android.util.Log;

public class Webservice {

	private String namespace, url;
	private SoapSerializationEnvelope envelope;
	private HttpTransportBasicAuth httpTransport;

	public Webservice(String username, String password, String namespace, String url){
		//Init
		this.namespace = namespace;
		this.url = url;
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
		httpTransport = new HttpTransportBasicAuth(url, username, password);
	}

	private HashMap<String, String> soapObjectToHashMap(SoapObject soap){
		//Init
		HashMap<String, String> map = new HashMap<String, String>();

		//Populating
		for (int i = 0; i < soap.getPropertyCount(); i++) {
			PropertyInfo propertyInfo = new PropertyInfo();
			soap.getPropertyInfo(i, propertyInfo);
			map.put(propertyInfo.getName(), soap.getProperty(i)+"");
		}

		return map;
	}

	protected ArrayList<HashMap<String, String>> executeWebservice(String method, String ... args){
		//Init
		SoapObject request = new SoapObject(namespace, method); 		

		//PARAMS
		for (int i = 0; i < args.length; i=i+2) {
			request.addProperty(args[i], args[i+1]);
		}

		//Envelope
		envelope.setOutputSoapObject(request);

		//Submiting
		try {
			httpTransport.call(namespace + "/" + method, envelope);

			SoapObject soap = (SoapObject) envelope.bodyIn;

			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			for (int i = 0; i < soap.getPropertyCount(); i++) {
				SoapObject returnValue = (SoapObject) soap.getProperty(i);
				list.add(soapObjectToHashMap(returnValue));
			}
			

			return list;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return new ArrayList<HashMap<String, String>>();
		}
		return null;
	}



	protected boolean validate(String username, String password) {
		//Init
		SoapObject request = new SoapObject(namespace, "findByPK");
		HttpTransportBasicAuth httpTransport = new HttpTransportBasicAuth(url, username, password);

		//Envelope
		envelope.setOutputSoapObject(request);

		request.addProperty("daytime", "");
		request.addProperty("objectid", "");

		//Submiting
		try {
			httpTransport.call(namespace + "/" + "findByPK", envelope);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		if(envelope.headerIn != null){
			Log.d("tieto", envelope.headerIn.length+"");			
			for (int i = 0; i < envelope.headerIn.length; i++) {
				Log.d("tieto", envelope.headerIn[i]+"");			
			}
		}else{
			Log.d("tieto", "Header in = null");						
		}

		return false;
	}
}
