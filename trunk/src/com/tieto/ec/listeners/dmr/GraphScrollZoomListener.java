package com.tieto.ec.listeners.dmr;

import com.androidplot.xy.BoundaryMode;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;

import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GraphScrollZoomListener implements OnTouchListener{

	private long t0;

	private double rangeUpper, rangeLower, domainUpper, domainLower;
	private double XMax, XMin, YMax, YMin;

	private float dx, dy;
	private float x0, y0;
	private float startSpacing, endSpacing;
	private float scale;
	private final Graph graph;

	private float lastScale;

	/**
	 * Creates a {@link OnTouchListener} for Zooming and translating the Lines on a {@link LineGraph}
	 * @param graph
	 */
	public GraphScrollZoomListener(Graph graph){
		this.graph = graph;
	}

	/**
	 * Runs when the user touches a {@link View} with the {@link OnTouchListener} attached
	 */
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getPointerCount() == 1){

			//Move
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				t0 = System.currentTimeMillis();
				x0 = event.getX();
				y0 = event.getY();
			}else if(event.getAction() == MotionEvent.ACTION_MOVE){
				if(XMax == 0){
					init();
				}
				translate(event);
			}else if(event.getAction() == MotionEvent.ACTION_UP){
				Log.d("tieto", "Graph was touched for " + (System.currentTimeMillis()-t0) + " ms");
				return System.currentTimeMillis()-t0>200;
			}
		}
		if(event.getPointerCount() == 2){
			//Zoom
			if(event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN){
				startSpacing = spacing(event);
			}else if(event.getAction() == MotionEvent.ACTION_MOVE){
				if(XMax == 0){
					init();
				}
				scale(event);
			}
		}
		return false;
	}
	

	/**
	 * Initializes the maximum and minimum values for the graph
	 */
	private void init() {
		YMax = graph.getCalculatedMaxY().doubleValue();
		YMin = graph.getCalculatedMinY().doubleValue();
		XMax = graph.getCalculatedMaxX().doubleValue();
		XMin = graph.getCalculatedMinX().doubleValue();	
		scale = 1f;
		lastScale = scale;
		rangeUpper = YMax*scale;
		rangeLower = YMax - YMax*scale;
		domainUpper = XMin + (XMax - XMin)*scale;
		domainLower = XMax - (XMax - XMin)*scale;
	}

	/**
	 * Translates the {@link Graph} when user drags one finger on the {@link Graph}
	 * @param event {@link MotionEvent} from onTouch()
	 */
	private void translate(MotionEvent event) {
		dx = x0 - event.getX();
		dy = y0 - event.getY();

		rangeLower -= dy*(YMax-YMin)*(0.005*Math.pow(scale, 5));
		rangeUpper -= dy*(YMax-YMin)*(0.005*Math.pow(scale, 5));
		domainLower += dx*(XMax-XMin)*(0.005*Math.pow(scale, 5));
		domainUpper += dx*(XMax-XMin)*(0.005*Math.pow(scale, 5));

		graph.setRangeUpperBoundary(rangeUpper, BoundaryMode.FIXED);
		graph.setRangeLowerBoundary(rangeLower, BoundaryMode.FIXED);
		graph.setDomainUpperBoundary(domainUpper, BoundaryMode.FIXED);
		graph.setDomainLowerBoundary(domainLower, BoundaryMode.FIXED);

		x0 = event.getX();
		y0 = event.getY();

		graph.invalidate();
	}

	/**
	 * Scales the {@link Graph} when the user uses two finger (pinch to zoom)
	 * @param event {@link MotionEvent} from onTouch()
	 */
	private void scale(MotionEvent event){
		endSpacing = spacing(event);

		scale += ((startSpacing/endSpacing)-1)/10f;

		if(scale > 1.5f) scale = 1.5f;
		if(scale <= 0.51f) scale = 0.51f;

		rangeUpper += (rangeUpper-rangeLower)*(scale-lastScale)*5;
		rangeLower -= (rangeUpper-rangeLower)*(scale-lastScale)*5;			

		domainUpper += (domainUpper-domainLower)*(scale-lastScale)*5;
		domainLower -= (domainUpper-domainLower)*(scale-lastScale)*5;

		graph.setRangeUpperBoundary(rangeUpper, BoundaryMode.FIXED);
		graph.setRangeLowerBoundary(rangeLower, BoundaryMode.FIXED);
		graph.setDomainLowerBoundary(domainLower, BoundaryMode.FIXED);
		graph.setDomainUpperBoundary(domainUpper, BoundaryMode.FIXED);

		graph.invalidate();

		lastScale = scale;
		startSpacing = spacing(event);
	}

	/**
	 * Calculates the distance between two fingers when they touches the screen
	 * @param event {@link MotionEvent} from onTouch()
	 * @return The distance between two fingers
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}
}
