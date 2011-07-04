package com.tieto.ec.listeners;

import java.util.Date;

import com.tieto.ec.gui.Graph;
import com.tieto.ec.logic.DateFormat;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GraphListener implements OnTouchListener {

	public boolean onTouch(View v, MotionEvent event) {
		Graph graph = (Graph) v;
		Double x = (Double)graph.getGraphWidget().getXVal(event.getX());
		
			
		Log.d("tieto", "X: "+event.getX()+" Y: "+event.getY());
		Log.d("tieto", graph.getGraphWidget().getXVal(event.getX())+"");
		Log.d("tieto", DateFormat.parse(graph.getGraphWidget().getXVal(event.getX())));
		return false;
	}

}
