package com.tieto.ec.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tieto.ec.gui.animation3D.WelcomeAnimation;
import com.tieto.ec.logic.FileManager;

public class Welcome extends Activity{

	private boolean quit;
	private WelcomeAnimation animation;

	/**
	 * Main class for the welcome animation, this is the class started when the application is started. 
	 * OnCreate is the constructor for the Super class Activity, and where all initializations start.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Cancel notifications if any 
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
		
		//Init
		try {
			if(Boolean.valueOf(FileManager.readPath(this, "Welcome Activity.3D Seen"))){
				quit = true;
				startActivity(new Intent(this, Login.class));
			}
		} catch (IOException e) {
			animation = new WelcomeAnimation(this);
			setContentView(animation);
			e.printStackTrace();
		}
	}
	
	/**
	 * This method defines that if the Welcome Activity is paused (for example by incoming telephone called)
	 * the animation thread should also be paused. 
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if(animation != null){
			animation.onPause();			
		}
	}
	
	/**
	 * This method defines that the Welcome animation should only run once, and on back pressed from subsequent
	 * Activities this class should terminate.
	 */
	@Override
	public void onRestart() {
		super.onRestart();
		onBackPressed();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(quit){
			onBackPressed();			
		}
	}
}