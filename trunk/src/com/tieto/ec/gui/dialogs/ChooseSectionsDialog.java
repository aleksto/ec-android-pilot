package com.tieto.ec.gui.dialogs;

import java.util.HashMap;

import android.R;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.logic.InitiateWarningText;
import com.tieto.ec.model.SectionWarning;



public class ChooseSectionsDialog extends Dialog {

	public enum SectionBoxState{
		Checked
	}
	
	private DailyMorningReport dailyMorningReport;
	private String title = "Send Warnings";
	private HashMap<String, SectionBoxState> showSection;

	public ChooseSectionsDialog(final DailyMorningReport dailyMorningReport) {
		super(dailyMorningReport);
	
		showSection = new HashMap<String, SectionBoxState>();
		final StringBuilder body = new StringBuilder();
		
		
		//Init
		this.dailyMorningReport = dailyMorningReport;
		setTitle(title);
		
		//Standard linearLayout params
		LinearLayout.LayoutParams standardParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		//Main
		LinearLayout main = new LinearLayout(dailyMorningReport);
		main.setOrientation(LinearLayout.VERTICAL);
		
		//Content
		RelativeLayout content = new RelativeLayout(dailyMorningReport);
		
		//Scroll
		LinearLayout scrollLayout = new LinearLayout(dailyMorningReport);
		RelativeLayout.LayoutParams scrollLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final int SCROLL_BOTTOM_MARGIN = 80;
		scrollLayoutParams.bottomMargin = SCROLL_BOTTOM_MARGIN;
		ScrollView scroll = new ScrollView(dailyMorningReport);
		int scrollId = 1;
		scroll.setId(scrollId);

		//ButtonLayout
		LinearLayout buttonLayout = new LinearLayout(dailyMorningReport);
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonLayout.setBackgroundResource(R.drawable.bottom_bar);
		final int BUTTON_LAYOUT_HEIGHT = 75;
		RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_LAYOUT_HEIGHT);
		buttonLayoutParams.addRule(RelativeLayout.BELOW, scrollId);
		buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);	
		final int BUTTON_HEIGHT = 70;
		final int BUTTON_MARGIN = 5;
		
		//Ok button
		Button ok = new Button(dailyMorningReport);
		ok.setText("OK");
		ok.setBackgroundResource(android.R.drawable.btn_default);
		LinearLayout.LayoutParams okParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_HEIGHT);
		okParams.setMargins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
		okParams.weight = 1;
		ok.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean show = false;
				for (SectionWarning sectionWarning : dailyMorningReport.getWarnings()) {
					body.append("MESSAGE:");
					try{
						Log.d("tieto", "Show Section: " + sectionWarning.getSectionTitle() + ": " + showSection.get(sectionWarning.getSectionTitle()));
						if(showSection.get(sectionWarning.getSectionTitle()) == SectionBoxState.Checked){
							//Adding section title to each section
							body.append("\n \n \n" + sectionWarning.getSectionTitle().toUpperCase() + "\n \n");
							
							//Getting sectionWarning comments
							InitiateWarningText initiateWarningText = new InitiateWarningText(sectionWarning);
							body.append(initiateWarningText.getInfo());
							show = true;
						}	
					}catch(NullPointerException e){
						e.printStackTrace();
					}
				}
					
				
				if(show){
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("text/plain");
					i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"Energy.Components@tieto.com"});
					i.putExtra(Intent.EXTRA_SUBJECT, "Energy Components: Warning Raport");
					i.putExtra(Intent.EXTRA_TEXT, body.toString());
					try {
						dailyMorningReport.startActivity(Intent.createChooser(i, "Send mail..."));
					} catch (android.content.ActivityNotFoundException ex) {
					    Toast.makeText(dailyMorningReport, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
					}
					dismiss();
				}
				else{
					Toast toast = Toast.makeText(dailyMorningReport, "Please select atleast 1 section", Toast.LENGTH_LONG);
					toast.show();
				}
			}});
		
		//Cancel button
		Button cancel = new Button(dailyMorningReport);
		cancel.setText("Cancel");
		cancel.setBackgroundResource(android.R.drawable.btn_default);
		LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_HEIGHT);
		cancelParams.setMargins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
		cancelParams.weight = 1;
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}});
		
		//Table
		LinearLayout tableLayout = new LinearLayout(dailyMorningReport);
		RelativeLayout.LayoutParams tableLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		TableLayout table = new TableLayout(dailyMorningReport);
		
		//Childs
		setContentView(main, standardParams);
		main.addView(content, standardParams);
		content.addView(scrollLayout, scrollLayoutParams);
		content.addView(buttonLayout, buttonLayoutParams);
		scrollLayout.addView(scroll, standardParams);
		scroll.addView(tableLayout, tableLayoutParams);
		tableLayout.addView(table, standardParams);
		buttonLayout.addView(ok, okParams);
		buttonLayout.addView(cancel, cancelParams);
	

		for (SectionWarning sectionWarning : dailyMorningReport.getWarnings()) {
			table.addView(addOptionRow(sectionWarning.getSectionTitle()));		
		}
		
	}

	private RelativeLayout addOptionRow(final String sectionTitle) {	
		RelativeLayout main = new RelativeLayout(dailyMorningReport);
		
		//Init
		TextView optionsTextView = new TextView(dailyMorningReport);
		TextView optionsSubTextView = new TextView(dailyMorningReport);
		RelativeLayout.LayoutParams rowParameters = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 100);
		RelativeLayout.LayoutParams textViewParameters = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams subTextViewParameters = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams buttonSpaceParameters = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		//LayoutParameters Rules
		textViewParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		subTextViewParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		subTextViewParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		buttonSpaceParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		buttonSpaceParameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
		//Row
	    main.setLayoutParams(rowParameters);
	    main.addView(optionsTextView, textViewParameters);
	    main.addView(optionsSubTextView, subTextViewParameters);
	    main.setBackgroundResource(android.R.drawable.btn_default);
		
		//Text
		optionsTextView.setTextColor(Color.BLACK);
		optionsTextView.setTextSize(20);
		optionsTextView.setText(sectionTitle);
		optionsSubTextView.setTextSize(10);
		optionsSubTextView.setTextColor(Color.BLACK);
		
		//Init
		CheckBox checkBox = new CheckBox(dailyMorningReport);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d("tieto", "CHECKING");
				if(isChecked){
					Log.d("tieto", "CHECKING: TRUE");
					showSection.put(sectionTitle, SectionBoxState.Checked);
					Log.d("tieto", "Checked: " + sectionTitle + ": " + showSection.get(sectionTitle));
				}
//				else if(!isChecked)
//					Log.d("tieto", "CHECKING: FALSE");
//					showSection.put(sectionTitle, SectionBoxState.NotChecked);
			}
		});

		Log.d("tieto", "Checked(OUTSIDE): " + sectionTitle + ": " + showSection.get(sectionTitle));

		
		//Childs
		main.addView(checkBox, buttonSpaceParameters);
	
		return main;
	}


}
