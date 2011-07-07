package com.tieto.ec.gui;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Cell extends RelativeLayout{
	
	private TextView textView;
	
	public Cell(Context context, String text) {
		//Super
		super(context);
		
		//TextView
		textView = new TextView(context);
		textView.setText(text);
		textView.setBackgroundColor(Color.WHITE);
		textView.setTextColor(Color.BLACK);
		textView.setPadding(2, 2, 2, 2);
		textView.setGravity(RelativeLayout.CENTER_IN_PARENT);
		
		//This
		addView(textView);
		setBackgroundColor(Color.BLACK);
	}
}
