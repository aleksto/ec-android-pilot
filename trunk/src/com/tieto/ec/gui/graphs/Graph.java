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


public abstract class Graph extends XYPlot{

	@SuppressWarnings("rawtypes")
	protected ArrayList<XYSeriesFormatter> formats;
	protected ArrayList<SimpleXYSeries> graphLines;
	protected String title;
	protected Context context;

	/**
	 * Creates a new empty graph. This class can only be instantiated by classes extending
	 * the class because it is only a abstract class. 
	 * @param context {@link Context} needed for Android framework actions
	 * @param title The title of the graph
	 */
	@SuppressWarnings("rawtypes")
	protected Graph(Context context, String title){
		super(context, title);

		this.context = context;
		this.title = title;
		graphLines = new ArrayList<SimpleXYSeries>();
		formats = new ArrayList<XYSeriesFormatter>();

		//this
		setDomainLabel("");
		setRangeLabel("");
		setRangeLowerBoundary(0, BoundaryMode.FIXED);
		setRangeValueFormat(new DecimalFormat("0"));
		setBorderStyle(BorderStyle.SQUARE, null, null);
		getBorderPaint().setStrokeWidth(1);
		getBorderPaint().setAntiAlias(false);
		getBorderPaint().setColor(Color.WHITE);
		setGridPadding(1, 5, 1, 1);
		disableAllMarkup();

		//GraphWidget
		XYGraphWidget widget = getGraphWidget();
		widget.getGridBackgroundPaint().setColor(Color.WHITE);
		widget.getGridLinePaint().setColor(Color.BLACK);
		widget.getGridLinePaint().setPathEffect(new DashPathEffect(new float[]{1,1}, 1));
		widget.getDomainOriginLinePaint().setColor(Color.BLACK);
		widget.getRangeOriginLinePaint().setColor(Color.BLACK);
	}

	/**
	 * Shows a graph line
	 * @param graphNr The number of the graph line
	 */
	public void show(int graphNr){
		addSeries(graphLines.get(graphNr), formats.get(graphNr));
		invalidate();
	}

	/**
	 * Shows a graph line
	 * @param title The title of the graph line
	 */
	public void show(String title){
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				addSeries(line, formats.get(graphLines.indexOf(line)));
				invalidate();
			}
		}

	}

	/**
	 * Hides a graph line
	 * @param graphNr The number of the graph line
	 */
	public void hide(int graphNr){
		if(getSeriesSet().size() > 1){
			removeSeries(graphLines.get(graphNr));		
			invalidate();	
		}
	}

	/**
	 * Hides a graph line
	 * @param title The title of the graph line
	 */
	public void hide(String title){
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				removeSeries(line);
				invalidate();
			}
		}
	}
	
	/**
	 * Returns <code>true</code> if the graph line is showing
	 * @param title The title of the graph line
	 * @return <code>true</code> if the line is showing
	 */
	public boolean isShowing(String title){
		Set<XYSeries> seriesSet = getSeriesSet();
		
		for (XYSeries xySeries : seriesSet) {
			if(xySeries.getTitle().equalsIgnoreCase(title)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Clear all values from a graph line
	 * @param title Title of the graph line
	 */
	public void clearGraphLine(String title){
		for (SimpleXYSeries line : graphLines) {
			if(line.getTitle().equalsIgnoreCase(title)){
				clearGraphLine(graphLines.indexOf(line));
			}
		}
	}

	/**
	 * Clear all values from a graph line
	 * @param nr The number of the graph line
	 */
	public void clearGraphLine(int nr){
		SimpleXYSeries line = graphLines.get(nr);
		int size = line.size();
		for (int i = 0; i < size; i++) {
			line.removeLast();
		}
	}

	/**
	 * Clear all the graph lines
	 */
	public void clearAllGraphLines(){
		if(graphLines != null)
		{
			for (SimpleXYSeries line : graphLines) {
				clearGraphLine(graphLines.indexOf(line));
			}
		}
	}
	
	/**
	 * @return {@link ArrayList} of all the lines {@link SimpleXYSeries}
	 */
	public ArrayList<SimpleXYSeries> getLines(){
		return graphLines;
	} 
	
	/**
	 * Gets the index of a line from a given title
	 * @param title The title of the graph line
	 * @return index of the graph line
	 */
	protected int idxFromTitle(String title){
		for (int i = 0; i < graphLines.size(); i++) {
			if(graphLines.get(i).getTitle().equalsIgnoreCase(title)){
				return i;
			}
		}
		return -1;
	}
}
