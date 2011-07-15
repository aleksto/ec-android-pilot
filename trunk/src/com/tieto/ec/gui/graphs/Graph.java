package com.tieto.ec.gui.graphs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeriesFormatter;


public class Graph extends XYPlot{

	@SuppressWarnings("rawtypes")
	protected ArrayList<XYSeriesFormatter> formats;
	protected ArrayList<SimpleXYSeries> graphLines;
	protected String title;
	protected Context context;

	@SuppressWarnings("rawtypes")
	public Graph(Context context, String title){
		super(context, title);

		this.context = context;
		this.title = title;
		graphLines = new ArrayList<SimpleXYSeries>();
		formats = new ArrayList<XYSeriesFormatter>();

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
	
	public Graph(Graph graph){
		this(graph.context, graph.title);
		
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
	
	public boolean isShowing(String title){
		Set<XYSeries> seriesSet = getSeriesSet();
		
		for (XYSeries xySeries : seriesSet) {
			if(xySeries.getTitle().equalsIgnoreCase(title)){
				return true;
			}
		}
		return false;
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
	
	public ArrayList<SimpleXYSeries> getLines(){
		return graphLines;
	} 
	
	protected int idxFromTitle(String title){
		for (int i = 0; i < graphLines.size(); i++) {
			if(graphLines.get(i).getTitle().equalsIgnoreCase(title)){
				return i;
			}
		}
		return -1;
	}
}
