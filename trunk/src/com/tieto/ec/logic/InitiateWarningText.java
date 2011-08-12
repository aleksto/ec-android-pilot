package com.tieto.ec.logic;

import com.tieto.ec.model.GraphWarning;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.CellWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class InitiateWarningText {

	private StringBuilder info;
	
	public StringBuilder getInfo(){
		return info;
	}
	
	public InitiateWarningText(SectionWarning sectionWarning){		
		info = new StringBuilder();
		initText(sectionWarning);
	}
	
	/**
	 * Initializes a {@link StringBuilder} and builds it up whit comments from the {@link SectionWarning} 
	 * @param sectionWarning
	 */
	public void initText(SectionWarning sectionWarning) {
		if(sectionWarning.getWarnings().get(0) instanceof GraphWarning){
			initGraphText(sectionWarning);
		}else if(sectionWarning.getWarnings().get(0) instanceof CellWarning){
			initTableText(sectionWarning);
		}
	}

	/**
	 * Builds the text for a Graph
	 * @param sectionWarning The {@link SectionWarning} containing the {@link GraphWarning}
	 */
	private void initGraphText(SectionWarning sectionWarning) {
		int index = 1;
		boolean first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			GraphWarning graphWarning = (GraphWarning) warning;
			if(warning.getType() == Type.CRITICAL && first){
				info.append("Critical:\n");
				info.append(index + ". " + graphWarning.getAttribute() + ", " + graphWarning.getTime() + ":\n" + graphWarning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.CRITICAL){
				info.append(index + ". " + graphWarning.getAttribute() + ", " + graphWarning.getTime() + ":\n" + graphWarning.getComment() + "\n\n");
				index++;
			}
		}


		first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			GraphWarning graphWarning = (GraphWarning) warning;
			if(warning.getType() == Type.WARNING && first){
				info.append("\nWarning:\n");
				info.append(index + ". " + graphWarning.getAttribute() + ", " + graphWarning.getTime() + ":\n" + graphWarning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.WARNING){
				info.append(index + ". " + graphWarning.getAttribute() + ", " + graphWarning.getTime() + ":\n" + graphWarning.getComment() + "\n\n");
				index++;
			}
		}
	}
	
	/**
	 * Builds the text for a table
	 * @param sectionWarning The {@link SectionWarning} containing the {@link TableWarning}
	 */
	private void initTableText(SectionWarning sectionWarning) {
		int index = 1;
		boolean first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			CellWarning tableWarning = (CellWarning) warning;
			if(warning.getType() == Type.CRITICAL && first){
				info.append("Critical:\n");
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getColumn() + "\n" + warning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.CRITICAL){
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getColumn() + "\n" + warning.getComment() + "\n\n");
				index++;
			}
		}


		first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			CellWarning tableWarning = (CellWarning) warning;
			if(warning.getType() == Type.WARNING && first){
				info.append("\nWarning:\n");
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getColumn() + "\n" + warning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.WARNING){
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getColumn() + "\n" + warning.getComment() + "\n\n");
				index++;
			}
		}
	}
}
