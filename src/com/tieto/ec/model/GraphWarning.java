package com.tieto.ec.model;

public class GraphWarning extends Warning{

	private final String attribute;

	public GraphWarning(Type type, String attribute, double actualValue, double targetValue, String comment) {
		super(type, actualValue, targetValue, comment);
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}
}
