package com.tieto.ec.listeners.dmr;

import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

import com.tieto.ec.activities.DailyMorningReport;

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
	 */
	public void onClick(View arg0) {
		switch (action) {
		case NEXT_DAY:
			date = dmr.getDate();
			date.setDate(date.getDate()+1);
			dmr.setDate(date);			
			break;
		case PREVIOUS_DAY:
			date = dmr.getDate();
			date.setDate(date.getDate()-1);
			dmr.setDate(date);
			break;
		case CHOOSE_DAY:
			date = dmr.getDate();
			dialog = new DatePickerDialog(dmr, this, date.getYear()+1900, date.getMonth(), date.getDate());
			dialog.show();
			break;
		}
		
		dmr.refresh();
	}

	/**
	 * Sets new {@link Date} in the report when user clicks ok at {@link DatePickerDialog}
	 */
	public void onDateSet(DatePicker arg0, int year, int month, int day) {
		dmr.setDate(new Date(year-1900, month, day));
		dmr.refresh();
	}
}
