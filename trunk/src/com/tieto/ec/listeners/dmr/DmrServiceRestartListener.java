package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

public class DmrServiceRestartListener implements OnDismissListener {

	private final DailyMorningReport dailyMorningReport;

	public DmrServiceRestartListener(DailyMorningReport dailyMorningReport) {
		this.dailyMorningReport = dailyMorningReport;
	}

	public void onDismiss(DialogInterface arg0) {
		dailyMorningReport.restartService();
	}
}
