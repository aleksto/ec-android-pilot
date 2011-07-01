package com.tieto.ec.activities;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.*;
import com.tieto.R;
import com.tieto.ec.listeners.GraphLineCheckBoxListener;
import com.tieto.ec.webServices.PwelDayStatusService;
 
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
 
public class Main extends Activity
{
	private XYPlot graph;
    private String username;
    private String password;
    private String namespace;
    private String url;
    private PwelDayStatusService webservice;
    private ArrayList<HashMap<String, String>>  valueList;
    private TextView data;
	
    private SimpleXYSeries gasLine;
    private LineAndPointFormatter gasLineformat;
    private SimpleXYSeries oilLine;
    private LineAndPointFormatter oilLineformat;
    private SimpleXYSeries waterLine;
    private LineAndPointFormatter waterLineformat;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.graph_view_landscape);
    	
    	username = getIntent().getExtras().getString("username");
    	password = getIntent().getExtras().getString("password");
    	 
    	
    	oilLineformat = new LineAndPointFormatter(Color.BLACK, Color.DKGRAY, Color.BLUE);
    	gasLineformat = new LineAndPointFormatter(Color.BLACK, Color.DKGRAY, Color.MAGENTA);
    	waterLineformat = new LineAndPointFormatter(Color.BLACK, Color.DKGRAY, Color.YELLOW);
    	graph = (XYPlot) findViewById(R.id.graph);
    	
    	testWebservice();
    	printInformation("opProductionunitCode", "opAreaCode", "opFcty1Code", "objectCode");
    	initializeGraphs(); 	
    	
    	CheckBox oilBox = (CheckBox) findViewById(R.id.oilBox);
    	CheckBox gasBox = (CheckBox) findViewById(R.id.gasBox);
    	CheckBox waterBox = (CheckBox) findViewById(R.id.waterBox);
    	
    	oilBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, gasLine, oilLineformat));
    	gasBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, oilLine, gasLineformat));
    	waterBox.setOnCheckedChangeListener(new GraphLineCheckBoxListener(graph, waterLine, waterLineformat));

	}

	private void testWebservice() {
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
    	graph.addSeries(oilLine, oilLineformat);
    	graph.addSeries(gasLine, gasLineformat);
    	graph.addSeries(waterLine, waterLineformat);
    	
    	int counter = 0;
    	for (HashMap<String,String> map : valueList){
    		counter++;	
       		addPointToLine(1, counter, Double.valueOf(map.get("theorOilVol")));
    	} 	
    	counter = 0;
    	for (HashMap<String,String> map : valueList){
    		counter++;
    		addPointToLine(2, counter, Double.valueOf(map.get("theorGasVol")));
    	} 	
    	counter = 0;
    	for (HashMap<String,String> map : valueList){
    		counter++;
    		addPointToLine(3, counter, Double.valueOf(map.get("theorWaterVol")));
    	}
	}

    private void addPointToLine(int lineNr, double x, double y){
        SimpleXYSeries line = null;
        Iterator<XYSeries> iterator = graph.getSeriesSet().iterator();
        for (int i = 0; i < lineNr; i++) {
                    line = (SimpleXYSeries) iterator.next();
              }
        Log.d("tieto", line.getTitle() + " X: " + x + " Y: " + y); 
        line.addLast(x, y);
      }

    
}