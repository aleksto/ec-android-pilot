package com.tieto.ec.enums;

public enum OptionTitle {
	
	//Daily Morning Report
	Options,
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
	NotificationOptions{
	    @Override
		public String toString() {
	        return "Notification Options";
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
	Default {
		@Override
		public String toString() {
	        return "Default";
	    }
	}, 
	ChooseSections {
		@Override
		public String toString() {
	        return "Choose Sections";
	    }
	}, 
	SentOptions{
		@Override
		public String toString() {
	        return "Email Options";
	    }
	}, 
	StandardReceiver{
		@Override
		public String toString() {
	        return "Standard Receiver(s)";
	    }
	}, 
	StandardTopic{
		@Override
		public String toString() {
	        return "Standard Topic";
	    }
	}, 
	Signature, 
	TimeDeterminedNotification{
		@Override
		public String toString() {
	        return "Time Determined";
	    }
	},  
	IntervalDeterminedNotification{
		@Override
		public String toString() {
	        return "Interval Determined";
	    }
	}, 
	SetTime{
		@Override
		public String toString() {
	        return "Set Time";
	    }
	}, 
	Monday, 
	Tuesday,
	Wednsday,
	Thursday,
	Friday,
	Saturday,
	Sunday,
	sound{
 		@Override
		public String toString() {
			return "Sound";
		}
	}, 
	vibrate{
		@Override
		public String toString() {
			return "Vibrate";
		}
	},
}