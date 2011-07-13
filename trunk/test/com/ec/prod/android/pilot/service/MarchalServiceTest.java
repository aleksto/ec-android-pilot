package com.ec.prod.android.pilot.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextElement;
import com.ec.prod.android.pilot.model.TextSection;


public class MarchalServiceTest {
	
	@Test
	public void shouldHandleTextData() {
		TextData td = new TextData();
		Date d1 = new Date();
		Date d2 = new Date();
		TextElement t1 = new TextElement(d1, "Text1");
		TextElement t2 = new TextElement(d2, "Text2");
		td.addTextElement(t1);		 
		td.addTextElement(t2);
		List<String> tdMarshalled = MarshalService.marshalTextData(td);
		TextData tdUnMarshalled = MarshalService.unMarshalTextData(tdMarshalled);
		assertEquals("Text2", tdUnMarshalled.getTextElements().get(1).getText());		
	}
	
	@Test
	public void shouldHandleTableData() {
		TableData td = new TableData();
		List<TableColumn> tableColumns = new LinkedList<TableColumn>();
		TableColumn c1 = new TableColumn("Column1");
		TableColumn c2 = new TableColumn("Column2");
		TableColumn c3 = new TableColumn("Column3");
		tableColumns.add(c1);
		tableColumns.add(c2);
		tableColumns.add(c3);
		td.setTableColumns(tableColumns);
		TableRow r1 = new TableRow();
		r1.setRowId("1");
		List<String> r1Values = new LinkedList<String>();
		r1Values.add("R1:Value1");
		r1Values.add("R1:Value2");
		r1Values.add("R1:Value3");
		r1.setRowValues(r1Values);
		TableRow r2 = new TableRow();
		r2.setRowId("1");
		List<String> r2Values = new LinkedList<String>();
		r2Values.add("R2:Value1");
		r2Values.add("R2:Value2");
		r2Values.add("R2:Value3");
		r2.setRowValues(r2Values);
		TableRow r3 = new TableRow();
		r3.setRowId("1");
		List<String> r3Values = new LinkedList<String>();
		r3Values.add("R3:Value1");
		r3Values.add("R3:Value2");
		r3Values.add("R3:Value3");
		r3.setRowValues(r3Values);
		td.addTableRow(r1);
		td.addTableRow(r2);
		td.addTableRow(r3);
		List<String> mTableData = MarshalService.marshalTableData(td);
		TableData umTableData = MarshalService.unMarshalTableData(mTableData);
		assertEquals("R2:Value2", umTableData.getTableRows().get(1).getRowValues().get(1));
		assertEquals("Column3", umTableData.getTableColumns().get(2).getHeader());
	}
	
	@Test
	public void shouldHandleGraphData() {
		
	}
	
	@Test
	public void shouldHandleTextSection() {
		TextSection section = new TextSection();
		section.setSectionHeader("Header1");
		section.setSectionId(100);
		String mSection = MarshalService.marshalTextSection(section);
		TextSection umSection = MarshalService.unMarshalTextSection(mSection);
		assertEquals(100, umSection.getSectionId());
		assertEquals("Header1", umSection.getSectionHeader());
	}
	
	@Test
	public void shouldHandleGraphSection() {
		GraphSection section = new GraphSection();
		section.setSectionHeader("Header1");
		section.setSectionId(100);
		String mSection = MarshalService.marshalGraphSection(section);
		GraphSection umSection = MarshalService.unMarshalGraphSection(mSection);
		assertEquals(100, umSection.getSectionId());
		assertEquals("Header1", umSection.getSectionHeader());
	}
	
	@Test
	public void shouldHandleTableSection() {
		TableSection section = new TableSection();
		section.setSectionHeader("Header1");
		section.setSectionId(100);
		String mSection = MarshalService.marshalTableSection(section);
		TableSection umSection = MarshalService.unMarshalTableSection(mSection);
		assertEquals(100, umSection.getSectionId());
		assertEquals("Header1", umSection.getSectionHeader());
	}
}
