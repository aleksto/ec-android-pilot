package com.tieto.ec.logic;

import java.util.HashMap;

import android.util.Log;

import com.ec.prod.android.pilot.model.Section;

public class SectionSaver {

	public enum Location{ACTUAL, TARGET};
	
	private HashMap<Section, Object> dataActual;
	private HashMap<Section, Object> dataTarget;
	
	public SectionSaver(){
		//Init 
		dataActual = new HashMap<Section, Object>();
		dataTarget = new HashMap<Section, Object>();
	}
	
	/**
	 * Returns true if the given section has been saved
	 * @param section The given {@link Section}
	 * @param location Save location, either ACTUAL or TARGET
	 * @return True if the section has been saved
	 */
	public boolean isSaved(Section section, Location location){
		switch (location) {
		case ACTUAL:
			return dataActual.containsKey(section);
		case TARGET:
			return dataTarget.containsKey(section);
		}
		return false;
	}
	
	/**
	 * Loads the data for a {@link Section}
	 * @param section The given {@link Section}
	 * @param location Location, either ACTUAL or TARGET
	 * @return The save data, or null if the section has not been saved
	 */
	public Object load(Section section, Location location){
		switch (location) {
		case ACTUAL:
			Log.d("tieto", "DATA: " + dataActual.get(section));
			return dataActual.get(section);
		case TARGET:
			return dataTarget.get(section);
		}
		return null;
	}
	
	/**
	 * Saves the given data for a {@link Section}
	 * @param section The {@link Section} for saving
	 * @param data The data to save
	 * @param location {@link Location} for the save data
	 */
	public void save(Section section, Object data, Location location){
		switch (location) {
		case ACTUAL:
			dataActual.put(section, data);
		case TARGET:
			dataTarget.put(section, data);
		}
	}
}
