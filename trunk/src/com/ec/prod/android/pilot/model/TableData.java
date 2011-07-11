package com.tieto.frmw.model;

import java.util.LinkedList;
import java.util.List;

public class TableData {

	private List<TableRow> tableRows = new LinkedList<TableRow>();
	private List<TableColumn> tableColumns;
	

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
