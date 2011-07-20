package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.gui.dialogs.EditTextDialog;
import com.tieto.ec.gui.dialogs.OptionDialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class EditButtonListener implements OnClickListener {

	private EditTextDialog dialog;
	
	public EditButtonListener(OptionDialog optionDialog, Context context, String basePath, String title) {
		dialog = new EditTextDialog(context, optionDialog, basePath, title);
	}

	public void onClick(View arg0) {
		dialog.show();
	}
}
