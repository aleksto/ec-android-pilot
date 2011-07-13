package com.ec.prod.android.pilot.service;

import java.util.Date;
import java.util.List;

public interface ViewServiceMarshalled {
		
	// Converts public List<Section> getSections();
	public List<String> getSections();
	
	//Converts public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution);
	public List<String> getTableData(String section, Date fromdate, Date toDate, int resolution);
	
	// Converts public GraphData getGraphData(GraphSection section, Date fromDate, Date toDate, int resolution);
	public List<String> getGraphDataBySection(String section, Date fromDate, Date toDate, int resolution);
	
	// Converts public GraphData getGraphData(TableRow row, Date fromDate, Date toDate, int resolution);
	public List<String> getGraphDataByRow(String row, Date fromDate, Date toDate, int resolution);
	
	// Converts public TextData getTextData(TextSection section, Date fromDate, Date toDate, int resolution);
	public List<String> getTextData(String section, Date fromDate, Date toDate, int resolution);	
}
