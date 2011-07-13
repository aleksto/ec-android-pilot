package com.tieto.ec.gui.table;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Cell extends RelativeLayout{
	
	private TextView textView;
	
	public Cell(Context context, String text, int backgroundColor, int textColor, int borderColor) {
		//Super
		super(context);
		
		//TextView
		textView = new TextView(context);
		textView.setText(text);
		textView.setBackgroundColor(backgroundColor);
		textView.setTextColor(textColor);
		textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		textView.setGravity(RelativeLayout.CENTER_IN_PARENT);
		
		//This
		addView(textView);
		setBackgroundColor(borderColor);
		setPadding(2, 2, 2, 2);
	}
}
