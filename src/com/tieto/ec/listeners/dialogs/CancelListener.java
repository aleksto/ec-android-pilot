package com.tieto.ec.listeners.dialogs;

import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tieto.ec.logic.FileManager;

public class CancelListener implements OnClickListener{

	private Dialog dialog;
	private String[] previousValues;
	private final String[] titles;
	private final Context context;
	private final String path;
	
	/**
	 * Creates a {@link OnClickListener} for a cancel {@link Button}
	 * @param dialog The parent {@link Dialog} to hide when clicked
	 * @param context {@link Context} needed for Android framework actions
	 * @param path {@link String} path to files to restore values when clicked
	 * @param titles Titles of the files
	 */
	public CancelListener(Dialog dialog, Context context, String path, String ... titles) {
		this.dialog = dialog;
		this.context = context;
		this.path = path;
		this.titles = titles;
		previousValues = new String[titles.length];
		
		for(int i = 0; i<titles.length; i++) {
			try {
				previousValues[i] = FileManager.readPath(context, path + "." + titles[i]);
			} catch (NumberFormatException e) {
				previousValues[i] = "";
				e.printStackTrace();
			} catch (IOException e) {
				previousValues[i] = "";
				e.printStackTrace();
			}
		}
	}

	/**
	 * Restores given files when clicked
	 */
	public void onClick(View arg0) {
		dialog.hide();
		
		for (int i = 0; i < titles.length; i++) {
			Log.d("tieto", "Restoring to: " + previousValues[i] + " at path: " + path + "." + titles[i]);
			FileManager.writePath(context, path + "." + titles[i], previousValues[i]);
		}
	}
}
