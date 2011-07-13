package com.tieto.ec.activities;

import com.tieto.ec.gui.WelcomeAnimation;

import android.app.Activity;
import android.os.Bundle;

public class WelcomeActivity extends Activity{

	private WelcomeAnimation animation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Init
		animation = new WelcomeAnimation(this);
		
		setContentView(animation);
		
		//WEBSERVICE TEST
//		ServicePilotAndroid service = new ServicePilotAndroid("", "", "http://service.pilot.android.prod.ec.com/", "http://wv001927.eu.tieto.com/com.ec.prod.android.pilot?wsdl");
//		
//		service.getTextData(new TextSection("Operational Comments"), new Date(), new Date(), Resolution.MONTHLY);
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
