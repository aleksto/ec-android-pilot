package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.logic.FileManager;

import android.content.Context;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckBoxChangeListener implements OnCheckedChangeListener {

	private final Context context;
	private final String path;

	public CheckBoxChangeListener(Context context, String path) {
		this.context = context;
		this.path = path;
	}

	public void onCheckedChanged(CompoundButton view, boolean val) {
		Log.d("tieto", "Writing value: " + val + " to path: " + path);
		FileManager.writePath(context, path, Boolean.toString(val));
	}
}
