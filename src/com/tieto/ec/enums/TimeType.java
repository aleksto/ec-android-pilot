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
			return "30 Minutes";
		}
	},
	hour1{
		@Override
		public String toString(){
			return "1 Hour";
		}
	},
	hour2{
		@Override
		public String toString(){
			return "2 Hours";
		}
	},
	hour5{
		@Override
		public String toString(){
			return "5 Hours";
		}
	},
	hour10{
		@Override
		public String toString(){
			return "10 Hours";
		}
	},
	hour12{
		@Override
		public String toString(){
			return "12 Hours";
		}
	},
}
