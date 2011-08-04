package com.tieto.ec.gui.dialogs;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoDialog{

	/**
	 * Simply a dialog where a text message can be shown to a user. 
	 * It is a substitute for Android {@link Toast}. 
	 * Context is needed for Androids framework actions.
	 * @param context
	 * @param message
	 * @return
	 */
	public static Dialog showInfoDialog(Context context, String message){
		//Init
		final Dialog dialog = new Dialog(context);

		//Dialog
		dialog.setTitle("Info");
		dialog.show();
		
		//Standard linearLayout params
		LinearLayout.LayoutParams standardParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		//Main
		LinearLayout main = new LinearLayout(context);
		main.setOrientation(LinearLayout.VERTICAL);
		
		//Content
		RelativeLayout content = new RelativeLayout(context);
		
		//Scroll
		LinearLayout scrollLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams scrollLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final int SCROLL_BOTTOM_MARGIN = 80;
		scrollLayoutParams.bottomMargin = SCROLL_BOTTOM_MARGIN;
		ScrollView scroll = new ScrollView(context);
		int scrollId = 1;
		scroll.setId(scrollId);

		//Exit button
		LinearLayout okLayout = new LinearLayout(context);
		okLayout.setBackgroundResource(R.drawable.bottom_bar);
		final int BUTTON_LAYOUT_HEIGHT = 75;
		RelativeLayout.LayoutParams okLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_LAYOUT_HEIGHT);
		okLayoutParams.addRule(RelativeLayout.BELOW, scrollId);
		okLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);	
		Button okButton = new Button(context);
		okButton.setText("Ok");
		okButton.setBackgroundResource(android.R.drawable.btn_default);
		final int BUTTON_HEIGHT = 70;
		LinearLayout.LayoutParams okParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_HEIGHT);
		final int BUTTON_MARGIN = 5;
		okParams.setMargins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.hide();
			}
		});
		
		//Text
		LinearLayout textLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams tableLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		TextView text = new TextView(context);
		text.setTextSize(20);
		text.setText(message);
		
		//Childs
		dialog.setContentView(main, standardParams);
		main.addView(content, standardParams);
		content.addView(scrollLayout, scrollLayoutParams);
		content.addView(okLayout, okLayoutParams);
		scrollLayout.addView(scroll, standardParams);
		scroll.addView(textLayout, tableLayoutParams);
		textLayout.addView(text, standardParams);
		okLayout.addView(okButton, okParams);
		
		
		
		return dialog;
	}
}
