package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.WellPeriod;
import com.tieto.ec.gui.DataDialog;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class SelectDataListener implements OnMenuItemClickListener {

	private DataDialog dataDialog;
	private WellPeriod main;
	
	public SelectDataListener(WellPeriod main) {
		dataDialog = new DataDialog(main);
		this.main = main;
	}

	public boolean onMenuItemClick(MenuItem item) {
		dataDialog.show();
		dataDialog.setData(main.getData("opProductionunitCode", "opAreaCode", "opFcty1Code", "objectCode"));
		return false;
	}
}