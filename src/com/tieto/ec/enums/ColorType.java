package com.tieto.ec.enums;

public enum ColorType {
	
	Black,
	Blue,
	Cyan,
	Gray,
	Green,
	Magenta,
	Red,
	White,
	Yellow,
	DarkGray{
	    @Override
		public String toString() {
	        return "Dark Gray";
	    }
	},
	LightGray{
	    @Override
		public String toString() {
	        return "Light Gray";
	    }
	},
	LightBlue{
	    @Override
		public String toString() {
	        return "Light Blue";
	    }
	
	},;
}
