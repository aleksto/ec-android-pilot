package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;

import android.view.View;
import android.view.View.OnLongClickListener;

public class TableOptionDialogListener implements OnLongClickListener{

	private OptionDialog tableDialog;
	
	public TableOptionDialogListener(DailyMorningReport dailyMorningReport, String title, TableData data){		
		//Section
		tableDialog = new OptionDialog(dailyMorningReport, title);
		OptionDialog visibleLines = new OptionDialog(dailyMorningReport, OptionTitle.VisibleColumns);
		OptionDialog warningLevels = new OptionDialog(dailyMorningReport, OptionTitle.WarningLevels);
		
		//Sub option dialogs
		for (TableColumn column : data.getTableColumns()) {
			visibleLines.addOptionRow(column.getHeader(), OptionRowType.CHECK_BOX);
			warningLevels.addOptionRow(column.getHeader(), OptionRowType.EDIT_BUTTON);
		}
		
		//Main Dialog
		tableDialog.addOptionRow(OptionTitle.VisibleColumns, OptionRowType.NONE);
		tableDialog.addOptionRow(OptionTitle.WarningLevels, OptionRowType.NONE);
		
		//Childs
		tableDialog.addChild(visibleLines);
		tableDialog.addChild(warningLevels);
		
		//Dialog
		visibleLines.setOnDismissListener(new DmrRefreshListener(dailyMorningReport));
	}
	
	public boolean onLongClick(View v) {
		tableDialog.show();
		return false;
	}
}
