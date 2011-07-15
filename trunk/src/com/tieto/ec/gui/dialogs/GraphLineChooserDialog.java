package com.tieto.ec.gui.dialogs;

import java.util.ArrayList;

import com.androidplot.xy.SimpleXYSeries;
import com.tieto.ec.gui.graphs.Graph;
import com.tieto.ec.listeners.dmr.graphLineChooserDialog.GraphCheckBoxListener;
import com.tieto.ec.listeners.dmr.graphLineChooserDialog.OkListener;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class GraphLineChooserDialog extends Dialog{

	private ScrollView scroll;
	private TableLayout table;
	
	public GraphLineChooserDialog(Context context, Graph graph) {
		//Super
		super(context);
		
		//Init
		scroll = new ScrollView(context);
		table = new TableLayout(context);
		
		//table
		table.setLayoutParams(new android.widget.TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		//This
		setContentView(scroll);
		setTitle("Show/Hide lines");
		
		//Childs
		scroll.addView(table);
		
		//CheckBoxes
		ArrayList<SimpleXYSeries> lines = graph.getLines();
		for (SimpleXYSeries simpleXYSeries : lines) {
			//Init
			RelativeLayout row = new RelativeLayout(context);
			TextView name = new TextView(context);
			CheckBox box = new CheckBox(context);
						
			//Text
			String title = simpleXYSeries.getTitle();
			name.setText(title);
			
			//Row
			row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			row.addView(name);
			row.addView(box);
			
			//Gravity
			name.setGravity(Gravity.LEFT);
			box.setGravity(Gravity.RIGHT);
			
			//Table
			table.addView(row);
			
			//Listener
			box.setOnCheckedChangeListener(new GraphCheckBoxListener(context, title, graph));
			
			box.setChecked(graph.isShowing(title));
		}
		
		//Button
		Button ok = new Button(context);
		ok.setText("Ok");
		ok.setOnClickListener(new OkListener(this));
		
		table.addView(ok);
	}
}
