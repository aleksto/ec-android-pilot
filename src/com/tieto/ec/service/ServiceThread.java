package com.tieto.ec.service;

import java.io.IOException;
import java.util.Timer;

import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.TimeType;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.UpdateTimeConverter;

import android.content.Context;
import android.util.Log;

public class ServiceThread implements Runnable{

	private Timer timer;
	private Thread thread;
	private long updateInterval;
	private ValueChecker valueChecker;

	public ServiceThread(Context context, String username, String password, String url, String namespace){
		//Init
		String updateTime = "";
		valueChecker = new ValueChecker(context, username, password, url, namespace);
		try {
			updateTime = FileManager.readPath(context, OptionTitle.DMRReport + "." + OptionTitle.ReportOptions + "." + OptionTitle.UpdateInterval);
			updateInterval = UpdateTimeConverter.parse(updateTime);
		} catch (IOException e) {
			FileManager.writePath(context, OptionTitle.DMRReport + "." + OptionTitle.ReportOptions + "." + OptionTitle.UpdateInterval, TimeType.off+"");
			updateInterval = 1;
			e.printStackTrace();
		}

		Log.d("tieto", "Service Started with update interval:" + updateTime);

		//Thread
		if(updateInterval > 0){
			thread = new Thread(this);			
		}
	}

	public Thread getThread(){
		return thread;
	}

	public Timer getTimer(){
		return timer;
	}

	public void run() {
		timer = new Timer();	
		timer.scheduleAtFixedRate(valueChecker, updateInterval, updateInterval);		
	}
}
