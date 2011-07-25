package com.tieto.ec.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.tieto.ec.activities.Login;

public class ServiceNotification {

	private static final int NOTIFICATION_ID = 1;
	private String tickerText;
	private int icon;
	private NotificationManager notificationManager;
	private Notification notification;
	private Context applicationContext;
	private String contentTitle;
	private PendingIntent contentIntent;
	

	public ServiceNotification(Context context, String username, String password, String url, String namespace) {
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
		Intent notificationIntent = new Intent(context, Login.class);
		contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
	}

	public void dislplayNotification(String message) {
		//Init
		notification = new Notification(icon, tickerText, System.currentTimeMillis());
		
		//Display
		notification.setLatestEventInfo(applicationContext, contentTitle, message, contentIntent);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

}