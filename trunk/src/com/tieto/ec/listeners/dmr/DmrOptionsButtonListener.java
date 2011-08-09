package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.OptionBuilder;

public class DmrOptionsButtonListener implements OnMenuItemClickListener {

	private OptionDialog root;
	private final DailyMorningReport dailyMorningReport;

	/**
	 * Creates a new {@link OnMenuItemClickListener} for {@link DailyMorningReport} option button
	 * for displaying a {@link OptionDialog}
	 * @param dmr {@link DailyMorningReport}
	 */
	public DmrOptionsButtonListener(DailyMorningReport dmr){
		this.dailyMorningReport = dmr;
		root = OptionBuilder.buildDailyMorningReportOption(dailyMorningReport);

	}

	/**
	 * Runs when the user clicks the {@link MenuItem} with this {@link OnMenuItemClickListener} attached
	 * This method builds up the dialogs
	 */
	public boolean onMenuItemClick(MenuItem arg0) {
		root.show();
		return false;
	}


	
}
