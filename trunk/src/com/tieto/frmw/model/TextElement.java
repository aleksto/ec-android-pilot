package com.tieto.frmw.model;

import java.util.Date;

public class TextElement {
	private Date daytime;
	private String text;

	public TextElement(Date daytime, String text) {
		this.daytime = daytime;
		this.text = text;
	}

	public Date getDaytime() {
		return daytime;
	}

	public void setDaytime(Date daytime) {
		this.daytime = daytime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
