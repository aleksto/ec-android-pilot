package com.tieto.ec.model;

import java.util.List;

import com.ec.prod.android.pilot.model.Section;

public class SectionWarning {

	private final String sectionTitle;
	private final List<Warning> warnings;

	/**
	 * Creates a new {@link SectionWarning}, this class represents a section that can have 0 or more {@link Warning}
	 * @param sectionTitle The title of the {@link Section}
	 * @param warnings The {@link Warning}s the section may have
	 */
	public SectionWarning(String sectionTitle, List<Warning> warnings){
		this.sectionTitle = sectionTitle;
		this.warnings = warnings;
	}

	/**
	 * @return The title of the section
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}

	/**
	 * @return A list of warnings the section may have
	 */
	public List<Warning> getWarnings() {
		return warnings;
	}
	
	/**
	 * @return True if the section has no warnings
	 */
	public boolean isOK(){
		return warnings.size() == 0;
	}
}
