package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.WellPeriod;
import com.tieto.ec.gui.PickObjectIDDialog;
import com.tieto.ec.webServices.PwelDayStatusService;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;

public class SelectObjectIDListener implements OnMenuItemClickListener {

	private PickObjectIDDialog dialog;
	
	public SelectObjectIDListener(WellPeriod main) {
		dialog = new PickObjectIDDialog(main);
	}

	public boolean onMenuItemClick(MenuItem item) {
		dialog.show();
		return false;
	}
}
