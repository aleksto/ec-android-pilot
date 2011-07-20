package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;

import android.R.dimen;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;

public class OnMetadataDismissListener implements OnDismissListener {

	private final DailyMorningReport dailyMorningReport;

	public OnMetadataDismissListener(DailyMorningReport dailyMorningReport) {
		this.dailyMorningReport = dailyMorningReport;
	}

	public void onDismiss(DialogInterface arg0) {
		dailyMorningReport.listSections();
		Log.d("tieto", "Metadata dialog dismissed");
	}
}
