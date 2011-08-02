package com.tieto.ec.gui.login;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Slider extends RelativeLayout implements OnTouchListener{

	private final int BUTTON_WIDTH = 160;
	private final int TEXT_SIZE = 25;
	
	private int padding;
	private int alpha;
	private ImageButton sliderButton;
	private Runnable onFinish;
	private TextView text;

	/**
	 * Creates a new slider and runs the {@link Runnable} when the lock is glided over
	 * @param context {@link Context} needed for Android framework actions
	 * @param onFinish The {@link Runnable} to run when lock is glided over
	 * @param messageText {@link String} message displayed on the {@link Slider}
	 */
	public Slider(Context context, Runnable onFinish, String messageText) {
		//Super
		super(context);
		this.onFinish = onFinish;

		//Init
		sliderButton = new ImageButton(context);
		
		//Text
		text = new TextView(context);
		LayoutParams textBoxParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		textBoxParams.addRule(ALIGN_PARENT_RIGHT);
		textBoxParams.addRule(CENTER_VERTICAL);
		textBoxParams.setMargins(0, 0, 15, 0);
		text.setText(messageText);
		text.setTextSize(TEXT_SIZE);
		addView(text, textBoxParams);

		//SliderButton
		LayoutParams sliderParams = new LayoutParams(BUTTON_WIDTH, BUTTON_WIDTH/2);
		sliderParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		sliderButton.setImageResource(android.R.drawable.ic_lock_lock);
		sliderButton.setOnTouchListener(this);
		addView(sliderButton, sliderParams);

		//This
		LayoutParams thisParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, BUTTON_WIDTH/2);
		thisParams.addRule(ALIGN_PARENT_BOTTOM);
		thisParams.addRule(CENTER_HORIZONTAL);
		setLayoutParams(thisParams);
		setBackgroundResource(android.R.drawable.editbox_background);
		setPadding(0, 5, 0, 0);
	}

	/**
	 * Method called when the user touches the lock button
	 * it takes the x coordinate of the finger and slides the button to the fingers x coordinate
	 * and when finger is released it either slides the button back, or runs the {@link Runnable}
	 */
	public boolean onTouch(View arg0, MotionEvent arg1) {
		//text.setVisibility(View.GONE);
		
		if(arg1.getAction() == MotionEvent.ACTION_MOVE){
			//text.setVisibility(GONE);
			padding = (int) (getPaddingLeft() + arg1.getX() - BUTTON_WIDTH/2);
			alpha = (int) (200 - (padding/(getWidth()/2f)*200));
			if(alpha > 0){
				text.setTextColor(Color.argb(alpha, 120, 120, 120));				
			}else{
				text.setTextColor(Color.argb(0, 120, 120, 120));		
			}
		}else if(arg1.getAction() == MotionEvent.ACTION_UP){
			if(padding > getWidth()-170){
				//Complete
				onFinish.run();
			}else{
				padding = 0;	
				text.setTextColor(Color.argb(200, 120, 120, 120));
				text.setVisibility(View.VISIBLE);
			}
		}
		
		if(padding >= 0 && padding < getWidth() - BUTTON_WIDTH){
			setPadding(padding, 5, 0, 0);				
		}
		
		invalidate();
		
		return true;
	}
	
}