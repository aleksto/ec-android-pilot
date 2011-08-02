package com.tieto.ec.logic;

import java.util.HashMap;
import java.util.TreeMap;

import android.util.Log;

import com.ec.prod.android.pilot.model.Section;

public class SectionSaver {

	public enum Location{ACTUAL, TARGET};
	
	private HashMap<Section, Object> dataActual = new HashMap<Section, Object>();
	private HashMap<Section, Object> dataTarget = new HashMap<Section, Object>();
	
	public boolean isSaved(Section section, Location location){
		switch (location) {
		case ACTUAL:
			Log.d("tieto", section.getSectionHeader() + " is saved: " + dataActual.containsKey(section));
			return dataActual.containsKey(section);
		case TARGET:
			Log.d("tieto", section.getSectionHeader() + " is saved: " + dataTarget.containsKey(section));
			return dataTarget.containsKey(section);
		}
		return false;
	}
	
	public Object load(Section section, Location location){
		switch (location) {
		case ACTUAL:
			return dataActual.get(section);
		case TARGET:
			return dataTarget.get(section);
		}
		return null;
	}
	
	public void save(Section section, Object data, Location location){
		switch (location) {
		case ACTUAL:
			Object put = dataActual.put(section, data);
			if(put != null){
				Log.d("tieto", put.toString());
			}
		case TARGET:
			Object put2 = dataTarget.put(section, data);
			if(put2 != null){
				Log.d("tieto", put2.toString());
			}
		}
	}
}
