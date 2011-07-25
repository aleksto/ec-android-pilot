package com.tieto.ec.activities;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.tieto.ec.gui.dialogs.InfoDialog;

public class Map extends MapActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		//Super
		super.onCreate(arg0);
		
		//Init
		MapView map = new MapView(this, "0UNgOgmNX74tAYYOGjfS7Gl9IYpHJlZZTHqwtdg");	
		
		//Map
//		map.setBuiltInZoomControls(true);
        map.setEnabled(true);
        map.setClickable(true);
		
		//This
		setContentView(map);
		
		InfoDialog.showInfoDialog(this, "This is a feature comming later!");
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
