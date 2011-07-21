package com.tieto.ec.logic;

import com.ec.prod.android.pilot.model.Resolution;

public class ResolutionConverter {

	public static int convert(String resolution){
		if(resolution.equalsIgnoreCase("Daily")){
			return Resolution.DAILY;
		}else if(resolution.equalsIgnoreCase("Weekly")){
			return Resolution.WEEKLY;
		}else if(resolution.equalsIgnoreCase("Monthly")){
			return Resolution.MONTHLY;
		}else{
			return Resolution.YEARLY;
		}
	}
}
