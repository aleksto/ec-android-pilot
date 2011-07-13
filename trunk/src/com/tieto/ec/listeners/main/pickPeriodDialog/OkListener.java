package com.tieto.ec.listeners.main.pickPeriodDialog;

import com.tieto.ec.activities.WellPeriod;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

public class OkListener implements OnClickListener{

	private WellPeriod main;
	private Dialog parent;
	private DatePicker fromDate;
	private DatePicker toDate;
	
	public OkListener(WellPeriod main, DatePicker fromDate, DatePicker toDate, Dialog parent) {
		this.fromDate = fromDate;
		this.toDate = toDate; 
		this.main = main;
		this.parent = parent;
	}

	
	public void onClick(View v) {
		int fromYear = fromDate.getYear();
		int fromMonth = fromDate.getMonth();
		int fromDay = fromDate.getDayOfMonth();
		int toYear = toDate.getYear();
		int toMonth = toDate.getMonth();
		int toDay = toDate.getDayOfMonth();
		
		StringBuilder toDateString = new StringBuilder();
		
		toDateString.append(toYear + "-");
		
		if(toMonth < 10){
			toDateString.append("0" + (toMonth+1) + "-");
		}else{
			toDateString.append((toMonth+1) + "-");
		}
		
		if(toDay < 10){
			toDateString.append("0" + toDay);
		}else{
			toDateString.append(toDay);
		}
	
		
		StringBuilder fromDateString = new StringBuilder();
		
		fromDateString.append(fromYear + "-");
		
		if(fromMonth < 10){
			fromDateString.append("0" + (fromMonth+1) + "-");
		}else{
			fromDateString.append((fromMonth+1) + "-");
		}
		
		if(fromDay < 10){
			fromDateString.append("0" + fromDay);
		}else{
			fromDateString.append(fromDay);
		}
		
		Log.d("tieto", fromDateString.toString() + "\t" + toDateString.toString());
		main.runWebservice("", fromDateString.toString(), toDateString.toString());
		parent.hide();
	}

}
