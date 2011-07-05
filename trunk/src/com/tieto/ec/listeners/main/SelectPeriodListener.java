package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.WellPeriod;
import com.tieto.ec.gui.PickPeriodDialog;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class SelectPeriodListener implements OnMenuItemClickListener {
	
	private PickPeriodDialog dialog;	
	
	public SelectPeriodListener(WellPeriod main) {
		dialog = new PickPeriodDialog(main);
	}

	public boolean onMenuItemClick(MenuItem item) {
		dialog.show();
		return false;
	}

}
