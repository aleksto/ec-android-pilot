package com.tieto.ec.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.SimpleXYSeries;

import android.content.Context;
import android.graphics.Color;

public class BarGraph extends Graph{

	public BarGraph(Context context, String title, int color) {
		//Super
		super(context, title);
		
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
	
	public void addBarsWithValueList(ArrayList<HashMap<String, Object>> valueList, String title, String ... keys){
		Double[] vals = new Double[valueList.size()*keys.length];
		int counter = 0;	
		for (HashMap<String,Object> map : valueList) {
			for (int i = 0; i < keys.length; i++) {
				vals[counter] = Double.valueOf(map.get(keys[i])+"");
				counter++;
			}
		}
		addBars(title, vals);
		invalidate();
	}
}
