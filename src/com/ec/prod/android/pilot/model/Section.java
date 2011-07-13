package com.ec.prod.android.pilot.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Section {

	public static int assignSectionId = 1;

	private String sectionHeader;

	private int sectionId;
	public Section() {

	}

	public Section(String sectionHeader) {
		this.sectionHeader = sectionHeader;
		this.sectionId = assignSectionId;
		assignSectionId++;
	}

	public String getSectionHeader() {
		return sectionHeader;
	}

	public int getSectionId() {
		return sectionId;
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

	}

	public void setSectionHeader(String sectionHeader) {
		this.sectionHeader = sectionHeader;

	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {

	}
}
