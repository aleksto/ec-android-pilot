package com.tieto.ec.gui.dmr;

import com.ec.prod.android.pilot.model.Resolution;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.listeners.dmr.SubButtonRowButtonsListener;

import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonRow extends LinearLayout{

	private Button daily, weekly, monthly, yearly;
	private final DailyMorningReport dmr;
	
	/**
	 * Creates a new {@link ButtonRow} that will appear underneath the {@link DateRow} when its
	 * been a {@link OnLongClickListener} on the Date 
	 * @param dmr {@link DailyMorningReport} 
	 */
	public ButtonRow(DailyMorningReport dmr) {
		//Super
		super(dmr);
		
		//Init
		this.dmr = dmr;
		daily = new Button(dmr);
		weekly = new Button(dmr);
		monthly = new Button(dmr);
		yearly = new Button(dmr);
		
		//Text
		daily.setText("Daily");
		weekly.setText("Weekly");
		monthly.setText("Monthly");
		yearly.setText("Yearly");
		
		//LayoutParams
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f);

		//Listeners
		daily.setOnClickListener(new SubButtonRowButtonsListener(dmr, Resolution.DAILY));
		weekly.setOnClickListener(new SubButtonRowButtonsListener(dmr, Resolution.WEEKLY));
		monthly.setOnClickListener(new SubButtonRowButtonsListener(dmr, Resolution.MONTHLY));
		yearly.setOnClickListener(new SubButtonRowButtonsListener(dmr, Resolution.YEARLY));
		
		//This
		setPadding(10, 10, 10, 0);
		addView(daily, params);
		addView(weekly, params);
		addView(monthly, params);
		addView(yearly, params);
	}

	/**
	 * Check the current resolution in the report, and disable the corresponding {@link Button}
	 */
	public void refreshButtonsState() {
		daily.setEnabled(true);
		weekly.setEnabled(true);
		monthly.setEnabled(true);
		yearly.setEnabled(true);
		
		if(dmr.getResolution() == Resolution.DAILY){
			daily.setEnabled(false);
		}else if(dmr.getResolution() == Resolution.WEEKLY){
			weekly.setEnabled(false);
		}else if(dmr.getResolution() == Resolution.MONTHLY){
			monthly.setEnabled(false);
		}else if(dmr.getResolution() == Resolution.YEARLY){
			yearly.setEnabled(false);
		}
	}
}
