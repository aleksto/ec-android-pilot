package com.tieto.ec.logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.tieto.ec.gui.Graph;
import com.tieto.ec.webServices.PwelDayStatusService;

public class WebserviceThread implements Runnable{

	private Thread thread;
	private ArrayList<HashMap<String, String>> valueList;
	private PwelDayStatusService webservice;
	private Graph graph;
	private String objectID;
	private String fromDate;
	private String toDate;
	
	public WebserviceThread(PwelDayStatusService webservice, Graph graph){
		this.webservice = webservice;
		this.graph = graph;
	}
	
	public synchronized ArrayList<HashMap<String, String>> startThread(String objectID, String fromDate, String toDate){
		thread = new Thread(this);
		thread.start();
		this.objectID = objectID;
		this.fromDate = fromDate;
		this.toDate = toDate;
		
		return valueList;
		
	}
	
	public void run() {
		valueList = webservice.findByPKTimeRange(objectID, fromDate, toDate);	
	}

}
