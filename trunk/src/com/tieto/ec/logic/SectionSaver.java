package com.tieto.ec.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ec.prod.android.pilot.model.DataType;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextSection;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.ec.activities.DailyMorningReport;

public class SectionSaver implements ViewService{

	public enum Location{ACTUAL, TARGET};
	

	private final DailyMorningReport dmr;
	private HashMap<Section, Object> dataActual;
	private HashMap<Section, Object> dataTarget;
	
	/**
	 * Creates a new SectionSaver, this class is used for storing {@link Section}
	 * @param dmr
	 */
	public SectionSaver(DailyMorningReport dmr){
		this.dmr = dmr;
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


	/**
	 * @return The {@link Section} listed in {@link DailyMorningReport}
	 */
	public List<Section> getSections() {
		return dmr.getSections();
	}


	/**
	 * @param section The section to load
	 * @param fromdate This is not used
	 * @param toDate This is not used
	 * @param type The location of the saved data
	 * @see DataType
	 * @return {@link TableData} for the given {@link Section}
	 */
	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution, int type) {
		if(type == DataType.ACTUAL){			
			return (TableData) load(section, Location.ACTUAL);
		}else if(type == DataType.TARGET){
			return (TableData) load(section, Location.TARGET);
		}
		return null;
	}
	
	/**
	 * @param section The section to load
	 * @param fromdate This is not used
	 * @param toDate This is not used
	 * @param type The location of the saved data
	 * @see DataType
	 * @return {@link GraphData} for the given {@link Section}
	 */

	public GraphData getGraphDataBySection(GraphSection section, Date fromDate, Date toDate, int resolution, int type) {
		if(type == DataType.ACTUAL){			
			return (GraphData) load(section, Location.ACTUAL);
		}else if(type == DataType.TARGET){
			return (GraphData) load(section, Location.TARGET);
		}
		return null;
	}
	
	/**
	 * This is not yet implemented
	 */

	public GraphData getGraphDataByRow(TableRow row, Date fromDate,	Date toDate, int resolution, int type) {
		//FIXME
		return null;
	}
	
	/**
	 * @param section The section to load
	 * @param fromdate This is not used
	 * @param toDate This is not used
	 * @param type The location of the saved data
	 * @see DataType
	 * @return {@link TextData} for the given {@link Section}
	 */

	public TextData getTextData(TextSection section, Date fromDate,	Date toDate, int resolution) {
		//This section does not have any target, so Location is always ACTUAL
		return (TextData) load(section, Location.ACTUAL);
	}
}
