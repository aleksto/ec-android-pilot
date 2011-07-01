package com.tieto.ec.listeners;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.tieto.ec.gui.Graph;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class GraphLineCheckBoxListener implements OnCheckedChangeListener {

	private Graph graph;
	private int lineNr;
	
	public GraphLineCheckBoxListener(Graph graph, int lineNr){
		this.graph = graph;
		this.lineNr = lineNr;
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		int size = graph.getSeriesSet().size();
		if(isChecked){
			graph.show(lineNr);
		}
		else{
			graph.hide(lineNr);
		}	
	}
}
