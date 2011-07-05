package com.tieto.ec.activities;

import com.tieto.ec.gui.BarGraph;
import com.tieto.ec.logic.NameFormat;

import android.graphics.Color;
import android.os.Bundle;

public class WellSingle extends Main{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Extras
		String daytime = getIntent().getExtras().getString("daytime");
		String objectID = getIntent().getExtras().getString("objectID");
		String username = getIntent().getExtras().getString("username");
		String password = getIntent().getExtras().getString("password");
		String namespace = getIntent().getExtras().getString("namespace");
		String url = getIntent().getExtras().getString("url");
		BarGraph graph = new BarGraph(this, "Well", Color.BLACK);
		
		//Super
		super.onCreate(savedInstanceState, username, password, namespace, url, null);
		
		//this
		setContentView(graph);
		
		//Valuelist
		valueList = webservice.findByPK(daytime, objectID);
		
		//Graph
		graph.addBarsWithValueList(valueList, "Well", "theorOilVol", "theorGasVol", "theorWaterVol");
		graph.setGridPadding(45, 20, 45, 0);
		graph.setTicksPerDomainLabel(2);
		graph.setDomainStepValue(5);
		graph.setDomainValueFormat(new NameFormat("Oil", "Gas", "Water"));
	}
}
