package com.tieto.ec.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;

public class Graph extends XYPlot{

	private ArrayList<SimpleXYSeries> graphLines;
	private ArrayList<LineAndPointFormatter> formats;

	public Graph(Context context, String title){
		super(context, title);

		graphLines = new ArrayList<SimpleXYSeries>();
		formats = new ArrayList<LineAndPointFormatter>();
		
		//this
		setRangeLowerBoundary(0, BoundaryMode.FIXED);
		setRangeValueFormat(new DecimalFormat("0"));
		setBorderStyle(BorderStyle.SQUARE, null, null);
		getBorderPaint().setStrokeWidth(1);
		getBorderPaint().setAntiAlias(false);
		getBorderPaint().setColor(Color.WHITE);
		disableAllMarkup();
		
		//GraphWidget
		XYGraphWidget widget = getGraphWidget();
        widget.getGridBackgroundPaint().setColor(Color.WHITE);
        widget.getGridLinePaint().setColor(Color.BLACK);
        widget.getGridLinePaint().setPathEffect(new DashPathEffect(new float[]{1,1}, 1));
        widget.getDomainOriginLinePaint().setColor(Color.BLACK);
        widget.getRangeOriginLinePaint().setColor(Color.BLACK);
	}

	public void addEmptyGraphLine(String title, int color){
		SimpleXYSeries line = new SimpleXYSeries(title);
		LineAndPointFormatter format = new LineAndPointFormatter(Color.BLACK, Color.BLACK, color);
		
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setAlpha(1000);
		format.setFillPaint(paint);
		
		addSeries(line, format);
		
		graphLines.add(line);
		formats.add(format);
	}

	public void addLineFromValues(String title, int color, ArrayList<HashMap<String, String>> values, String key){
		addEmptyGraphLine(title, color);
		addValuesToExistingLine(values, key, idxFromTitle(title));
		
	}

	public void show(int graphNr){
		addSeries(graphLines.get(graphNr), formats.get(graphNr));
		invalidate();
		
	}

	public void show(String title){
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				addSeries(line, formats.get(graphLines.indexOf(line)));
				invalidate();
			}
		}
		
	}

	public void hide(int graphNr){
		if(getSeriesSet().size() > 1){
			removeSeries(graphLines.get(graphNr));		
			invalidate();	
		}
		
	}

	public void hide(String title){
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				removeSeries(line);
				invalidate();
			}
		}
		
	}

	public void addValuesToExistingLine(ArrayList<HashMap<String, String>> values, String key, int lineNr){
		int counter = 0;
		for (HashMap<String,String> map : values){
			counter++;
			addPointToLine(lineNr, counter, Double.valueOf(map.get(key).replace(",", ".")));
		} 
	}

	public void addValuesToExistingLines(ArrayList<HashMap<String, String>> values, String ... key){
		for (int i = 0; i < key.length; i++) {
			int counter = 0;
			for (HashMap<String,String> map : values){
				counter++;
				addPointToLine(i, counter, Double.valueOf(map.get(key[i]).replace(",", ".")));
			} 			
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
		SimpleXYSeries graphLine = null;
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				graphLine = line;
			}
		}
		graphLine.addLast(x, y);
		invalidate();
	}

	public void addPointToLine(int lineNr, double x, double y){
		SimpleXYSeries line = graphLines.get(lineNr);
		line.addLast(x, y);
		invalidate();
	}

	public void clearGraphLine(String title){
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				clearGraphLine(graphLines.indexOf(line));
			}
		}
	}
	
	public void clearGraphLine(int nr){
		SimpleXYSeries line = graphLines.get(nr);
		int size = line.size();
		for (int i = 0; i < size; i++) {
			line.removeLast();
		}
	}
	
	public void clearAllGraphLines(){
		if(graphLines != null)
		{
			for (SimpleXYSeries line : graphLines) {
				clearGraphLine(graphLines.indexOf(line));
			}
		}
	}
	
	private int idxFromTitle(String title){
		for (int i = 0; i < graphLines.size(); i++) {
			if(graphLines.get(i).getTitle().equalsIgnoreCase(title)){
				return i;
			}
		}
		return -1;
	}
}
