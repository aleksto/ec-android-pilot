package com.tieto.ec.gui;

import java.util.Date;
import java.util.List;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.tieto.frmw.model.GraphData;
import com.tieto.frmw.model.GraphPoint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

public class LineGraph extends Graph{

	public LineGraph(Context context, String title) {
		super(context, title);
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
