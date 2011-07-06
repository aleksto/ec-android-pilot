package com.tieto.ec.activities;

import java.util.HashMap;
import java.util.Set;

import com.tieto.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DailyManagementReport extends Activity{

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
		
		//This
		setContentView(R.layout.daily_management_report);
		
		table = (TableLayout) findViewById(R.id.dmr_table);
		
		//TEST
		HashMap<String, String> val = new HashMap<String, String>();
		val.put("A", "This is a DMR");
		val.put("B", "DMR = DailyManagementReport");
		val.put("C", "ABCDEFGHIJKLMNOPQRSTUVWXYZ∆ ÿ≈ABCDEFGHIJKLMNOPQRSTUVWXYZ∆ÿ≈");
		val.put("D", "!\"#§%&/()");
		val.put("E", "EC_ANDROID_PILOT");
		val.put("F", "......................................");
		val.put("G", "............................");
		val.put("H", ".............");
		val.put("I", "........");
		val.put("J", "...");
		val.put("K", ".");
		addSection("Title", val);
		//TEST
	}
	
	private void addSection(String title, HashMap<String, String> values){
		//Init
		TextView sectionTitle = new TextView(this);
		TableLayout sectionTable = new TableLayout(this);
		
		//Title
		sectionTitle.setText(title);
		
		//Childs
		table.addView(sectionTitle);
		table.addView(sectionTable);
		
		//Values
		Set<String> keySet = values.keySet();
		for (String key : keySet){
			//Init
			TableRow row = new TableRow(this);
			TextView keyView = new TextView(this);
			TextView valView = new TextView(this);
			
			//Childs
			sectionTable.addView(row);
			row.addView(keyView);
			row.addView(valView);
			
			//Text
			keyView.setText(key + ":\t");
			valView.setText(values.get(key));
		}
	}
}
