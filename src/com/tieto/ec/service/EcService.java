package com.tieto.ec.service;

import java.util.ArrayList;
import java.util.Timer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tieto.ec.enums.Webservice;

public class EcService extends Service{
	
	private ArrayList<Timer> timers;
	private ServiceThread serviceThread;

	/**
	 * Start a new {@link Service}
	 */
	@Override
	public void onStart(Intent intent, int startId){
		//Super
		super.onStart(intent, startId);
		
		//Log
		Log.d("tieto", "Starting service");
		
		//Init
		String username = intent.getExtras().getString(Webservice.username.toString());
		String password = intent.getExtras().getString(Webservice.password.toString());
		String namespace = intent.getExtras().getString(Webservice.namespace.toString());
		String url = intent.getExtras().getString(Webservice.url.toString());
		timers = new ArrayList<Timer>();
		
		//Thread
		serviceThread = new ServiceThread(this, username, password, url, namespace);
		if(serviceThread.getThread() != null){
			serviceThread.getThread().start();			
		}
	}
	
	/**
	 * Called when {@link Service} is stopped
	 * This stops the timer that automatically checks for updates
	 */
	@Override
	public void onDestroy(){
		//Log
		Log.d("tieto", "Stopping service");
		
		//Stopping timer
		if(serviceThread != null){
			cancelAll();
		}
		//Super
		super.onDestroy();
	}
	
	private void cancelAll(){
		for (Timer timer : timers) {
			timer.cancel();
		}
	}
	
	public void addTimer(Timer timer){
		timers.add(timer);
	}
	
	/**
	 * Not used
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
