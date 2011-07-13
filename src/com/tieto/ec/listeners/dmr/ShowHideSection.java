package com.tieto.ec.listeners.dmr;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

public class ShowHideSection implements OnClickListener{

	private String title;
	private TableLayout table;
	private View removed;
	private boolean showing;
	
	public ShowHideSection(String title, TableLayout table){
		this.title = title;
		this.table = table;
		showing = true;
	}
	
	public void onClick(View v) {
		if(removed == null){
			removed = table.getChildAt(getIdx()+1);
			onClick(v);
		}else{
			if(showing){
				table.removeViewAt(getIdx()+1);
				showing = false;
			}
			else{
				table.addView(removed, getIdx()+1);
				showing = true;
			}
		}
	}
	
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
