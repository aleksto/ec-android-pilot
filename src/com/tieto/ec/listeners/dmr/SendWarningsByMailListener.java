package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.ChooseSectionsDialog;

public class SendWarningsByMailListener implements OnMenuItemClickListener {

	private DailyMorningReport dailyMorningReport;

	public SendWarningsByMailListener(DailyMorningReport dailyMorningReport) {
		this.dailyMorningReport = dailyMorningReport;
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		
		ChooseSectionsDialog chooseSectionsDialog = new ChooseSectionsDialog(dailyMorningReport);
		chooseSectionsDialog.show();
	
		
		return false;
	}

}
