package com.ec.prod.android.pilot.model;

import java.util.LinkedList;
import java.util.List;

public class TableRow {

	List<String> rowValues = new LinkedList<String>();
	private String rowId;
	
	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
	public TableRow() {
		
	}

	public List<String> getRowValues() {
		return rowValues;
	}

	public void setRowValues(List<String> rowValues) {
		this.rowValues = rowValues;
	}

	public TableRow(String...rowValues) {
		for (String rowValue : rowValues) {
			this.rowValues.add(rowValue);
		}
	}

	public List<String> getValues() {		
		return rowValues;
	}

}
