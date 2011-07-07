package com.tieto.ec.activities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tieto.R;
import com.tieto.ec.gui.Cell;
import com.tieto.ec.gui.LineGraph;
import com.tieto.ec.listeners.activities.dmr.DmrOptionsButtonListener;
import com.tieto.ec.listeners.activities.dmr.GraphFullScreenListener;
import com.tieto.ec.listeners.activities.dmr.TableMetaDataListener;
import com.tieto.ec.logic.FileManager;
import com.tieto.frmw.model.GraphData;
import com.tieto.frmw.model.GraphSection;
import com.tieto.frmw.model.Resolution;
import com.tieto.frmw.model.Section;
import com.tieto.frmw.model.TableColumn;
import com.tieto.frmw.model.TableData;
import com.tieto.frmw.model.TableSection;
import com.tieto.frmw.model.TextData;
import com.tieto.frmw.model.TextElement;
import com.tieto.frmw.model.TextSection;
import com.tieto.frmw.service.ExampleViewService;
import com.tieto.frmw.service.ViewService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DailyMorningReport extends Activity{

	private List<Section> sections;
	private ViewService webservice;
	private String username, password, namespace, url;
	private TableLayout table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Init
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
		namespace = getIntent().getExtras().getString("namespace");
		url = getIntent().getExtras().getString("url");
		webservice = new ExampleViewService();
		
		//This
		setContentView(R.layout.daily_management_report);
		
		table = (TableLayout) findViewById(R.id.dmr_table);
		
		//Building report
		sections = webservice.getSections();
		
		//List Sections
		listSections();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.dmr_menu, menu);
		
		MenuItem optionButton = menu.findItem(R.id.dmr_options);
		optionButton.setOnMenuItemClickListener(new DmrOptionsButtonListener(this));
		return super.onCreateOptionsMenu(menu);
	}
	
	public void listSections(){
		table.removeAllViews();
		Calendar c = Calendar.getInstance();
		for (Section section : sections) {
			addSection(section, c.getTime(), c.getTime(), Resolution.MONTHLY);			
		}
	}
	
	private void addSection(Section section, Date fromdate, Date toDate, int resolution){
		//Init
		TextView sectionTitle = new TextView(this);
		
		//Title
		sectionTitle.setText(section.getSectionHeader() + ":");
		sectionTitle.setTextSize(30);
		
		//Childs
		table.addView(sectionTitle);
		
		//Values
		if(section instanceof TextSection){
			TextData textData = webservice.getTextData((TextSection)section, fromdate, toDate, resolution);
			addTextData(textData, table);
		}
		else if(section instanceof TableSection){
			TableData tableData = webservice.getTableData((TableSection)section, fromdate, toDate, resolution);
			sectionTitle.setOnClickListener(new TableMetaDataListener(this, section.getSectionHeader(), tableData));
			addTableData(tableData, table, section.getSectionHeader());
		}
		else if(section instanceof GraphSection){
			GraphData graphData = webservice.getGraphData((GraphSection)section, fromdate, toDate, resolution);	
			addGraphData(graphData, table, section.getSectionHeader());
		}
	}

	private void addGraphData(GraphData graphData, TableLayout sectionTable, String title) {
		//Init
		LineGraph graph = new LineGraph(this, "");
		
		//Add data
		graph.add(graphData);
		graph.setDomainValueFormat(new SimpleDateFormat());
		
		//LayoutParams
		int width = getWindowManager().getDefaultDisplay().getWidth();
		graph.setLayoutParams(new LayoutParams(width, 200));
		
		//Childs
		sectionTable.addView(graph);
		
		//Listener
		graph.setOnClickListener(new GraphFullScreenListener(this, graphData, title));
	}

	private void addTableData(TableData tableData, TableLayout sectionTable, String title) {
		//Init
		HorizontalScrollView hScroll = new HorizontalScrollView(this);
		TableLayout table = new TableLayout(this);
		List<com.tieto.frmw.model.TableRow> tableRows = tableData.getTableRows();
		
		//Table
		table.setStretchAllColumns(true);
		
		//Active columns
		ArrayList<Integer> activeColumns = activeColumns(title, tableData);
		
		//Headers
		List<TableColumn> tableColumns = tableData.getTableColumns();
		TableRow headerRow = new TableRow(this);
		for (int i = 0; i < tableColumns.size(); i++) {
			//Init
			if(activeColumns.contains(i)){
				headerRow.addView(new Cell(this, tableColumns.get(i).getHeader()));				
			}
		}
		table.addView(headerRow);
		
		//Rows
		for (com.tieto.frmw.model.TableRow tableRow : tableRows) {
			//Init
			TableRow row = new TableRow(this);
			List<String> values = tableRow.getValues();
			
			for (int i = 0; i < values.size(); i++) {
				//Add Cell
				if(activeColumns.contains(i)){
					row.addView(new Cell(this, values.get(i)));					
				}
			}
			
			//Childs
			table.addView(row);
		}
		
		//Childs
		hScroll.addView(table);
		sectionTable.addView(hScroll);
	}

	private void addTextData(TextData textData, TableLayout sectionTable) {
		List<TextElement> textElements = textData.getTextElements();
		for (TextElement text : textElements) {
			TextView view = new TextView(this);
			view.setText(text.getDaytime() + ":\n" + text.getText());
			sectionTable.addView(view);
		}
	}

	private ArrayList<Integer> activeColumns(String title, TableData tableData){
		ArrayList<Integer> columns = new ArrayList<Integer>();
		try {
			String[] atributes = FileManager.readPath(this, "com.tieto.ec.tables." + title).split("#@#");
			List<TableColumn> tableColumns = tableData.getTableColumns();
			
			for (int i = 0; i < atributes.length; i++) {
				
				for (TableColumn tableColumn : tableColumns) {
					if(tableColumn.getHeader().equalsIgnoreCase(atributes[i])){
						columns.add(tableColumns.indexOf(tableColumn));
					}
				}
			}
			return columns;
		} catch (IOException e) {
			StringBuilder builder = new StringBuilder();
			List<TableColumn> tableColumns = tableData.getTableColumns();
			
			for (TableColumn tableColumn : tableColumns) {
				builder.append(tableColumn.getHeader() + "#@#");
			}
			Log.d("tieto", builder.toString());
			FileManager.writePath(this, "com.tieto.ec.tables." + title, builder.toString());
			
			return activeColumns(title, tableData);
		}
	}
}
