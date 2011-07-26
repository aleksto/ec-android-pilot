package com.tieto.ec.gui.dialogs;

import java.io.IOException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.listeners.dialogs.DatePickerListener;
import com.tieto.ec.listeners.dialogs.DialogActionListener;
import com.tieto.ec.logic.FileManager;

public class OptionRow extends RelativeLayout{

	public OptionRow(OptionDialog optionDialog, String optionsTitle, OptionRowType optionRowType){
		super(optionDialog.getContext());
		
		boolean goBack;
		Dialog nextState = null;
		
		//Init
//		RelativeLayout optionRow = new RelativeLayout(optionDialog.getContext());
		TextView optionsTextView = new TextView(optionDialog.getContext());
		TextView optionsSubTextView = new TextView(optionDialog.getContext());
		LayoutParams rowParameters = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, 100);
		LayoutParams textViewParameters = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		LayoutParams subTextViewParameters = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		LayoutParams buttonSpaceParameters = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		//LayoutParameters Rules
		textViewParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		textViewParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		subTextViewParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		subTextViewParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		buttonSpaceParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		buttonSpaceParameters.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		
		//Row
	    setLayoutParams(rowParameters);
		addView(optionsTextView, textViewParameters);
	    addView(optionsSubTextView, subTextViewParameters);
		setBackgroundResource(android.R.drawable.btn_default);
		
		//Text
		optionsTextView.setTextColor(Color.BLACK);
		optionsTextView.setTextSize(20);
		optionsTextView.setText(optionsTitle);
		optionsSubTextView.setTextSize(10);
		optionsSubTextView.setTextColor(Color.BLACK);
		
		switch (optionRowType) {
		case EDIT_BUTTON:
			//Init
//			Button editButton = new Button(context);
			
			//Text
//			editButton.setText("Edit");
			try {
				optionsSubTextView.setText(FileManager.readPath(optionDialog.getContext(), optionDialog.getPath() + "." + optionsTitle));
				Log.d("tieto", "Read path: " + optionDialog.getPath() + "." + optionsTitle + " for subtext. \tString read: " + optionsSubTextView.getText());
			} catch (IOException e) {
				optionsSubTextView.setText("");
				e.printStackTrace();
			}
			
			nextState = new EditTextDialog(optionDialog.getContext(), optionDialog, optionDialog.getPath(), optionsTitle.toString());
			
			//Listener
			goBack = false;
			setOnClickListener(new DialogActionListener(optionDialog, goBack, optionsTitle, nextState));
			
			//Child
//			optionRow.addView(editButton, buttonSpaceParameters);
			break;
		case DATE_BUTTON:
			//Init
//			Button dateButton = new Button(context);
			
			//Text
//			dateButton.setText("Edit");
			try {
				optionsSubTextView.setText(FileManager.readPath(optionDialog.getContext(), optionDialog.getPath() + "." + optionsTitle));
				Log.d("tieto", "Read path: " + optionDialog.getPath() + "." + optionsTitle + " for subtext. \tString read: " + optionsSubTextView.getText());
			} catch (IOException e) {
				optionsSubTextView.setText("");
				e.printStackTrace();
			}
			

			Calendar c = Calendar.getInstance();
			nextState = new DatePickerDialog(optionDialog.getContext(), new DatePickerListener(optionDialog, optionDialog.getPath() + "." + optionsTitle), c.get(Calendar.YEAR),
					c.get(Calendar.MONTH),
					c.get(Calendar.DATE));
			
			//Listener
			goBack = false;
			setOnClickListener(new DialogActionListener(optionDialog, goBack, optionsTitle, nextState));
			
			//Child
//			optionRow.addView(dateButton, buttonSpaceParameters);
			break;
		case CHOOSE_BUTTON:
			//Init
//			Button chooseButton = new Button(context);
			
			//Text
//			chooseButton.setText("Choose");
			
			//Listener
			goBack = true;
			setOnClickListener(new DialogActionListener(optionDialog, goBack, optionsTitle, nextState));
			
			//Child
//			optionRow.addView(chooseButton, buttonSpaceParameters);
			break;
		case CHECK_BOX:
			//Init
			CheckBox checkBox = new CheckBox(optionDialog.getContext());
			
			//Listener
			goBack = false;
			checkBox.setOnCheckedChangeListener(new DialogActionListener(optionDialog, goBack, optionsTitle, nextState));
			
			//Checking state
			try {
				checkBox.setChecked(Boolean.valueOf(FileManager.readPath(optionDialog.getContext(), optionDialog.getPath() + "." + optionsTitle)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Childs
			addView(checkBox, buttonSpaceParameters);
			break;
		case NONE:
			nextState = optionDialog.getChild(optionsTitle.toString());
			goBack = false;
			optionsTextView.setOnClickListener(new DialogActionListener(optionDialog, goBack, optionsTitle, nextState));
			setOnClickListener(new DialogActionListener(optionDialog, goBack, optionsTitle, nextState));
			break;
		}
		
	}
	
}
