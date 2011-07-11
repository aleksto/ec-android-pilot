package com.ec.prod.android.pilot.model;

import java.util.LinkedList;
import java.util.List;

public class TableRow {

	List<String> rowValues = new LinkedList<String>();
	private String rowID;
	
	public TableRow(String...rowValues) {
		for (String rowValue : rowValues) {
			this.rowValues.add(rowValue);
		}
	}

	public List<String> getValues() {		
		return rowValues;
	}

	public void setRowID(String ID){
		this.rowID = ID;
	}

	public String getRowID(){
		return rowID;
	}
}
