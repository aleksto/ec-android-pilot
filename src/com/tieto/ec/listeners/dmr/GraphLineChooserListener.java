package com.tieto.ec.listeners.dmr;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnLongClickListener;

import com.androidplot.xy.SimpleXYSeries;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.gui.dialogs.OptionRow.OptionRowType;
import com.tieto.ec.gui.graphs.Graph;
import android.app.Dialog;

public class GraphLineChooserListener implements OnLongClickListener {

	private OptionDialog dialog;

	/**
	 * Creates a new {@link OnLongClickListener} for a {@link View}
	 * and creates a {@link Dialog} with all lines from a given {@link Graph}
	 * and refreshes {@link DailyMorningReport} when the {@link Dialog} is closed/dismissed 
	 * @param dmr {@link DailyMorningReport}
	 * @param graph {@link Graph} for getting lines
	 * @param title Title of the graph
	 */
	public GraphLineChooserListener(DailyMorningReport dmr, Graph graph, String title) {
		dialog = new OptionDialog(dmr, title);
		ArrayList<SimpleXYSeries> lines = graph.getLines();

		for (SimpleXYSeries line : lines) {
			dialog.addOptionRow(line.getTitle(), OptionRowType.CHECK_BOX);
		}
		
		dialog.setOnDismissListener(new DmrRefreshListener(dmr));
	}

	/**
	 * Runs when user performs a long click on a {@link View} with this listener attached
	 * This shows the dialog
	 */
	public boolean onLongClick(View arg0) {
		dialog.show();
		return false;
	}
}
