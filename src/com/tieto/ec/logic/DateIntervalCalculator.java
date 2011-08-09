package com.tieto.ec.logic;

import java.util.Date;

import com.ec.prod.android.pilot.model.Resolution;

public class DateIntervalCalculator {

	/**
	 * Calculates the fromDate relative to a toDate, with the given {@link Resolution}
	 * @param toDate Given toDate
	 * @param resolution Given {@link Resolution}
	 * @return A new {@link Date} before the toDate
	 */
	public static Date calcFromDate(Date toDate, int resolution){
		switch (resolution) {
		case Resolution.DAILY:
			return toDate;
		case Resolution.WEEKLY:
			return calcFromDateWeekly(toDate);
		case Resolution.MONTHLY:
			return calcFromDateMonthly(toDate);
		case Resolution.YEARLY:
			return calcFromDateYearly(toDate);
		}
		return toDate;
	}

	/**
	 * Returns the Monday before the given toDate 
	 * @param toDate Given {@link Date}
	 * @return A new {@link Date}
	 */
	private static Date calcFromDateWeekly(Date toDate) {
		//An offset is needed to make munday the "Start of the week" day
		int offset = 1;
		//The Date class automatically sets sunday as "Start of the week", so when toDate is sunday
		//an offset of 6 days is needed
		if(toDate.getDay() == 0)
			offset = -6;
		Date date = new Date(toDate.getYear(), toDate.getMonth(), toDate.getDate()-toDate.getDay()+offset);
		return date;
	}

	/**
	 * Returns the first date of the toDate's month
	 * @param toDate given toDate
	 * @return A new {@link Date}
	 */
	private static Date calcFromDateMonthly(Date toDate) {
		Date date = new Date(toDate.getYear(), toDate.getMonth(), 1);
		return date;
	}

	/**
	 * Returns the first date and first month of the toDate's year
	 * @param toDate given toDate
	 * @return A new {@link Date}
	 */
	private static Date calcFromDateYearly(Date toDate) {
		Date date = new Date(toDate.getYear(), 0, 1);
		return date;
	}

	/**
	 * Calculates a new toDate relative to a toDate, with the given {@link Resolution}
	 * @param toDate Given toDate
	 * @param resolution Given {@link Resolution}
	 * @return A new {@link Date} before the toDate
	 */
	public static Date calcToDate(Date toDate, int resolution, boolean backward){
		switch (resolution) {
		case Resolution.DAILY:
			return toDate;
		case Resolution.WEEKLY:
			return calcToDateWeekly(toDate, backward);
		case Resolution.MONTHLY:
			return calcToDateMonthly(toDate, backward);
		case Resolution.YEARLY:
			return calcToDateYearly(toDate, backward);
		}
		return toDate;
	}

	/**
	 * Returns the previous/next Sunday
	 * @param toDate given toDate
	 * @param backward previous or next
	 * @return A new {@link Date}
	 */
	private static Date calcToDateWeekly(Date toDate, boolean backward) {
		if(backward){			
			Date newDate = new Date(toDate.getYear(), toDate.getMonth(), toDate.getDate());
			if(newDate.getDay() == 0){
				newDate.setDate(newDate.getDate()-1);				
			}
			while(newDate.getDay() != 0){
				newDate.setDate(newDate.getDate()-1);
			}
			return newDate;
		}else{
			Date newDate = new Date(toDate.getYear(), toDate.getMonth(), toDate.getDate());
			while(newDate.getDay() != 0){
				newDate.setDate(newDate.getDay()+1);
			}
			return newDate;
		}
	}

	/**
	 * Returns the last date of previous/next month
	 * @param toDate given toDate
	 * @param backward previous or next
	 * @return A new {@link Date}
	 */
	private static Date calcToDateMonthly(Date toDate, boolean backward) {
		Date newDate = new Date(toDate.getYear(), toDate.getMonth(), 15);
		if(backward){			
			while(newDate.getMonth() == toDate.getMonth()){
				newDate.setDate(newDate.getDate()-1);
			}
			return newDate;
		}else{
			newDate.setMonth(newDate.getMonth()+1);
			while(newDate.getDate() != 1){
				newDate.setDate(newDate.getDate()+1);
			}
			newDate.setDate(newDate.getDate()-1);	
			return newDate;
		}
	}

	/**
	 * Returns the last date of the previous/next year
	 * @param toDate given toDate
	 * @param backward previous or next
	 * @return A new {@link Date}
	 */
	private static Date calcToDateYearly(Date toDate, boolean backward) {
		if(backward){			
			return new Date(toDate.getYear()-1, 11, 31);
		}else{
			return new Date(toDate.getYear()+1, 11, 31);
		}
	}
}
