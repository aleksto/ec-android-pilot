package com.tieto.ec.model;

public class TableWarning extends Warning{

	private final String row;
	private final String collumn;

	public TableWarning(Type type, String collumn, String row, double actualValue, double targetValue, String comment) {
		super(type, actualValue, targetValue, comment);
		this.collumn = collumn;
		this.row = row;
	}

	public String getRow() {
		return row;
	}

	public String getCollumn() {
		return collumn;
	}
}
