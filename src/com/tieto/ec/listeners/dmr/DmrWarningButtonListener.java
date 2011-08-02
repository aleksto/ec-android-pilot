package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;

public class DmrWarningButtonListener implements OnMenuItemClickListener {

	private final DailyMorningReport dmr;

	public DmrWarningButtonListener(DailyMorningReport dmr) {
		this.dmr = dmr;
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		dmr.getWarningDialog().show();
		return false;
	}
}
