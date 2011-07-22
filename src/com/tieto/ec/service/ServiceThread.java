package com.tieto.ec.service;

import java.util.Timer;

public class ServiceThread implements Runnable{

	private Timer timer;
	private Thread thread;
	private long updateInterval;
	
	public ServiceThread(long updateInterval){
		//Init
		this.updateInterval = updateInterval;
		
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
		timer.scheduleAtFixedRate(new ValueChecker(), 0, updateInterval);		
	}
}
