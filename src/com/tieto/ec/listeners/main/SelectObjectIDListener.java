package com.tieto.ec.listeners.main;

import com.tieto.ec.activities.WellPeriod;
import com.tieto.ec.gui.dialogs.PickObjectIDDialog;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

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
