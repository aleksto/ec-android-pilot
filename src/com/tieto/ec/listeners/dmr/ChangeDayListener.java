package com.tieto.ec.listeners.dmr;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.logic.DateIntervalCalculator;

public class ChangeDayListener implements OnClickListener, OnDateSetListener{
	
	public enum Action{
		NEXT_DAY, PREVIOUS_DAY, CHOOSE_DAY
	}
	
	private DatePickerDialog dialog;
	private final DailyMorningReport dmr;
	private Action action;
	private Date date;

	/**
	 * Creates a new {@link OnClickListener} for changing day. Changing day depends on given {@link Action}
	 * NEXT_DAY Increases date with one day, PREVIOUS_DAY decreases date with one day and CHOOSE_DAY displays
	 * a {@link DatePickerDialog} for choosing a new {@link Date}
	 * @param dmr {@link DailyMorningReport}
	 * @param action {@link Action} action type
	 */
	public ChangeDayListener(DailyMorningReport dmr, Action action){
		this.dmr = dmr;
		this.action = action;
	}
	
	/**
	 * Increases, decreases or displays {@link DatePickerDialog}
	 * and refreshes the report 
//	 */
	public void onClick(View arg0) {
		Log.d("tieto", "ChangeDayListener: onClick()");
		switch (action) {
		case NEXT_DAY:
			date = dmr.getToDate();
			dmr.setToDate(DateIntervalCalculator.calcToDate(date, dmr.getResolution(), false));	
			break;
		case PREVIOUS_DAY:
			date = dmr.getToDate();
			dmr.setToDate(DateIntervalCalculator.calcToDate(date, dmr.getResolution(), true));	
			break;
		case CHOOSE_DAY:
			date = dmr.getToDate();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			
			String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
			String monthOfYear = getMonthOfYear(c.get(Calendar.MONDAY));
			
			dialog = new DatePickerDialog(dmr, this, date.getYear()+1900, date.getMonth(), date.getDate());
			dialog.setTitle(dayOfWeek + ", " + monthOfYear + " " + c.get(Calendar.DATE) + ", " + c.get(Calendar.YEAR));
			dialog.show();
			break;
		}
	}

	private String getMonthOfYear(int monthOfYear) {
		if(monthOfYear == 0)
			return "January";
		if(monthOfYear == 1)
			return "February";
		if(monthOfYear == 2)
			return "March";
		if(monthOfYear == 3)
			return "April";
		if(monthOfYear == 4)
			return "May";
		if(monthOfYear == 5)
			return "June";
		if(monthOfYear == 6)
			return "July";
		if(monthOfYear == 7)
			return "August";
		if(monthOfYear == 8)
			return "September";
		if(monthOfYear == 9)
			return "October";
		if(monthOfYear == 10)
			return "November";
		if(monthOfYear == 11)
			return "December";
		return null;
		
	}

	private String getDayOfWeek(int dayOfWeek) {
		if(dayOfWeek == 1)
			return "Sunday";
		if(dayOfWeek == 2)
			return "Monday";
		if(dayOfWeek == 3)
			return "Tuesday";
		if(dayOfWeek == 4)
			return "Wednsday";
		if(dayOfWeek == 5)
			return "Thursday";
		if(dayOfWeek == 6)
			return "Friday";
		if(dayOfWeek == 7)
			return "Saturday";		
		return null;
	}

	/**
	 * Sets new {@link Date} in the report when user clicks ok at {@link DatePickerDialog}
	 */
	public void onDateSet(DatePicker arg0, int year, int month, int day) {
		dmr.setToDate(new Date(year-1900, month, day));
	}
}
