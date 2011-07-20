package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.FileManager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ChooseButtonListener implements OnClickListener {

	private final Context context;
	private final String basePath;
	private final String optionText;
	private final OptionDialog parent;

	public ChooseButtonListener(OptionDialog parent, Context context, String basePath, String optionText) {
		this.context = context;
		this.parent = parent;
		this.basePath = basePath;
		this.optionText = optionText;
	}

	public void onClick(View arg0) {
		FileManager.writePath(context, basePath, optionText);
		parent.dismiss();
		Log.d("tieto", "Writing value: \"" + optionText + "\" to path: " + basePath);
	}
}
