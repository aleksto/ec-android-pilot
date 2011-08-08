package com.tieto.ec.listeners.dmr;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.Section;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dmr.WarningMeter;
import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.listeners.dialogs.HideDialogListener;
import com.tieto.ec.logic.IndexFormat;
import com.tieto.ec.logic.InitiateWarningText;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class WarningMeterListener implements OnClickListener{

	private StringBuilder info;
	private Dialog dialog;
	private ScrollView scroll;
	private RelativeLayout main;
	private TextView text;
	private BarGraph graph;
	private Button ok;

	/**
	 * Creates a {@link OnClickListener} for a {@link WarningMeter}
	 * @param dmr {@link DailyMorningReport} getting {@link Context} from here used for Android framework actions
	 * @param sectionWarning A {@link SectionWarning} containing all the {@link Warning}s for the {@link Section}
	 */
	public WarningMeterListener(Activity activity, SectionWarning sectionWarning){
		info = new StringBuilder();
		dialog = new Dialog(activity);
		graph = new BarGraph(activity, "");
		scroll = new ScrollView(activity);
		main = new RelativeLayout(activity);
		text = new TextView(activity);
		ok = new Button(activity);

		InitiateWarningText initiateWarningText = new InitiateWarningText(sectionWarning);
		info = initiateWarningText.getInfo();
			
		//Dialog
		dialog.setContentView(main);
		dialog.setTitle("Levels");
		
		//ID
		graph.setId(1);
		ok.setId(2);

		//Layoutparams
		RelativeLayout.LayoutParams graphParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 200);
		RelativeLayout.LayoutParams scrollParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		graphParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		scrollParams.addRule(RelativeLayout.BELOW, graph.getId());
		scrollParams.addRule(RelativeLayout.ABOVE, ok.getId());
		
		//Childs
		main.addView(graph, graphParams);
		main.addView(scroll, scrollParams);
		main.addView(ok, buttonParams);

		//Button
		ok.setText("Ok");
		ok.setOnClickListener(new HideDialogListener(dialog));
		
		//Scroll
		scroll.addView(text);

		//Text
		text.setText(info.toString());

		//Graph
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		graph.setLayoutParams(new RelativeLayout.LayoutParams(width-20, 200));
		graph.addBars("Target", Color.RED, generateTargetValues(sectionWarning));
		graph.addBars("Actual", Color.GREEN, generateActualValues(sectionWarning));
		graph.setDomainStepValue(sectionWarning.getWarnings().size());
		graph.setDomainValueFormat(new IndexFormat(sectionWarning.getWarnings().size()));
	}

	/**
	 * Creates a Number[] of the actual values from the {@link SectionWarning}
	 * @param sectionWarning The {@link SectionWarning} to generate the numbers from
	 * @return A new Number[] of the actual values
	 */
	private Number[] generateActualValues(SectionWarning sectionWarning) {
		ArrayList<Number> values = new ArrayList<Number>();
		for (Warning warning : sectionWarning.getWarnings()) {
			if(warning.getType() == Type.CRITICAL){
				values.add(warning.getActualValue());				
			}
		}
		for (Warning warning : sectionWarning.getWarnings()) {
			if(warning.getType() == Type.WARNING){
				values.add(warning.getActualValue());				
			}
		}
		Number[] array = new Number[values.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = values.get(i);
		}
		return array;
	}

	/**
	 * Creates a Number[] of the target values from the {@link SectionWarning}
	 * @param sectionWarning The {@link SectionWarning} to generate the numbers from
	 * @return A new Number[] of the target values
	 */
	private Number[] generateTargetValues(SectionWarning sectionWarning) {
		ArrayList<Number> values = new ArrayList<Number>();
		for (Warning warning : sectionWarning.getWarnings()) {
			if(warning.getType() == Type.CRITICAL){
				values.add(warning.getTargetValue());				
			}
		}
		for (Warning warning : sectionWarning.getWarnings()) {
			if(warning.getType() == Type.WARNING){
				values.add(warning.getTargetValue());				
			}
		}
		Number[] array = new Number[values.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = values.get(i);
		}
		return array;
	}

	

	/**
	 * Runs when user clicks the {@link View} with this {@link OnClickListener} attached
	 */
	public void onClick(View v) {
		dialog.show();
	}
}
