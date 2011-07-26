package com.tieto.ec.activities;

import android.app.Activity;
import android.os.Bundle;

import com.tieto.ec.gui.animation3D.WelcomeAnimation;

public class Welcome extends Activity{

	private WelcomeAnimation animation;

	/**
	 * Main class for the welcome animation, this is the class started when the application is started. 
	 * OnCreate is the constructor for the Super class Activity, and where all initializations start.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Init
		animation = new WelcomeAnimation(this);
		
		setContentView(animation);
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		animation.onPause();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		onBackPressed();
	}
}