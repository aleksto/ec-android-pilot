package com.ec.prod.android.pilot.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GraphPoint {

	private Date daytime;
	private Map<String, String> pointValues = new HashMap<String, String>();
	private Map<String, String> pointComments = new HashMap<String, String>();

	public GraphPoint(Date daytime) {
		this.daytime = daytime;
	}

	public Date getDaytime() {
		return daytime;
	}

	public String getValue(String attributeName) {
		return pointValues.get(attributeName);
	}

	public void addValue(String attribute, String value) {
		pointValues.put(attribute, value);		
	}

	public Map<String, String> getValues() {
		return pointValues;
	}

	public void addComment(String attribute, String value) {
		getPointComments().put(attribute, value);
	}
	
	public String getPointComment(String attribute) {
		if(pointComments.containsKey(attribute)){
			return getPointComments().get(attribute);			
		}else{
			return "No comment";
		}
	}

	public void setPointComments(Map<String, String> pointComments) {
		this.pointComments = pointComments;
	}

	public Map<String, String> getPointComments() {
		return pointComments;
	}
}
