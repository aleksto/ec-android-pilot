package com.tieto.ec.activities;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tieto.R;
import com.tieto.ec.gui.Graph;
import com.tieto.ec.listeners.main.GraphLineCheckBoxListener;
import com.tieto.ec.listeners.main.SelectObjectIDListener;
import com.tieto.ec.listeners.main.SelectPeriodListener;
import com.tieto.ec.webServices.PwelDayStatusService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Main extends Activity
{
	private Graph graph;
	private String username, password, namespace, url;
	private String objectID, fromDate, toDate;
	private PwelDayStatusService webservice;
	private ArrayList<HashMap<String, String>>  valueList;
	private TextView data;
	private RelativeLayout relative;
	private CheckBox oilBox;
	private CheckBox gasBox;
	private CheckBox waterBox;
	private Button selectPeriod;
	private Button selectObjectID;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//Super
		super.onCreate(savedInstanceState);

		//Webservice
		username = getIntent().getExtras().getString("username");
		password = getIntent().getExtras().getString("password");
		namespace = "http://pweldaystatuswsp.service.generated.ws.frmw.ec.com/";
		url = "http://wv001927.eu.tieto.com/com.ec.frmw.ws.generated/PwelDayStatusWspService?";

		//Init
		int rotation = getWindowManager().getDefaultDisplay().getRotation();
		if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
			setContentView(R.layout.graph_view_portrait);
			relative = (RelativeLayout) findViewById(R.id.graphLayout_portrait);
			oilBox = (CheckBox) findViewById(R.id.oilBox_portrait);
			gasBox = (CheckBox) findViewById(R.id.gasBox_portrait);
			waterBox = (CheckBox) findViewById(R.id.waterBox_portrait);
			selectPeriod = (Button) findViewById(R.id.dateButton_portrait);
			selectObjectID = (Button) findViewById(R.id.objectIDButton_portrait);
			data = (TextView) findViewById(R.id.data_portrait);
		}else{
			setContentView(R.layout.graph_view_landscape);
			relative = (RelativeLayout) findViewById(R.id.graphLayout_landscape);
			oilBox = (CheckBox) findViewById(R.id.oilBox_landscape);
			gasBox = (CheckBox) findViewById(R.id.gasBox_landscape);
			waterBox = (CheckBox) findViewById(R.id.waterBox_landscape);
			selectPeriod = (Button) findViewById(R.id.dateButton_landscape);
			selectObjectID = (Button) findViewById(R.id.objectIDButton_landscape);
			data = (TextView) findViewById(R.id.data_landscape);
		}


		graph = new Graph(this, "Well");    	
		webservice = new PwelDayStatusService(username , password, namespace, url);

		//Graph
		graph.addEmptyGraphLine("Oil", Color.BLACK);
		graph.addEmptyGraphLine("Gas", Color.BLUE);
		graph.addEmptyGraphLine("Water", Color.GREEN);
		relative.addView(graph);

		//Initialize webservice
		runWebservice("9FB4E1510D033B19E040340A2B4042D7", "2003-01-01", "2003-01-31");

		//Print well data
		printInformation("opProductionunitCode", "opAreaCode", "opFcty1Code", "objectCode");

		//CheckBoxes
		oilBox.setChecked(true);
		gasBox.setChecked(true);
		waterBox.setChecked(true);

		//Buttons/CheckBoxes Listeners
		selectPeriod.setOnClickListener(new SelectPeriodListener(this));
		selectObjectID.setOnClickListener(new SelectObjectIDListener(this));
		oilBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 0));
		gasBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 1));
		waterBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 2));
	}

	private void printInformation(String ... args) {
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
		data.setText(builder);  
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
		Log.d("tieto", valueList.size()+"");

		graph.clearAllGraphLines();
		graph.addValuesToExistingLines(valueList, "theorOilVol", "theorGasVol", "theorWaterVol");
		graph.invalidate();
	} 
}