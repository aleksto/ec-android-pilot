package com.tieto.ec.listeners.dialogs;

import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.graphics.Path;
import android.util.Log;
import android.widget.DatePicker;

import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.DateConverter;

public class DatePickerListener implements OnDateSetListener {

	private final String path;
	private final OptionDialog dialog;

	/**
	 * Creates a new {@link OnDateSetListener} for a {@link DatePicker}
	 * @param dialog Parent {@link Dialog}
	 * @param path Path to the file to write date
	 */
	public DatePickerListener(OptionDialog dialog, String path) {
		this.dialog = dialog;
		this.path = path;
	}

	/**
	 * Writes the given date to {@link Path}
	 * @param arg0 The {@link DatePicker}
	 * @param year Year
	 * @param month Month
	 * @param day Day
	 */
	public void onDateSet(DatePicker arg0, int year, int month, int day) {
		Log.d("tieto", "Writing date " + DateConverter.parse(new Date(year-1900, month, day), DateConverter.Type.DATE) + " to path: " + path);
		FileManager.writePath(dialog.getContext(), path, DateConverter.parse(new Date(year-1900, month, day), DateConverter.Type.DATE));
		dialog.refresh();
	}
}
