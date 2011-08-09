package com.tieto.ec.service;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;

import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.TimeType;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.UpdateTimeConverter;

public class ServiceThread implements Runnable{

	private Timer timer;
	private Thread thread;
	private long updateInterval;
	private ValueChecker valueChecker;

	/**
	 * Start a new {@link Thread} and a new {@link Timer}, 
	 * this timer is responsible for automatically check for new updates
	 *  
	 * @param context {@link Context} used for Android framework actions
	 * @param username The username for {@link ViewService}
	 * @param password The password for {@link ViewService}
	 * @param url The URL for {@link ViewService}
	 * @param namespace The Namespace for {@link ViewService}
	 */
	public ServiceThread(Context context, String username, String password, String url, String namespace){
		//Init
		String updateTime = "";
		valueChecker = new ValueChecker(context, username, password, url, namespace);
		try {
			updateTime = FileManager.readPath(context, OptionTitle.DMRReport + "." + OptionTitle.NotificationOptions);
			updateInterval = UpdateTimeConverter.parse(updateTime);
		} catch (IOException e) {
			FileManager.writePath(context, OptionTitle.DMRReport + "." + OptionTitle.NotificationOptions, TimeType.off+"");
			updateInterval = -1;
			e.printStackTrace();
		}

		Log.d("tieto", "Service Started with update interval:" + updateTime);

		//Thread
		if(updateInterval > 0){
			thread = new Thread(this);			
		}
	}

	/**
	 * @return The {@link Thread} used for updating
	 */
	public Thread getThread(){
		return thread;
	}

	/**
	 * @return the {@link Timer} that is checking for updates
	 */
	public Timer getTimer(){
		return timer;
	}

	/**
	 * Runs when the {@link Thread} is started, and this initialize the {@link Timer}
	 * and sets the {@link TimerTask} to {@link ValueChecker}
	 */
	public void run() {
		timer = new Timer();	
		timer.scheduleAtFixedRate(valueChecker, updateInterval, updateInterval);		
	}
}
