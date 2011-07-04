package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.Main;
import com.tieto.ec.gui.DataDialog;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class SelectDataListener implements OnMenuItemClickListener {

	private DataDialog dataDialog;
	private Main main;
	
	public SelectDataListener(Main main) {
		dataDialog = new DataDialog(main);
		this.main = main;
	}

	public boolean onMenuItemClick(MenuItem item) {
		dataDialog.show();
		dataDialog.setData(main.getData("opProductionunitCode", "opAreaCode", "opFcty1Code", "objectCode"));
		return false;
	}
}