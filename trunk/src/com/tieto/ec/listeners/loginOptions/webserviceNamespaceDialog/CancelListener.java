package com.tieto.ec.listeners.loginOptions.webserviceNamespaceDialog;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class CancelListener implements OnClickListener{

	private Dialog dialog;
	
	public CancelListener(Dialog dialog) {
		this.dialog = dialog;
	}

	public void onClick(View v) {
		dialog.hide();
	}
}
