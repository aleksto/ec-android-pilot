package com.tieto.ec.service;

import java.util.TimerTask;

import android.util.Log;

public class ValueChecker extends TimerTask{

	public ValueChecker(){
		
	}
	
	@Override
	public void run() {
		Log.d("tieto", "Value Checker is running");
	}
}
