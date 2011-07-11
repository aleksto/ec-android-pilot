package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.TableMetaDataDialog;
import com.ec.prod.android.pilot.model.TableData;

import android.view.View;
import android.view.View.OnLongClickListener;

public class TableMetaDataListener implements OnLongClickListener{

	private TableMetaDataDialog dialog;
	
	public TableMetaDataListener(DailyMorningReport dailyMorningReport, String title, TableData data){
		dialog = new TableMetaDataDialog(dailyMorningReport, title, data);
	}
	
	public boolean onLongClick(View v) {
		dialog.updateCheckBoxes();
		dialog.show();
		return false;
	}
}
