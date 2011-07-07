package com.tieto.ec.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tieto.R;
import com.tieto.ec.gui.Cell;
import com.tieto.ec.gui.LineGraph;
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
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DailyMorningReport extends Activity{

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
		List<Section> sections = webservice.getSections();
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
		
		//Childs
		table.addView(sectionTitle);
		
		//Values
		if(section instanceof TextSection){
			TextData textData = webservice.getTextData((TextSection)section, fromdate, toDate, resolution);
			addTextData(textData, table);
		}
		else if(section instanceof TableSection){
			TableData tableData = webservice.getTableData((TableSection)section, fromdate, toDate, resolution);			
			addTableData(tableData, table);
		}
		else if(section instanceof GraphSection){
			GraphData graphData = webservice.getGraphData((GraphSection)section, fromdate, toDate, resolution);	
			addGraphData(graphData, table);
		}
	}

	private void addGraphData(GraphData graphData, TableLayout sectionTable) {
		//Init
		LineGraph graph = new LineGraph(this, "Daily Morning Report");
		
		//Add data
		graph.add(graphData);
		graph.setDomainValueFormat(new SimpleDateFormat());
		
		//LayoutParams
		int width = getWindowManager().getDefaultDisplay().getWidth();
		graph.setLayoutParams(new LayoutParams(width, 200));
		
		//Childs
		sectionTable.addView(graph);
	}

	private void addTableData(TableData tableData, TableLayout sectionTable) {
		//Init
		HorizontalScrollView hScroll = new HorizontalScrollView(this);
		TableLayout table = new TableLayout(this);
		List<com.tieto.frmw.model.TableRow> tableRows = tableData.getTableRows();
		
		//Table
		table.setStretchAllColumns(true);
		
		//Headers
		List<TableColumn> tableColumns = tableData.getTableColumns();
		TableRow headerRow = new TableRow(this);
		for (TableColumn tableColumn : tableColumns) {
			//Init
			TextView headerView = new TextView(this);
			
			//Text
			headerView.setText("\t" + tableColumn.getHeader());
			
			headerRow.addView(new Cell(this, tableColumn.getHeader()));
		}
		table.addView(headerRow);
		
		//Rows
		for (com.tieto.frmw.model.TableRow tableRow : tableRows) {
			//Init
			TableRow row = new TableRow(this);
			List<String> values = tableRow.getValues();
			
			for (String string : values) {
				//Add Cell
				row.addView(new Cell(this, string));
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
}
