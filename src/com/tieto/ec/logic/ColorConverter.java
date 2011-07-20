package com.tieto.ec.logic;

import android.graphics.Color;

public class ColorConverter {

	public static int parseColor(String color){
		if(color.equalsIgnoreCase("Dark Gray")){
			return Color.DKGRAY;
		}else if(color.equalsIgnoreCase("Light Gray")){
			return Color.LTGRAY;
		}else if(color.equalsIgnoreCase("Light Blue")){
			return Color.rgb(180, 201, 220);
		}else{
			return Color.parseColor(color);
		}
	}
}
