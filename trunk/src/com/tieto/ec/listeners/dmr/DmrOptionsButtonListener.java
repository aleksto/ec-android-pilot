package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.gui.dialogs.OptionDialog;

public class DmrOptionsButtonListener implements OnMenuItemClickListener {

	private OptionDialog root;
	private final DailyMorningReport dailyMorningReport;

	public DmrOptionsButtonListener(DailyMorningReport dmr){
		this.dailyMorningReport = dmr;
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		//Init
		root = new OptionDialog(dailyMorningReport, "DMR Report");

		OptionDialog security = new OptionDialog(dailyMorningReport, "Security Options");
		OptionDialog color = new OptionDialog(dailyMorningReport, "Color Options");
		OptionDialog reportOption = new OptionDialog(dailyMorningReport, "Report Options");

		OptionDialog textColor = new OptionDialog(dailyMorningReport, "Text Color");
		OptionDialog backgroundColor = new OptionDialog(dailyMorningReport, "Background Color");
		OptionDialog cellTextColor = new OptionDialog(dailyMorningReport, "Cell Background Color");
		OptionDialog cellBackgroundColor = new OptionDialog(dailyMorningReport, "Cell Border Color");

		OptionDialog intervalDialog = new OptionDialog(dailyMorningReport, "Interval");
		OptionDialog datesDialog = new OptionDialog(dailyMorningReport, "Dates");


		// Root options
		createRootOptions();
		createColorOptions(color);
		createReportOptions(reportOption);
		createIntervalOptions(intervalDialog);
		createDateOptions(datesDialog);
		createSecurityOptions(security);
		createSubColorOptions(textColor);
		createSubColorOptions(backgroundColor);
		createSubColorOptions(cellBackgroundColor);
		createSubColorOptions(cellTextColor);

		root.addChild(security);
		root.addChild(color);
		root.addChild(reportOption);

		reportOption.addChild(intervalDialog);
		reportOption.addChild(datesDialog);

		color.addChild(textColor);
		color.addChild(backgroundColor);
		color.addChild(cellTextColor);
		color.addChild(cellBackgroundColor);

		//Listeners
		DmrRefreshListener listener = new DmrRefreshListener(dailyMorningReport);
		textColor.setOnDismissListener(listener);
		backgroundColor.setOnDismissListener(listener);
		cellTextColor.setOnDismissListener(listener);
		cellBackgroundColor.setOnDismissListener(listener);
		intervalDialog.setOnDismissListener(listener);

		root.show();
		return false;
	}

	private void createDateOptions(OptionDialog section) {
		section.addOption("From Date", OptionRowType.DATE_BUTTON);
		section.addOption("To Date", OptionRowType.DATE_BUTTON);
	}

	private void createReportOptions(OptionDialog section) {
		section.addOption("Interval", OptionRowType.NONE);
		section.addOption("Dates", OptionRowType.NONE);
	}

	private void createIntervalOptions(OptionDialog section) {
		section.addOption("Daily", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Weekly", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Monthly", OptionRowType.CHOOSE_BUTTON);
		section.addOption("Yearly", OptionRowType.CHOOSE_BUTTON);
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
		root.addOption("Security Options", OptionRowType.NONE);
		root.addOption("Color Options", OptionRowType.NONE);
		root.addOption("Report Options", OptionRowType.NONE);
	}
}
