package com.tieto.ec.service;

import java.util.TimerTask;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;

import android.content.Context;
import android.util.Log;

public class ValueChecker extends TimerTask{

	private ServiceNotification serviceNotification;
	private DMRViewServiceUnmarshalled webService;

	public ValueChecker(Context context, String username, String password, String url, String namespace){
		serviceNotification = new ServiceNotification(context, username, password, url, namespace);
		webService = new DMRViewServiceUnmarshalled(false, username, password, namespace, url);
	}
	
	@Override
	public void run() {
		//Log
		Log.d("tieto", "Value Checker is running");
		
		
		
		
		serviceNotification.dislplayNotification("Hello");	
	}
}
