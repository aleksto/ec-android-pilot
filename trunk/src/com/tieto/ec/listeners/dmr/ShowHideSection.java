package com.tieto.ec.listeners.dmr;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.Section;
import com.tieto.R;
import com.tieto.ec.logic.SectionBuilder;

public class ShowHideSection implements OnClickListener{

	private final String title;
	private final TableLayout table;
	private boolean showing;
	private final View status;
	private final SectionBuilder sectionBuilder;
	private AlphaAnimation scaleIn, scaleOut;
	private View removed;
	
	/**
	 * Creates a new {@link OnClickListener} for a {@link View}
	 * This will hide and show the sections with given title
	 * @param sectionBuilder 
	 * @param title Title of the {@link Section}
	 * @param table The {@link TableLayout} where the section is added
	 * @param status 
	 */
	public ShowHideSection(SectionBuilder sectionBuilder, String title, TableLayout table, View status){
		this.sectionBuilder = sectionBuilder;
		this.title = title;
		this.table = table;
		this.status = status;
		showing = true;
		
	}
	
	/**
	 * Runs when user clicks the {@link View} with this {@link OnClickListener} attached
	 */
	public void onClick(View v) {
		if(removed == null){
			removed = table.getChildAt(getIdx()+1);
			onClick(v);
		}else{
			if(showing){
				setAnimation(removed, false);
				table.removeViewAt(getIdx()+1);
				sectionBuilder.getOpenSections().remove(title);
				status.setBackgroundResource(R.drawable.expander_ic_minimized);
				table.invalidate();
				showing = false;
			}
			else{
				setAnimation(removed, true);
				table.addView(removed, getIdx()+1);
				sectionBuilder.getOpenSections().add(title);
				status.setBackgroundResource(R.drawable.expander_ic_maximized);
				showing = true;
			}
		}	
	}
	
	private void setAnimation(View view, boolean in){
		if (in) {
			scaleIn = new AlphaAnimation(0, 1);
			scaleIn.setDuration(500);
			view.setAnimation(scaleIn);
		}
	}
	
	/**
	 * Gets the index of the {@link Section} with given title
	 * @return The index of the {@link Section}
	 */
	private int getIdx(){
		for (int i = 0; i<table.getChildCount(); i++) {
			if(table.getChildAt(i) instanceof LinearLayout){
				LinearLayout titleRow = (LinearLayout) table.getChildAt(i);
				
				TextView title = (TextView)titleRow.getChildAt(1);
				if(title != null && title.getText().equals(this.title+":")){
					return i;
				}
			}
		}
		return -1;
	}
}
