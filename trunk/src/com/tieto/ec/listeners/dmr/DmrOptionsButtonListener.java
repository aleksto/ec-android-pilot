package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.gui.dialogs.OptionDialog;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class DmrOptionsButtonListener implements OnMenuItemClickListener {

	private OptionDialog root;
	private final DailyMorningReport dailyMorningReport;

	public DmrOptionsButtonListener(DailyMorningReport dmr){
		this.dailyMorningReport = dmr;
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		//Init
		root = new OptionDialog(dailyMorningReport, "DMR Report");
		OptionDialog security = new OptionDialog(dailyMorningReport, "Security");
		OptionDialog color = new OptionDialog(dailyMorningReport, "Colors");
		OptionDialog textColor = new OptionDialog(dailyMorningReport, "Text Color");
		OptionDialog backgroundColor = new OptionDialog(dailyMorningReport, "Background Color");
		OptionDialog cellTextColor = new OptionDialog(dailyMorningReport, "Cell Background Color");
		OptionDialog cellBackgroundColor = new OptionDialog(dailyMorningReport, "Cell Border Color");
		
		// Root options
		createRootOptions();
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
		
		//Listeners
		textColor.setOnDismissListener(new DmrRefreshListener(dailyMorningReport));
		backgroundColor.setOnDismissListener(new DmrRefreshListener(dailyMorningReport));
		cellTextColor.setOnDismissListener(new DmrRefreshListener(dailyMorningReport));
		cellBackgroundColor.setOnDismissListener(new DmrRefreshListener(dailyMorningReport));
		
		root.show();
		return false;
	}

	private void createSubColorOptions(OptionDialog section) {
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

	private void createSecurityOptions(OptionDialog section) {
		section.addOption("Clear Username\nAnd Password", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Remember Login\nCredentials", OptionRowType.CHECK_BOX);
	}

	private void createColorOptions(OptionDialog section) {
		section.addOption("Text Color", OptionRowType.NONE);
		section.addOption("Background Color", OptionRowType.NONE);
		section.addOption("Cell Background Color", OptionRowType.NONE);
		section.addOption("Cell Border Color", OptionRowType.NONE);
	}

	private void createRootOptions() {
		root.addOption("Security", OptionRowType.NONE);
		root.addOption("Colors", OptionRowType.NONE);
	}
}
