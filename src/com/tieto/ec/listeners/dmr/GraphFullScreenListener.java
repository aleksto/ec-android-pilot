package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.GraphFullScreenDialog;
import com.tieto.ec.gui.graphs.LineGraph;

import android.view.View;
import android.view.View.OnClickListener;

public class GraphFullScreenListener implements OnClickListener {

	private LineGraph graph;
	private GraphFullScreenDialog graphFullScreenDialog;
	private String title;
	
	public GraphFullScreenListener(DailyMorningReport dailyMorningReport, LineGraph graph, String title) {
		this.title = title;
		this.graph = graph;
		graphFullScreenDialog = new GraphFullScreenDialog(dailyMorningReport);
	}

	public void onClick(View v) {	
		graphFullScreenDialog.show();
		graphFullScreenDialog.setContentView(new LineGraph(graph));
		graphFullScreenDialog.setTitle(title);
	}
}
