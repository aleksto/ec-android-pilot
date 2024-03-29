package com.ec.prod.android.pilot.model;

import java.util.LinkedList;
import java.util.List;

public class TableData {

	private List<TableRow> tableRows = new LinkedList<TableRow>();
	private List<TableColumn> tableColumns;
	
	public void setTableColumns(List<TableColumn> tableColumns) {
		this.tableColumns = tableColumns;
	}

	public TableData() {
		
	}
	
	public TableData(List<TableColumn> tableColumns) {
		this.tableColumns = tableColumns;
	}

	public List<TableColumn> getTableColumns() {		
		return tableColumns;
	}

	public List<TableRow> getTableRows() {		
		return tableRows;
	}

	public void addTableRow(TableRow tableRow) {
		tableRows.add(tableRow);		
	}

}
