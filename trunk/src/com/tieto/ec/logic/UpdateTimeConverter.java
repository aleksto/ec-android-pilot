package com.tieto.ec.logic;

import com.tieto.ec.enums.TimeType;

public class UpdateTimeConverter {

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
		else if(time.equalsIgnoreCase("Debug: 20 sec")){
			return 20*1000;
		}
		else{
			return -1;
		}
	}
}