package com.tieto.ec.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.tieto.R;
import com.tieto.ec.gui.Graph;
import com.tieto.ec.listeners.GraphListener;
import com.tieto.ec.listeners.main.GraphLineCheckBoxListener;
import com.tieto.ec.webServices.PwelDayStatusService;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Surface;
import android.widget.CheckBox;
import android.widget.RelativeLayout;


public class Main extends Activity {
	
	protected String username, password, namespace, url;
	protected PwelDayStatusService webservice;
	protected Graph graph;
	protected ArrayList<HashMap<String, String>>  valueList;
	
	protected RelativeLayout relative;
	protected CheckBox oilBox;
	protected CheckBox gasBox;
	protected CheckBox waterBox;
	
	public void onCreate(Bundle savedInstanceState, String username, String password, String namespace, String url)
	{
		//Super
		super.onCreate(savedInstanceState);
		
		this.username = username;
		this.password = password;
		this.namespace = namespace;
		this.url = url;

		//Init
		int rotation = getWindowManager().getDefaultDisplay().getRotation();
		if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
			setContentView(R.layout.graph_view_portrait);
			relative = (RelativeLayout) findViewById(R.id.graphLayout_portrait);
			oilBox = (CheckBox) findViewById(R.id.oilBox_portrait);
			gasBox = (CheckBox) findViewById(R.id.gasBox_portrait);
			waterBox = (CheckBox) findViewById(R.id.waterBox_portrait);
		}else{
			setContentView(R.layout.graph_view_landscape);
			relative = (RelativeLayout) findViewById(R.id.graphLayout_landscape);
			oilBox = (CheckBox) findViewById(R.id.oilBox_landscape);
			gasBox = (CheckBox) findViewById(R.id.gasBox_landscape);
			waterBox = (CheckBox) findViewById(R.id.waterBox_landscape);
		}


		graph = new Graph(this, "Well");    	
		webservice = new PwelDayStatusService(username , password, namespace, url);

		//Graph
		graph.addEmptyGraphLine("Oil", Color.BLACK);
		graph.addEmptyGraphLine("Gas", Color.BLUE);
		graph.addEmptyGraphLine("Water", Color.GREEN);
		relative.addView(graph);
		
		//CheckBoxes
		oilBox.setChecked(true);
		gasBox.setChecked(true);
		waterBox.setChecked(true);

		//Buttons/CheckBoxes Listeners
		oilBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 0));
		gasBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 1));
		waterBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 2));
		graph.setOnTouchListener(new GraphListener());
	}
}
