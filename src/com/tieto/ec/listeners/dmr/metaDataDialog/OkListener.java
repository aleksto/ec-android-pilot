package com.tieto.ec.listeners.dmr.metaDataDialog;

import com.tieto.ec.activities.DailyMorningReport;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class OkListener implements OnClickListener {

	private Dialog dialog;
	private DailyMorningReport dailyMorningReport;
	
	public OkListener(DailyMorningReport dailyMorningReport, Dialog dialog) {
		this.dailyMorningReport = dailyMorningReport;
		this.dialog = dialog;
	}

	public void onClick(View v) {
		dailyMorningReport.listSections();
		dialog.hide();
	}
}
