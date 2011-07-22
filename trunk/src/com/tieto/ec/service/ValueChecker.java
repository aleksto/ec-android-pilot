package com.tieto.ec.service;

import java.util.TimerTask;

import javax.mail.search.NotTerm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

public class ValueChecker extends TimerTask{

	private ServiceNotification serviceNotification;

	public ValueChecker(Context context, String username, String password, String url, String namespace){
		serviceNotification = new ServiceNotification(context, username, password, url, namespace);
	}
	
	@Override
	public void run() {
		Log.d("tieto", "Value Checker is running");
		serviceNotification.dislplayNotification("Hello");	
	}
}
