package com.tieto.ec.gui;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class DataDialog extends Dialog {

	private TextView textView;
	
	public DataDialog(Context context) {
		super(context);
		textView = new TextView(context);
		setContentView(textView);
		setTitle("Data");
	}
	
	public void setData(String data){
		textView.setText(data);
	}

}
