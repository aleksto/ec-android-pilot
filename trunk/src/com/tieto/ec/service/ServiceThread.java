package com.tieto.ec.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;

import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.ec.enums.Days;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.TimeType;
import com.tieto.ec.logic.DateConverter;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.NextDateFinder;
import com.tieto.ec.logic.UpdateTimeConverter;
import com.tieto.ec.logic.DateConverter.Type;

public class ServiceThread implements Runnable{
	
	private Timer timer;
	private Thread thread;
	private long updateInterval;
	private ValueChecker valueChecker;
	private final Context context;
	private final String username;
	private final String password;
	private final String url;
	private final String namespace;
	private ArrayList<Date> timeDeterminedNotificationDates;

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
		this.context = context;
		this.username = username;
		this.password = password;
		this.url = url;
		this.namespace = namespace;
		String updateTime = "";
		try {
			updateTime = FileManager.readPath(context, OptionTitle.Options + "." + OptionTitle.NotificationOptions);
			updateInterval = UpdateTimeConverter.parse(updateTime);
		} catch (IOException e) {
			FileManager.writePath(context, OptionTitle.Options + "." + OptionTitle.NotificationOptions, TimeType.off+"");
			updateInterval = -1;
			e.printStackTrace();
		}
		Log.d("tieto", "Service Started with update interval:" + updateTime);
	
		timeDeterminedNotificationDates = getTimeDeterminedNotificationDates();
		
		
		//Thread
		if(updateInterval > 0){
			thread = new Thread(this);			
		}
	}

	private ArrayList<Date> getTimeDeterminedNotificationDates() {
		ArrayList<Date> notificationDates = new ArrayList<Date>();
		ArrayList<Days> notificationDays = loadNotificationDays();
		
		String notificationTimeString = "";
		try {
			notificationTimeString = FileManager.readPath(context, OptionTitle.Options + "." + OptionTitle.NotificationOptions + "." + OptionTitle.TimeDeterminedNotification + "." + OptionTitle.SetTime);
			Date notificationTime = DateConverter.parse(notificationTimeString, Type.TIME);

			for (Days notificationDay : notificationDays) {
				Log.d("tieto", "DAYS TO BE ADDED: " + notificationDay.toString());
				Log.d("tieto", "DATE TO BE ADDED: " + notificationTime+"");

				
				notificationDates.add(NextDateFinder.findNextDate(notificationDay, notificationTime));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return notificationDates;
	}

	private ArrayList<Days> loadNotificationDays() {
		ArrayList<Days> notificationDays = new ArrayList<Days>();
		for (Days day : Days.values()) {
			try {
				String isChecked = FileManager.readPath(context, OptionTitle.Options + "." + OptionTitle.NotificationOptions + "." + OptionTitle.TimeDeterminedNotification + "." + day);
				if(Boolean.valueOf(isChecked)){
					notificationDays.add(day);
				}
						
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return notificationDays;
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
		valueChecker = new ValueChecker(context, username, password, url, namespace);
		timer = new Timer();
		timer.scheduleAtFixedRate(valueChecker, updateInterval, updateInterval);
		
		for (Date notificationDate : timeDeterminedNotificationDates) {
			timer.schedule(valueChecker, notificationDate, UpdateTimeConverter.parse(TimeType.min1.toString()));
		}

	}
}
