package com.tieto.ec.logic;

public class Warning {

	public enum Type{OK, WARNING, CRITICAL}
	
	private final Type type;
	private final double actualValue;
	private final double targetValue;
	private final String comment;

	
	public Warning(Type type, double actualValue, double targetValue, String comment){
		this.type = type;
		this.actualValue = actualValue;
		this.targetValue = targetValue;
		this.comment = comment;
	}

	public Type getType() {
		return type;
	}

	public double getActualValue() {
		return actualValue;
	}

	public double getTargetValue() {
		return targetValue;
	}

	public String getComment() {
		if(comment == null){
			return "No comment";
		}
		return comment;
	}
}
