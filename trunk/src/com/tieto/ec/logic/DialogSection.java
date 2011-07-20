package com.tieto.ec.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.tieto.ec.enums.OptionRowType;

public class DialogSection {

	private String title, path;
	private TreeMap<String, OptionRowType> option;
	private List<DialogSection> childs;
	private DialogSection parent;

	public DialogSection(String title){
		this.title = title;
		path = title;
		childs = new ArrayList<DialogSection>();
		option = new TreeMap<String, OptionRowType>();
		parent = null;
	}
	
	public void addChild(DialogSection child){
		childs.add(child);
		child.parent = this;
		child.path = this.path + "." + child.title;
	}
	
	public List<DialogSection> getChilds(){
		return childs;
	}
	
	public DialogSection getChild(String title){
		for (DialogSection child : childs) {
			if(child.title.equalsIgnoreCase(title)){
				return child;
			}
		}
		return null;
	}
	
	public DialogSection getParent(){
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

	public String getBasePath() {
		return path;
	}
}