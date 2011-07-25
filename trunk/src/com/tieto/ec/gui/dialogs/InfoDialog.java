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
	
	private final static int TEXT_SIZE = 20;
	private final static String TITLE = "Info";
	private final static String OK_TEXT = "Ok";
	
	public static Dialog showInfoDialog(Context context, String msg){
		//Init
		final Dialog dialog = new Dialog(context);
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		TextView text = new TextView(context);
		Button ok = new Button(context);
		
		//Dialog
		dialog.setTitle(TITLE);
		dialog.setContentView(scroll);
		dialog.show();
		
		//Childs
		scroll.addView(table);
		table.addView(text);
		table.addView(ok);
		
		//Text
		text.setTextSize(TEXT_SIZE);
		text.setText(msg);
		ok.setText(OK_TEXT);
		
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.hide();
			}
		});
		
		return dialog;
	}
}
