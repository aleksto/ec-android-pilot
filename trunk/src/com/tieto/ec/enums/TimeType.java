package com.tieto.ec.enums;

public enum TimeType {

	off{
		@Override
		public String toString(){
			return "Off";
		}
	},
	min30{
		@Override
		public String toString(){
			return "Minutes 30";
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
}
