package com.tieto.ec.model;

public class Warning {

	public enum Type{OK, WARNING, CRITICAL}
	
	private final Type type;
	private final double actualValue;
	private final double targetValue;
	private final String comment;

	/**
	 * Creates a new Warning, this class is used in {@link SectionWarning} and this represents if a value in the
	 * report is classified as a warning
	 * @param type The {@link Type} of the warning, either WARNING or CRITICAL
	 * @param actualValue The actual value
	 * @param targetValue The target value
	 * @param comment Comment
	 */
	public Warning(Type type, double actualValue, double targetValue, String comment){
		this.type = type;
		this.actualValue = actualValue;
		this.targetValue = targetValue;
		this.comment = comment;
	}

	/**
	 * @return The type of the warning, either WARNING or CRITICAL
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return The actual value
	 */
	public double getActualValue() {
		return actualValue;
	}

	/**
	 * @return The target value
	 */
	public double getTargetValue() {
		return targetValue;
	}

	/**
	 * @return The comment of this warning
	 */
	public String getComment() {
		if(comment == null){
			return "No comment";
		}
		return comment;
	}
}
