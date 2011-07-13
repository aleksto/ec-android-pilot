package com.ec.prod.android.pilot.service;


import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Resolution;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextElement;
import com.ec.prod.android.pilot.model.TextSection;

public class ViewServiceTest {

	private static ViewService service;
	
	@BeforeClass
	public static void setUp() throws Exception {
		service = new DMRViewService(); 
	}	
	
	@Test
	public void shouldRetrieveSections() {
		List<Section> sections = service.getSections();
		Section section = sections.get(1);
		assertEquals("Well Status", section.getSectionHeader());
		assertEquals(2, section.getSectionId());
		section = sections.get(3);
		assertEquals(4, section.getSectionId());
	}
	
	@Test
	public void shouldGetDailyTableData() {
		List<Section> sections = service.getSections();
		TableSection section = (TableSection)sections.get(1);
		Date date = Calendar.getInstance().getTime();
		TableData tableData = service.getTableData(section, null, date, Resolution.DAILY);
		List<TableColumn> tableColumns = tableData.getTableColumns();		
		TableColumn column1 = tableColumns.get(0);
		TableColumn column2 = tableColumns.get(1);
		TableColumn column3 = tableColumns.get(2);
		TableColumn column4 = tableColumns.get(3);
		TableColumn column5 = tableColumns.get(4);
		TableColumn column6 = tableColumns.get(5);
		TableColumn column7 = tableColumns.get(6);
		assertEquals(column1.getHeader(), "Well");
		assertEquals(column2.getHeader(), "On Stream Hours");
		assertEquals(column3.getHeader(), "AVG Well Head Temperature");
		assertEquals(column4.getHeader(), "AVG Well Head Pressure");
		assertEquals(column5.getHeader(), "Allocated Oil Volume");
		assertEquals(column6.getHeader(), "Allocated Gas Volume");
		assertEquals(column7.getHeader(), "Allocated Water Volume");
		List<TableRow> tableRows = tableData.getTableRows();
		TableRow row1 = tableRows.get(0);
		List<String> rowValues = row1.getValues();
		assertEquals("Well PH65R", rowValues.get(0));		
		assertEquals("159", rowValues.get(3));
		TableRow row2 = tableRows.get(1);
		rowValues = row2.getValues();
		assertEquals("Well PR87D", rowValues.get(0));		
		assertEquals("895.0", rowValues.get(5));
	}
	
	@Test
	public void shouldGetDailyTextData() {
		List<Section> sections = service.getSections();
		TextSection section = (TextSection)sections.get(0);
		Date date = Calendar.getInstance().getTime();
		TextData textData = service.getTextData(section, date, null, Resolution.DAILY);
		List<TextElement> textElements = textData.getTextElements();
		TextElement element = textElements.get(1);
		assertEquals("Just kidding", element.getText());
	}
	
	@Test
	public void shouldGetDailyGraphData() {
		GraphSection graphSection = (GraphSection)service.getSections().get(5);
		Date date = Calendar.getInstance().getTime();
		GraphData graphData = service.getGraphDataBySection(graphSection, null, date, Resolution.DAILY);
		List<GraphPoint> graphPoints = graphData.getGraphPoints();
		GraphPoint point1 = graphPoints.get(0);
		Date daytime = point1.getDaytime();		
		String oilValue = point1.getValue("OIL");
		String gasValue = point1.getValue("GAS");
		String waterValue = point1.getValue("WATER");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals("2011-07-06", sdf.format(daytime));
		assertEquals("8000", oilValue);
		assertEquals("5000", gasValue);
		assertEquals("2000", waterValue);
	}
	
	@Test
	public void shouldGetPointValueNames() {
		GraphSection graphSection = (GraphSection)service.getSections().get(5);
		Date date = Calendar.getInstance().getTime();
		GraphData graphData = service.getGraphDataBySection(graphSection, null, date, Resolution.DAILY);
		List<String> pointAttributes = graphData.getPointAttributes();
		assertEquals("OIL", pointAttributes.get(0));
		assertEquals("GAS", pointAttributes.get(1));
		assertEquals("WATER", pointAttributes.get(2));
	}
	

}
