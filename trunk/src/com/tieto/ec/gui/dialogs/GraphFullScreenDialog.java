package com.tieto.ec.gui.dialogs;

import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;
import com.tieto.ec.listeners.dialogs.HideDialogListener;
import com.tieto.ec.listeners.dmr.GraphScrollZoomListener;

import android.app.Dialog;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GraphFullScreenDialog extends Dialog{

	private RelativeLayout main;
	
	/**
	 * Creates a dialog where a full screen representation of a graph can be shown. 
	 * @param context {@link Context} needed for Android framework actions.
	 */
	public GraphFullScreenDialog(Context context, Graph graph) {
		//Super
		super(context);		
		
		//Init
		main = new RelativeLayout(context);
		
		//Graph
		graph.setOnClickListener(new HideDialogListener(this));
		
		if(graph instanceof LineGraph){
			graph.setOnTouchListener(new GraphScrollZoomListener(graph));			
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
