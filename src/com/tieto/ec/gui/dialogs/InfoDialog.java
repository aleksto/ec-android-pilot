package com.tieto.ec.gui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoDialog{
	
	private final static int TEXT_SIZE = 20;
	private final static String TITLE = "Info";
	private final static String OK_TEXT = "Ok";
	
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
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		TextView text = new TextView(context);
		Button ok = new Button(context);
		
		//Dialog
		dialog.setTitle(TITLE);
		dialog.setContentView(table);
		dialog.show();
		
		//Childs
		table.addView(ok);
		table.addView(scroll);
		scroll.addView(text);
		
		//Text
		text.setTextSize(TEXT_SIZE);
		text.setText(message);
		ok.setText(OK_TEXT);
		
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.hide();
			}
		});
		
		return dialog;
	}
}
