package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.logic.FileManager;

import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SeekBarChangeListener implements OnSeekBarChangeListener{

	private Context context;
	private int value;
	private String path, title;
	private TextView titleField;
	private final int minVal;
	
	public SeekBarChangeListener(Context context, int maxVal, int minVal, TextView titleField, String path) {
		this.context = context;
		this.minVal = minVal;
		this.titleField = titleField;
		this.path = path;
		
		int idx = path.lastIndexOf(".") + 1;
		title = path.substring(idx);
	}

	public void onProgressChanged(SeekBar arg0, int val, boolean arg2) {
		titleField.setText(title + ": " + Integer.toString(val + minVal));
		value = val + minVal;
	}

	public void onStartTrackingTouch(SeekBar arg0) {}

	public void onStopTrackingTouch(SeekBar arg0) {
		Log.d("tieto", "Writing value: " + value + " to path: " + path);
		FileManager.writePath(context, path, Integer.toString(value));
	}
}
