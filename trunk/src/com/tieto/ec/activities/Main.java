package com.tieto.ec.activities;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.androidplot.Plot;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.*;
import com.tieto.R;
import com.tieto.ec.webServices.PwelDayStatusService;
 
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
    private TextView view;
	private LineAndPointFormatter format;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	username = getIntent().getExtras().getString("username");
    	password = getIntent().getExtras().getString("password");
    	testWebservice();
    	printInformation();
    	
    	format = new LineAndPointFormatter(Color.BLACK, Color.YELLOW, Color.MAGENTA);
    	graph = (XYPlot) findViewById(R.id.graph);
    	
    	
    	graph.addSeries(simpleSeries(1.0, 2.0, 3.0, 4.0, 5.0, 6.0), format);
      
	}

	private SimpleXYSeries simpleSeries(Double ... values) {
		SimpleXYSeries graphLine = new SimpleXYSeries("");
		
		for (int i = 0; i < values.length; i+=2) {
			graphLine.addLast(values[i], values[i+1]);
		}
		
		return graphLine;
		
		
	}

	private void testWebservice() {
    	namespace = "http://pweldaystatuswsp.service.generated.ws.frmw.ec.com/";
    	url = "http://wv001927.eu.tieto.com/com.ec.frmw.ws.generated/PwelDayStatusWspService?";
    	webservice = new PwelDayStatusService(username , password, namespace, url);
    	valueList = webservice.findByPKTimeRange("9FB4E1510D033B19E040340A2B4042D7", "2003-01-01", "2003-01-31");					 
    	view = (TextView) findViewById(R.id.webservice);
	}

    private void printInformation() {
    	StringBuilder builder = new StringBuilder();
    	Log.d("tieto", valueList.size()+"");
    	for (HashMap<String, String> map : valueList) {
			Log.d("tieto", map.toString());
	        Set<String> keySet = map.keySet();
	        
	        for(String key: keySet)
	        {
	        	builder.append(key + ":" + map.get(key));
	        	builder.append("\n");
	        }
	       	        	
    	}
    	view.setText(builder);
	}

    
    
    
	private class MyDateFormat extends Format {
 
 
            // create a simple date format that draws on the year portion of our timestamp.
            // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
            // for a full description of SimpleDateFormat.
            private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
 
 
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                long timestamp = ((Number) obj).longValue();
                Date date = new Date(timestamp);
                return dateFormat.format(date, toAppendTo, pos);
            }
 
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
 
            }
        }
    
}