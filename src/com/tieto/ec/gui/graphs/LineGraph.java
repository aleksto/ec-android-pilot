package com.tieto.ec.gui.graphs;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class LineGraph extends Graph{
	
	public LineGraph(Context context, String title) {
		super(context, title);
	}
	
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
	}
	
	public void add(GraphData graphData) {
		List<GraphPoint> graphPoints = graphData.getGraphPoints();
		List<String> pointAttributes = graphData.getPointAttributes();
		
		for (String string : pointAttributes) {
			addEmptyGraphLine(string, Color.rgb((int)(Math.random()*255.0), (int)(Math.random()*255.0), (int)(Math.random()*255.0)));
		}
		
		for (GraphPoint point : graphPoints) {
			Date daytime = point.getDaytime();
			for (String string : pointAttributes) {
				String value = point.getValue(string);
				int idx = pointAttributes.indexOf(string);
				addPointToLine(idx, (double)daytime.getTime(), Double.valueOf(value));
			}
		}
		
		invalidate();
	}
	
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
	
	private void addPointToLine(int lineNr, double x, double y){
		SimpleXYSeries line = graphLines.get(lineNr);
		line.addLast(x, y);
		invalidate();
	}
}
