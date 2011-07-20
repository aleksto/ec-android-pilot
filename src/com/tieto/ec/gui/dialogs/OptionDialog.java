package com.tieto.ec.gui.dialogs;

import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.listeners.dialogs.CheckBoxChangeListener;
import com.tieto.ec.listeners.dialogs.ChooseButtonListener;
import com.tieto.ec.listeners.dialogs.DrillDownListener;
import com.tieto.ec.listeners.dialogs.EditButtonListener;
import com.tieto.ec.logic.DialogSection;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class OptionDialog extends Dialog {

	private TableLayout table;
	private Context context;
	private String basePath;
	private TreeMap<String, OptionRowType> options;
	private DialogSection section;

	public OptionDialog(Context context, DialogSection section){
		//Super
		super(context);
		
		//Init
		this.context = context;
		this.section = section;
		basePath = section.getBasePath();
		options = section.getOptions();
		table = new TableLayout(context);
		ScrollView scroll = new ScrollView(context);
		
		//This
		setContentView(scroll);
		setTitle(section.getTitle());
		
		//Scroll
		scroll.addView(table);
		
		populateOptionDialog();
	}
	
	@Override
	public void onBackPressed() {
		Log.d("tieto", "Option Dialog on back pressed");
		dismiss();
		super.onBackPressed();
	}

	private void populateOptionDialog() {
		Set<String> optionTexts = options.keySet();
		
		for (String optionText : optionTexts) {
			//Init
			RelativeLayout optionRow = new RelativeLayout(context);
			TextView optionsTextView = new TextView(context);
			TextView optionsSubTextView = new TextView(context);
			LayoutParams rowParameters = new LayoutParams(LayoutParams.FILL_PARENT, 100);
			LayoutParams textViewParameters = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			LayoutParams subTextViewParameters = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			LayoutParams buttonSpaceParameters = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

			//LayoutParameters Rules
			textViewParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			textViewParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			subTextViewParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			subTextViewParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			buttonSpaceParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			buttonSpaceParameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			
			//Row
			optionRow.setLayoutParams(rowParameters);
			optionRow.addView(optionsTextView, textViewParameters);
			optionRow.addView(optionsSubTextView, subTextViewParameters);
			
			//Text
			optionsTextView.setTextSize(20);
			optionsSubTextView.setTextSize(10);
			optionsTextView.setText(optionText);
			
			switch (options.get(optionText)) {
			case EDIT_BUTTON:
				//Init
				Button editButton = new Button(context);
				
				//Text
				editButton.setText("Edit");
				try {
					optionsSubTextView.setText(FileManager.readPath(context, basePath + "." + optionText));
					Log.d("tieto", "Read path: " + basePath + "." + optionText + " for subtext. \tString read: " + optionsSubTextView.getText());
				} catch (IOException e) {
					optionsSubTextView.setText("");
					e.printStackTrace();
				}
				
				//Listener
				editButton.setOnClickListener(new EditButtonListener(this, context, basePath, optionText));
				
				//Child
				optionRow.addView(editButton, buttonSpaceParameters);
				break;
			case CHOOSE_BUTTON:
				//Init
				Button chooseButton = new Button(context);
				
				//Text
				chooseButton.setText("Choose");
				
				//Listener
				chooseButton.setOnClickListener(new ChooseButtonListener(this, context, basePath, optionText));
				
				//Child
				optionRow.addView(chooseButton, buttonSpaceParameters);
				break;
			case CHECK_BOX:
				//Init
				CheckBox checkBox = new CheckBox(context);
				
				//Listener
				checkBox.setOnCheckedChangeListener(new CheckBoxChangeListener(context, basePath + "." + optionText));
				
				//Checking state
				try {
					checkBox.setChecked(Boolean.valueOf(FileManager.readPath(context, basePath + "." + optionText)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Childs
				optionRow.addView(checkBox, buttonSpaceParameters);
				break;
			case NONE:
				optionsTextView.setOnClickListener(new DrillDownListener(context, section.getChild(optionText)));
				break;
			}
			
			table.addView(optionRow);
		}
	}

	public void refresh() {
		table.removeAllViews();
		populateOptionDialog();
	}
}