package com.tieto.ec.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Resolution;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextElement;
import com.ec.prod.android.pilot.model.TextSection;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.ColorType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;
import com.tieto.ec.gui.table.Cell;
import com.tieto.ec.listeners.dmr.GraphFullScreenListener;
import com.tieto.ec.listeners.dmr.GraphLineChooserListener;
import com.tieto.ec.listeners.dmr.ShowHideSection;
import com.tieto.ec.listeners.dmr.TableOptionDialogListener;
import com.tieto.ec.logic.DateConverter.Type;

public class SectionBuilder {

	private DailyMorningReport dmr;
	private Date date; 
	
	/**
	 * Initialize the {@link SectionBuilder}
	 * @param dmr {@link DailyMorningReport} for refreshing the report
	 */
	public SectionBuilder(DailyMorningReport dmr){
		this.dmr = dmr;
	}
	
	/**
	 * Gets the sections from the Report and builds up the UI.
	 */
	public void listSections(){
		dmr.getTable().removeAllViews();
		date = dmr.getDate();
		dmr.getCurrentdayLabel().setText(DateConverter.parse(date, Type.DATE));
		
		try {
			int resolution = Integer.valueOf(FileManager.readPath(dmr, "DMR Report.Resolution"));
			Date fromDate;
			switch (resolution) {
			case Resolution.DAILY:
				for (Section section : dmr.getSections()) {
					addSection(section, date, date, resolution);			
				}
				break;
			case Resolution.WEEKLY:
				fromDate = new Date(date.getYear(), date.getMonth(), date.getDate()-7);
				for (Section section : dmr.getSections()) {
					addSection(section, fromDate, date, resolution);			
				}
				break;
			case Resolution.MONTHLY:
				fromDate = new Date(date.getYear(), date.getMonth()-1, date.getDate());
				for (Section section : dmr.getSections()) {
					addSection(section, fromDate, date, resolution);			
				}
				break;
			case Resolution.YEARLY:
				fromDate = new Date(date.getYear()-1, date.getMonth(), date.getDate());
				for (Section section : dmr.getSections()) {
					addSection(section, fromDate, date, resolution);			
				}
				break;
			}
			dmr.setResolution(resolution);
		} catch (IOException e) {
			Log.d("tieto", "Setting default time and resolution");
			FileManager.writePath(dmr, "DMR Report.Resolution", "Daily");
			listSections();
			e.printStackTrace();
		}		
	}
	
	/**
	 * Adds a given section to the report, the section could be a {@link TextSection}, {@link TableSection} or {@link GraphSection}.
	 * @param section The section to add to the report
	 * @param fromdate From {@link Date} for the report
	 * @param toDate To {@link Date} for the report
	 * @param resolution Resolution for the report
	 */
	private void addSection(Section section, Date fromdate, Date toDate, int resolution){
		//Init
		TextView sectionTitle = new TextView(dmr);
		
		//Title
		sectionTitle.setText(section.getSectionHeader() + ":");
		sectionTitle.setTextSize(30);
		sectionTitle.setTextColor(dmr.getTextColor());
		sectionTitle.setTypeface(Typeface.create("arial", Typeface.NORMAL));
		sectionTitle.setPadding(0, 20, 0, 0);
		
		//Childs
		dmr.getTable().addView(sectionTitle);
		dmr.getTable().setPadding(10, 10, 10, 0);
		
		//Listeners
		sectionTitle.setOnClickListener(new ShowHideSection(section.getSectionHeader(), dmr.getTable()));
		
		//Values
		if(section instanceof TextSection){
			TextData textData = dmr.getWebservice().getTextData((TextSection)section, fromdate, toDate, resolution);				
			addTextData(textData, dmr.getTable(), sectionTitle);
		}
		else if(section instanceof TableSection){
			TableData tableData = dmr.getWebservice().getTableData((TableSection)section, fromdate, toDate, resolution);
			addTableData(tableData, dmr.getTable(), section.getSectionHeader(), sectionTitle);
		}
		else if(section instanceof GraphSection){
			GraphData graphData = dmr.getWebservice().getGraphDataBySection((GraphSection)section, fromdate, toDate, resolution);	
			addGraphData(graphData, dmr.getTable(), section.getSectionHeader(), sectionTitle);
		}
	}

	/**
	 * Adds a graph section to the report from a given {@link GraphData}
	 * @param graphData The given {@link GraphData}
	 * @param sectionTable the table {@link TableLayout} to add the section to
	 * @param sectionTitle Needed in case of adding a {@link OnLongClickListener} to the title {@link View}
	 */
	private void addGraphData(GraphData graphData, TableLayout sectionTable, String title, TextView sectionTitle) {
		//Init
		Graph graph = null;
		
		//Add data
		if(graphData.getGraphPoints().size()>1){
			//Line Graph
			graph = new LineGraph(dmr, "");
			((LineGraph) graph).add(graphData, title);			
			sectionTitle.setOnLongClickListener(new GraphLineChooserListener(dmr, graph, title));
		}else{
			//Bar graph
			graph = new BarGraph(dmr, "", Color.GREEN);
			((BarGraph) graph).add(graphData);	
		}
		
		//LayoutParams
		int width = dmr.getWindowManager().getDefaultDisplay().getWidth();
		graph.setLayoutParams(new LayoutParams(width-20, 200));
		
		//Childs
		sectionTable.addView(graph);
		
		//Listener
		graph.setOnClickListener(new GraphFullScreenListener(dmr, graph, title));
	}

	/**
	 * Adds a table section to the report from a given {@link TableData}
	 * @param tableData The given {@link TableData}
	 * @param sectionTable the table {@link TableLayout} to add the section to
	 * @param sectionTitle Needed in case of adding a {@link OnLongClickListener} to the title {@link View}
	 */
	private void addTableData(TableData tableData, TableLayout sectionTable, String title, TextView sectionTitle) {
		//Init
		HorizontalScrollView hScroll = new HorizontalScrollView(dmr);
		TableLayout table = new TableLayout(dmr);
		List<com.ec.prod.android.pilot.model.TableRow> tableRows = tableData.getTableRows();
		
		//Table
		table.setStretchAllColumns(true);
		table.setBackgroundColor(dmr.getBackgroundColor());
		
		//Active columns
		ArrayList<String> activeColumns = activeColumns(title, tableData);
		
		//Listener
		sectionTitle.setOnLongClickListener(new TableOptionDialogListener(dmr, title, tableData));
		
		//Headers
		List<TableColumn> tableColumns = tableData.getTableColumns();
		TableRow headerRow = new TableRow(dmr);
		for (TableColumn column: tableColumns) {
			//Init
			if(activeColumns.contains(column.getHeader())){
				headerRow.addView(new Cell(dmr, column.getHeader(), dmr.getCellBackgroundColor(), dmr.getCellTextColor(), dmr.getCellBorderColor()));				
			}
		}
		table.addView(headerRow);
		
		//Rows
		for (com.ec.prod.android.pilot.model.TableRow tableRow : tableRows) {
			//Init
			TableRow row = new TableRow(dmr);
			List<String> values = tableRow.getValues();
			
			for (int i = 0; i < values.size(); i++) {
				//Add Cell
				String header = tableColumns.get(i).getHeader();
				if(activeColumns.contains(header)){
					row.addView(new Cell(dmr, values.get(i), dmr.getCellBackgroundColor(), dmr.getCellTextColor(), dmr.getCellBorderColor()));					
				}
			}
			
			//Childs
			table.addView(row);
		}
		
		//Childs
		hScroll.addView(table);
		sectionTable.addView(hScroll);
	}

	/**
	 * Adds a text section to the report from a given {@link TextData}
	 * @param textData The given {@link TextData}
	 * @param sectionTable the table {@link TableLayout} to add the section to
	 * @param sectionTitle Needed in case of adding a {@link OnLongClickListener} to the title {@link View}
	 */
	private void addTextData(TextData textData, TableLayout sectionTable, TextView sectionTitle) {
		TableLayout table = new TableLayout(dmr);
		List<TextElement> textElements = textData.getTextElements();
		for (TextElement text : textElements) {
			TextView view = new TextView(dmr);
			view.setText(DateConverter.parse(text.getDaytime(), DateConverter.Type.TIME) + "\n" + text.getText() + "\n");
			view.setTextColor(dmr.getTextColor());
			view.setTypeface(Typeface.create("arial", Typeface.NORMAL));
			table.addView(view);
		}
		sectionTable.addView(table);
	}

	/**
	 * Creates a {@link ArrayList} of Strings containing the active columns headers of a given table.
	 * @param title the title of the table
	 * @param tableData the {@link TableData} of the table
	 * @return {@link ArrayList} of active columns
	 */
	private ArrayList<String> activeColumns(String title, TableData tableData){
		Log.d("tieto", "Checking active columns for table " + title);
		ArrayList<String> columns = new ArrayList<String>();
		try {
			List<TableColumn> columnsList = tableData.getTableColumns();
			for (int i = 0; i < columnsList.size(); i++) {
				boolean active = Boolean.valueOf(FileManager.readPath(dmr, title + "." + columnsList.get(i).getHeader()));
				
				if(active){
					columns.add(columnsList.get(i).getHeader());
				}
			}
			
			return columns;
		} catch (IOException e) {
			List<TableColumn> tableColumns = tableData.getTableColumns();
			
			Log.d("tieto", "Setting active columns: All");
			
			for (TableColumn tableColumn : tableColumns) {
				FileManager.writePath(dmr,  title + "." + tableColumn.getHeader(), "true");
			}			
			
			return activeColumns(title, tableData);
		}
	}

	/**
	 * Updates the colors of the report, but doesn't update the UI {@link listSections()} 
	 */
	public void updateColors(){
		String basePath = OptionTitle.DMRReport + "." + OptionTitle.ColorOptions + ".";
		try {
			dmr.setBackgroundColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.BackgroundColor)));
			dmr.setTextColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.TextColor)));
			dmr.setCellTextColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.TextColor)));
			dmr.setCellBackgroundColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.CellBackgroundColor)));
			dmr.setCellBorderColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.CellBorderColor)));
		} catch (IOException e) {
			Log.d("tieto", "Setting default color");
			dmr.setBackgroundColor(Color.BLACK);
			dmr.setTextColor(Color.GRAY);
			dmr.setCellBackgroundColor(Color.WHITE);
			dmr.setCellTextColor(Color.BLACK);
			dmr.setCellBorderColor(Color.BLACK);
			FileManager.writePath(dmr, basePath + OptionTitle.BackgroundColor, ColorType.LightBlue.toString());
			FileManager.writePath(dmr, basePath + OptionTitle.TextColor, ColorType.Black.toString());
			FileManager.writePath(dmr, basePath + OptionTitle.CellBackgroundColor, ColorType.White.toString());
			FileManager.writePath(dmr, basePath + OptionTitle.CellBorderColor, ColorType.Black.toString());
			e.printStackTrace();
		}
		
		dmr.getScrollView().setBackgroundColor(dmr.getBackgroundColor());
		dmr.getTable().setBackgroundColor(dmr.getBackgroundColor());
	}
}