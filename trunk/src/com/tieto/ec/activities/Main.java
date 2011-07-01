package com.tieto.ec.activities;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.series.XYSeries;
import com.tieto.R;
import com.tieto.ec.gui.Graph;
import com.tieto.ec.listeners.GraphLineCheckBoxListener;
import com.tieto.ec.webServices.PwelDayStatusService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
 
public class Main extends Activity
{
	private Graph graph;
    private String username, password, namespace, url;
    private PwelDayStatusService webservice;
    private ArrayList<HashMap<String, String>>  valueList;
    private TextView data;
	
    private SimpleXYSeries gasLine, oilLine, waterLine;
    private LineAndPointFormatter gasLineformat, oilLineformat, waterLineformat;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	//Super
    	super.onCreate(savedInstanceState);
    	
    	//This
    	setContentView(R.layout.graph_view_landscape);
    	
    	//Init
    	username = getIntent().getExtras().getString("username");
    	password = getIntent().getExtras().getString("password");
    	initializeWebservice();
    	initializeGraphs(); 	
    	initializeCheckBoxes();
    	
    	printInformation("opProductionunitCode", "opAreaCode", "opFcty1Code", "objectCode");
	}


	private void initializeWebservice() {
    	namespace = "http://pweldaystatuswsp.service.generated.ws.frmw.ec.com/";
    	url = "http://wv001927.eu.tieto.com/com.ec.frmw.ws.generated/PwelDayStatusWspService?";
    	webservice = new PwelDayStatusService(username , password, namespace, url);
    	valueList = webservice.findByPKTimeRange("9FB4E1510D033B19E040340A2B4042D7", "2003-01-01", "2003-01-31");					 
    	data = (TextView) findViewById(R.id.data);
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

    private void initializeGraphs() {
    	RelativeLayout relative = (RelativeLayout) findViewById(R.id.graphLayout);
    	graph = new Graph(this, "Well");
    	relative.addView(graph);
    	
    	graph.addEmptyGraphLine("Oil", Color.BLACK);
    	graph.addEmptyGraphLine("Gas", Color.BLUE);
    	graph.addEmptyGraphLine("Water", Color.GREEN);
    	
    	graph.addValuesToExistingLines(valueList, "theorOilVol", "theorGasVol", "theorWaterVol");
	}

	private void initializeCheckBoxes() {
    	CheckBox oilBox = (CheckBox) findViewById(R.id.oilBox);
    	CheckBox gasBox = (CheckBox) findViewById(R.id.gasBox);
    	CheckBox waterBox = (CheckBox) findViewById(R.id.waterBox);
    	
    	oilBox.setChecked(true);
    	gasBox.setChecked(true);
    	waterBox.setChecked(true);
    	
    	oilBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 0));
    	gasBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 1));
    	waterBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, 2));
	}
    


    
}