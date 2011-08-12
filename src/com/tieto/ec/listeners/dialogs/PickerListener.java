package com.tieto.ec.listeners.dialogs;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ec.prod.android.pilot.model.Resolution;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.DateConverter;
import com.tieto.ec.logic.DateConverter.Type;
import com.tieto.ec.logic.FileManager;

public class PickerListener implements OnDateSetListener, OnTimeSetListener {

	private final String path;
	private final OptionDialog optionDialog;
	private final TextView subText;

	public PickerListener(OptionDialog optionDialog, String path, TextView subText) {
		this.optionDialog = optionDialog;
		this.path = path;
		this.subText = subText;
		
	}

	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		FileManager.writePath(optionDialog.getContext(), path, DateConverter.parse(new Date(year, monthOfYear, dayOfMonth), Type.DATE, Resolution.DAILY));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Calendar c = Calendar.getInstance();
		Date date = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), hourOfDay, minute, c.get(Calendar.SECOND));
		FileManager.writePath(optionDialog.getContext(), path, DateConverter.parse(date, Type.TIME, Resolution.DAILY));	
		subText.setText(DateConverter.parse(date, Type.TIME, Resolution.DAILY));
		optionDialog.refresh();
		Log.d("tieto", "WRITING TO: " + path);
	}
}