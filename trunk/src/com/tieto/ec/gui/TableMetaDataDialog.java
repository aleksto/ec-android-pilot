package com.tieto.ec.gui;

import java.util.List;

import com.tieto.frmw.model.TableColumn;
import com.tieto.frmw.model.TableData;

import android.app.Dialog;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableMetaDataDialog extends Dialog{

	public TableMetaDataDialog(Context context, String title, TableData data) {
		//Super
		super(context);
		
		//Init
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		List<TableColumn> tableColumns = data.getTableColumns();
		
		//This
		setContentView(scroll);
		setTitle("Metadata: " + title);
		
		//Scroll
		scroll.addView(table);
		
		for (TableColumn tableColumn : tableColumns) {
			//Init
			TableRow row = new TableRow(context);
			TextView text = new TextView(context);
			CheckBox box = new CheckBox(context);
			
			//Text
			text.setText(tableColumn.getHeader());
			
			//Childs
			row.addView(text);
			row.addView(box);
			table.addView(row);
		}
	}
}
