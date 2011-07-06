package com.tieto.frmw.service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.tieto.frmw.model.GraphData;
import com.tieto.frmw.model.GraphPoint;
import com.tieto.frmw.model.GraphSection;
import com.tieto.frmw.model.Section;
import com.tieto.frmw.model.TableColumn;
import com.tieto.frmw.model.TableData;
import com.tieto.frmw.model.TableRow;
import com.tieto.frmw.model.TableSection;
import com.tieto.frmw.model.TextData;
import com.tieto.frmw.model.TextElement;
import com.tieto.frmw.model.TextSection;


public class ExampleViewService implements ViewService {

	public List<Section> getSections() {
		List<Section> sectionList = new LinkedList<Section>();
		sectionList.add(new TextSection("Operational Comments"));
		sectionList.add(new TableSection("Well Status"));
		sectionList.add(new TableSection("Stream Status"));
		sectionList.add(new TableSection("Deferments"));
		sectionList.add(new TableSection("Equipment"));
		sectionList.add(new GraphSection("Export Curves"));
		return sectionList;
	}

	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution) {
		List<TableColumn> tableColumns = new LinkedList<TableColumn>();
		tableColumns.add(new TableColumn("Well"));
		tableColumns.add(new TableColumn("On Stream Hours"));
		tableColumns.add(new TableColumn("AVG Well Head Temperature"));
		tableColumns.add(new TableColumn("AVG Well Head Pressure"));
		tableColumns.add(new TableColumn("Allocated Oil Volume"));
		tableColumns.add(new TableColumn("Allocated Gas Volume"));
		tableColumns.add(new TableColumn("Allocated Water Volume"));				
		TableData data = new TableData(tableColumns);
		data.addTableRow(new TableRow("Well PH65R", "24", "60", "159", "5595.5", "656.8", "300.2"));
		data.addTableRow(new TableRow("Well PR87D", "24", "62", "160", "6008.8", "895.0", "256.7"));
		data.addTableRow(new TableRow("Well PP12A", "23", "54", "180", "3789.0", "902.1", "189.4"));
		return data;
	}

	public GraphData getGraphData(GraphSection section, Date fromDate, Date toDate, int resolution) {
		GraphData data = new GraphData();
		data.setPointAttributes("OIL", "GAS", "WATER");
		Calendar c = Calendar.getInstance();
		c.set(2011, Calendar.JULY, 6);
		Date daytime = c.getTime();
		GraphPoint graphPoint1 = new GraphPoint(daytime);
		graphPoint1.addValue("OIL", "8000");
		graphPoint1.addValue("GAS", "5000");
		graphPoint1.addValue("WATER", "2000");
		data.addGraphPoint(graphPoint1);
		return data;
	}

	public TextData getTextData(TextSection section, Date fromDate, Date toDate, int resolution) {
		TextData textData = new TextData();
		Date today = Calendar.getInstance().getTime();		
		textData.addTextElement(new TextElement(today, "Facility not producing optimally"));
		textData.addTextElement(new TextElement(today, "Just kidding"));					
		return textData;
	}

	
	public GraphData getGraphData(TableRow row, Date fromDate, Date toDate, int resolution) {
		// TODO Auto-generated method stub
		return null;
	}

}
