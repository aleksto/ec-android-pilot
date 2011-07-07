package com.tieto.ec.activities.listActivities;

import com.tieto.ec.gui.ColorChooserDialog;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DmrOptionActivity extends ListActivity{
	
	private ColorChooserDialog background, text, cell;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Init
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		background = new ColorChooserDialog(this);
		text = new ColorChooserDialog(this);
		cell = new ColorChooserDialog(this);
		
		//ArrayAdapter
		adapter.add("Background Color");
		adapter.add("Text Color");
		adapter.add("Cell Color");
		
		//This
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView textView = (TextView) v;
		String action = (String) textView.getText();

		if(action.equalsIgnoreCase("Background Color")){
			background.show();
		}else if(action.equalsIgnoreCase("Text Color")){
			text.show();
		}else if(action.equalsIgnoreCase("Cell Color")){
			cell.show();
		}
	}
}
