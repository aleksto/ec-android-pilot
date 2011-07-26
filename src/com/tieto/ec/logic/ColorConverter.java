package com.tieto.ec.logic;

import android.graphics.Color;

import com.tieto.ec.enums.ColorType;

/**
 * Class for converting a string representation of a color to a {@link Color}
 */
public class ColorConverter {

	/**
	 * This method takes a string which stands for a certain color, checks
	 * if this color is defined in {@link ColorType} and returns the {@link Color}
	 * which it represents.
	 * @param color
	 * @return
	 */
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
