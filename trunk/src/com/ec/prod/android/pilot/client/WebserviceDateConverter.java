package com.ec.prod.android.pilot.client;

import java.util.Date;


public class WebserviceDateConverter {

	public static long parse(String string){
		//YYYY-MM-DDT00:00:00Z
		int year = Integer.valueOf(string.substring(0, 4));
		int month = Integer.valueOf(string.substring(5, 7));
		int day = Integer.valueOf(string.substring(8, 10));
		int hour = Integer.valueOf(string.substring(11, 13));
		int min = Integer.valueOf(string.substring(14, 16));
		int sec = Integer.valueOf(string.substring(17, 19));
		
		Date date = new Date(year, (month-1), day, hour, min, sec);
		return date.getTime();
	}
	
	public static String parse(Date date){
		int offset = date.getHours()/12;
		if((date.getMonth()+1) < 10 && date.getDate() < 10){
			return (date.getYear()+1900) + "-0" + (date.getMonth()+1) + "-0" + (date.getDate() + offset);			
		}
		if((date.getMonth()+1) >= 10 && date.getDate() < 10){
			return (date.getYear()+1900) + "-" + (date.getMonth()+1) + "-0" + (date.getDate() + offset);			
		}
		if((date.getMonth()+1) < 10 && date.getDate() >= 10){
			return (date.getYear()+1900) + "-0" + (date.getMonth()+1) + "-" + (date.getDate() + offset);			
		}
		else{
			return (date.getYear()+1900) + "-" + (date.getMonth()+1) + "-" + (date.getDate() + offset);			
		}
	}
}