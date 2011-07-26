package com.tieto.ec.gui.table;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Cell extends RelativeLayout{
	
	private TextView textView;	
	
	/**
	 * Creates a new Cell for a table, 
	 * @param context {@link Context} needed for Android framework actions
	 * @param text {@link String} text of the cell
	 * @param backgroundColor {@link Color} of the background
	 * @param textColor {@link Color} of the text
	 * @param borderColor {@link Color} of the border
	 */
	public Cell(Context context, String text, int backgroundColor, int textColor, int borderColor) {
		//Super
		super(context);
		
		//TextView
		textView = new TextView(context);
		textView.setText(text);
		textView.setBackgroundColor(backgroundColor);
		textView.setTextColor(textColor);
		textView.setPadding(10, 5, 10, 5);
		textView.setTypeface(Typeface.create("arial", Typeface.NORMAL));
		
		//Params
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		//Background
		RelativeLayout background = new RelativeLayout(context);
		background.setBackgroundColor(backgroundColor);
		background.addView(textView, layoutParams);

		//This
		addView(background, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setBackgroundColor(borderColor);
		setPadding(2, 2, 2, 2);
	}
}
