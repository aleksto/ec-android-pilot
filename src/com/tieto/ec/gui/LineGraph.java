package com.tieto.ec.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.tieto.ec.logic.DateFormat;

import android.content.Context;
import android.graphics.Paint;

public class LineGraph extends Graph{

	public LineGraph(Context context, String title) {
		super(context, title);
	}
	
	public void addEmptyGraphLine(String title, int color){
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

	public void addLineFromValues(String title, int color, ArrayList<HashMap<String, String>> values, String key){
		addEmptyGraphLine(title, color);
		addValuesToExistingLine(values, key, idxFromTitle(title));
	}

	public void addValuesToExistingLines(ArrayList<HashMap<String, String>> values, String ... key){
		for (int i = 0; i < key.length; i++) {		
			addValuesToExistingLine(values, key[i], i);
		}
	}
	
	public void addValuesToExistingLine(ArrayList<HashMap<String, String>> values, String key, int lineNr){
		for (HashMap<String,String> map : values){
			addPointToLine(lineNr, DateFormat.parse(map.get("daytime")), Double.valueOf(map.get(key).replace(",", ".")));
		} 
	}
	
	public void addPointsToLine(String title, Double ... values){
		for (int i = 0; i < values.length; i+=2) {
			addPointToLine(title, values[i], values[i+1]);
		}
	}
	
	public void addPointsToLine(int lineNr, Double ... values){
		for (int i = 0; i < values.length; i+=2) {
			addPointToLine(lineNr, values[i], values[i+1]);
		}
	}
	
	public void addPointToLine(String title, double x, double y){
		for (int i = 0; i<graphLines.size(); i++) {
			if(graphLines.get(i).getTitle().equalsIgnoreCase(title)){
				addPointToLine(i, x, y);
			}
		}
		invalidate();
	}
	
	private void addPointToLine(int lineNr, double x, double y){
		SimpleXYSeries line = graphLines.get(lineNr);
		line.addLast(x, y);
		invalidate();
	}
}
