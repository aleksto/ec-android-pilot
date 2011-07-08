package com.tieto.ec.activities;

import com.tieto.ec.gui.WelcomeAnimation;

import android.app.Activity;
import android.os.Bundle;

public class WelcomeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Init
		WelcomeAnimation animation = new WelcomeAnimation(this);
		
		setContentView(animation);
	}
}
