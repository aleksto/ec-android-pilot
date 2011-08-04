package com.tieto.ec.logic;

import java.io.ObjectInputStream.GetField;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

import com.ec.prod.android.pilot.model.Resolution;
import com.tieto.ec.logic.DateConverter.Type;

public class DateIntervalCalculator {

	public static Date calcFromDate(Date toDate, int resolution){
		Log.d("tieto", "Calculating with toDate: " + DateConverter.parse(toDate, Type.DATE));
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

	private static Date calcFromDateWeekly(Date toDate) {
		//An offset is needed to make munday the "Start of the week" day
		int offset = 1;
		//The Date class automatically sets sunday as "Start of the week", so when toDate is sunday
		//an offset of 6 days is needed
		if(toDate.getDay() == 0)
			offset = -6;
		Date date = new Date(toDate.getYear(), toDate.getMonth(), toDate.getDate()-toDate.getDay()+offset);
		Log.d("tieto", "Fromdate: " + date + " ");
		return date;
	}


	private static Date calcFromDateMonthly(Date toDate) {
		Date date = new Date(toDate.getYear(), toDate.getMonth(), 1);
		Log.d("tieto", "Fromdate: " + DateConverter.parse(date, Type.DATE));
		return date;
	}

	private static Date calcFromDateYearly(Date toDate) {
		Date date = new Date(toDate.getYear(), 0, 1);
		Log.d("tieto", "Fromdate: " + DateConverter.parse(date, Type.DATE));
		return date;
	}
}
