package com.tieto.ec.enums;

public enum OptionTitle {
	
	//Daily Morning Report
	SecurityOptions{
	    public String toString() {
	        return "Security Options";
	    }
	}, 
	ColorOptions{
	    public String toString() {
	        return "Color Options";
	    }
	}, 
	ReportOptions{
	    public String toString() {
	        return "Report Options";
	    }
	},
	TextColor{
	    public String toString() {
	        return "Text Color";
	    }
	}, 
	BackgroundColor{
	    public String toString() {
	        return "Background Color";
	    }
	}, 
	CellBackgroundColor{
	    public String toString() {
	        return "Cell Background Color";
	    }
	}, 
	CellBorderColor{
	    public String toString() {
	        return "Cell Border Color";
	    }
	},
	DMRReport{
	    public String toString() {
	        return "DMR Report";
	    }
	},
	ClearUsernameAndPassword {
		public String toString() {
	        return "Clear Username\nAnd Password";
	    }
	},
	RememberLoginCredentials {
		public String toString() {
	        return "Remember Login\nCredentials";
	    }
	},
	Interval,
	UpdateInterval{
		public String toString(){
			return "Update Interval";
		}
	},
	Dates,
	ToDate{
		public String toString() {
	        return "To Date";
	    }
	},
	FromDate{
		public String toString() {
	        return "From Date";
	    }
	},
	Daily,
	Weekly,
	Monthly,
	Yearly,
	VisibleColumns{
		public String toString(){
			return "Visible Columns";
		}
	},
	WarningLevels{
		public String toString(){
			return "Warning Levels";
		}
	},

	//Login 
	InputOptions {
		public String toString() {
	        return "Input Options";
	    }
	},
	WebserviceURL {
		public String toString() {
	        return "Webservice URL";
	    }
	},
	WebserviceNamespace {
		public String toString() {
	        return "Webservice Namespace";
	    }
	},




	
}