package com.tieto.ec.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tieto.ec.enums.Webservice;

public class EcService extends Service{
	
	private ServiceThread serviceThread;

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
		
		//Thread
		serviceThread = new ServiceThread(this, username, password, url, namespace);
		if(serviceThread.getThread() != null){
			serviceThread.getThread().start();			
		}
	}
	
	@Override
	public void onDestroy(){
		//Log
		Log.d("tieto", "Stopping service");
		
		//Stopping timer
		if(serviceThread.getTimer() != null){
			serviceThread.getTimer().cancel();			
		}
		
		//Super
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("tieto", "Service binded");
		return null;
	}
}