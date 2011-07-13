package com.tieto.ec.listeners.dmrOption.colorDialog;

import com.tieto.ec.gui.dialogs.ColorChooserDialog;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;

public class OkListener implements OnClickListener {

	private Dialog dialog;
	private SeekBar red, green, blue;
	private String object;
	private Context context;
	
	public OkListener(Dialog dialog, Context context, SeekBar red, SeekBar green, SeekBar blue, String object) {
		this.dialog = dialog;
		this.context = context;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.object = object;
	}

	public void onClick(View v) {
		if(object.equalsIgnoreCase(ColorChooserDialog.BACKGROUND)){
			FileManager.writePath(context, "com.tieto.ec.options.backgroundColor", Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress())+"");
		}
		else if(object.equalsIgnoreCase(ColorChooserDialog.TEXT)){
			FileManager.writePath(context, "com.tieto.ec.options.textColor", Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress())+"");
		}
		else if(object.equalsIgnoreCase(ColorChooserDialog.CELL_TEXT)){
			FileManager.writePath(context, "com.tieto.ec.options.cellTextColor", Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress())+"");
		}
		else if(object.equalsIgnoreCase(ColorChooserDialog.CELL_BACKGROUND)){
			FileManager.writePath(context, "com.tieto.ec.options.cellBackgroundColor", Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress())+"");
		}
		else if(object.equalsIgnoreCase(ColorChooserDialog.CELL_BORDER)){
			FileManager.writePath(context, "com.tieto.ec.options.cellBorderColor", Color.rgb(red.getProgress(), green.getProgress(), blue.getProgress())+"");
		}
		dialog.hide();
	}
}
