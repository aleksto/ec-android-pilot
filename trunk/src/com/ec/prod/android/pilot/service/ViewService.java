package com.tieto.frmw.service;

import java.util.Date;
import java.util.List;

import com.tieto.frmw.model.GraphData;
import com.tieto.frmw.model.GraphSection;
import com.tieto.frmw.model.Section;
import com.tieto.frmw.model.TableData;
import com.tieto.frmw.model.TableRow;
import com.tieto.frmw.model.TableSection;
import com.tieto.frmw.model.TextData;
import com.tieto.frmw.model.TextSection;


public interface ViewService {
	public List<Section> getSections();
	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution);
	public GraphData getGraphData(GraphSection section, Date fromDate, Date toDate, int resolution);
	public GraphData getGraphData(TableRow row, Date fromDate, Date toDate, int resolution);
	public TextData getTextData(TextSection section, Date fromDate, Date toDate, int resolution);
}
