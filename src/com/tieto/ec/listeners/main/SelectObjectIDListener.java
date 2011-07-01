package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.Main;
import com.tieto.ec.gui.PickObjectIDDialog;
import com.tieto.ec.webServices.PwelDayStatusService;

import android.view.View;
import android.view.View.OnClickListener;

public class SelectObjectIDListener implements OnClickListener {

	private PickObjectIDDialog dialog;
	
	public SelectObjectIDListener(Main main) {
		dialog = new PickObjectIDDialog(main);
	}

	public void onClick(View v) {
		dialog.show();
	}
}
