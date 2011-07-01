package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.Main;
import com.tieto.ec.gui.PickPeriodDialog;
import com.tieto.ec.webServices.PwelDayStatusService;

import android.view.View;
import android.view.View.OnClickListener;

public class SelectPeriodListener implements OnClickListener {
	
	private PickPeriodDialog dialog;	
	
	public SelectPeriodListener(Main main) {
		dialog = new PickPeriodDialog(main);
	}

	public void onClick(View v) {
		dialog.show();
		

	}

}
