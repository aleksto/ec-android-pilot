package com.tieto.ec.listeners.dmr;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.tieto.ec.gui.dialogs.GraphFullScreenDialog;
import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;

public class GraphFullScreenListener implements OnClickListener {

	private GraphFullScreenDialog dialog;
	
	/**
	 * Creates a {@link OnClickListener} for a {@link Graph}, When clicked it will pop up a
	 * {@link Dialog} whit the {@link Graph} clicked
	 * @param context {@link Context} used for Android framework actions
	 * @param graph The new {@link Graph} to display
	 * @param title Title for the dialog
	 */
	public GraphFullScreenListener(Context context, Graph graph, String title) {
		//Graph
		if(graph instanceof LineGraph){			
			dialog = new GraphFullScreenDialog(context, new LineGraph((LineGraph) graph));
		}else if(graph instanceof BarGraph){
			dialog = new GraphFullScreenDialog(context, new BarGraph((BarGraph) graph));
		}
		
		//Dialog
		dialog.setTitle(title);
	}

	/**
	 * Runs when user clicks a {@link Graph}
	 * this show a new dialog, and set its view to a new {@link Graph}
	 */
	public void onClick(View v) {	
		dialog.show();
	}
}
