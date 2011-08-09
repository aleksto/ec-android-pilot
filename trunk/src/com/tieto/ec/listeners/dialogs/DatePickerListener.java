package com.tieto.ec.listeners.dialogs;

import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

import com.ec.prod.android.pilot.model.Resolution;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.DateConverter;
import com.tieto.ec.logic.DateConverter.Type;
import com.tieto.ec.logic.FileManager;

public class DatePickerListener implements OnDateSetListener {

	private final String path;
	private final OptionDialog optionDialog;

	public DatePickerListener(OptionDialog optionDialog, String path) {
		this.optionDialog = optionDialog;
		this.path = path;
		
	}

	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		FileManager.writePath(optionDialog.getContext(), path, DateConverter.parse(new Date(year, monthOfYear, dayOfMonth), Type.DATE, Resolution.DAILY));
	}
}