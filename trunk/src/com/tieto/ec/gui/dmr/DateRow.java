package com.tieto.ec.gui.dmr;

import java.util.Date;

import android.graphics.Color;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.listeners.dmr.ChangeDayListener;
import com.tieto.ec.listeners.dmr.DisplayButtonRowListener;

public class DateRow extends RelativeLayout{

	private TextView currentDay;

	/**
	 * Creates a new {@link DateRow} 
	 * @param dmr {@link DailyMorningReport}
	 */
	public DateRow(DailyMorningReport dmr){
		//Super
		super(dmr);
		
		//ButtonRow
		Date date = dmr.getToDate();
		setBackgroundResource(android.R.drawable.title_bar);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Button previousDay = new Button(dmr);
		Button nextDay = new Button(dmr);
		RelativeLayout titleLayout = new RelativeLayout(dmr);
		TextView dmrTitle = new TextView(dmr);
		currentDay = new TextView(dmr);
		dmrTitle.setId(1);
		
		//Layout Rules
		params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		params2.addRule(RelativeLayout.CENTER_VERTICAL);
		params3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params3.addRule(RelativeLayout.CENTER_VERTICAL);
		params4.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params5.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params5.addRule(RelativeLayout.BELOW, dmrTitle.getId());
		params6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params6.addRule(RelativeLayout.CENTER_VERTICAL);
		
		//Buttons
		previousDay.setBackgroundResource(android.R.drawable.ic_media_rew);
		nextDay.setBackgroundResource(android.R.drawable.ic_media_ff);
		currentDay.setText(date.getDate() + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900));
		currentDay.setTextColor(Color.BLACK);
		dmrTitle.setText("Daily Morning Report");
		dmrTitle.setTextColor(Color.BLACK);
		nextDay.setOnClickListener(new ChangeDayListener(dmr, ChangeDayListener.Action.NEXT_DAY));
		previousDay.setOnClickListener(new ChangeDayListener(dmr, ChangeDayListener.Action.PREVIOUS_DAY));
		titleLayout.setOnClickListener(new ChangeDayListener(dmr, ChangeDayListener.Action.CHOOSE_DAY));
		titleLayout.setOnLongClickListener(new DisplayButtonRowListener(dmr));
		
		
		//ButtonRow Childs
		addView(previousDay, params2);
		addView(titleLayout, params3);
		titleLayout.addView(dmrTitle, params4);
		titleLayout.addView(currentDay, params5);
		addView(nextDay, params6);
	}
	
	/**
	 * @return The {@link TextView} in the {@link DateRow} displaying the {@link Date}
	 */
	public TextView getCurrentDayLabel(){
		return currentDay;
	}
}
