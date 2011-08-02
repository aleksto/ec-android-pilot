package com.tieto.ec.listeners.dmr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;

import com.tieto.ec.activities.DailyMorningReport;

public class DmrRefreshListener implements OnDismissListener {

	private final DailyMorningReport dailyMorningReport;

	/**
	 * Creates a {@link OnDismissListener} for a {@link Dialog}
	 * This refreshes the {@link DailyMorningReport} when dialog is closed/dismissed
	 * @param dailyMorningReport
	 */
	public DmrRefreshListener(DailyMorningReport dailyMorningReport) {
		this.dailyMorningReport = dailyMorningReport;
	}

	/**
	 * Runs when the {@link Dialog} is closed/dismissed
	 */
	public void onDismiss(DialogInterface arg0) {
		dailyMorningReport.refresh(false);
		Log.d("tieto", "Dialog dismissed");
	}
}
