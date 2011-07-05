package com.tieto.ec.logic;

import java.util.Date;


public class DateFormat {

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
	
	public static String parse(double daytime){
		Date date = new Date((long) daytime);
		if((date.getMonth()+1) < 10 && date.getDate() < 10){
			return date.getYear() + "-0" + (date.getMonth()+1) + "-0" + date.getDate();			
		}
		if((date.getMonth()+1) >= 10 && date.getDate() < 10){
			return date.getYear() + "-" + (date.getMonth()+1) + "-0" + date.getDate();			
		}
		if((date.getMonth()+1) < 10 && date.getDate() >= 10){
			return date.getYear() + "-0" + (date.getMonth()+1) + "-" + date.getDate();			
		}
		else{
			return date.getYear() + "-" + (date.getMonth()+1) + "-" + date.getDate();			
		}
	}
}
