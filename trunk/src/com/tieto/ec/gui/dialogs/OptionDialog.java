package com.tieto.ec.gui.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.OptionRow.OptionRowType;
import com.tieto.ec.listeners.dialogs.ExitOptionTree;
import com.tieto.ec.logic.FileManager;

public class OptionDialog extends Dialog {
	
	private String title;
	private String path;
	private TreeMap<String, OptionRowType> optionRowTypes;
	private ArrayList<OptionRow> optionRows;
	private List<OptionDialog> childs;
	private OptionDialog parent;
	private TableLayout table;
	 
	/**
	 * Creates a branch in a complete options tree. Which is created with a set of this class.
	 * {@link OptionTitle} title is the identity of the instantiation of this branch. 
	 * Context is needed for Androids framework actions.
	 * @param context
	 * @param title
	 */
	public OptionDialog(Context context, OptionTitle title){
		this(context, title.toString());
	}
	
	/**
	 * Creates a branch in a complete options tree. Which is created with a set of this class.
	 * Title is the identity of the instantiation of this branch. 
	 * Context is needed for Androids framework actions.
	 * @param context
	 * @param title
	 */
	public OptionDialog(Context context, String title){
		//Super
		super(context);
				
		//Init
		this.title = title;
		setTitle(title.toString());
		path = title.toString();
		childs = new ArrayList<OptionDialog>();
		optionRowTypes = new TreeMap<String, OptionRowType>();
		optionRows = new ArrayList<OptionRow>();
		parent = null;
		
		//Standard linearLayout params
		LinearLayout.LayoutParams standardParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
		//Main
		LinearLayout main = new LinearLayout(context);
		main.setOrientation(LinearLayout.VERTICAL);
		
		//Content
		RelativeLayout content = new RelativeLayout(context);
		
		//Scroll
		LinearLayout scrollLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams scrollLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final int SCROLL_BOTTOM_MARGIN = 80;
		scrollLayoutParams.bottomMargin = SCROLL_BOTTOM_MARGIN;
		ScrollView scroll = new ScrollView(context);
		int scrollId = 1;
		scroll.setId(scrollId);

		//Exit button
		LinearLayout exitLayout = new LinearLayout(context);
		exitLayout.setBackgroundResource(R.drawable.bottom_bar);
		final int BUTTON_LAYOUT_HEIGHT = 75;
		RelativeLayout.LayoutParams exitLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_LAYOUT_HEIGHT);
		exitLayoutParams.addRule(RelativeLayout.BELOW, scrollId);
		exitLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);	
		Button exit = new Button(context);
		exit.setText("Exit");
		exit.setBackgroundResource(android.R.drawable.btn_default);
		final int BUTTON_HEIGHT = 70;
		LinearLayout.LayoutParams exitParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_HEIGHT);
		final int BUTTON_MARGIN = 5;
		exitParams.setMargins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
		exit.setOnClickListener(new ExitOptionTree(this));

		
		//Table
		LinearLayout tableLayout = new LinearLayout(context);
		RelativeLayout.LayoutParams tableLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		table = new TableLayout(context);
		
		//Childs
		setContentView(main, standardParams);
		main.addView(content, standardParams);
		content.addView(scrollLayout, scrollLayoutParams);
		content.addView(exitLayout, exitLayoutParams);
		scrollLayout.addView(scroll, standardParams);
		scroll.addView(tableLayout, tableLayoutParams);
		tableLayout.addView(table, standardParams);
		exitLayout.addView(exit, exitParams);
	
	}
	
	/**
	 * 	Including to using Androids {@link Dialog} show() this method calls
	 *  the buildOptionDialogRows method which populates the dialog. 
	 */
	@Override
	public void show() {
		if(getParent() == null){
			buildOptionDialogRows(this);
		}	
		super.show();
	}
	
	/**
	 * This method makes sure the dialog is dismissed when pressing the back button
	 */
	@Override
	public void onBackPressed() {
		dismiss();
		super.onBackPressed();
	}
	
	/**
	 * This method refreshes the option tree. It cleans the table and builds it's rows over again
	 */
	public void refresh() {
		table.removeAllViews();
		optionRows.clear();
		buildOptionDialogRows(this);
	}
	
	/**
	 * Adds a child to the option tree. This is set to be parent and a reference path is created for
	 * writing to local memory. 
	 * @param child
	 */
	public void addChild(OptionDialog child){
		childs.add(child);
		child.parent = this;
		child.path = this.path + "." + child.title;
	}
	
	/**
	 * This method returns childs of this {@link OptionDialog}
	 * @return
	 */
	public List<OptionDialog> getChilds(){
		return childs;
	}
	
	/**
	 * This method returns a specific child of this {@link OptionDialog}
	 * @param title
	 * @return
	 */
	public OptionDialog getChild(String title){
		for (OptionDialog child : childs) {
			if(child.title.toString().equalsIgnoreCase(title)){
				return child;
			}
		}
		return null;
	}
	
	/**
	 * This method returns the parent of this {@link OptionDialog}
	 * @return
	 */
	public OptionDialog getParent(){
		return parent;
	}
	
	/**
	 * This method returns true if this {@link OptionDialog} has parent,
	 * and false if not.
	 * @return
	 */
	public boolean hasParent(){
		if(parent == null){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * Returns all the option rows in this {@link OptionDialog}
	 * @return
	 */
	public TreeMap<String, OptionRowType> getOptionRowTypes(){
		return optionRowTypes;
	}

	/**
	 * Adds a option row in this {@link OptionDialog} using String
	 * @param text
	 * @param optionRowType
	 */
	public void addOptionRow(String text, OptionRowType optionRowType){
		optionRowTypes.put(text, optionRowType);
	}
	
	/**
	 * Adds a option row this {@link OptionDialog} using Enum
	 * @param data.text
	 * @param optionRowType
	 */
	public void addOptionRow(Enum<?> type, OptionRowType optionRowType){
		String text = type.toString();
		addOptionRow(text, optionRowType);
	}
	
	/**
	 * Returns title of this {@link OptionDialog}
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns path of this {@link OptionDialog}
	 * @return
	 */
	public String getPath() {
		return path;
	}

	public ArrayList<OptionRow> getOptionRows(){
		return optionRows;
	}
	
	/**
	 * This method populates this {@link OptionDialog} with instances of {@link OptionRow}
	 */
	private void buildOptionDialogRows(OptionDialog dialog) {
		if(dialog != null){
			dialog.table.removeAllViews();			
		}
		Set<String> optionTexts = dialog.optionRowTypes.keySet();
		
		for (String optionsTitle : optionTexts) {
			OptionRow optionRow = new OptionRow(dialog, optionsTitle, dialog.optionRowTypes.get(optionsTitle));
			Log.d("tieto", "ADDING OPTION ROW: " + optionRow.getTitle());
			dialog.optionRows.add(optionRow);
			dialog.table.addView(optionRow);
		
			
			if(dialog.optionRowTypes.get(optionsTitle) == OptionRowType.NONE){
				Log.d("tieto", "Building dialog " + optionsTitle);
				buildOptionDialogRows(dialog.getChild(optionsTitle));
			}
		}
	}


	/**
	 * This method will delete the local memory of all the childs of the given {@link OptionDialog}.
	 * Deleting the childs memory will force the application to restore the default values. 
	 * @param dialog
	 * @param path
	 */
	public void setDefaultValues(OptionDialog dialog, String path) {
		Log.d("tieto", "SIZE: " + dialog.getOptionRows().size());
		for (OptionRow optionRow : dialog.getOptionRows()) {
			Log.d("tieto", "TITLE: " + optionRow.getTitle() + " TYPE: " + optionRow.getOptionRowType());
			
			if(optionRow.getOptionRowType() == OptionRowType.CHOOSE_BUTTON){
				Log.d("tieto", "DELETING: " + path);
				FileManager.deletePath(getContext(), path);
			}
			else if(optionRow.getOptionRowType() == OptionRowType.CHECK_BOX){
				FileManager.deletePath(getContext(), path + "." + optionRow.getTitle());
			}
			else if(optionRow.getOptionRowType() == OptionRowType.EDIT_BUTTON){
				FileManager.deletePath(getContext(), path + "." + optionRow.getTitle());
			}
			else if(optionRow.getOptionRowType() == OptionRowType.DATE_BUTTON){
				FileManager.deletePath(getContext(), path + "." + optionRow.getTitle());
			}
			else if(optionRow.getOptionRowType() == OptionRowType.NONE){
				Log.d("tieto", "CHILD: " + dialog.getChild(optionRow.getTitle()).getTitle());
				setDefaultValues(dialog.getChild(optionRow.getTitle()), path + "." + optionRow.getTitle());
			}
		}
	}
	
	
	
	
	
}