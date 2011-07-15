package com.tieto.ec.gui.graphs;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.SimpleXYSeries;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;

import android.content.Context;
import android.graphics.Color;

public class BarGraph extends Graph{

	private int color;
	
	public BarGraph(Context context, String title, int color) {
		//Super
		super(context, title);
		
		//Init
		this.color = color;
		
		//This      
        getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        getGraphWidget().getGridLinePaint().setColor(Color.BLACK);
        getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
 
        setBorderStyle(BorderStyle.SQUARE, null, null);
        getBorderPaint().setStrokeWidth(1);
        getBorderPaint().setAntiAlias(false);
        getBorderPaint().setColor(Color.WHITE);
        getDomainLabelWidget().pack();
        
        //List
        graphLines.add(new SimpleXYSeries(""));
        formats.add(new BarFormatter(1, 1));
	}
	
	public BarGraph(BarGraph graph){
		this(graph.context, graph.title, graph.color);
		
		this.graphLines = graph.graphLines;
		this.formats = graph.formats;

		Set<XYSeries> seriesSet = graph.getSeriesSet();
		
		for (XYSeries xySeries : seriesSet) {
			if(graphLines.contains(xySeries)){
				int inx = graphLines.indexOf(xySeries);
				addSeries(graphLines.get(inx), formats.get(inx));
			}
		}
	}
	
	private void addBars(String title, Number ... vals){
		//Init
		SimpleXYSeries bar = new SimpleXYSeries(Arrays.asList(vals), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, title);
		BarFormatter barFormatter  = new BarFormatter(Color.argb(100, 0, 200, 0), Color.rgb(0, 80, 0));
        
        //List
		graphLines.set(0, bar);
		formats.set(0, barFormatter);
		
		//This
		addSeries(bar, BarRenderer.class, barFormatter);
        
        //BarRenderer
        BarRenderer renderer = (BarRenderer) getRenderer(BarRenderer.class);
        if(renderer != null){
        	renderer.setBarWidth(50);
        }
		
		//Series
		bar.setModel(Arrays.asList(vals), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
	}
	
	public void add(GraphData graphData) {
		List<String> pointAttributes = graphData.getPointAttributes();
		List<GraphPoint> graphPoints = graphData.getGraphPoints();
		Double[] vals = new Double[graphPoints.size()*pointAttributes.size()];
		
		int counter = 0;
		for (GraphPoint point : graphPoints) {
			for (String attribute : pointAttributes) {
				vals[counter++] =  Double.valueOf(point.getValue(attribute));
			}
		}
		
		addBars(title, vals);
		
		invalidate();
	}
}
