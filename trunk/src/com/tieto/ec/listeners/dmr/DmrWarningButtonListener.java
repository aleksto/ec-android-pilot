package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;

public class DmrWarningButtonListener implements OnMenuItemClickListener {

	private final DailyMorningReport dmr;

	/**
	 * This class is a listener for showing the Daily Morning Report Warning
	 * dialog when it is selected in the menu
	 * @param dmr
	 */
	public DmrWarningButtonListener(DailyMorningReport dmr) {
		this.dmr = dmr;
	}

	/**
	 * This is the actual method for showing the Daily Morning Report Warning dialog
	 */
	public boolean onMenuItemClick(MenuItem arg0) {
		dmr.getWarningDialog().show();
		return false;
	}
}
