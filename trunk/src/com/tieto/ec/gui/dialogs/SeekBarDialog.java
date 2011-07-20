package com.tieto.ec.gui.dialogs;

import com.tieto.ec.listeners.dialogs.CancelListener;
import com.tieto.ec.listeners.dialogs.SeekBarChangeListener;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

public class SeekBarDialog extends Dialog{

	public SeekBarDialog(Context context, int minVal, int maxVal, String dialogTitle, String path, String ... titles) {
		//Super
		super(context);
		
		//Init
		TableLayout table = new TableLayout(context);
		TableLayout seekBarTable = new TableLayout(context);
		ScrollView scroll = new ScrollView(context);
		Button ok = new Button(context);
		Button cancel = new Button(context);
		
		//This
		setContentView(table);
		setTitle(dialogTitle);
		
		//Childs
		table.addView(scroll);
		table.addView(ok);
		table.addView(cancel);
		
		//Scroll
		scroll.addView(seekBarTable);
		
		//Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		
		for (String title : titles) {
			//Init
			TextView text = new TextView(context);
			SeekBar seekBar = new SeekBar(context);
			
			//Text
			text.setText(title);
			
			//Seekbar Min / Max
			seekBar.setMax(maxVal - minVal);
			
			//Childs
			seekBarTable.addView(text);
			seekBarTable.addView(seekBar);
			
			//Listener
			seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(context, maxVal, minVal, text, path + "." + title));
		}
		
		//Buttons
		cancel.setOnClickListener(new CancelListener(this, context, path, titles));
//		ok.setOnClickListener(new OkListener(this));
	}	
}
