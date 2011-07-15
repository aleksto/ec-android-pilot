package com.ec.prod.android.pilot.service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextElement;
import com.ec.prod.android.pilot.model.TextSection;


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
		
		for (int i = 0; i < 10; i++) {
			c.set(2011, Calendar.JULY, (i+3));
			Date daytime = c.getTime();
			GraphPoint graphPoint1 = new GraphPoint(daytime);
			graphPoint1.addValue("OIL", "" + Math.random()*10000);
			graphPoint1.addValue("GAS", "" + Math.random()*5000);
			graphPoint1.addValue("WATER", "" + Math.random()*2000);
			data.addGraphPoint(graphPoint1);
		}
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

	public GraphData getGraphDataBySection(GraphSection section, Date fromDate,
			Date toDate, int resolution) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphData getGraphDataByRow(TableRow row, Date fromDate,
			Date toDate, int resolution) {
		// TODO Auto-generated method stub
		return null;
	}
}
