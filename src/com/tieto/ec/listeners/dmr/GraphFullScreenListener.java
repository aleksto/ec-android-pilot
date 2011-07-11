package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.GraphFullScreenDialog;
import com.tieto.ec.gui.LineGraph;
import com.ec.prod.android.pilot.model.GraphData;

import android.view.View;
import android.view.View.OnClickListener;

public class GraphFullScreenListener implements OnClickListener {

	private LineGraph graph;
	private GraphFullScreenDialog graphFullScreenDialog;
	private String title;
	
	public GraphFullScreenListener(DailyMorningReport dailyMorningReport, GraphData graphData, String title) {

		this.graph = new LineGraph(dailyMorningReport, "");
		this.title = title;
		graph.add(graphData);
		graphFullScreenDialog = new GraphFullScreenDialog(dailyMorningReport);
	}

	public void onClick(View v) {
		graphFullScreenDialog.show();
		graphFullScreenDialog.setData(graph);
		graphFullScreenDialog.setTitle(title);
	}

}
