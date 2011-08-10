package com.tieto.ec.logic;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class ChooseDate extends DatePickerDialog {

	public ChooseDate(Context context, Date date, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		String dayOfTheWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
		String monthOfTheYear = getMonthOfYear(c.get(Calendar.MONTH));
		
		setTitle(dayOfTheWeek + ", " + monthOfTheYear + " " + c.get(Calendar.DATE) + ", " + c.get(Calendar.YEAR));	
	}
	
	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		Date date = new Date(year, month, day-1);
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		String dayOfTheWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
		String monthOfTheYear = getMonthOfYear(c.get(Calendar.MONTH));
		
		setTitle(dayOfTheWeek + ", " + monthOfTheYear + " " + day + ", " + year);
	}
		
	public String getMonthOfYear(int monthOfYear) {
		if(monthOfYear == Calendar.JANUARY)
			return "January";
		if(monthOfYear == Calendar.FEBRUARY)
			return "February";
		if(monthOfYear == Calendar.MARCH)
			return "March";
		if(monthOfYear == Calendar.APRIL)
			return "April";
		if(monthOfYear == Calendar.MAY)
			return "May";
		if(monthOfYear == Calendar.JUNE)
			return "June";
		if(monthOfYear == Calendar.JULY)
			return "July";
		if(monthOfYear == Calendar.AUGUST)
			return "August";
		if(monthOfYear == Calendar.SEPTEMBER)
			return "September";
		if(monthOfYear == Calendar.OCTOBER)
			return "October";
		if(monthOfYear == Calendar.NOVEMBER)
			return "November";
		if(monthOfYear == Calendar.DECEMBER)
			return "December";
		return null;
		
	}

	public String getDayOfWeek(int dayOfWeek) {
		if(dayOfWeek == Calendar.SUNDAY)
			return "Sunday";
		if(dayOfWeek == Calendar.MONDAY)
			return "Monday";
		if(dayOfWeek == Calendar.TUESDAY)
			return "Tuesday";
		if(dayOfWeek == Calendar.WEDNESDAY)
			return "Wednsday";
		if(dayOfWeek == Calendar.THURSDAY)
			return "Thursday";
		if(dayOfWeek == Calendar.FRIDAY)
			return "Friday";
		if(dayOfWeek == Calendar.SATURDAY)
			return "Saturday";		
		return null;
	}
	


}
