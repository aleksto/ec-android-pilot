package com.tieto.ec.enums;

public enum TimeType {

	off{
		public String toString(){
			return "Off";
		}
	},
	min15{
		public String toString(){
			return "Minutes 15";
		}
	},
	min30{
		public String toString(){
			return "Minutes 30";
		}
	},
	min45{
		public String toString(){
			return "Minutes 45";
		}
	},
	hour1{
		public String toString(){
			return "Hour 1";
		}
	},
	hour2{
		public String toString(){
			return "Hour 2";
		}
	},
	hour5{
		public String toString(){
			return "Hour 5";
		}
	},
	hour10{
		public String toString(){
			return "Hour 10";
		}
	},
	hour12{
		public String toString(){
			return "Hour 12";
		}
	},
	day1{
		public String toString(){
			return "Day 1";
		}
	},
	day2{
		public String toString(){
			return "Days 2";
		}
	},
	day3{
		public String toString(){
			return "Days 3";
		}
	},
}
