package com.tieto.ec.listeners.main;

import com.tieto.ec.gui.Graph;
import com.tieto.ec.logic.DateFormat;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GraphListener implements OnTouchListener{

	public boolean onTouch(View v, MotionEvent event) {
		Graph graph = (Graph) v;
		Double xVal = graph.getGraphWidget().getXVal(event.getX());
		String date = DateFormat.parse(xVal);
		
		return false;
	}
}