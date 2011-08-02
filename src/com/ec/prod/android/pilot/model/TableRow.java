package com.ec.prod.android.pilot.model;

import java.util.LinkedList;
import java.util.List;

public class TableRow {

	List<TableCell> rowValues = new LinkedList<TableCell>();
	private String rowId;
	
	public TableRow(String...rowValues) {
		for (String rowValue : rowValues) {
			this.rowValues.add(new TableCell(rowValue));
		}
	}
	
	public TableRow(TableCell...rowValues) {
		for (TableCell rowValue : rowValues) {
			this.rowValues.add(rowValue);
		}
	}
	
	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
	public TableRow() {
		
	}

	public List<TableCell> getRowValues() {
		return rowValues;
	}

	public void setRowValues(List<String> rowValues) {
		for (String string : rowValues) {
			this.rowValues.add(new TableCell(string));			
		}
	}
	
	public void setRowComments(List<String> comments){
		for (int i = 0; i < comments.size(); i++) {
			rowValues.get(i).setComment(comments.get(i));
		}
	}
	
	public void setRowComments(String ... comments){
		for (int i = 0; i < comments.length; i++) {
			rowValues.get(i).setComment(comments[i]);
		}
	}

	public List<TableCell> getValues() {		
		return rowValues;
	}

}
