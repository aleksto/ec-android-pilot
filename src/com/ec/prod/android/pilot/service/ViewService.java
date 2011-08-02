package com.ec.prod.android.pilot.service;

import java.util.Date;
import java.util.List;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextSection;

public interface ViewService {
	
	public List<Section> getSections();

	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution, int type);
	
	public GraphData getGraphDataBySection(GraphSection section, Date fromDate, Date toDate, int resolution, int type);
	
	public GraphData getGraphDataByRow(TableRow row, Date fromDate, Date toDate, int resolution, int type);
	
	public TextData getTextData(TextSection section, Date fromDate, Date toDate, int resolution);	
	
	public void clearSaveData();
}
