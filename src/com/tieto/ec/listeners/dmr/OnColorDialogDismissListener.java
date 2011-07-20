package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

public class OnColorDialogDismissListener implements OnDismissListener {

	private final DailyMorningReport dmr;

	public OnColorDialogDismissListener(DailyMorningReport dmr){
		this.dmr = dmr;
	}
	
	public void onDismiss(DialogInterface arg0) {
		dmr.refresh();
	}
}
