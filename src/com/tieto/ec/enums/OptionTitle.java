package com.tieto.ec.enums;

public enum OptionTitle {
	
	//Daily Morning Report
	SecurityOptions{
	    @Override
		public String toString() {
	        return "Security Options";
	    }
	}, 
	ColorOptions{
	    @Override
		public String toString() {
	        return "Color Options";
	    }
	}, 
	ReportOptions{
	    @Override
		public String toString() {
	        return "Report Options";
	    }
	},
	TextColor{
	    @Override
		public String toString() {
	        return "Text Color";
	    }
	}, 
	BackgroundColor{
	    @Override
		public String toString() {
	        return "Background Color";
	    }
	}, 
	CellBackgroundColor{
	    @Override
		public String toString() {
	        return "Cell Background Color";
	    }
	}, 
	CellBorderColor{
	    @Override
		public String toString() {
	        return "Cell Border Color";
	    }
	},
	DMRReport{
	    @Override
		public String toString() {
	        return "DMR Report";
	    }
	},
	ClearUsernameAndPassword {
		@Override
		public String toString() {
	        return "Clear Username\nAnd Password";
	    }
	},
	RememberLoginCredentials {
		@Override
		public String toString() {
	        return "Remember Login\nCredentials";
	    }
	},
	Interval,
	UpdateInterval{
		@Override
		public String toString(){
			return "Update Interval";
		}
	},
	Dates,
	ToDate{
		@Override
		public String toString() {
	        return "To Date";
	    }
	},
	FromDate{
		@Override
		public String toString() {
	        return "From Date";
	    }
	},
	Daily,
	Weekly,
	Monthly,
	Yearly,
	VisibleColumns{
		@Override
		public String toString(){
			return "Visible Columns";
		}
	},
	WarningLevels{
		@Override
		public String toString(){
			return "Warning Levels";
		}
	},
	DisplayWarnings{
		@Override
		public String toString(){
			return "Automatic display\nwarnings?";
		}
	},
	
	//Login 
	InputOptions {
		@Override
		public String toString() {
	        return "Input Options";
	    }
	},
	WebserviceURL {
		@Override
		public String toString() {
	        return "Webservice URL";
	    }
	},
	WebserviceNamespace {
		@Override
		public String toString() {
	        return "Webservice Namespace";
	    }
	},




	
}