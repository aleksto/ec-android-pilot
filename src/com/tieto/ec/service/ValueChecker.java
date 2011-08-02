package com.tieto.ec.service;

import java.util.TimerTask;

import android.content.Context;
import android.util.Log;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;

public class ValueChecker extends TimerTask{

	private ServiceNotification serviceNotification;

	/**
	 * Creates a new {@link TimerTask} used in the {@link EcService}. 
	 * @param context
	 * @param username
	 * @param password
	 * @param url
	 * @param namespace
	 */
	public ValueChecker(Context context, String username, String password, String url, String namespace){
		serviceNotification = new ServiceNotification(context);
		new DMRViewServiceUnmarshalled(username, password, namespace, url);
	}
	
	/**
	 * In the future this is where the automatic update will run
	 */
	@Override
	public void run() {
		//Log
		Log.d("tieto", "Value Checker is running");
		serviceNotification.dislplayNotification("Hello");	
	}
}
