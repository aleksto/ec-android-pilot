package com.ec.prod.android.pilot.model;

public class TableCell {

	private final String value;
	private String comment;

	public TableCell(String value){
		this.value = value;		
	}

	public String getValue() {
		return value;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		if(comment == null){
			return "No comment";
		}
		return comment;
	}
}
