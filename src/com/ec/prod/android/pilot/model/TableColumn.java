package com.tieto.frmw.model;

public class TableColumn {

	private String text;

	public TableColumn(String text) {
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHeader() {
		return text;
	}

}
