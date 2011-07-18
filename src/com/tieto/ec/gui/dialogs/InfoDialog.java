package com.tieto.ec.gui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class InfoDialog{
	
	public static Dialog showInfoDialog(Context context, String msg){
		//Init
		final Dialog dialog = new Dialog(context);
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		TextView text = new TextView(context);
		Button ok = new Button(context);
		
		//Dialog
		dialog.setTitle("Info");
		dialog.setContentView(scroll);
		dialog.show();
		
		//Childs
		scroll.addView(table);
		table.addView(text);
		table.addView(ok);
		
		//Text
		text.setTextSize(20);
		text.setText(msg);
		ok.setText("Ok");
		
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.hide();
			}
		});
		
		return dialog;
	}
}
