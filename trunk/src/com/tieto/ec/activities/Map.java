package com.tieto.ec.activities;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.os.Bundle;

public class Map extends MapActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		//Super
		super.onCreate(arg0);
		
		//This
		setContentView(new MapView(this, "0hoZ07_5UfH6X3Y2PsdQhiZz93hfNnj9n07T-Ow"));
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
