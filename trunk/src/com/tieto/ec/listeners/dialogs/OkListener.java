package com.tieto.ec.listeners.dialogs;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import com.tieto.ec.gui.dialogs.OptionDialog;

public class OkListener implements OnClickListener {

	private final Dialog dialog;
	private final OptionDialog optionDialog;

	/**
	 * Creates a {@link OnClickListener} for a Ok Button
	 * @param editTextDialog Parent {@link Dialog} to hide when click
	 * @param optionDialog {@link OptionDialog} to refresh when click
	 */
	public OkListener(Dialog dialog, OptionDialog optionDialog) {
		this.dialog = dialog;
		this.optionDialog = optionDialog;
	}
	
	/**
	 * Called when user click the button
	 */
	public void onClick(View arg0) {
		optionDialog.refresh();
		dialog.hide();
	}
}
