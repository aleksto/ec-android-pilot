package com.tieto.ec.listeners.dialogs;

import java.util.Date;

import com.ec.prod.android.pilot.client.WebserviceDateConverter;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.FileManager;

import android.app.DatePickerDialog.OnDateSetListener;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerSetListener implements OnDateSetListener {

	private final String path;
	private final OptionDialog dialog;

	public DatePickerSetListener(OptionDialog dialog, String path) {
		this.dialog = dialog;
		this.path = path;
	}

	public void onDateSet(DatePicker arg0, int year, int month, int day) {
		Log.d("tieto", "Writing date " + WebserviceDateConverter.parse(new Date(year-1900, month, day)) + " to path: " + path);
		FileManager.writePath(dialog.getContext(), path, WebserviceDateConverter.parse(new Date(year-1900, month, day)));
		dialog.refresh();
	}
}
