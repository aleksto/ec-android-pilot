package com.tieto.ec.listeners.dmr;

import com.tieto.ec.gui.dialogs.GraphLineChooserDialog;
import com.tieto.ec.gui.graphs.Graph;

import android.content.Context;
import android.view.View;
import android.view.View.OnLongClickListener;

public class GraphLineChooserListener implements OnLongClickListener {

	private GraphLineChooserDialog dialog;
	
	public GraphLineChooserListener(Context context, Graph graph) {
		dialog = new GraphLineChooserDialog(context, graph);
	}

	public boolean onLongClick(View arg0) {
		dialog.show();
		return false;
	}
}
