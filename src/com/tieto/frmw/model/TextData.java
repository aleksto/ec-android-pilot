package com.tieto.frmw.model;

import java.util.LinkedList;
import java.util.List;

public class TextData {

	private List<TextElement> textElements = new LinkedList<TextElement>();

	public void setTextElements(List<TextElement> textElements) {
		this.textElements = textElements;
	}

	public List<TextElement> getTextElements() {
		return textElements;
	}
	
	public void addTextElement(TextElement textElement) {
		textElements.add(textElement); 
	}
	
}
