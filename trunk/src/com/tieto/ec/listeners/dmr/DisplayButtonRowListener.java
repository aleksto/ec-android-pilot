package com.tieto.ec.listeners.dmr;

import android.view.View;
import android.view.View.OnLongClickListener;

import com.tieto.ec.activities.DailyMorningReport;

public class DisplayButtonRowListener implements OnLongClickListener{

	private final DailyMorningReport dmr;

	public DisplayButtonRowListener(DailyMorningReport dmr){
		this.dmr = dmr;
	}
	
	public boolean onLongClick(View arg0) {
		dmr.toogleSubButtonRow();
		return true;
	}
}
