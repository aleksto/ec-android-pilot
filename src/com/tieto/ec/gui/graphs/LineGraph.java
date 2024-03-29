package com.tieto.ec.gui.graphs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;
import com.tieto.ec.logic.FileManager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class LineGraph extends Graph{
	
	private final int[] COLORS = {Color.BLACK, Color.BLUE, Color.RED, Color.DKGRAY};
	private final double STEP_VALUE = 4.5;
	
	/**
	 * Creates a new empty {@link LineGraph}
	 * @param context {@link Context} needed for Android frameword actions
	 * @param title The title of the graph
	 */
	public LineGraph(Context context, String title) {
		super(context, title);
	}
	
	/**
	 * Clones and creates a new given {@link LineGraph}
	 * @param graph
	 */
	public LineGraph(LineGraph graph){
		super(graph.context, graph.title);
		
		this.graphLines = graph.graphLines;
		this.formats = graph.formats;

		Set<XYSeries> seriesSet = graph.getSeriesSet();
		
		for (XYSeries xySeries : seriesSet) {
			if(graphLines.contains(xySeries)){
				int inx = graphLines.indexOf(xySeries);
				addSeries(graphLines.get(inx), formats.get(inx));
			}
		}

		this.setDomainValueFormat(new SimpleDateFormat("yyyy-MM-dd"));
		this.setDomainStepValue(STEP_VALUE);
	}
	
	/**
	 * Adds lines to the graph by a given {@link GraphData}
	 * @param graphData The {@link GraphData} used
	 * @param title Title of this line
	 */
	public void add(GraphData graphData, String title) {
		data.add(graphData);
		
		List<GraphPoint> graphPoints = graphData.getGraphPoints();
		List<String> pointAttributes = graphData.getPointAttributes();
		
		this.setDomainValueFormat(new SimpleDateFormat("yyyy-MM-dd-hh-mm"));
		this.setDomainStepValue(STEP_VALUE);
		
		for (String string : pointAttributes) {
			int idx = pointAttributes.indexOf(string)%COLORS.length;
			addEmptyGraphLine(string, COLORS[idx]);
		}
		
		for (GraphPoint point : graphPoints) {
			Date daytime = point.getDaytime();
			for (String string : pointAttributes) {
				String value = point.getValue(string);
				int idx = pointAttributes.indexOf(string) + (data.indexOf(graphData))*pointAttributes.size();
				addPointToLine(idx, daytime.getTime(), Double.valueOf(value));						
			}
		}

		for (String attribute : pointAttributes) {
			try {
				if(!Boolean.valueOf(FileManager.readPath(context, title + "." + attribute))){
					hide(attribute);
				}
			} catch (IOException e) {
				FileManager.writePath(context, title + "." + attribute, "true");
				add(graphData, title);
				e.printStackTrace();
			}			
		}
		invalidate();
	}
	
	/**
	 * Adds a new graph line with no values
	 * @param title The title of the graph line
	 * @param color The {@link Color} of the graph line
	 */
	private void addEmptyGraphLine(String title, int color){
		SimpleXYSeries line = new SimpleXYSeries(title);
		LineAndPointFormatter format = new LineAndPointFormatter(color, color, color);

		Paint paint = new Paint();
		paint.setColor(color);
		paint.setAlpha(0);
		format.setFillPaint(paint);

		addSeries(line, format);

		graphLines.add(line);
		formats.add(format);
	}
	
	/**
	 * Adds a singel point to a given graph line
	 * @param lineNr Index of the graph line
	 * @param x X Value
	 * @param y Y Value
	 */
	private void addPointToLine(int lineNr, double x, double y){
		SimpleXYSeries line = graphLines.get(lineNr);
		line.addLast(x, y);
		invalidate();
	}
}
