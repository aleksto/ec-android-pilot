package com.tieto.ec.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.listeners.dmr.metaDataDialog.CancelListener;
import com.tieto.ec.listeners.dmr.metaDataDialog.HeaderChangeListener;
import com.tieto.ec.listeners.dmr.metaDataDialog.OkListener;
import com.tieto.ec.logic.FileManager;
import com.tieto.frmw.model.TableColumn;
import com.tieto.frmw.model.TableData;

import android.app.Dialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableMetaDataDialog extends Dialog{
	
	private ArrayList<String> headers;
	private ArrayList<CheckBox> checkBoxes;
	private DailyMorningReport dailyMorningReport;
	private String title;

	public TableMetaDataDialog(DailyMorningReport dailyMorningReport, String title, TableData data) {
		//Super
		super(dailyMorningReport);
		
		//Init
		this.dailyMorningReport = dailyMorningReport;
		this.title = title;
		ScrollView scroll = new ScrollView(dailyMorningReport);
		TableLayout table = new TableLayout(dailyMorningReport);
		headers = new ArrayList<String>();
		checkBoxes = new ArrayList<CheckBox>();
		List<TableColumn> tableColumns = data.getTableColumns();
		
		//This
		setContentView(scroll);
		setTitle("Metadata: " + title);
		
		//Scroll
		scroll.addView(table);
		
		for (TableColumn tableColumn : tableColumns) {
			//Init
			TableRow row = new TableRow(dailyMorningReport);
			TextView text = new TextView(dailyMorningReport);
			CheckBox box = new CheckBox(dailyMorningReport);
			
			//Text
			text.setText(tableColumn.getHeader());
			
			//List
			headers.add(tableColumn.getHeader());
			checkBoxes.add(box);
			
			//Childs
			row.addView(text);
			row.addView(box);
			table.addView(row);
			
			//CheckBox listener
			box.setOnCheckedChangeListener(new HeaderChangeListener(dailyMorningReport, title, tableColumn.getHeader()));
		}
		
		//Ok Cancel
		Button ok = new Button(dailyMorningReport);
		Button cancel = new Button(dailyMorningReport);
		
		//Button Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		
		//Childs
		table.addView(ok);
		table.addView(cancel);
		
		//Listeners
		ok.setOnClickListener(new OkListener(dailyMorningReport, this));
		cancel.setOnClickListener(new CancelListener(this));
	}

	public void updateCheckBoxes() {
		try {
			String[] activeColumns = FileManager.readPath(dailyMorningReport, "com.tieto.ec.tables." + title).split("#@#");
			
			for (int i = 0; i < activeColumns.length; i++) {
				if(headers.contains(activeColumns[i])){
					int idx = headers.indexOf(activeColumns[i]);
					checkBoxes.get(idx).setChecked(true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
