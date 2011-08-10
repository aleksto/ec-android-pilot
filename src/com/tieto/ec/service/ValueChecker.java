package com.tieto.ec.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;
import com.ec.prod.android.pilot.model.Resolution;
import com.ec.prod.android.pilot.service.ExampleViewService;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.ec.logic.WarningChecker;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class ValueChecker extends TimerTask{

	private ServiceNotification serviceNotification;
	private ArrayList<String> notifications;
	private ViewService viewService;
	private int criticallyLow;

	/**
	 * Creates a new {@link TimerTask} used in the {@link EcService}. 
	 * @param context
	 * @param username
	 * @param password
	 * @param url
	 * @param namespace
	 */
	public ValueChecker(Context context, String username, String password, String url, String namespace){		
		serviceNotification = new ServiceNotification(context, username, password, namespace, url);
		if(url.equalsIgnoreCase("debug")){
			viewService = new ExampleViewService();			
		}else{
			viewService = new DMRViewServiceUnmarshalled(username, password, namespace, url);
		}		
	}
	
	/**
	 * In the future this is where the automatic update will run
	 */
	@Override
	public void run() {
		WarningChecker warningChecker = new WarningChecker(viewService, Resolution.DAILY);
		List<SectionWarning> sectionWarnings = warningChecker.checkForWarnings(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));

		notifications = new ArrayList<String>();	
		criticallyLow = 0;
		for (SectionWarning sectionWarning : sectionWarnings) {
			double target = 0;
			double actual = 0;
			for (Warning warning : sectionWarning.getWarnings()) {
				if(warning.getType() == Type.CRITICAL || warning.getType() == Type.WARNING) {
					target += warning.getTargetValue();
					actual += warning.getActualValue();
				}
				
			}
			int prosent =  (int) Math.round((actual/(target*1f)) * 100);
			if(prosent < 50){
				criticallyLow++;
			}
			String prosentString = Math.round((actual/(target*1f)) * 100) + " %";
			
			notifications.add(prosentString);
		}
		StringBuilder text = new StringBuilder();
		text.append(notifications.size() + "/" + viewService.getSections().size() + " warnings. ");
		text.append(criticallyLow + "/" + viewService.getSections().size() + " critical values.");
		serviceNotification.dislplayNotification(text.toString());	
		
		Log.d("tieto", "Value checker running, " + text.toString());
	}
}
