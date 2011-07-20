package com.tieto.ec.activities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;
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
import com.ec.prod.android.pilot.service.ExampleViewService;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.R;
import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;
import com.tieto.ec.gui.table.Cell;
import com.tieto.ec.listeners.dmr.DmrMapButtonListener;
import com.tieto.ec.listeners.dmr.DmrOptionsButtonListener;
import com.tieto.ec.listeners.dmr.GraphFullScreenListener;
import com.tieto.ec.listeners.dmr.GraphLineChooserListener;
import com.tieto.ec.listeners.dmr.ShowHideSection;
import com.tieto.ec.listeners.dmr.TableMetaDataListener;
import com.tieto.ec.logic.ColorConverter;
import com.tieto.ec.logic.FileManager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DailyMorningReport extends Activity{

	private List<Section> sections;
	private ViewService webservice;
	private String username, password, namespace, url;
	private TableLayout table;
	private int backgroundColor, textColor, cellTextColor, cellBackgroundColor, cellBorderColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Init
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
		namespace = getIntent().getExtras().getString("namespace");
		url = getIntent().getExtras().getString("url");
		if(url.equalsIgnoreCase("debug") && namespace.equalsIgnoreCase("debug")){
			webservice = new ExampleViewService();
		}else{
			webservice = new DMRViewServiceUnmarshalled(username, password, namespace, url);			
		}
		
		//Options
		try {
			backgroundColor = ColorConverter.parseColor(FileManager.readPath(this, "DMR Report.Colors.Background Color"));
			textColor = ColorConverter.parseColor(FileManager.readPath(this, "DMR Report.Colors.Text Color"));
			cellTextColor = ColorConverter.parseColor(FileManager.readPath(this, "DMR Report.Colors.Text Color"));
			cellBackgroundColor = ColorConverter.parseColor(FileManager.readPath(this, "DMR Report.Colors.Cell Background Color"));
			cellBorderColor = ColorConverter.parseColor(FileManager.readPath(this, "DMR Report.Colors.Cell Border Color"));
		} catch (IOException e) {
			backgroundColor = Color.BLACK;
			textColor = Color.GRAY;
			cellBackgroundColor = Color.WHITE;
			cellTextColor = Color.BLACK;
			cellBorderColor = Color.BLACK;
			FileManager.writePath(this, "DMR Report.Colors.Background Color", "Light Blue");
			FileManager.writePath(this, "DMR Report.Colors.Text Color", "Black");
			FileManager.writePath(this, "DMR Report.Colors.Cell Background Color", "White");
			FileManager.writePath(this, "DMR Report.Colors.Cell Border Color", "Black");
			e.printStackTrace();
		}
		
		//This
		setContentView(R.layout.daily_management_report);
		
		//Background
		ScrollView scroll = (ScrollView) findViewById(R.id.dmr_scroll);
		table = (TableLayout) findViewById(R.id.dmr_table);
		scroll.setBackgroundColor(backgroundColor);
		table.setBackgroundColor(backgroundColor);
		
		//Building report
		sections = webservice.getSections();
		
		
		//List Sections
		listSections();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		onCreate(null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.dmr_menu, menu);
		
		MenuItem optionButton = menu.findItem(R.id.dmr_options);
		optionButton.setOnMenuItemClickListener(new DmrOptionsButtonListener(this));
		

		MenuItem mapButton = menu.findItem(R.id.dmr_map);
		mapButton.setOnMenuItemClickListener(new DmrMapButtonListener(this));
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
		sectionTitle.setTextColor(textColor);
		
		//Childs
		table.addView(sectionTitle);
		
		//Listeners
		sectionTitle.setOnClickListener(new ShowHideSection(section.getSectionHeader(), table));
		
		//Values
		if(section instanceof TextSection){
			TextData textData = webservice.getTextData((TextSection)section, fromdate, toDate, resolution);
			addTextData(textData, table);
		}
		else if(section instanceof TableSection){
			TableData tableData = webservice.getTableData((TableSection)section, fromdate, toDate, resolution);
			sectionTitle.setOnLongClickListener(new TableMetaDataListener(this, section.getSectionHeader(), tableData));
			addTableData(tableData, table, section.getSectionHeader());
		}
		else if(section instanceof GraphSection){
			GraphData graphData = webservice.getGraphDataBySection((GraphSection)section, fromdate, toDate, resolution);	
			addGraphData(graphData, table, section.getSectionHeader());
		}
	}

	private void addGraphData(GraphData graphData, TableLayout sectionTable, String title) {
		//Init
		Graph graph = null;
		
		//Add data
		if(graphData.getGraphPoints().size()>1){
			//Line Graph
			graph = new LineGraph(this, "");
			graph.setDomainValueFormat(new SimpleDateFormat("yyyy-MM-dd"));
			graph.setOnLongClickListener(new GraphLineChooserListener(this, graph));
			((LineGraph) graph).add(graphData);			
		}else{
			//Bar graph
			graph = new BarGraph(this, "", Color.GREEN);
			((BarGraph) graph).add(graphData);	
		}
		
		//LayoutParams
		int width = getWindowManager().getDefaultDisplay().getWidth();
		graph.setLayoutParams(new LayoutParams(width, 200));
		
		//Childs
		sectionTable.addView(graph);
		
		//Listener
		graph.setOnClickListener(new GraphFullScreenListener(this, graph, title));
	}

	private void addTableData(TableData tableData, TableLayout sectionTable, String title) {
		//Init
		HorizontalScrollView hScroll = new HorizontalScrollView(this);
		TableLayout table = new TableLayout(this);
		List<com.ec.prod.android.pilot.model.TableRow> tableRows = tableData.getTableRows();
		
		//Table
		table.setStretchAllColumns(true);
		table.setBackgroundColor(backgroundColor);
		
		//Active columns
		ArrayList<String> activeColumns = activeColumns(title, tableData);
		
		//Headers
		List<TableColumn> tableColumns = tableData.getTableColumns();
		TableRow headerRow = new TableRow(this);
		for (TableColumn column: tableColumns) {
			//Init
			if(activeColumns.contains(column.getHeader())){
				headerRow.addView(new Cell(this, column.getHeader(), cellBackgroundColor, cellTextColor, cellBorderColor));				
			}
		}
		table.addView(headerRow);
		
		//Rows
		for (com.ec.prod.android.pilot.model.TableRow tableRow : tableRows) {
			//Init
			TableRow row = new TableRow(this);
			List<String> values = tableRow.getValues();
			
			for (int i = 0; i < values.size(); i++) {
				//Add Cell
				String header = tableColumns.get(i).getHeader();
				if(activeColumns.contains(header)){
					row.addView(new Cell(this, values.get(i), cellBackgroundColor, cellTextColor, cellBorderColor));					
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
		TableLayout table = new TableLayout(this);
		List<TextElement> textElements = textData.getTextElements();
		for (TextElement text : textElements) {
			TextView view = new TextView(this);
			view.setText(text.getDaytime() + ":\n" + text.getText());
			view.setTextColor(textColor);
			table.addView(view);
		}
		sectionTable.addView(table);
	}

	private ArrayList<String> activeColumns(String title, TableData tableData){
		ArrayList<String> columns = new ArrayList<String>();
		try {
			List<TableColumn> columnsList = tableData.getTableColumns();
			for (int i = 0; i < columnsList.size(); i++) {
				boolean active = Boolean.valueOf(FileManager.readPath(this, title + "." + columnsList.get(i).getHeader()));
				
				if(active){
					columns.add(columnsList.get(i).getHeader());
				}
			}
			
			return columns;
		} catch (IOException e) {
			List<TableColumn> tableColumns = tableData.getTableColumns();
			
			for (TableColumn tableColumn : tableColumns) {
				FileManager.writePath(this,  title + "." + tableColumn.getHeader(), "true");
				
			}			
			
			return activeColumns(title, tableData);
		}
	}
}
