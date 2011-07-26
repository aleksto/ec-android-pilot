package com.tieto.ec.listeners.dmr;

import android.view.View;
import android.view.View.OnLongClickListener;

import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.gui.dialogs.OptionRow.OptionRowType;
import com.ec.prod.android.pilot.model.TableSection;

import android.app.Dialog;

public class TableOptionDialogListener implements OnLongClickListener{

	private OptionDialog tableDialog;
	
	/**
	 * Creates a new {@link OnLongClickListener} for a {@link TableSection}
	 * It creates a {@link Dialog} displaying each column in a {@link TableSection}
	 * where you can choose which should be visible 
	 * @param dailyMorningReport {@link DailyMorningReport}
	 * @param title Title for the {@link TableSection}
	 * @param data {@link TableData} for the {@link TableSection}
	 */
	public TableOptionDialogListener(DailyMorningReport dailyMorningReport, String title, TableData data){		
		//Section
		tableDialog = new OptionDialog(dailyMorningReport, title);
		
		//Options
		for (TableColumn column : data.getTableColumns()) {
			tableDialog.addOptionRow(column.getHeader(), OptionRowType.CHECK_BOX);
		}
		
		//Dialog
		tableDialog.setOnDismissListener(new DmrRefreshListener(dailyMorningReport));
	}
	
	/**
	 * Runs when user performs a long click on the {@link View} with this listener attached
	 * It Shows the dialog created
	 */
	public boolean onLongClick(View v) {
		tableDialog.show();
		return false;
	}
}
