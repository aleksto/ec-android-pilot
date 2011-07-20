package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.gui.dialogs.EditTextDialog;
import com.tieto.ec.gui.dialogs.OptionDialog;

import android.view.View;
import android.view.View.OnClickListener;

public class OkListener implements OnClickListener {

	private final EditTextDialog editTextDialog;
	private final OptionDialog optionDialog;

	public OkListener(EditTextDialog editTextDialog, OptionDialog optionDialog) {
		this.editTextDialog = editTextDialog;
		this.optionDialog = optionDialog;
	}

	public void onClick(View arg0) {
		optionDialog.refresh();
		editTextDialog.hide();
	}
}
