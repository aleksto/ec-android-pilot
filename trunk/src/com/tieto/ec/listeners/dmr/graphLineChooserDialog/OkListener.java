package com.tieto.ec.listeners.dmr.graphLineChooserDialog;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class OkListener implements OnClickListener {

	private Dialog dialog;
	
	public OkListener(Dialog dialog) {
		this.dialog = dialog;
	}

	public void onClick(View v) {
		dialog.hide();
	}
}