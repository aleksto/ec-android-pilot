package com.tieto.ec.enums;

public enum TimeType {

	off{
		@Override
		public String toString(){
			return "#Off";
		}
	},
	//Test
	sec5{
		@Override
		public String toString(){
			return "Seconds 15";
		}
	},
	min15{
		@Override
		public String toString(){
			return "Minutes 15";
		}
	},
	min30{
		@Override
		public String toString(){
			return "Minutes 30";
		}
	},
	min45{
		@Override
		public String toString(){
			return "Minutes 45";
		}
	},
	hour1{
		@Override
		public String toString(){
			return "Hour 1";
		}
	},
	hour2{
		@Override
		public String toString(){
			return "Hour 2";
		}
	},
	hour5{
		@Override
		public String toString(){
			return "Hour 5";
		}
	},
	hour10{
		@Override
		public String toString(){
			return "Hour 10";
		}
	},
	hour12{
		@Override
		public String toString(){
			return "Hour 12";
		}
	},
	day1{
		@Override
		public String toString(){
			return "Day 1";
		}
	},
	day2{
		@Override
		public String toString(){
			return "Days 2";
		}
	},
	day3{
		@Override
		public String toString(){
			return "Days 3";
		}
	},
}
