package com.tieto.ec.listeners.dialogs;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class HideDialogListener implements OnClickListener{

	private final Dialog dialog;

	public HideDialogListener(Dialog dialog){
		this.dialog = dialog;
	}
	
	public void onClick(View v) {
		dialog.dismiss();
	}
}
