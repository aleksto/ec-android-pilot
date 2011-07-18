package com.tieto.ec.listeners.loginOptions;

import com.tieto.ec.gui.dialogs.WebsericeNamespaceDialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class NamespaceButtonsListener implements OnClickListener {

	private WebsericeNamespaceDialog dialog;
	
	public NamespaceButtonsListener(Context context){
		dialog = new WebsericeNamespaceDialog(context);
	}

	public void onClick(View v) {
		dialog.show();
	}
}
