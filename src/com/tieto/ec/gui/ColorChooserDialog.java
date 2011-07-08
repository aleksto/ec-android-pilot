package com.tieto.ec.gui;

import com.tieto.ec.listeners.dmrOption.colorDialog.RGBChangeListener;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

public class ColorChooserDialog extends Dialog{

	private TableLayout table;
	private SeekBar red;
	private SeekBar green;
	private SeekBar blue;
	
	public ColorChooserDialog(Context context) {
		//Super
		super(context);
		
		//Init
		table = new TableLayout(context);
		red = new SeekBar(context);
		green = new SeekBar(context);
		blue = new SeekBar(context);
		TextView redLabel = new TextView(context);
		TextView greenLabel = new TextView(context);
		TextView blueLabel = new TextView(context);
		Button ok = new Button(context);
		Button cancel = new Button(context);
		
		//This
		setContentView(table);
		setTitle("Choose Color");
		
		//Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		redLabel.setText("Red");
		greenLabel.setText("Green");
		blueLabel.setText("Blue");
		
		//Seekbars
		red.setMax(255);
		green.setMax(255);
		blue.setMax(255);
		
		//Listeners
		red.setOnSeekBarChangeListener(new RGBChangeListener(this));
		green.setOnSeekBarChangeListener(new RGBChangeListener(this));
		blue.setOnSeekBarChangeListener(new RGBChangeListener(this));
		
		//Table
		table.addView(redLabel);
		table.addView(red);
		table.addView(greenLabel);
		table.addView(green);
		table.addView(blueLabel);
		table.addView(blue);
		table.addView(ok);
		table.addView(cancel);
	}

	public void updateColor(){
		table.setBackgroundColor(Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress()));
	}
}
