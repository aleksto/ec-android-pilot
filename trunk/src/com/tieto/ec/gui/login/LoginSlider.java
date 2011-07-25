package com.tieto.ec.gui.login;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class LoginSlider extends RelativeLayout implements OnTouchListener{

	private ImageButton sliderButton;
	private int padding;

	public LoginSlider(Context context) {
		//Super
		super(context);

		//Init
		sliderButton = new ImageButton(context);

		//SliderButton
		LayoutParams sliderParams = new LayoutParams(160, 80);
		sliderParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		sliderButton.setImageResource(android.R.drawable.ic_lock_lock);
		//		sliderButton.setText("Drag Me");
		sliderButton.setOnTouchListener(this);
		addView(sliderButton, sliderParams);

		//This
		LayoutParams thisParams = new LayoutParams(LayoutParams.FILL_PARENT, 80);
		thisParams.addRule(ALIGN_PARENT_BOTTOM);
		thisParams.addRule(CENTER_HORIZONTAL);
		setLayoutParams(thisParams);
		setBackgroundResource(android.R.drawable.editbox_background);
		setOnTouchListener(this);
		setPadding(0, 5, 0, 0);
	}

	public boolean onTouch(View arg0, MotionEvent arg1) {
		if(arg1.getAction() == MotionEvent.ACTION_MOVE){
			if(arg0 == sliderButton){
				padding = (int) (getPaddingLeft() + arg1.getX() - 80);
			}else{
				padding = (int) (arg1.getX()-80);
			}
			
			if(padding > 0 && padding < getWidth() - 160){
				setPadding(padding, 5, 0, 0);				
			}
		}else{
			padding = 0;
			setPadding(padding, 5, 0, 0);				
		}
		invalidate();
		return true;
	}
}