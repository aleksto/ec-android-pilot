package com.ec.prod.android.pilot.model;

import java.util.LinkedList;
import java.util.List;

public class GraphData {

	private List<GraphPoint> graphPoints = new LinkedList<GraphPoint>();
	private List<String> pointAttributes = new LinkedList<String>();

	public List<GraphPoint> getGraphPoints() {
		return graphPoints;
	}

	public void addGraphPoint(GraphPoint point) {
		graphPoints.add(point);		
	}

	public List<String> getPointAttributes() {
		return pointAttributes;
	}

	public void setPointAttributes(String...attributes) {
		for (String attribute : attributes) {
			pointAttributes.add(attribute);
		}
	}

}
