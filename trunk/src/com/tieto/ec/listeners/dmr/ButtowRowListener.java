package com.tieto.ec.listeners.dmr;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.ResolutionConverter;

public class ButtowRowListener implements OnClickListener{

	private final DailyMorningReport dmr;
	private final int resolution;

	public ButtowRowListener(DailyMorningReport dmr, int resolution){
		this.dmr = dmr;
		this.resolution = resolution;
	}
	
	public void onClick(View arg0) {
		Log.d("tieto", "Writing: " + ResolutionConverter.convert(resolution) + " to path: DMR Report.Resolution");
		FileManager.writePath(dmr, "DMR Report.Resolution", resolution+"");
		dmr.setResolution(resolution);
		dmr.toogleSubButtonRow();
	}
}
