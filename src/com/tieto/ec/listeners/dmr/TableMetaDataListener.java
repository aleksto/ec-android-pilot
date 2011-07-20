package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;

import android.view.View;
import android.view.View.OnLongClickListener;

public class TableMetaDataListener implements OnLongClickListener{

	private OptionDialog optionDialog;
	
	public TableMetaDataListener(DailyMorningReport dailyMorningReport, String title, TableData data){		
		//Section
		optionDialog = new OptionDialog(dailyMorningReport, title);
		
		//Options
		for (TableColumn column : data.getTableColumns()) {
			optionDialog.addOption(column.getHeader(), OptionRowType.CHECK_BOX);
		}
		
		//Dialog
		optionDialog.setOnDismissListener(new DmrRefreshListener(dailyMorningReport));
	}
	
	public boolean onLongClick(View v) {
		optionDialog.show();
		return false;
	}
}
