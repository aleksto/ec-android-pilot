package com.tieto.ec.gui.dialogs;

import java.util.ArrayList;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.listeners.dialogs.HideDialogListener;
import com.tieto.ec.logic.IndexFormat;
import com.tieto.ec.logic.InitiateWarningText;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class SectionWarningsDialog extends Dialog{

	private StringBuilder info;
	private LinearLayout main;
	private BarGraph graph;
	private Button ok;
	
	/**
	 * Creates a {@link Dialog} displaying a {@link BarGraph} with each warnings and a list off comments
	 * @param activity {@link Activity} needed for Android framework actions
	 * @param sectionWarning The {@link SectionWarning} for this dialog
	 */
	public SectionWarningsDialog(Activity activity, SectionWarning sectionWarning) {
		super(activity);
		
		main = new LinearLayout(activity);
		info = new StringBuilder();
		graph = new BarGraph(activity, "");
		ok = new Button(activity);

		InitiateWarningText initiateWarningText = new InitiateWarningText(sectionWarning);
		info = initiateWarningText.getInfo();
			
		//Dialog
		setContentView(main);
		setTitle("Levels and Warnings");
		
		//ID
		graph.setId(1);
		ok.setId(2);

		//Standard linearLayout params
		LinearLayout.LayoutParams standardParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		//Content
		RelativeLayout content = new RelativeLayout(activity);

		//Graph (Layout)
		RelativeLayout graphLayout = new RelativeLayout(activity);
		RelativeLayout.LayoutParams graphLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		graphLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		final int GRAPH_HEIGHT = 200;
		int graphWidth = activity.getWindowManager().getDefaultDisplay().getWidth()/3;
		RelativeLayout.LayoutParams graphParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, graphWidth);
		
		//Scroll
		LinearLayout scrollLayout = new LinearLayout(activity);
		RelativeLayout.LayoutParams scrollLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final int SCROLL_BOTTOM_MARGIN = 80;
		scrollLayoutParams.bottomMargin = SCROLL_BOTTOM_MARGIN;
		scrollLayoutParams.topMargin = GRAPH_HEIGHT;
		ScrollView scroll = new ScrollView(activity);
		int scrollId = 2;
		scroll.setId(scrollId);

		//Exit button
		LinearLayout okLayout = new LinearLayout(activity);
		okLayout.setBackgroundResource(R.drawable.bottom_bar);
		final int BUTTON_LAYOUT_HEIGHT = 75;
		RelativeLayout.LayoutParams okLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_LAYOUT_HEIGHT);
		okLayoutParams.addRule(RelativeLayout.BELOW, scrollId);
		okLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);	
		Button okButton = new Button(activity);
		okButton.setText("Ok");
		okButton.setBackgroundResource(android.R.drawable.btn_default);
		final int BUTTON_HEIGHT = 70;
		LinearLayout.LayoutParams okParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_HEIGHT);
		final int BUTTON_MARGIN = 5;
		okParams.setMargins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
		okButton.setOnClickListener(new HideDialogListener(this));

		//Text
		LinearLayout textLayout = new LinearLayout(activity);
		RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		TextView text = new TextView(activity);
		text.setText(info.toString());
		
		//Childs
//		setContentView(main, standardParams);
		main.addView(content, standardParams);
		content.addView(scrollLayout, scrollLayoutParams);
		content.addView(graphLayout, graphLayoutParams);
		content.addView(okLayout, okLayoutParams);
		graphLayout.addView(graph, graphParams);
		scrollLayout.addView(scroll, standardParams);		
		scroll.addView(textLayout, textLayoutParams);
		textLayout.addView(text, standardParams);
		okLayout.addView(okButton, okParams);
	
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
}