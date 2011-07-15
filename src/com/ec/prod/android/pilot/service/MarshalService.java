package com.ec.prod.android.pilot.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

// Class (and architecture could be substituted by readObject / writeObject on Serializable model objects)
public class MarshalService {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final String delimiter = ";";	

	public static List<String> marshalGraphData(GraphData graphData) {
		List<String> graphDataExpression = new LinkedList<String>();
		
		// Handles attributes
		List<String> attributes = graphData.getPointAttributes();
		String attributeString = "";
		for (String attribute : attributes) {
			attributeString += attribute + delimiter;
		}
		graphDataExpression.add(attributeString);
		
		// Handles points
		List<GraphPoint> points = graphData.getGraphPoints();
		for (GraphPoint point : points) {			
			String pointExpression = "";
			Date daytime = point.getDaytime();
			pointExpression += sdf.format(daytime) + delimiter;			
			Map<String, String> values = point.getValues();
			for (String key : values.keySet()) {
				String keyValue = values.get(key);
				pointExpression += key + "#" + keyValue + delimiter;
			}
			graphDataExpression.add(pointExpression);					
		}
		return graphDataExpression;
		
	}

	public static String marshalGraphSection(GraphSection section) {
		String sectionHeader = section.getSectionHeader();
		int sectionId = section.getSectionId();
		return sectionId + delimiter + sectionHeader;
	}

	public static List<String> marshalSections(List<Section> sections) {
		List<String> sectionsAsString = new LinkedList<String>();
		for (Section section : sections) {
			String sectionHeader = section.getSectionHeader();
			int sectionId = section.getSectionId();
			String sectionType = "";
			if (section instanceof TextSection) {
				sectionType = "TEXT";
			} else if (section instanceof TableSection) {
				sectionType = "TABLE";
			} else if (section instanceof GraphSection) {
				sectionType = "GRAPH";
			}
			sectionsAsString.add(sectionId + delimiter + sectionHeader + delimiter + sectionType);
		}
		return sectionsAsString;
	}

	public static List<String> marshalTableData(TableData tableData) {
		List<String> mTableData = new LinkedList<String>();
		List<TableColumn> columns = tableData.getTableColumns();
		String columnsExpression = "";
		for (TableColumn column : columns) {
			columnsExpression += column.getHeader() + delimiter;
		}
		mTableData.add(columnsExpression);
		List<TableRow> rows = tableData.getTableRows();		
		for (TableRow row : rows) {
			mTableData.add(marshalTableRow(row));
		}
		return mTableData;
	}

	public static String marshalTableRow(TableRow row) {
		String rowString = "";
		String rowId = row.getRowId();
		List<String> values = row.getRowValues();
		rowString = rowId + delimiter;
		for(String value : values) {
			rowString += value + delimiter;
		}
		return rowString;
	}

	public static String marshalTableSection(TableSection section) {
		String sectionHeader = section.getSectionHeader();
		int sectionId = section.getSectionId();
		return sectionId + delimiter + sectionHeader;
	}

	public static List<String> marshalTextData(TextData textData) {
		List<String> data = new LinkedList<String>();
		List<TextElement> elements = textData.getTextElements();
		for (TextElement element : elements) {
			String stringElement = "";
			Date daytime = element.getDaytime();
			String text = element.getText();
			stringElement = sdf.format(daytime) + delimiter + text;
			data.add(stringElement);
		}
		return data;
	}

	public static String marshalTextSection(Section section) {
		String sectionHeader = section.getSectionHeader();
		int sectionId = section.getSectionId();
		return sectionId + delimiter + sectionHeader;
	}

	public static GraphData unMarshalGraphData(List<String> graphData) {
		GraphData graphDataObject = new GraphData();
		
		// Handles attributes
		String attributesExpression = graphData.get(0);
		String[] attributes = attributesExpression.split(delimiter);		
		graphDataObject.setPointAttributes(attributes);
		
		// Handles points
		for (int i = 1; i < graphData.size(); i++) {			
			String graphPointExpression = graphData.get(i);
			String[] pointValues = graphPointExpression.split(delimiter);
			Date daytime;
			try {
				daytime = sdf.parse(pointValues[0]);
			} catch (ParseException e) {
				throw new IllegalStateException("Could not parse daytime out of Graph Data string", e);
			}
			GraphPoint point = new GraphPoint(daytime);			
			for (int t = 1; t < pointValues.length; t++) {
				String[] valueSet = pointValues[t].split("#");
				point.addValue(valueSet[0], valueSet[1]);
			}			
			graphDataObject.addGraphPoint(point);			
		}		
		return graphDataObject;
	}

	public static GraphSection unMarshalGraphSection(String section) {
		String[] values = section.split(delimiter);
		GraphSection graphSection = new GraphSection();
		graphSection.setSectionHeader(values[1]);
		graphSection.setSectionId(Integer.parseInt(values[0]));
		return graphSection;
	}

	public static List<Section> unMarshalSections(List<String> sections) {
		List<Section> sectionObjects = new LinkedList<Section>();
		for (String section : sections) {
			String[] values = section.split(delimiter);
			Section sectionObject = null;
			if (values[2].equals("TEXT")) {
				sectionObject = new TextSection();
			} else if (values[2].equals("TABLE")) {
				sectionObject = new TableSection();
			} else if (values[2].equals("GRAPH")) {
				sectionObject = new GraphSection();
			}	
			sectionObject.setSectionHeader(values[1]);
			sectionObject.setSectionId(Integer.parseInt(values[0]));
			sectionObjects.add(sectionObject);
		}
		return sectionObjects;
	}

	public static TableData unMarshalTableData(List<String> tableData) {
		TableData tableDataObject = new TableData();
		
		// Handles columns
		List<TableColumn> tableColumns = new LinkedList<TableColumn>();
		String columns = tableData.get(0);
		String[] columnTexts = columns.split(delimiter);
		for (String columnText : columnTexts) {
			TableColumn tableColumn = new TableColumn();
			tableColumn.setText(columnText);
			tableColumns.add(tableColumn);
		}
		tableDataObject.setTableColumns(tableColumns);
		
		// Handles row values		
		for (int i = 1; i < tableData.size(); i++) {
			TableRow tableRow = unMarshalTableRow(tableData.get(i));
			tableDataObject.addTableRow(tableRow);
		}
		return tableDataObject;
	}

	public static TableRow unMarshalTableRow(String row) {
		TableRow tableRow = new TableRow();
		String[] values = row.split(delimiter);
		tableRow.setRowId(values[0]);
		List<String> rowValues = new LinkedList<String>();
		for (int i = 1; i < values.length; i++) {
			rowValues.add(values[i]);
		}
		tableRow.setRowValues(rowValues);
		return tableRow;
	}

	public static TableSection unMarshalTableSection(String section) {
		String[] values = section.split(delimiter);
		TableSection tableSection = new TableSection();
		tableSection.setSectionHeader(values[1]);
		tableSection.setSectionId(Integer.parseInt(values[0]));
		return tableSection;
	}

	public static TextData unMarshalTextData(List<String> textData) {
		TextData data = new TextData();
		for (String element : textData) {
			TextElement textElement = new TextElement();
			String[] values = element.split(delimiter);
			try {
				textElement.setDaytime(sdf.parse(values[0]));
			} catch (ParseException e) {
				throw new IllegalStateException("Could not handle daytime when converting text data elements");
			}
			textElement.setText(values[1]);
			data.addTextElement(textElement);
		}
		return data;
	}

	public static TextSection unMarshalTextSection(String section) {
		String[] values = section.split(delimiter);
		TextSection textSection = new TextSection();
		textSection.setSectionHeader(values[1]);
		textSection.setSectionId(Integer.parseInt(values[0]));
		return textSection;
	}

}
