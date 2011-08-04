package com.tieto.ec.listeners.dmr;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.androidplot.xy.SimpleXYSeries;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.graphs.BarGraph;

public class BarGraphOnTouchListener implements OnTouchListener {

	private final BarGraph graph;
	private double x,y;
	private Number[] xValsActual, yValsActual, yValsTarget;
	private int size;

	/**
	 * THIS CLASS IS NOT USED
	 * @param dmr
	 * @param graph
	 */
	public BarGraphOnTouchListener(DailyMorningReport dmr, BarGraph graph){
		this.graph = graph;

		size = graph.getLines().get(1).size();
		xValsActual = new Number[size];
		yValsActual = new Number[size];
		yValsTarget = new Number[size];

		Log.d("tieto", "" + size);
		SimpleXYSeries lineActual = graph.getLines().get(0);
		SimpleXYSeries lineTarget = graph.getLines().get(1);
		for (int i = 0; i < size; i++) {
			xValsActual[i] = lineActual.getX(i);
			yValsActual[i] = lineActual.getY(i);
			yValsTarget[i] = lineTarget.getY(i);
		}
	}
	
	public boolean onTouch(View arg0, MotionEvent arg1) {
		try{
			if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE){
				x = graph.getGraphWidget().getXVal(arg1.getX());
				y = graph.getGraphWidget().getYVal(arg1.getY());
				
				int returnValue = -1;
				Log.d("tieto", "Checking");
				if((returnValue = isClose(x, y)) != -1){
					Log.d("tieto", returnValue+"");
				}
			}			
		}catch(java.lang.IllegalArgumentException e){
			//Nothing to do
		}
		return false;
	}
	
	private int isClose(double x, double y){
		for (int i = 0; i < size; i++) {
			if(x > xValsActual[i].doubleValue()-0.1 && x < xValsActual[i].doubleValue()+0.1 && y<yValsTarget[i].doubleValue()){
				return i;
			}
		}
		return -1;
	}
}
