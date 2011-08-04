package com.tieto.ec.listeners.dmr;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.Section;

public class ShowHideSection implements OnClickListener{

	private String title;
	private TableLayout table;
	private View removed;
	private boolean showing;
	
	/**
	 * Creates a new {@link OnClickListener} for a {@link View}
	 * This will hide and show the sections with given title
	 * @param title Title of the {@link Section}
	 * @param table The {@link TableLayout} where the section is added
	 */
	public ShowHideSection(String title, TableLayout table){
		this.title = title;
		this.table = table;
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
				table.removeViewAt(getIdx()+1);
				table.invalidate();
				showing = false;
			}
			else{
				table.addView(removed, getIdx()+1);
				showing = true;
			}
		}
	}
	
	/**
	 * Gets the index of the {@link Section} with given title
	 * @return The index of the {@link Section}
	 */
	private int getIdx(){
		for (int i = 0; i<table.getChildCount(); i++) {
			if(table.getChildAt(i) instanceof TextView){
				TextView text = (TextView) table.getChildAt(i);
				
				if(text.getText().equals(title+":")){
					return i;
				}
			}
		}
		return -1;
	}
}
