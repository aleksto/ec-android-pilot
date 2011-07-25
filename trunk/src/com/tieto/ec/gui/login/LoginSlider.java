package com.tieto.ec.gui.login;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

public class LoginSlider extends RelativeLayout implements OnTouchListener{

	private final int BUTTON_WIDTH = 160;
	
	private ImageButton sliderButton;
	private int padding;
	private Runnable onFinish;

	public LoginSlider(Context context, Runnable onFinish) {
		//Super
		super(context);
		this.onFinish = onFinish;

		//Init
		sliderButton = new ImageButton(context);

		//SliderButton
		LayoutParams sliderParams = new LayoutParams(BUTTON_WIDTH, BUTTON_WIDTH/2);
		sliderParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		sliderButton.setImageResource(android.R.drawable.ic_lock_lock);
		sliderButton.setOnTouchListener(this);
		addView(sliderButton, sliderParams);

		//This
		LayoutParams thisParams = new LayoutParams(LayoutParams.FILL_PARENT, BUTTON_WIDTH/2);
		thisParams.addRule(ALIGN_PARENT_BOTTOM);
		thisParams.addRule(CENTER_HORIZONTAL);
		setLayoutParams(thisParams);
		setBackgroundResource(android.R.drawable.editbox_background);
		setPadding(0, 5, 0, 0);
	}

	public boolean onTouch(View arg0, MotionEvent arg1) {
		if(arg1.getAction() == MotionEvent.ACTION_MOVE){
			padding = (int) (getPaddingLeft() + arg1.getX() - BUTTON_WIDTH/2);
		}else if(arg1.getAction() == MotionEvent.ACTION_UP){
			if(padding > getWidth()-170){
				//Complete
				onFinish.run();
			}else{
				padding = 0;												
			}
		}
		
		if(padding >= 0 && padding < getWidth() - BUTTON_WIDTH){
			setPadding(padding, 5, 0, 0);				
		}
		
		invalidate();
		return true;
	}
}