package com.tieto.ec.activities;

import com.tieto.ec.gui.animation3D.WelcomeAnimation;

import android.app.Activity;
import android.os.Bundle;

public class Welcome extends Activity{

	private WelcomeAnimation animation;

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
