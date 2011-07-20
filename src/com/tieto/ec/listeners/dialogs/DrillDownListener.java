package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.DialogSection;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class DrillDownListener implements OnClickListener {

	private OptionDialog dialog;

	public DrillDownListener(Context context, DialogSection child) {
		dialog = new OptionDialog(context, child);
	}

	public void onClick(View arg0) {
		dialog.show();
	}
}
