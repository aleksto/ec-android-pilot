package com.tieto.ec.listeners.dmr.graphLineChooserDialog;

import com.tieto.ec.gui.graphs.Graph;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class GraphCheckBoxListener implements OnCheckedChangeListener {

	private Context context;
	private Graph graph;
	private String title;

	public GraphCheckBoxListener(Context context, String title, Graph graph) {
		this.context = context;
		this.graph = graph;
		this.title = title;
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			graph.show(title);
			graph.invalidate();
		}else{
			if(graph.getSeriesSet().size() > 1){
				graph.hide(title);
				graph.invalidate();				
			}else{
				CheckBox box = (CheckBox) buttonView;
				box.setChecked(true);
				Toast.makeText(context, "One line must always be visible", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
