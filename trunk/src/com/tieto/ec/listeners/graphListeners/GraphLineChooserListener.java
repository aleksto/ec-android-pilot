package com.tieto.ec.listeners.graphListeners;

import com.ec.prod.android.pilot.model.GraphData;
import com.tieto.ec.gui.GraphLineChooserDialog;
import com.tieto.ec.gui.LineGraph;

import android.content.Context;
import android.view.View;
import android.view.View.OnLongClickListener;

public class GraphLineChooserListener implements OnLongClickListener {

	private Context context;
	private LineGraph graph;
	
	public GraphLineChooserListener(Context context, LineGraph graph) {
		this.context = context;
		this.graph = graph;
	}

	public boolean onLongClick(View arg0) {
		GraphLineChooserDialog graphLineChooserDialog = new GraphLineChooserDialog(context, graph);
		return false;
		
	}

}
