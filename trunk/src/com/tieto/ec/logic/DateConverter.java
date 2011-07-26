package com.tieto.ec.logic;

import java.util.Date;

/**
 * Class for converting {@link Date} to String, and String to Date.
 * These strings are Webservice ready, thus can be handled by a Webservice
 */
public class DateConverter {
	
	public enum Type{DATE, TIME}
	private final static int OFFSET = 1900;
	
	/**
	 * This method takes a String of type YYYY-MM-DDT00:00:00Z. 
	 * YYYY is year, MM is month, DD is day, and 00:00:00 is time.
	 * T and Z are string constants. The String is converted to a {@link Date}
	 * @param string
	 * @return
	 */
	public static Date parse(String string){
		//YYYY-MM-DDT00:00:00Z
		Date date;
		int year = Integer.valueOf(string.substring(0, 4));
		int month = Integer.valueOf(string.substring(5, 7));
		int day = Integer.valueOf(string.substring(8, 10));
		if(string.length() > 10){
			int hour = Integer.valueOf(string.substring(11, 13));
			int min = Integer.valueOf(string.substring(14, 16));
			int sec = Integer.valueOf(string.substring(17, 19));
			date = new Date(year-OFFSET, (month-1), day, hour, min, sec);
		}
		else{
			date = new Date(year-OFFSET, (month-1), day);
		}
		return date;
	}
	
	/**
	 * This method takes a {@link Date}, checks if it contains a Time or only a Date. 
	 * From this information it converts the Date into a String which is Webservice ready.
	 * The return format is YYYY-MM-DDT00:00:00Z where YYYY is year, MM is month, DD is day, and 00:00:00 is time.
	 * T and Z are string constants.
	 * @param date
	 * @param type
	 * @return
	 */
	public static String parse(Date date, Type type){
		
		switch (type) {
		case DATE:
			int offset = date.getHours()/12;
			if((date.getMonth()+1) < 10 && date.getDate() < 10){
				return (date.getYear()+OFFSET) + "-0" + (date.getMonth()+1) + "-0" + (date.getDate() + offset);			
			}
			if((date.getMonth()+1) >= 10 && date.getDate() < 10){
				return (date.getYear()+OFFSET) + "-" + (date.getMonth()+1) + "-0" + (date.getDate() + offset);			
			}
			if((date.getMonth()+1) < 10 && date.getDate() >= 10){
				return (date.getYear()+OFFSET) + "-0" + (date.getMonth()+1) + "-" + (date.getDate() + offset);			
			}
			else{
				return (date.getYear()+OFFSET) + "-" + (date.getMonth()+1) + "-" + (date.getDate() + offset);			
			}
		case TIME:
			if(date.getHours() < 10 && date.getMinutes() < 10){
				return "0" + date.getHours() + ":0" + date.getMinutes();
			}
			else if(date.getHours() < 10 && date.getMinutes() > 10){
				return "0" + date.getHours() + ":" + date.getMinutes();
			}
			else if(date.getHours() >= 10 && date.getMinutes() < 10){
				return "" + date.getHours() + ":0" + date.getMinutes();
			}
			else{
				return "" + date.getHours() + ":" + date.getMinutes();
			}
		}
		return "";
	}
}
