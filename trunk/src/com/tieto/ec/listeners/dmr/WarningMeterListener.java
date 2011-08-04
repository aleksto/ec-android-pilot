package com.tieto.ec.listeners.dmr;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.Section;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dmr.WarningMeter;
import com.tieto.ec.gui.graphs.BarGraph;
import com.tieto.ec.logic.IndexFormat;
import com.tieto.ec.model.GraphWarning;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.TableWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class WarningMeterListener implements OnClickListener{

	private StringBuilder info;
	private Dialog dialog;
	private ScrollView scroll;
	private TableLayout table;
	private TextView text;
	private BarGraph graph;

	/**
	 * Creates a {@link OnClickListener} for a {@link WarningMeter}
	 * @param dmr {@link DailyMorningReport} getting {@link Context} from here used for Android framework actions
	 * @param sectionWarning A {@link SectionWarning} containing all the {@link Warning}s for the {@link Section}
	 */
	public WarningMeterListener(DailyMorningReport dmr, SectionWarning sectionWarning){
		info = new StringBuilder();
		dialog = new Dialog(dmr);
		graph = new BarGraph(dmr, "");
		scroll = new ScrollView(dmr);
		table = new TableLayout(dmr);
		text = new TextView(dmr);

		initText(sectionWarning);

		//Dialog
		dialog.setContentView(table);
		dialog.setTitle("Levels");

		//Table
		table.addView(graph);
		table.addView(scroll);

		//Scroll
		scroll.addView(text);

		//Text
		text.setText(info.toString());

		//Graph
		int width = dmr.getWindowManager().getDefaultDisplay().getWidth();
		graph.setLayoutParams(new TableLayout.LayoutParams(width-20, 200));
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
	 * Initializes a {@link StringBuilder} and builds it up whit comments from the {@link SectionWarning} 
	 * @param sectionWarning
	 */
	private void initText(SectionWarning sectionWarning) {
		if(sectionWarning.getWarnings().get(0) instanceof GraphWarning){
			initGraphText(sectionWarning);
		}else if(sectionWarning.getWarnings().get(0) instanceof TableWarning){
			initTableText(sectionWarning);
		}

	}

	private void initGraphText(SectionWarning sectionWarning) {
		int index = 1;
		boolean first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			GraphWarning graphWarning = (GraphWarning) warning;
			if(warning.getType() == Type.CRITICAL && first){
				info.append("Critical:\n");
				info.append(index + ". " + graphWarning.getAttribute() + ":\n" + graphWarning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.CRITICAL){
				info.append(index + ". " + graphWarning.getAttribute() + ":\n" + graphWarning.getComment() + "\n\n");
				index++;
			}
		}


		first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			GraphWarning graphWarning = (GraphWarning) warning;
			if(warning.getType() == Type.WARNING && first){
				info.append("\nWarning:\n");
				info.append(index + ". " + graphWarning.getAttribute() + ":\n" + graphWarning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.WARNING){
				info.append(index + ". " + graphWarning.getAttribute() + ":\n" + graphWarning.getComment() + "\n\n");
				index++;
			}
		}
	}
	
	private void initTableText(SectionWarning sectionWarning) {
		int index = 1;
		boolean first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			TableWarning tableWarning = (TableWarning) warning;
			if(warning.getType() == Type.CRITICAL && first){
				info.append("Critical:\n");
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getCollumn() + "\n" + warning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.CRITICAL){
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getCollumn() + "\n" + warning.getComment() + "\n\n");
				index++;
			}
		}


		first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			TableWarning tableWarning = (TableWarning) warning;
			if(warning.getType() == Type.WARNING && first){
				info.append("\nWarning:\n");
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getCollumn() + "\n" + warning.getComment() + "\n\n");
				first = false;
				index++;
			}else if(warning.getType() == Type.WARNING){
				info.append(index + ". " + tableWarning.getRow() + ", " + tableWarning.getCollumn() + "\n" + warning.getComment() + "\n\n");
				index++;
			}
		}
	}

	/**
	 * Runs when user clicks the {@link View} with this {@link OnClickListener} attached
	 */
	public void onClick(View v) {
		dialog.show();
	}
}
