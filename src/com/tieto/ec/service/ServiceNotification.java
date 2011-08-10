package com.tieto.ec.service;

import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.ec.prod.android.pilot.model.Resolution;
import com.tieto.ec.activities.Login;
import com.tieto.ec.logic.DateConverter;
import com.tieto.ec.logic.DateConverter.Type;

public class ServiceNotification {

	private static final int NOTIFICATION_ID = 1;
	private String tickerText;
	private int icon;
	private NotificationManager notificationManager;
	private Notification notification;
	private Context applicationContext;
	private String contentTitle;
	private PendingIntent contentIntent;
	private Intent notificationIntent;
	
	/**
	 * This class makes a notification service which notifies the user of information when the application is closed.
	 * Context is needed for Androids framework actions. 
	 * @param context
	 */
	public ServiceNotification(Context context, String username, String password, String namespace, String url) {
		//Init
		String notificationService = Context.NOTIFICATION_SERVICE;
		applicationContext = context.getApplicationContext();
		
		//Notification
		tickerText = "Energy Components Message";
		contentTitle = "Energy Components";
		icon = android.R.drawable.stat_sys_warning;

		//Manager
		notificationManager = (NotificationManager) context.getSystemService(notificationService);

		//Intent
		notificationIntent = new Intent(context, Login.class);
		contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/**
	 * This method displays the a message in this service notification
	 * @param message
	 */
	public void dislplayNotification(String message) {
		//Init
		notification = new Notification(icon, tickerText, System.currentTimeMillis());
		
		//Display
		notification.setLatestEventInfo(applicationContext, contentTitle, message, contentIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(NOTIFICATION_ID, notification);
		
		//Setting dailyMorningReport time
		notificationIntent.putExtra("toDate", DateConverter.parse(new Date(System.currentTimeMillis()), Type.DATE, Resolution.DAILY));
	}

}
