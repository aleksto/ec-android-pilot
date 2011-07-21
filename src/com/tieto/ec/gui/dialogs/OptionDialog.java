package com.tieto.ec.gui.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.listeners.dialogs.DatePickerSetListener;
import com.tieto.ec.listeners.dialogs.DialogActionListener;
import com.tieto.ec.logic.FileManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class OptionDialog extends Dialog {

	private String title, path;
	private TreeMap<String, OptionRowType> option;
	private List<OptionDialog> childs;
	private OptionDialog parent;
	
	private TableLayout table;
	private Context context;
	private String optionsTitle;
	private Dialog nextState;
	private boolean goBack;

	public OptionDialog(Context context, String title){
		//Super
		super(context);
				
		//Init
		this.title = title;
		this.context = context;
		path = title;
		table = new TableLayout(context);
		ScrollView scroll = new ScrollView(context);
		childs = new ArrayList<OptionDialog>();
		option = new TreeMap<String, OptionRowType>();
		parent = null;
		
		//This
		setContentView(scroll);
		setTitle(title);
		
		//Scroll
		scroll.addView(table);
		
	}
	
	@Override
	public void show() {
		populateOptionDialog();
		super.show();
	}
	
	@Override
	public void onBackPressed() {
		dismiss();
		super.onBackPressed();
	}

	private void populateOptionDialog() {
		table.removeAllViews();
		Set<String> optionTexts = option.keySet();
		
		for (String optionText : optionTexts) {
			//Init
			optionsTitle = optionText;
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
			optionsTextView.setText(optionsTitle);
			
			switch (option.get(optionsTitle)) {
			case EDIT_BUTTON:
				//Init
				Button editButton = new Button(context);
				
				//Text
				editButton.setText("Edit");
				try {
					optionsSubTextView.setText(FileManager.readPath(context, path + "." + optionsTitle));
					Log.d("tieto", "Read path: " + path + "." + optionsTitle + " for subtext. \tString read: " + optionsSubTextView.getText());
				} catch (IOException e) {
					optionsSubTextView.setText("");
					e.printStackTrace();
				}
				
				nextState = new EditTextDialog(context, this, path, optionsTitle);
				
				//Listener
				goBack = false;
				editButton.setOnClickListener(new DialogActionListener(this, goBack));
				
				//Child
				optionRow.addView(editButton, buttonSpaceParameters);
				break;
			case DATE_BUTTON:
				//Init
				Button dateButton = new Button(context);
				
				//Text
				dateButton.setText("Edit");
				try {
					optionsSubTextView.setText(FileManager.readPath(context, path + "." + optionsTitle));
					Log.d("tieto", "Read path: " + path + "." + optionsTitle + " for subtext. \tString read: " + optionsSubTextView.getText());
				} catch (IOException e) {
					optionsSubTextView.setText("");
					e.printStackTrace();
				}
				

				Calendar c = Calendar.getInstance();
				nextState = new DatePickerDialog(context, new DatePickerSetListener(this, path + "." + optionsTitle), c.get(Calendar.YEAR),
						c.get(Calendar.MONTH),
						c.get(Calendar.DATE));
				
				//Listener
				goBack = false;
				dateButton.setOnClickListener(new DialogActionListener(this, goBack));
				
				//Child
				optionRow.addView(dateButton, buttonSpaceParameters);
				break;
			case CHOOSE_BUTTON:
				//Init
				Button chooseButton = new Button(context);
				
				//Text
				chooseButton.setText("Choose");
				
				//Listener
				goBack = true;
				chooseButton.setOnClickListener(new DialogActionListener(this, goBack));
				
				//Child
				optionRow.addView(chooseButton, buttonSpaceParameters);
				break;
			case CHECK_BOX:
				//Init
				CheckBox checkBox = new CheckBox(context);
				
				//Listener
				goBack = false;
				checkBox.setOnCheckedChangeListener(new DialogActionListener(this, goBack));
				
				//Checking state
				try {
					checkBox.setChecked(Boolean.valueOf(FileManager.readPath(context, path + "." + optionsTitle)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//Childs
				optionRow.addView(checkBox, buttonSpaceParameters);
				break;
			case NONE:
				nextState = getChild(optionsTitle);

				goBack = false;
				optionsTextView.setOnClickListener(new DialogActionListener(this, goBack));
				break;
			}
			
			table.addView(optionRow);
		}
	}

	public void refresh() {
		table.removeAllViews();
		populateOptionDialog();
	}
	
	public void addChild(OptionDialog child){
		childs.add(child);
		child.parent = this;
		child.path = this.path + "." + child.title;
	}
	
	public List<OptionDialog> getChilds(){
		return childs;
	}
	
	public OptionDialog getChild(String title){
		for (OptionDialog child : childs) {
			if(child.title.equalsIgnoreCase(title)){
				return child;
			}
		}
		return null;
	}
	
	public OptionDialog getParent(){
		return parent;
	}
	
	public boolean hasParent(){
		if(parent == null){
			return false;
		}else{
			return true;
		}
	}

	public TreeMap<String, OptionRowType> getOptions(){
		return option;
	}

	public void addOption(String text, OptionRowType type){
		option.put(text, type);
	}
	
	public String getTitle() {
		return title;
	}

	public String getPath() {
		return path;
	}

	public String getOptionTitle() {
		return optionsTitle;
	}

	public Dialog getNextState() {
		return nextState;
	}
}