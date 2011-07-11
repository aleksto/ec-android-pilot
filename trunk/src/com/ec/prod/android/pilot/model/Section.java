package com.ec.prod.android.pilot.model;

public abstract class Section {
	
	public static int assignSectionId = 1;
	
	private String sectionHeader;
	private int sectionId;

	public String getSectionHeader() {
		return sectionHeader;
	}

	public void setSectionHeader(String sectionHeader) {
		this.sectionHeader = sectionHeader;
		
	}

	public Section(String sectionHeader) {
		this.sectionHeader = sectionHeader;
		this.sectionId = assignSectionId;
		assignSectionId++;
	}

	public int getSectionId() {
		return sectionId;
	}
}
