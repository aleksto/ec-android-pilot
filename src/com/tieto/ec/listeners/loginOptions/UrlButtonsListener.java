package com.tieto.ec.listeners.loginOptions;

import com.tieto.ec.gui.dialogs.WebserviceUrlDialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class UrlButtonsListener implements OnClickListener {

	private WebserviceUrlDialog dialog;
	
	public UrlButtonsListener(Context context) {
		dialog = new WebserviceUrlDialog(context);
	}

	public void onClick(View v) {
		dialog.show();
	}
}
