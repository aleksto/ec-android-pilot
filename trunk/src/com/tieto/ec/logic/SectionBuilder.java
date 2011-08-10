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

import com.ec.prod.android.pilot.model.DataType;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Resolution;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableCell;
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
import com.tieto.ec.listeners.dmr.CellListener;
import com.tieto.ec.listeners.dmr.GraphFullScreenListener;
import com.tieto.ec.listeners.dmr.GraphLineChooserListener;
import com.tieto.ec.listeners.dmr.ShowHideSection;
import com.tieto.ec.listeners.dmr.TableOptionDialogListener;
import com.tieto.ec.logic.DateConverter.Type;
import com.tieto.ec.logic.SectionSaver.Location;

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
	public void listSections(boolean newWebserviceValues){
		dmr.getTable().removeAllViews();
		date = dmr.getToDate();
		dmr.getCurrentdayLabel().setText(DateConverter.parse(date, Type.DATE, dmr.getResolution()));

		int resolution = dmr.getResolution();
		Log.d("tieto", "Listing sections with resolution " + ResolutionConverter.convert(resolution));
		Date fromDate = DateIntervalCalculator.calcFromDate(date, resolution);
	
		for (Section section : dmr.getSections()) {
			addSection(section, date, date, resolution, newWebserviceValues);			
		}

		dmr.setFromDate(fromDate);	
	}

	/**
	 * Adds a given section to the report, the section could be a {@link TextSection}, {@link TableSection} or {@link GraphSection}.
	 * @param section The section to add to the report
	 * @param fromdate From {@link Date} for the report
	 * @param toDate To {@link Date} for the report
	 * @param resolution Resolution for the report
	 * @param newWebserviceValues 
	 */
	private void addSection(Section section, Date fromdate, Date toDate, int resolution, boolean newWebserviceValues){
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
			TextData textData = null;
			if(newWebserviceValues){
				textData = dmr.getWebservice().getTextData((TextSection)section, fromdate, toDate, resolution);	
				dmr.getSaveManager().save(section, textData, Location.ACTUAL);
			}else{
				if(dmr.getSaveManager().isSaved(section, Location.ACTUAL)){ 
					textData = (TextData) dmr.getSaveManager().load(section, Location.ACTUAL);
				}else{
					textData = dmr.getWebservice().getTextData((TextSection)section, fromdate, toDate, resolution);	
					dmr.getSaveManager().save(section, textData, Location.ACTUAL);
				}
			}
			addTextData(textData, dmr.getTable(), sectionTitle);
		}
		else if(section instanceof TableSection){
			TableData tableDataActual;
			TableData tableDataTarget;

			if(newWebserviceValues){
				tableDataActual = dmr.getWebservice().getTableData((TableSection)section, fromdate, toDate, resolution, DataType.ACTUAL);	
				tableDataTarget = dmr.getWebservice().getTableData((TableSection)section, fromdate, toDate, resolution, DataType.TARGET);
				dmr.getSaveManager().save(section, tableDataActual, Location.ACTUAL);
				dmr.getSaveManager().save(section, tableDataTarget, Location.TARGET);
			}else{
				if(dmr.getSaveManager().isSaved(section, Location.ACTUAL)){ 
					tableDataActual = (TableData) dmr.getSaveManager().load(section, Location.ACTUAL);
				}else{
					tableDataActual = dmr.getWebservice().getTableData((TableSection)section, fromdate, toDate, resolution, DataType.ACTUAL);	
					dmr.getSaveManager().save(section, tableDataActual, Location.ACTUAL);
				}

				if(dmr.getSaveManager().isSaved(section, Location.TARGET)){ 
					tableDataTarget = (TableData) dmr.getSaveManager().load(section, Location.TARGET);
				}else{
					tableDataTarget = dmr.getWebservice().getTableData((TableSection)section, fromdate, toDate, resolution, DataType.TARGET);	
					dmr.getSaveManager().save(section, tableDataTarget, Location.TARGET);
				}
			}
			addTableData(tableDataActual, tableDataTarget, dmr.getTable(), section.getSectionHeader(), sectionTitle);
		}
		else if(section instanceof GraphSection){
			GraphSection graphSection = (GraphSection) section;
			GraphData graphDataActual;
			GraphData graphDataTarget;

			if(newWebserviceValues){
				graphDataActual = dmr.getWebservice().getGraphDataBySection(graphSection, fromdate, toDate, resolution, DataType.ACTUAL);
				graphDataTarget = dmr.getWebservice().getGraphDataBySection(graphSection, fromdate, toDate, resolution, DataType.TARGET);	
				dmr.getSaveManager().save(graphSection, graphDataActual, Location.ACTUAL);
				dmr.getSaveManager().save(graphSection, graphDataTarget, Location.TARGET);
			}else{
				if(dmr.getSaveManager().isSaved(graphSection, Location.ACTUAL)){ 
					graphDataActual = (GraphData) dmr.getSaveManager().load(graphSection, Location.ACTUAL);
				}else{
					graphDataActual = dmr.getWebservice().getGraphDataBySection(graphSection, fromdate, toDate, resolution, DataType.ACTUAL);	
					dmr.getSaveManager().save(graphSection, graphDataActual, Location.ACTUAL);
				}

				if(dmr.getSaveManager().isSaved(graphSection, Location.TARGET)){ 
					graphDataTarget = (GraphData) dmr.getSaveManager().load(graphSection, Location.TARGET);
				}else{
					graphDataTarget = dmr.getWebservice().getGraphDataBySection(graphSection, fromdate, toDate, resolution, DataType.TARGET);	
					dmr.getSaveManager().save(graphSection, graphDataTarget, Location.TARGET);
				}
			}
			addGraphData(graphDataActual, graphDataTarget, dmr.getTable(), graphSection.getSectionHeader(), sectionTitle);
		}
	}

	/**
	 * Adds a graph section to the report from a given {@link GraphData}
	 * @param graphDataActual The given {@link GraphData}
	 * @param graphDataTarget 
	 * @param sectionTable the table {@link TableLayout} to add the section to
	 * @param sectionTitle Needed in case of adding a {@link OnLongClickListener} to the title {@link View}
	 */
	private void addGraphData(GraphData graphDataActual, GraphData graphDataTarget, TableLayout sectionTable, String title, TextView sectionTitle) {
		//Init
		Graph graph = null;

		//Add data
		if(graphDataActual.getGraphPoints().size()>1){
			//Line Graph
			graph = new LineGraph(dmr, "");
			((LineGraph) graph).add(graphDataActual, title);
			((LineGraph) graph).add(graphDataTarget, title);			
			sectionTitle.setOnLongClickListener(new GraphLineChooserListener(dmr, graph, title));
		}else{
			//Bar graph
			graph = new BarGraph(dmr, "");
			((BarGraph) graph).add(graphDataActual, Color.argb(100, 0, 200, 0), "Actual");	
			((BarGraph) graph).add(graphDataTarget, Color.argb(100, 200, 0, 0), "Target");	
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
	 * @param tableDataActual The given {@link TableData}
	 * @param tableDataTarget 
	 * @param sectionTable the table {@link TableLayout} to add the section to
	 * @param sectionTitle Needed in case of adding a {@link OnLongClickListener} to the title {@link View}
	 */
	private void addTableData(TableData tableDataActual, TableData tableDataTarget, TableLayout sectionTable, String title, TextView sectionTitle) {
		//Init
		HorizontalScrollView hScroll = new HorizontalScrollView(dmr);
		TableLayout table = new TableLayout(dmr);
		List<com.ec.prod.android.pilot.model.TableRow> tableRowsActual = tableDataActual.getTableRows();

		//Table
		table.setStretchAllColumns(true);
		table.setBackgroundColor(dmr.getBackgroundColor());

		//Active columns
		ArrayList<String> activeColumns = activeColumns(title, tableDataActual);

		//Listener
		sectionTitle.setOnLongClickListener(new TableOptionDialogListener(dmr, title, tableDataActual));

		//Headers
		List<TableColumn> tableColumns = tableDataActual.getTableColumns();
		TableRow headerRow = new TableRow(dmr);
		for (TableColumn column: tableColumns) {
			//Init
			if(activeColumns.contains(column.getHeader())){
				headerRow.addView(new Cell(dmr, new TableCell(column.getHeader()), new TableCell(""), dmr.getCellBackgroundColor(), dmr.getCellTextColor(), dmr.getCellBorderColor()));				
			}
		}
		table.addView(headerRow);

		//Rows
		for (com.ec.prod.android.pilot.model.TableRow tableRow : tableRowsActual) {
			//Init
			int idx = tableDataActual.getTableRows().indexOf(tableRow);
			TableRow row = new TableRow(dmr);
			List<TableCell> valuesActual = tableRow.getValues();
			List<TableCell> valuesTarget = tableDataTarget.getTableRows().get(idx).getValues();

			for (int i = 0; i < valuesActual.size(); i++) {
				//Add Cell
				String header = tableColumns.get(i).getHeader();
				if(activeColumns.contains(header)){
					Cell cell = new Cell(dmr, valuesActual.get(i), valuesTarget.get(i), dmr.getCellBackgroundColor(), dmr.getCellTextColor(), dmr.getCellBorderColor());
					row.addView(cell);
					
					if(i>0){
						cell.setOnClickListener(new CellListener(cell));
					}
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
			view.setText(DateConverter.parse(text.getDaytime(), DateConverter.Type.TIME, Resolution.DAILY) + "\n" + text.getText() + "\n");
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
			//if(basePath + OptionTitle.Default))
			dmr.setBackgroundColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.BackgroundColor)));
			dmr.setTextColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.TextColor)));
			dmr.setCellTextColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.TextColor)));
			dmr.setCellBackgroundColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.CellBackgroundColor)));
			dmr.setCellBorderColor(ColorConverter.parseColor(FileManager.readPath(dmr, basePath + OptionTitle.CellBorderColor)));
		} catch (IOException e) {
			setDefaultColor(basePath);
			e.printStackTrace();
		}

		dmr.getScrollView().setBackgroundColor(dmr.getBackgroundColor());
		dmr.getTable().setBackgroundColor(dmr.getBackgroundColor());
	}

	/**
	 * This method sets the default color of the daily morning repost class
	 * @param basePath
	 */
	private void setDefaultColor(String basePath) {
		Log.d("tieto", "Setting default color");
		dmr.setBackgroundColor(Color.rgb(180, 201, 220));
		dmr.setTextColor(Color.BLACK);
		dmr.setCellBackgroundColor(Color.WHITE);
		dmr.setCellTextColor(Color.BLACK);
		dmr.setCellBorderColor(Color.BLACK);
		FileManager.writePath(dmr, basePath + OptionTitle.BackgroundColor, ColorType.LightBlue.toString());
		FileManager.writePath(dmr, basePath + OptionTitle.TextColor, ColorType.Black.toString());
		FileManager.writePath(dmr, basePath + OptionTitle.CellBackgroundColor, ColorType.White.toString());
		FileManager.writePath(dmr, basePath + OptionTitle.CellBorderColor, ColorType.Black.toString());
	}
}