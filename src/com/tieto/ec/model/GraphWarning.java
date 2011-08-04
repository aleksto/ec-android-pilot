package com.tieto.ec.model;

public class GraphWarning extends Warning{

	private final String attribute;
	private final String time;

	public GraphWarning(Type type, String attribute, String time, double actualValue, double targetValue, String comment) {
		super(type, actualValue, targetValue, comment);
		this.attribute = attribute;
		this.time = time;
	}

	public String getAttribute() {
		return attribute;
	}

	public String getTime() {
		return time;
	}
}
