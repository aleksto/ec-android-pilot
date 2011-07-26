package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.service.EcService;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

public class DmrServiceRestartListener implements OnDismissListener {

	private final DailyMorningReport dailyMorningReport;

	/**
	 * Creates a {@link OnDismissListener} for a {@link Dialog}
	 * This restarts the {@link EcService} when dialog is closed/dismissed
	 * @param dailyMorningReport
	 */
	public DmrServiceRestartListener(DailyMorningReport dailyMorningReport) {
		this.dailyMorningReport = dailyMorningReport;
	}

	/**
	 * Runs when the {@link Dialog} is closed/dismissed
	 */
	public void onDismiss(DialogInterface arg0) {
		dailyMorningReport.restartService();
	}
}
