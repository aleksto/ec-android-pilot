package com.tieto.ec.listeners.dmr;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.DialogSection;

import android.content.Context;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class DmrOptionsButtonListener implements OnMenuItemClickListener {

	private OptionDialog options;

	public DmrOptionsButtonListener(Context context){
		//Init
		DialogSection root = new DialogSection("DMR Report");
		DialogSection security = new DialogSection("Security");
		DialogSection color = new DialogSection("Colors");
		DialogSection textColor = new DialogSection("Text Color");
		DialogSection backgroundColor = new DialogSection("Background Color");
		DialogSection cellTextColor = new DialogSection("Cell Background Color");
		DialogSection cellBackgroundColor = new DialogSection("Cell Border Color");
		
		// Root options
		createRootOptions(root);
		createColorOptions(color);
		createSecurityOptions(security);
		createSubColorOptions(textColor);
		createSubColorOptions(backgroundColor);
		createSubColorOptions(cellBackgroundColor);
		createSubColorOptions(cellTextColor);
		
		root.addChild(security);
		root.addChild(color);
		
		color.addChild(textColor);
		color.addChild(backgroundColor);
		color.addChild(cellTextColor);
		color.addChild(cellBackgroundColor);
		
		options = new OptionDialog(context, root);
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		options.show();
		return false;
	}

	private void createSubColorOptions(DialogSection section) {
		section.addOption("Black", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Blue", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Light Blue", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Cyan", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Dark Gray", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Light Gray", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Gray", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Green", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Magenta", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Red", OptionRowType.CHOOSE_BUTTON);
		section.addOption("White", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Yellow", OptionRowType.CHOOSE_BUTTON);
	}

	private void createSecurityOptions(DialogSection section) {
		section.addOption("Clear Username And Password", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Remember Login Credentials", OptionRowType.CHECK_BOX);
	}

	private void createColorOptions(DialogSection section) {
		section.addOption("Text Color", OptionRowType.NONE);
		section.addOption("Background Color", OptionRowType.NONE);
		section.addOption("Cell Background Color", OptionRowType.NONE);
		section.addOption("Cell Border Color", OptionRowType.NONE);
	}

	private void createRootOptions(DialogSection section) {
		section.addOption("Security", OptionRowType.NONE);
		section.addOption("Colors", OptionRowType.NONE);
	}
}
