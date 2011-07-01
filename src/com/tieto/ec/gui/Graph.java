package com.tieto.ec.gui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;

public class Graph extends XYPlot{

	private ArrayList<SimpleXYSeries> graphLines;
	private ArrayList<LineAndPointFormatter> formats;

	public Graph(Context context, String title){
		super(context, title);

		graphLines = new ArrayList<SimpleXYSeries>();
		formats = new ArrayList<LineAndPointFormatter>();
	}

	public void addEmptyGraphLine(String title, int color){
		SimpleXYSeries line = new SimpleXYSeries(title);
		LineAndPointFormatter format = new LineAndPointFormatter(Color.BLACK, Color.BLACK, color);
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
				int idx = graphLines.indexOf(line);
				graphLines.set(idx, new SimpleXYSeries(title));
			}
		}
	}
	
	public void clearGraphLine(int nr){
		SimpleXYSeries line = graphLines.get(nr);

		for (int i = 0; i < line.size(); i++) {
			line.removeFirst();
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
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				return graphLines.indexOf(line);
			}
		}
		return -1;
	}
}
