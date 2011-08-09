package com.tieto.ec.model;


public class CellWarning extends Warning{

	private final String row;
	private final String column;

	/**
	 * Creates a new Warning for a {@link SectionWarning}
	 * @param type {@link Type} either CRITICAL or WARNING
	 * @param column Column title for this cell
	 * @param row Row title for this cell
	 * @param actualValue The actual value
	 * @param targetValue The Target value
	 * @param comment The comment for this cell
	 */
	public CellWarning(Type type, String column, String row, double actualValue, double targetValue, String comment) {
		super(type, actualValue, targetValue, comment);
		this.column = column;
		this.row = row;
	}

	/**
	 * @return The row title for this cell
	 */
	public String getRow() {
		return row;
	}

	/**
	 * @return The column title for this cell
	 */
	public String getColumn() {
		return column;
	}
}
