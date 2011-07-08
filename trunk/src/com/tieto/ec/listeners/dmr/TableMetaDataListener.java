package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.TableMetaDataDialog;
import com.tieto.frmw.model.TableData;

import android.view.View;
import android.view.View.OnClickListener;

public class TableMetaDataListener implements OnClickListener{

	private TableMetaDataDialog dialog;
	
	public TableMetaDataListener(DailyMorningReport dailyMorningReport, String title, TableData data){
		dialog = new TableMetaDataDialog(dailyMorningReport, title, data);
	}
	
	public void onClick(View v) {
		dialog.updateCheckBoxes();
		dialog.show();
	}
}
