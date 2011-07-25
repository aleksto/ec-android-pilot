package com.tieto.ec.logic;

import android.graphics.Color;

import com.tieto.ec.enums.ColorType;

public class ColorConverter {

	public static int parseColor(String color){
		if(color.equalsIgnoreCase(ColorType.DarkGray.toString())){
			return Color.DKGRAY;
		}else if(color.equalsIgnoreCase(ColorType.LightGray.toString())){
			return Color.LTGRAY;
		}else if(color.equalsIgnoreCase(ColorType.LightBlue.toString())){
			return Color.rgb(180, 201, 220);
		}else{
			return Color.parseColor(color);
		}
	}
}
