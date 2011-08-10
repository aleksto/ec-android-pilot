package com.tieto.ec.logic;

import android.R.string;

import com.tieto.ec.enums.TimeType;
import com.tieto.ec.service.ServiceThread;

/**
 * This class is used for converting {@link TimeType} to milliseconds
 */
public class UpdateTimeConverter {

	/**
	 * This method is used in {@link ServiceThread} for converting a {@link string} to milliseconds
	 * The String must be one off the values in {@link TimeType}
	 * @param time {@link TimeType}.toString()
	 * @return number off milliseconds for given {@link TimeType}
	 */
	public static long parse(String time){
		if(time.equalsIgnoreCase(TimeType.day1.toString())){
			return 	1*24*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.day2.toString())){
			return 	2*24*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.day3.toString())){
			return 	3*24*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.hour1.toString())){
			return 	1*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.hour2.toString())){
			return 	2*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.hour5.toString())){
			return 	5*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.hour10.toString())){
			return 	10*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.hour12.toString())){
			return 	12*60*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.min15.toString())){
			return 	15*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.min30.toString())){
			return 	30*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.min45.toString())){
			return 	45*60*1000;
		}
		else if(time.equalsIgnoreCase(TimeType.sec20.toString())){
			return 20*1000;
		}
		else{
			return -1;
		}
	}
}