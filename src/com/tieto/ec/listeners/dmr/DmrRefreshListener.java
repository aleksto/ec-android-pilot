package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;

public class DmrRefreshListener implements OnDismissListener {

	private final DailyMorningReport dailyMorningReport;

	public DmrRefreshListener(DailyMorningReport dailyMorningReport) {
		this.dailyMorningReport = dailyMorningReport;
	}

	public void onDismiss(DialogInterface arg0) {
		dailyMorningReport.refresh();
		Log.d("tieto", "Dialog dismissed");
	}
}
