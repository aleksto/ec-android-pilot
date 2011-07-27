package com.tieto.ec.gui.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import android.app.Dialog;
import android.content.Context;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.OptionRow.OptionRowType;

public class OptionDialog extends Dialog {
	
	private String title;
	private String path;
	private TreeMap<String, OptionRowType> optionRows;
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
		path = title.toString();
		table = new TableLayout(context);
		ScrollView scroll = new ScrollView(context);
		childs = new ArrayList<OptionDialog>();
		optionRows = new TreeMap<String, OptionRowType>();
		parent = null;
		
		//This
		setContentView(scroll);
		setTitle(title.toString());
		
		//Scroll
		scroll.addView(table);
		
	}
	
	/**
	 * 	Including to using Androids {@link Dialog} show() this method calls
	 *  the buildOptionDialogRows method which populates the dialog. 
	 */
	@Override
	public void show() {
		buildOptionDialogRows();
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
		buildOptionDialogRows();
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
	public TreeMap<String, OptionRowType> getOptionRows(){
		return optionRows;
	}

	/**
	 * Adds a option row in this {@link OptionDialog} using String
	 * @param text
	 * @param optionRowType
	 */
	public void addOptionRow(String text, OptionRowType optionRowType){
		optionRows.put(text, optionRowType);
	}
	
	/**
	 * Adds a option row this {@link OptionDialog} using Enum
	 * @param text
	 * @param optionRowType
	 */
	public void addOptionRow(Enum<?> type, OptionRowType optionRowType){
		String text = type.toString();
		optionRows.put(text, optionRowType);
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

	/**
	 * This method populates this {@link OptionDialog} with instances of {@link OptionRow}
	 */
	private void buildOptionDialogRows() {
		table.removeAllViews();
		Set<String> optionTexts = optionRows.keySet();
		
		for (String optionsTitle : optionTexts) {
			OptionRow optionRow = new OptionRow(this, optionsTitle, optionRows.get(optionsTitle));
			table.addView(optionRow);
		}
	}
}