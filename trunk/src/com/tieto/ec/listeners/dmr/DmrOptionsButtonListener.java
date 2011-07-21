package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.ColorType;
import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.OptionDialog;

public class DmrOptionsButtonListener implements OnMenuItemClickListener {

	private OptionDialog root;
	private final DailyMorningReport dailyMorningReport;

	public DmrOptionsButtonListener(DailyMorningReport dmr){
		this.dailyMorningReport = dmr;
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		//Init
		root = new OptionDialog(dailyMorningReport, OptionTitle.DMRReport);

		OptionDialog security = new OptionDialog(dailyMorningReport, OptionTitle.SecurityOptions);
		OptionDialog color = new OptionDialog(dailyMorningReport, OptionTitle.ColorOptions);
		OptionDialog reportOption = new OptionDialog(dailyMorningReport, OptionTitle.ReportOptions);

		OptionDialog textColor = new OptionDialog(dailyMorningReport, OptionTitle.TextColor);
		OptionDialog backgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.BackgroundColor);
		OptionDialog cellTextColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBackgroundColor);
		OptionDialog cellBackgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBorderColor);

		OptionDialog intervalDialog = new OptionDialog(dailyMorningReport, OptionTitle.Interval);
		OptionDialog datesDialog = new OptionDialog(dailyMorningReport, OptionTitle.Dates);


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
		datesDialog.setOnDismissListener(listener);

		root.show();
		return false;
	}

	
	private void createReportOptions(OptionDialog section) {
		section.addOption(OptionTitle.Interval, OptionRowType.NONE);
		section.addOption(OptionTitle.Dates, OptionRowType.NONE);
	}
	
	private void createDateOptions(OptionDialog section) {
		section.addOption(OptionTitle.FromDate, OptionRowType.DATE_BUTTON);
		section.addOption(OptionTitle.ToDate, OptionRowType.DATE_BUTTON);
	}


	private void createIntervalOptions(OptionDialog section) {
		section.addOption(OptionTitle.Daily, OptionRowType.CHOOSE_BUTTON);
		section.addOption(OptionTitle.Weekly, OptionRowType.CHOOSE_BUTTON);
		section.addOption(OptionTitle.Monthly, OptionRowType.CHOOSE_BUTTON);
		section.addOption(OptionTitle.Yearly, OptionRowType.CHOOSE_BUTTON);
	}

	private void createSubColorOptions(OptionDialog section) {
		section.addOption(ColorType.Black, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.Blue, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.LightBlue, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.Cyan, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.DarkGray, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.LightGray, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.Gray, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.Green, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.Magenta, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.Red, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.White, OptionRowType.CHOOSE_BUTTON);
		section.addOption(ColorType.Yellow, OptionRowType.CHOOSE_BUTTON);
	}

	private void createSecurityOptions(OptionDialog section) {
		section.addOption(OptionTitle.ClearUsernameAndPassword, OptionRowType.CHOOSE_BUTTON);
		section.addOption(OptionTitle.RememberLoginCredentials, OptionRowType.CHECK_BOX);
	}

	private void createColorOptions(OptionDialog section) {
		section.addOption(OptionTitle.TextColor, OptionRowType.NONE);
		section.addOption(OptionTitle.BackgroundColor, OptionRowType.NONE);
		section.addOption(OptionTitle.CellBackgroundColor, OptionRowType.NONE);
		section.addOption(OptionTitle.CellBorderColor, OptionRowType.NONE);
	}

	private void createRootOptions() {
		root.addOption(OptionTitle.SecurityOptions, OptionRowType.NONE);
		root.addOption(OptionTitle.ColorOptions, OptionRowType.NONE);
		root.addOption(OptionTitle.ReportOptions, OptionRowType.NONE);
	}
}
