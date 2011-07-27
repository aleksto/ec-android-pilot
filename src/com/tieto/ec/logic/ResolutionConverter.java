package com.tieto.ec.logic;

import com.ec.prod.android.pilot.model.Resolution;
import com.tieto.ec.enums.OptionTitle;

/**
 * Class for converting a String to a resolution integer representing a time interval
 */
public class ResolutionConverter {

	/**
	 * This method converts a String representing a time interval and converts it
	 * into a resolution integer. Strings daily, weekly, monthly and yearly represents
	 * the integers 1, 2, 3 and 4 respectively. 
	 * @param resolution
	 * @return
	 */
	public static int convert(String resolution){
		if(resolution.equalsIgnoreCase(OptionTitle.Daily.toString())){
			return Resolution.DAILY;
		}else if(resolution.equalsIgnoreCase(OptionTitle.Weekly.toString())){
			return Resolution.WEEKLY;
		}else if(resolution.equalsIgnoreCase(OptionTitle.Monthly.toString())){
			return Resolution.MONTHLY;
		}else{
			return Resolution.YEARLY;
		}
	}
	
	/**
	 * Converts A {@link Integer} resolution to a {@link String}
	 * @see Resolution
	 * @param resolution int resolution 
	 * @return A String representing the resolution, etc "Daily", "Weekly", "Monthly", "Yearly"
	 */
	public static String convert(int resolution){
		if(resolution == Resolution.DAILY){
			return "Daily";
		}else if(resolution == Resolution.WEEKLY){
			return "Weekly";
		}else if(resolution == Resolution.MONTHLY){
			return "Monthly";
		}else{
			return "Yearly";
		}
	}
}
