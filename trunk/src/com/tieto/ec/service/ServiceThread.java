package com.tieto.ec.service;

import java.util.Timer;

import android.content.Context;

public class ServiceThread implements Runnable{

	private Timer timer;
	private Thread thread;
	private long updateInterval;
	private ValueChecker valueChecker;
	
	public ServiceThread(Context context, long updateInterval, String username, String password, String url, String namespace){
		//Init
		this.updateInterval = updateInterval;
		valueChecker = new ValueChecker(context, username, password, url, namespace);
		
		//Thread
		thread = new Thread(this);
	}
	
	public Thread getThread(){
		return thread;
	}
	
	public Timer getTimer(){
		return timer;
	}
	
	public void run() {
		timer = new Timer();
		timer.scheduleAtFixedRate(valueChecker, 0, updateInterval);		
	}
}
