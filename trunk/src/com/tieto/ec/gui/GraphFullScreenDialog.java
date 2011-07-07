package com.tieto.ec.gui;

import android.app.Dialog;
import android.content.Context;

public class GraphFullScreenDialog extends Dialog{



	public GraphFullScreenDialog(Context context) {
		super(context);		
	}

	public void setData(Graph graph) {
		setContentView(graph);	
	}

}
