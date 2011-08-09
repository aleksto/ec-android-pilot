package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.ChooseSectionsToSendDialog;

public class SendWarningsByMailListener implements OnMenuItemClickListener {

	private DailyMorningReport dailyMorningReport;

	/**
	 * This class is for showing the dialog where the yser chooses which sections they want to send (export)
	 * @param dailyMorningReport
	 */
	public SendWarningsByMailListener(DailyMorningReport dailyMorningReport) {
		this.dailyMorningReport = dailyMorningReport;
	}

	/**
	 * Method for showing the dialog where the user chooses which section they want to send
	 */
	public boolean onMenuItemClick(MenuItem arg0) {
		
		ChooseSectionsToSendDialog chooseSectionsDialog = new ChooseSectionsToSendDialog(dailyMorningReport);
		chooseSectionsDialog.show();
	
		
		return false;
	}

}
