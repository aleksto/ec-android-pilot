package com.tieto.frmw.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GraphPoint {

	private Date daytime;
	private Map<String, String> pointValues = new HashMap<String, String>();

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

}
