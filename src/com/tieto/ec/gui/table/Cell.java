package com.tieto.ec.gui.table;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.TableCell;

public class Cell extends RelativeLayout{
	
	private TextView textView;	
	private TableCell actual, target;
	
	/**
	 * Creates a new Cell for a table, 
	 * @param context {@link Context} needed for Android framework actions
	 * @param text {@link String} text of the cell
	 * @param string 
	 * @param backgroundColor {@link Color} of the background
	 * @param textColor {@link Color} of the text
	 * @param borderColor {@link Color} of the border
	 */
	public Cell(Context context, TableCell actual, TableCell target, int backgroundColor, int textColor, int borderColor) {
		//Super
		super(context);
		
		//Init
		this.actual = actual;
		this.target = target;
		textView = new TextView(context);
		RelativeLayout line = new RelativeLayout(context);
		RelativeLayout background = new RelativeLayout(context);
		
		//TextView
		textView.setText(actual.getValue());
		textView.setBackgroundColor(backgroundColor);
		textView.setTextColor(textColor);
		textView.setPadding(10, 5, 10, 5);
		textView.setTypeface(Typeface.create("arial", Typeface.NORMAL));
		
		//Target check
		try{
			double actualValue = Double.valueOf(actual.getValue());
			double targetValue = Double.valueOf(target.getValue());
			double differential = actualValue/targetValue;
			if(differential >= 0.95){
				line.setBackgroundColor(backgroundColor);
			}else if(differential < 0.95 && differential >= 0.9){
				line.setBackgroundColor(Color.YELLOW);
			}else if(differential < 0.9){
				line.setBackgroundColor(Color.RED);
			}
		}catch(java.lang.NumberFormatException e){
			setBackgroundColor(backgroundColor);
		}
		
		//Params
		LayoutParams textParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		LayoutParams lineParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 3);
		textParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		//Background
		background.setBackgroundColor(backgroundColor);
		background.addView(textView, textParams);
		background.addView(line, lineParams);

		//This
		addView(background, new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
		setBackgroundColor(borderColor);
		setPadding(2, 2, 2, 2);
	}

	public TableCell getActual() {
		return actual;
	}

	public TableCell getTarget() {
		return target;
	}
}
