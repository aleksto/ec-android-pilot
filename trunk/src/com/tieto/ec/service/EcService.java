package com.tieto.ec.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class EcService extends Service{
	
	private ServiceThread serviceThread;

	@Override
	public void onStart(Intent intent, int startId){
		//Log
		Log.d("tieto", "Service Started");
		
		//Super
		super.onStart(intent, startId);
		
		//Init
		long UpdateInterval = intent.getExtras().getInt("Update interval");
		String username = intent.getExtras().getString("username");
		String password = intent.getExtras().getString("password");
		String namespace = intent.getExtras().getString("namespace");
		String url = intent.getExtras().getString("url");
		
		//Thread
		serviceThread = new ServiceThread(this, UpdateInterval, username, password, url, namespace);
		serviceThread.getThread().start();
	}
	
	@Override
	public void onDestroy() {
		//Log
		Log.d("tieto", "Stopping service");
		
		//Stopping timer
		serviceThread.getTimer().cancel();
		
		//Super
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("tieto", "Service binded");
		return null;
	}
}
