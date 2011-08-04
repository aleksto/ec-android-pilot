package com.tieto.ec.gui.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.SimpleXYSeries;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;
import com.tieto.ec.logic.NameFormat;

import android.content.Context;
import android.graphics.Color;

public class BarGraph extends Graph{

	private List<Integer> colors;
	private List<String> titles;
	
	/**
	 * Creates a new empty Bar graph with given title and color.
	 * @param context {@link Context} needed for Android framework actions
	 * @param title The title of the graph
	 * @param color The color of the graph
	 */
	public BarGraph(Context context, String title) {
		//Super
		super(context, title);
		
		//Init
		titles = new ArrayList<String>();
		colors = new ArrayList<Integer>();
		
		//This      
        getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        getGraphWidget().getGridLinePaint().setColor(Color.BLACK);
        getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
 
        setGridPadding(25, 0, 25, 0);
        setBorderStyle(BorderStyle.SQUARE, null, null);
        getBorderPaint().setStrokeWidth(1);
        getBorderPaint().setAntiAlias(false);
        getBorderPaint().setColor(Color.WHITE);
        getDomainLabelWidget().pack();
	}
	
	/**
	 * Clones and creates a new {@link BarGraph}
	 * @param graph
	 */
	public BarGraph(BarGraph graph){
		//This
		this(graph.context, graph.title);
		
		for (GraphData graphData : graph.data) {
			int idx = graph.data.indexOf(graphData);
			add(graphData, graph.colors.get(idx), graph.titles.get(idx));			
		}
	}
	
	/**
	 * Adds an array of values to the {@link BarGraph}
	 * @param title Title of the bars
	 * @param vals The array of values
	 */
	public void addBars(String title, int color, Number ... vals){
		//Init
		SimpleXYSeries bar = new SimpleXYSeries(Arrays.asList(vals), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, title);
		BarFormatter barFormatter  = new BarFormatter(color, Color.rgb(0, 80, 0));
		
		//This
		addSeries(bar, BarRenderer.class, barFormatter);
        
		//Needed if size == 1, because external library could not show only 1 value
		if(vals.length == 1){
			Number temp = vals[0];
			vals = new Number[3];
			vals[0] = 0;
			vals[1] = temp;
			vals[2] = 0;
		}
		
        //BarRenderer
        BarRenderer renderer = (BarRenderer) getRenderer(BarRenderer.class);
        if(renderer != null){
        	renderer.setBarWidth(150/vals.length);
        }
        
		
		//Series
		bar.setModel(Arrays.asList(vals), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY);
		
		//List
		graphLines.add(bar);
		formats.add(barFormatter);
	}
	
	/**
	 * Adds bars to the graph by a given {@link GraphData}
	 * @param graphData data
	 */
	public void add(GraphData graphData, int color, String title) {
		//Init
		colors.add(color);
		data.add(graphData);
		titles.add(title);
		List<String> pointAttributes = graphData.getPointAttributes();
		List<GraphPoint> graphPoints = graphData.getGraphPoints();
		Double[] vals = new Double[graphPoints.size()*pointAttributes.size()];
		
		//Format
		setDomainStepValue(graphData.getPointAttributes().size());
		setDomainValueFormat(new NameFormat(graphData.getPointAttributes()));
		
		int counter = 0;
		for (GraphPoint point : graphPoints) {
			for (String attribute : pointAttributes) {
				vals[counter++] =  Double.valueOf(point.getValue(attribute));
			}
		}
		
		addBars(title, color, vals);
		
		invalidate();
	}
}