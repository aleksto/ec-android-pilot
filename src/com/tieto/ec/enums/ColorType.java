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
	    public String toString() {
	        return "Dark Gray";
	    }
	},
	LightGray{
	    public String toString() {
	        return "Light Gray";
	    }
	},
	LightBlue{
	    public String toString() {
	        return "Light Blue";
	    }
	
	},;
}
