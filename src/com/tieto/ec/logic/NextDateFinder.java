package com.tieto.ec.logic;

import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.tieto.ec.enums.Days;

public class NextDateFinder {

	/**
	 * Finds the next {@link Days}
	 * @param day Given {@link Days}
	 * @param time Time 
	 * @return A new {@link Date}
	 */
	public static Date findNextDate(Days day, Date time){
		switch (day) {
		case Monday:
			return check(time, Calendar.MONDAY);
		case Tuesday:
			return check(time, Calendar.TUESDAY);
		case Wednsday:
			return check(time, Calendar.WEDNESDAY);
		case Thursday:
			return check(time, Calendar.THURSDAY);
		case Friday:
			return check(time, Calendar.FRIDAY);
		case Saturday:
			return check(time, Calendar.SATURDAY);
		case Sunday:
			return check(time, Calendar.SUNDAY);
		}
		 
		return null;
	}
	
	/**
	 * Finds the next day from provided dayToCheck
	 * @param time Time of day
	 * @param dayToCheck Given day
	 * @return A new {@link Date}
	 */
	private static Date check(Date time, int dayToCheck){
		Calendar cal = Calendar.getInstance();
		
		//An offset is needed to make monday the "Start of the week" day
		int offset = 0;
		//The Date class automatically sets sunday as "Start of the week", so when toDate is sunday
		//an offset of 6 days is needed
		if(cal.get(Calendar.DAY_OF_WEEK) == 0){
			offset = -6;			
		}
		
		if(cal.get(Calendar.HOUR_OF_DAY) > time.getHours()){
			cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
		}else if(cal.get(Calendar.HOUR_OF_DAY) ==  time.getHours() && cal.get(Calendar.MINUTE) >= time.getMinutes()){
			cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
		}
		
		while(cal.get(Calendar.DAY_OF_WEEK)+offset != dayToCheck){
			cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
		}
		
		cal.set(Calendar.HOUR_OF_DAY, time.getHours());
		cal.set(Calendar.MINUTE, time.getMinutes());
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
}
