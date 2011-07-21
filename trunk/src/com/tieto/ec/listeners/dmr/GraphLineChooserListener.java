package com.tieto.ec.listeners.dmr;

import java.util.ArrayList;

import com.androidplot.xy.SimpleXYSeries;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.gui.graphs.Graph;

import android.view.View;
import android.view.View.OnLongClickListener;

public class GraphLineChooserListener implements OnLongClickListener {

	private OptionDialog dialog;

	public GraphLineChooserListener(DailyMorningReport dmr, Graph graph, String title) {
		dialog = new OptionDialog(dmr, title);
		ArrayList<SimpleXYSeries> lines = graph.getLines();

		for (SimpleXYSeries line : lines) {
			dialog.addOption(line.getTitle(), OptionRowType.CHECK_BOX);
		}
		
		dialog.setOnDismissListener(new DmrRefreshListener(dmr));
	}

	public boolean onLongClick(View arg0) {
		dialog.show();
		return false;
	}
}
