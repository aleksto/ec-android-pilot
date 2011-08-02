package com.tieto.ec.logic;

import java.util.List;

public class SectionWarning {

	private final String sectionTitle;
	private final List<Warning> warnings;

	public SectionWarning(String sectionTitle, List<Warning> warnings){
		this.sectionTitle = sectionTitle;
		this.warnings = warnings;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public List<Warning> getWarnings() {
		return warnings;
	}
	
	public boolean isOK(){
		return warnings.size() == 0;
	}
}
