package com.tieto.ec.activities;

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
		
		
		//Super
//		super.onCreate(savedInstanceState, username, password, namespace, url);
		
		valueList = webservice.findByPK(daytime, objectID);
	}
}
