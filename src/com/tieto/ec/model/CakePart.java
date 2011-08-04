package com.tieto.ec.model;

public class CakePart {

	private double value;
	private String title;
	private int color;

	public CakePart(String title, double value, int color) {
		this.color = color;
		this.setTitle(title);
		this.setValue(value);
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setColor(int color){
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
}
