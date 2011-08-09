package com.tieto.ec.model;

import com.ec.prod.android.pilot.model.GraphPoint;

public class GraphWarning extends Warning{

	private final String attribute;
	private final String time;

	/**
	 * Creates a new Warning for a {@link SectionWarning}
	 * @param type {@link Type} either CRITICAL or WARNING
	 * @param attribute The attribute for this {@link GraphPoint}
	 * @param time The time for this {@link GraphPoint}
	 * @param actualValue The actual value
	 * @param targetValue The Target value
	 * @param comment The comment for this cell
	 */
	public GraphWarning(Type type, String attribute, String time, double actualValue, double targetValue, String comment) {
		super(type, actualValue, targetValue, comment);
		this.attribute = attribute;
		this.time = time;
	}

	/**
	 * @return The attribute for this warning
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @return The time for this warning
	 */
	public String getTime() {
		return time;
	}
}
