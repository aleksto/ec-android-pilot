package com.tieto.ec.listeners;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class GraphLineCheckBoxListener implements OnCheckedChangeListener {

	private XYPlot graph;
	private SimpleXYSeries graphLine;
	private LineAndPointFormatter format;
	
	public GraphLineCheckBoxListener(XYPlot graph, SimpleXYSeries graphLine, LineAndPointFormatter format){
		this.graph = graph;
		this.graphLine = graphLine;
		this.format = format;
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			graph.addSeries(graphLine, format);
		}
		else{
			graph.removeSeries(graphLine);
		}	
	}
}
