package com.tieto.ec.gui.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tieto.ec.listeners.dialogs.HideDialogListener;
import com.tieto.ec.listeners.dialogs.XEditTextOkListener;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class XEditTextDialog extends Dialog implements OnItemSelectedListener{

	private final String TEXT = "Number: ";
	private final int ON_TOP = 0;
	private final int ON_BOTTOM = -1;
	private final String basePath;
	private final String title;
	private Spinner spinner;
	private TableLayout table;

	public XEditTextDialog(Context context, String basePath, String title) {
		//Super
		super(context);
		this.title = title;
		this.basePath = basePath;
		
		//Init
		spinner = new Spinner(context);
		table = new TableLayout(context);
		RelativeLayout main = new RelativeLayout(context);
		LinearLayout buttonRow = new LinearLayout(context);
		ScrollView scroll = new ScrollView(context);
		Button ok = new Button(context);
		Button cancel = new Button(context);
		
		//Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		
		//Spinner
		addValuesToSpinner(spinner, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
		spinner.setOnItemSelectedListener(this);
		try {
			spinner.setSelection(Integer.valueOf(FileManager.readPath(getContext(), basePath + "." + title + "." + "numberOfValues")) - 1);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ID
		spinner.setId(1);
		scroll.setId(2);
		buttonRow.setId(3);
		
		//Childs
		scroll.addView(table);
		addViewCenterAndBelow(main, spinner, ON_TOP, 0);
		addViewCenterAndBelow(main, scroll, spinner.getId(), buttonRow.getId());
		addViewCenterAndBelow(main, buttonRow, ON_BOTTOM, 0);
		addButtonToRow(buttonRow, ok);
		addButtonToRow(buttonRow, cancel);
		
		//This
		setContentView(main);
		setTitle(title);
		
		//Listeners
		cancel.setOnClickListener(new HideDialogListener(this));
		ok.setOnClickListener(new XEditTextOkListener(this));
	}

	private void changeNumberOfInputFields(int number){
		table.removeAllViews();
		
		for (int i = 0; i < number; i++) {
			TextView text = new TextView(getContext());
			EditText editText = new EditText(getContext());
			
			table.addView(text);
			table.addView(editText);
			
			try {
				text.setText(TEXT + (i+1));
				editText.setText(FileManager.readPath(getContext(), basePath + "." + title + "." + i));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addViewCenterAndBelow(RelativeLayout main, View view, int belowID, int aboveID){
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		if(main.getChildCount() == 0 && belowID == ON_TOP){	
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		}else if(belowID == ON_BOTTOM){
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		}else{
			params.addRule(RelativeLayout.BELOW, belowID);	
			params.addRule(RelativeLayout.ABOVE, aboveID);			
		}
		main.addView(view, params);
	}

	private void addButtonToRow(LinearLayout row, Button button){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
		row.addView(button, params);
	}

	private void addValuesToSpinner(Spinner spinner, Object ... values){
		ArrayAdapter<Object> valuesAdapter = new ArrayAdapter<Object>(getContext(), android.R.layout.simple_spinner_item, values);
		spinner.setAdapter(valuesAdapter);
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		changeNumberOfInputFields(arg2+1);
		FileManager.writePath(getContext(), basePath + "." + title + "." + "numberOfValues", (arg2+1)+"");
	}

	public void onNothingSelected(AdapterView<?> arg0) {}

	public String getPath(){
		return basePath + "." + title;
	}
	
	public List<String> getInsertedText(){
		ArrayList<String> list = new ArrayList<String>();
		
		View view;
		EditText row;
		for (int i = 0; i < table.getChildCount(); i++) {
			view = table.getChildAt(i);
			
			if(view instanceof EditText){
				row = (EditText) view;
				list.add(row.getText().toString());
			}
		}
		
		return list;
	}
}
