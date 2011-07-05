package com.tieto.ec.activities;

import android.graphics.Color;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tieto.R;

import com.tieto.ec.gui.LineGraph;
import com.tieto.ec.listeners.main.GraphListener;
import com.tieto.ec.listeners.main.SelectDataListener;
import com.tieto.ec.listeners.main.SelectObjectIDListener;
import com.tieto.ec.listeners.main.SelectPeriodListener;

import java.text.SimpleDateFormat;
import java.util.Set;

public class WellPeriod extends Main
{
	private String objectID, fromDate, toDate;
	private LineGraph graph;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//Webservice
		objectID = "9FB4E1510D033B19E040340A2B4042D7";
		fromDate = "2003-01-01";
		toDate = "2003-01-31";
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
		namespace = getIntent().getExtras().getString("namespace");
		url = getIntent().getExtras().getString("url");
		graph = new LineGraph(this, "Well");
		
		//Super
		super.onCreate(savedInstanceState, username, password, namespace, url, graph);
		
		//Graph
		graph.setDomainValueFormat(new SimpleDateFormat());
		graph.setDomainStepValue(5);
		graph.setGridPadding(0, 20, 0, 0);
		graph.addEmptyGraphLine("Oil", Color.BLACK);
		graph.addEmptyGraphLine("Gas", Color.BLUE);
		graph.addEmptyGraphLine("Water", Color.GREEN);
		graph.setOnTouchListener(new GraphListener(this, objectID, username, password, namespace, url));
		
		//Initialize webservice
		runWebservice(objectID, fromDate, toDate);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflator = new MenuInflater(this);
		menuInflator.inflate(R.menu.main_menu, menu);
		
		MenuItem selectPeriod = menu.findItem(R.id.selectPeriod);
		MenuItem selectObjectID = menu.findItem(R.id.selectObjectID);
		MenuItem data = menu.findItem(R.id.data);
		selectPeriod.setOnMenuItemClickListener(new SelectPeriodListener(this));
		selectObjectID.setOnMenuItemClickListener(new SelectObjectIDListener(this));
		data.setOnMenuItemClickListener(new SelectDataListener(this));
		return super.onCreateOptionsMenu(menu);
	}
	
	public String getData(String ... args) {
		StringBuilder builder = new StringBuilder();
		Set<String> keySet = valueList.get(0).keySet();
		String indent = "";
		for(String key: args)
		{
			if(keySet.contains(key)){
				builder.append("\n");
				builder.append(indent + key + ":");
				builder.append("\n");
				builder.append(indent + valueList.get(0).get(key));
				indent += "--";
			} 	
		}
		return builder.toString();
	}

	public void runWebservice(String objectID, String fromDate, String toDate) {
		if(!objectID.equalsIgnoreCase("")){
			this.objectID = objectID;
		}
		if(!fromDate.equalsIgnoreCase("")){
			this.fromDate = fromDate;
		}
		if(!toDate.equalsIgnoreCase("")){
			this.toDate = toDate;
		}
		
		valueList = webservice.findByPKTimeRange(this.objectID, this.fromDate, this.toDate);	

		graph.clearAllGraphLines();
		graph.addValuesToExistingLines(valueList, "theorOilVol", "theorGasVol", "theorWaterVol");
		graph.invalidate();
	} 
}




