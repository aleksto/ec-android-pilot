package com.tieto.ec.gui.dialogs;

import java.io.IOException;

import com.tieto.ec.listeners.dmrOption.colorDialog.CancelListener;
import com.tieto.ec.listeners.dmrOption.colorDialog.OkListener;
import com.tieto.ec.listeners.dmrOption.colorDialog.RGBChangeListener;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;

public class ColorChooserDialog extends Dialog{

	public final static String BACKGROUND = "background";
	public final static String TEXT = "text";
	public final static String CELL_TEXT = "cellText";
	public final static String CELL_BACKGROUND = "cellBackground";
	public static final String CELL_BORDER = "cellBorder";
	
	private Context context;
	private TableLayout table;
	private SeekBar red;
	private SeekBar green;
	private SeekBar blue;
	
	public ColorChooserDialog(Context context, String object) {
		//Super
		super(context);
		
		//Init
		this.context = context;
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
		ok.setOnClickListener(new OkListener(this, context, red, green, blue, object));
		cancel.setOnClickListener(new CancelListener(this));
		
		
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
	
	public void updateBars(String path){
		try {
			int color = Integer.valueOf(FileManager.readPath(context, path));
			red.setProgress(Color.red(color));
			green.setProgress(Color.green(color));
			blue.setProgress(Color.blue(color));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateColor(){
		table.setBackgroundColor(Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress()));
	}
}
