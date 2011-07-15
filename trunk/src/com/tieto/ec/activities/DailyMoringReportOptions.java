package com.tieto.ec.activities;

import java.io.IOException;

import com.tieto.ec.gui.dialogs.ColorChooserDialog;
import com.tieto.ec.logic.FileManager;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DailyMoringReportOptions extends ListActivity{

	private ColorChooserDialog background, text, cellText, cellBackground, cellBorder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);

		//Init
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		background = new ColorChooserDialog(this, ColorChooserDialog.BACKGROUND);
		text = new ColorChooserDialog(this, ColorChooserDialog.TEXT);
		cellText = new ColorChooserDialog(this, ColorChooserDialog.CELL_TEXT);
		cellBackground = new ColorChooserDialog(this, ColorChooserDialog.CELL_BACKGROUND);
		cellBorder = new ColorChooserDialog(this, ColorChooserDialog.CELL_BORDER);

		//ArrayAdapter
		adapter.add("Clear username and password");
		adapter.add("Remember username and password");
		adapter.add("Background Color");
		adapter.add("Text Color");
		adapter.add("Cell text Color");
		adapter.add("Cell background Color");
		adapter.add("Cell border Color");

		//This
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView textView = (TextView) v;
		String action = (String) textView.getText();
		if(action.equalsIgnoreCase("Clear username and password")){
			clearUsernameAndPassword();
		}
		else if(action.equalsIgnoreCase("Remember username and password")){
			rememberUsernameAndPassword();
		}
		else if(action.equalsIgnoreCase("Background Color")){
			background.updateBars("com.tieto.ec.options.backgroundColor");
			background.show();
		}
		else if(action.equalsIgnoreCase("Text Color")){
			text.updateBars("com.tieto.ec.options.textColor");
			text.show();
		}
		else if(action.equalsIgnoreCase("Cell text Color")){
			cellText.updateBars("com.tieto.ec.options.cellTextColor");
			cellText.show();
		}
		else if(action.equalsIgnoreCase("Cell background Color")){
			cellBackground.updateBars("com.tieto.ec.options.cellBackgroundColor");
			cellBackground.show();
		}
		else if(action.equalsIgnoreCase("Cell border Color")){
			cellBorder.updateBars("com.tieto.ec.options.cellBorderColor");
			cellBorder.show();
		}
	}
	
	private void rememberUsernameAndPassword() {
		try {
			String val = FileManager.readPath(this, "com.tieto.ec.options.rememberUsernameAndPassword");
			
			if(val.equalsIgnoreCase("true")){
				FileManager.writePath(this, "com.tieto.ec.options.rememberUsernameAndPassword", "false");
				Toast.makeText(this, "Not remembering username and password", Toast.LENGTH_SHORT).show();
			}else{
				FileManager.writePath(this, "com.tieto.ec.options.rememberUsernameAndPassword", "true");
				Toast.makeText(this, "Remembering username and password", Toast.LENGTH_SHORT).show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearUsernameAndPassword(){
		FileManager.writePath(this, "com.tieto.ec.username", "");
		FileManager.writePath(this, "com.tieto.ec.password", "");
		Toast.makeText(this, "Username and password cleared", Toast.LENGTH_SHORT).show();
	}
}
