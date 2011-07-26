package com.tieto.ec.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;
import com.ec.prod.android.pilot.client.WebserviceDateConverter;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
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
import com.tieto.ec.enums.ColorType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.Webservice;
import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.gui.graphs.LineGraph;
import com.tieto.ec.gui.table.Cell;
import com.tieto.ec.listeners.dmr.ChangeDayListener;
import com.tieto.ec.listeners.dmr.DmrMapButtonListener;
import com.tieto.ec.listeners.dmr.DmrOptionsButtonListener;
import com.tieto.ec.listeners.dmr.GraphFullScreenListener;
import com.tieto.ec.listeners.dmr.GraphLineChooserListener;
import com.tieto.ec.listeners.dmr.ShowHideSection;
import com.tieto.ec.listeners.dmr.TableOptionDialogListener;
import com.tieto.ec.logic.ColorConverter;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.ResolutionConverter;
import com.tieto.ec.service.EcService;

public class DailyMorningReport extends Activity{

	private Intent serviceIntent;
	
	private List<Section> sections;
	private ViewService webservice;
	private String username, password, namespace, url;
	private TableLayout table, main;
	private int backgroundColor, textColor, cellTextColor, cellBackgroundColor, cellBorderColor;
	private ScrollView scroll;

	private Date date;

	private RelativeLayout buttonRow;

	private TextView currentDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Init
		main = new TableLayout(this);
		username = getIntent().getExtras().getString(Webservice.username.toString());
		password = getIntent().getExtras().getString(Webservice.password.toString());
		namespace = getIntent().getExtras().getString(Webservice.namespace.toString());
		url = getIntent().getExtras().getString(Webservice.url.toString());
		
		if(url.equalsIgnoreCase("debug") && namespace.equalsIgnoreCase("debug")){
			webservice = new ExampleViewService(true);
		}else{
			webservice = new DMRViewServiceUnmarshalled(true, username, password, namespace, url);
		}
		
		//Service
		if(serviceIntent == null){
			restartService();			
		}
		
		//This
		setContentView(main);
		
		//Date
		date = new Date(System.currentTimeMillis());
		date.setDate(date.getDate()-1);
		
		//Background
		scroll = new ScrollView(this);
		table = new TableLayout(this);
		
		//ButtonRow
		buttonRow = new RelativeLayout(this);
		buttonRow.setBackgroundResource(android.R.drawable.title_bar);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		Button previousDay = new Button(this);
		Button nextDay = new Button(this);
		RelativeLayout titleLayout = new RelativeLayout(this);
		TextView dmrTitle = new TextView(this);
		currentDay = new TextView(this);
		dmrTitle.setId(1);
		
		//Layout Rules
		params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params2.addRule(RelativeLayout.CENTER_VERTICAL);
		params3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params3.addRule(RelativeLayout.CENTER_VERTICAL);
		params4.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params5.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params5.addRule(RelativeLayout.BELOW, dmrTitle.getId());
		params6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params6.addRule(RelativeLayout.CENTER_VERTICAL);
		
		//Buttons
		previousDay.setBackgroundResource(android.R.drawable.ic_media_rew);
		nextDay.setBackgroundResource(android.R.drawable.ic_media_ff);
		currentDay.setText(date.getDate() + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900));
		currentDay.setTextColor(Color.BLACK);
		dmrTitle.setText("Daily Morning Report");
		dmrTitle.setTextColor(Color.BLACK);
		nextDay.setOnClickListener(new ChangeDayListener(this, ChangeDayListener.Action.NEXT_DAY));
		previousDay.setOnClickListener(new ChangeDayListener(this, ChangeDayListener.Action.PREVIOUS_DAY));
		currentDay.setOnClickListener(new ChangeDayListener(this, ChangeDayListener.Action.CHOOSE_DAY));
		dmrTitle.setOnClickListener(new ChangeDayListener(this, ChangeDayListener.Action.CHOOSE_DAY));
		
		
		//ButtonRow Childs
		buttonRow.addView(previousDay, params2);
		buttonRow.addView(titleLayout, params3);
		titleLayout.addView(dmrTitle, params4);
		titleLayout.addView(currentDay, params5);
		buttonRow.addView(nextDay, params6);
		
		//Main
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 80);
		main.addView(buttonRow, params1);
		main.addView(scroll);
		scroll.addView(table);
		
		//Options
		updateColors();
		
		//Building report
		sections = webservice.getSections();
		
		//List Sections
		listSections();
	}
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public Date getDate(){
		return date;
	}
	
	public void refreshWebserviceValues(){
		onCreate(null);
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
		optionButton.setIcon(android.R.drawable.ic_menu_manage);
		

		MenuItem mapButton = menu.findItem(R.id.dmr_map);
		mapButton.setOnMenuItemClickListener(new DmrMapButtonListener(this));
		mapButton.setIcon(android.R.drawable.ic_menu_mapmode);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void restartService() {
		if(serviceIntent == null){
			serviceIntent = new Intent(this, EcService.class);
			serviceIntent.putExtra(Webservice.username.toString(), username);
			serviceIntent.putExtra(Webservice.password.toString(), password);
			serviceIntent.putExtra(Webservice.namespace.toString(), namespace);
			serviceIntent.putExtra(Webservice.url.toString(), url);			
		}else{
			stopService(serviceIntent);
		}
		startService(serviceIntent);
	}
	
	public void refresh(){
		Log.d("tieto", "Refreshing DMR");
		updateColors();
		listSections();
	}
	
	public void updateColors(){
		String basePath = OptionTitle.DMRReport + "." + OptionTitle.ColorOptions + ".";
		try {
			backgroundColor = ColorConverter.parseColor(FileManager.readPath(this, basePath + OptionTitle.BackgroundColor));
			textColor = ColorConverter.parseColor(FileManager.readPath(this, basePath + OptionTitle.TextColor));
			cellTextColor = ColorConverter.parseColor(FileManager.readPath(this, basePath + OptionTitle.TextColor));
			cellBackgroundColor = ColorConverter.parseColor(FileManager.readPath(this, basePath + OptionTitle.CellBackgroundColor));
			cellBorderColor = ColorConverter.parseColor(FileManager.readPath(this, basePath + OptionTitle.CellBorderColor));
		} catch (IOException e) {
			Log.d("tieto", "Setting default color");
			backgroundColor = Color.BLACK;
			textColor = Color.GRAY;
			cellBackgroundColor = Color.WHITE;
			cellTextColor = Color.BLACK;
			cellBorderColor = Color.BLACK;
			FileManager.writePath(this, basePath + OptionTitle.BackgroundColor, ColorType.LightBlue.toString());
			FileManager.writePath(this, basePath + OptionTitle.TextColor, ColorType.Black.toString());
			FileManager.writePath(this, basePath + OptionTitle.CellBackgroundColor, ColorType.White.toString());
			FileManager.writePath(this, basePath + OptionTitle.CellBorderColor, ColorType.Black.toString());
			e.printStackTrace();
		}
		
		scroll.setBackgroundColor(backgroundColor);
		table.setBackgroundColor(backgroundColor);
	}
	
	public void listSections(){
		table.removeAllViews();
		currentDay.setText(date.getDate() + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900));
		
		String basePath = OptionTitle.DMRReport + "." + OptionTitle.ReportOptions + ".";
		try {
			int resolution = ResolutionConverter.convert(FileManager.readPath(this, basePath + OptionTitle.Interval));
			for (Section section : sections) {
				addSection(section, date, date, resolution);			
			}
		} catch (IOException e) {
			Log.d("tieto", "Setting default time and resolution");
			FileManager.writePath(this, basePath + OptionTitle.Interval, "Weekly");
			listSections();
			e.printStackTrace();
		}		
	}
	
	private void addSection(Section section, Date fromdate, Date toDate, int resolution){
		//Init
		TextView sectionTitle = new TextView(this);
		
		//Title
		sectionTitle.setText(section.getSectionHeader() + ":");
		sectionTitle.setTextSize(30);
		sectionTitle.setTextColor(textColor);
		sectionTitle.setTypeface(Typeface.create("arial", Typeface.NORMAL));
		sectionTitle.setPadding(0, 20, 0, 0);
		
		//Childs
		table.addView(sectionTitle);
		table.setPadding(10, 10, 10, 0);
		
		//Listeners
		sectionTitle.setOnClickListener(new ShowHideSection(section.getSectionHeader(), table));
		
		//Values
		if(section instanceof TextSection){
			TextData textData = webservice.getTextData((TextSection)section, fromdate, toDate, resolution);				
			addTextData(textData, table);
		}
		else if(section instanceof TableSection){
			TableData tableData = webservice.getTableData((TableSection)section, fromdate, toDate, resolution);
			sectionTitle.setOnLongClickListener(new TableOptionDialogListener(this, section.getSectionHeader(), tableData));
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
		Log.d("tieto", "Adding graph with size: " + graphData.getGraphPoints().size());
		if(graphData.getGraphPoints().size()>1){
			//Line Graph
			graph = new LineGraph(this, "");
			((LineGraph) graph).add(graphData, title);			
			graph.setOnLongClickListener(new GraphLineChooserListener(this, graph, title));
		}else{
			//Bar graph
			graph = new BarGraph(this, "", Color.GREEN);
			((BarGraph) graph).add(graphData);	
		}
		
		//LayoutParams
		int width = getWindowManager().getDefaultDisplay().getWidth();
		graph.setLayoutParams(new LayoutParams(width-20, 200));
		
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
		ArrayList<String> activeColumns = activeColumns(title + "." + OptionTitle.VisibleColumns, tableData);
		
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
			view.setText(WebserviceDateConverter.parse(text.getDaytime(), WebserviceDateConverter.Type.TIME) + "\n" + text.getText() + "\n");
			view.setTextColor(textColor);
			view.setTypeface(Typeface.create("arial", Typeface.NORMAL));
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
