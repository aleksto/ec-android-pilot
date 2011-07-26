package com.tieto.ec.listeners.dialogs;

import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.tieto.ec.logic.FileManager;

public class SeekBarChangeListener implements OnSeekBarChangeListener{

	private Context context;
	private int value;
	private String path, title;
	private TextView titleField;
	private final int minVal;
	
	/**
	 * Creates a {@link OnSeekBarChangeListener} for a {@link SeekBar}
	 * @param context {@link Context} needed for Android framework actions
	 * @param maxVal Max value for the {@link SeekBar}
	 * @param minVal Min value for the {@link SeekBar}
	 * @param titleField Title field for the {@link SeekBar}
	 * @param path Path for file for writing
	 */
	public SeekBarChangeListener(Context context, int maxVal, int minVal, TextView titleField, String path) {
		this.context = context;
		this.minVal = minVal;
		this.titleField = titleField;
		this.path = path;
		
		int idx = path.lastIndexOf(".") + 1;
		title = path.substring(idx);
	}

	/**
	 * Called when {@link SeekBar} have changed progress
	 * This method sets the value of the {@link SeekBar} to its title field
	 */
	public void onProgressChanged(SeekBar arg0, int val, boolean arg2) {
		titleField.setText(title + ": " + Integer.toString(val + minVal));
		value = val + minVal;
	}

	/**
	 * Not used
	 */
	public void onStartTrackingTouch(SeekBar arg0) {}

	/**
	 * Called when user is done changing the value of the {@link SeekBar}
	 * This method gets the current value, and writes the value to given path
	 */
	public void onStopTrackingTouch(SeekBar arg0) {
		Log.d("tieto", "Writing value: " + value + " to path: " + path);
		FileManager.writePath(context, path, Integer.toString(value));
	}
}
