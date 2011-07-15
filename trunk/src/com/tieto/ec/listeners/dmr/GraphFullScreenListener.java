package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.GraphFullScreenDialog;
import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;

import android.view.View;
import android.view.View.OnClickListener;

public class GraphFullScreenListener implements OnClickListener {

	private Graph graph;
	private GraphFullScreenDialog graphFullScreenDialog;
	private String title;
	
	public GraphFullScreenListener(DailyMorningReport dailyMorningReport, Graph graph, String title) {
		this.title = title;
		this.graph = graph;
		graphFullScreenDialog = new GraphFullScreenDialog(dailyMorningReport);
	}

	public void onClick(View v) {	
		graphFullScreenDialog.show();
		if(graph instanceof LineGraph){			
			graphFullScreenDialog.setContentView(new Graph((LineGraph) graph));
		}else if(graph instanceof BarGraph){
			graphFullScreenDialog.setContentView(new Graph((BarGraph) graph));
		}
		graphFullScreenDialog.setTitle(title);
	}
}
