package com.tieto.ec.gui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;
import com.tieto.ec.listeners.dialogs.HideDialogListener;
import com.tieto.ec.listeners.dmr.GraphScrollZoomListener;

public class GraphFullScreenDialog extends Dialog{

	private RelativeLayout main;
	
	/**
	 * Creates a dialog where a full screen representation of a graph can be shown. 
	 * @param context {@link Context} needed for Android framework actions.
	 */
	public GraphFullScreenDialog(DailyMorningReport dmr, Graph graph) {
		//Super
		super(dmr);		
		
		//Init
		main = new RelativeLayout(dmr);
		
		//Graph
		graph.setOnClickListener(new HideDialogListener(this));
		
		if(graph instanceof LineGraph){
			graph.setOnTouchListener(new GraphScrollZoomListener(graph));			
		}else{
//			graph.setOnTouchListener(new BarGraphOnTouchListener(dmr, (BarGraph) graph));
		}
		
		//Params
		LayoutParams graphParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		graphParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		//Main
		main.addView(graph, graphParams);
		
		//Dialog
		setContentView(main);
	}
}
