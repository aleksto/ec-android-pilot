package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.ColorType;
import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.TimeType;
import com.tieto.ec.gui.dialogs.OptionDialog;

public class DmrOptionsButtonListener implements OnMenuItemClickListener {

	private OptionDialog root;
	private final DailyMorningReport dailyMorningReport;

	/**
	 * Creates a new {@link OnMenuItemClickListener} for {@link DailyMorningReport} option button
	 * for displaying a {@link OptionDialog}
	 * @param dmr {@link DailyMorningReport}
	 */
	public DmrOptionsButtonListener(DailyMorningReport dmr){
		this.dailyMorningReport = dmr;
	}

	/**
	 * Runs when the user clicks the {@link MenuItem} with this {@link OnMenuItemClickListener} attached
	 * This method builds up the dialogs
	 */
	public boolean onMenuItemClick(MenuItem arg0) {
		//Level1
		root = new OptionDialog(dailyMorningReport, OptionTitle.DMRReport);

		//Level2
		OptionDialog security = new OptionDialog(dailyMorningReport, OptionTitle.SecurityOptions);
		OptionDialog color = new OptionDialog(dailyMorningReport, OptionTitle.ColorOptions);
		OptionDialog reportOption = new OptionDialog(dailyMorningReport, OptionTitle.ReportOptions);

		//Level3
		OptionDialog textColor = new OptionDialog(dailyMorningReport, OptionTitle.TextColor);
		OptionDialog backgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.BackgroundColor);
		OptionDialog cellTextColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBackgroundColor);
		OptionDialog cellBackgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBorderColor);
		OptionDialog intervalDialog = new OptionDialog(dailyMorningReport, OptionTitle.Interval);
		OptionDialog updateIntervalDialog = new OptionDialog(dailyMorningReport, OptionTitle.UpdateInterval);


		// Root options
		createRootOptions();
		createColorOptions(color);
		createReportOptions(reportOption);
		createUpdateIntervalDialog(updateIntervalDialog);
		createIntervalOptions(intervalDialog);
		createSecurityOptions(security);
		createSubColorOptions(textColor);
		createSubColorOptions(backgroundColor);
		createSubColorOptions(cellBackgroundColor);
		createSubColorOptions(cellTextColor);

		root.addChild(security);
		root.addChild(color);
		root.addChild(reportOption);

		reportOption.addChild(updateIntervalDialog);
		reportOption.addChild(intervalDialog);

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
		updateIntervalDialog.setOnDismissListener(new DmrServiceRestartListener(dailyMorningReport));

		root.show();
		return false;
	}

	/**
	 * Creates sub dialog update interval
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createUpdateIntervalDialog(OptionDialog dialog) {
		dialog.addOption(TimeType.off, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.min15, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.min30, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.min45, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.hour1, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.hour2, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.hour5, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.hour10, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.hour12, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.day1, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.day2, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(TimeType.day3, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption("Debug: 20 sec", OptionRowType.CHOOSE_BUTTON);
	}

	/**
	 * Creates sub dialog report options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createReportOptions(OptionDialog dialog) {
		dialog.addOption(OptionTitle.Interval, OptionRowType.NONE);
		dialog.addOption(OptionTitle.Dates, OptionRowType.NONE);
		dialog.addOption(OptionTitle.UpdateInterval, OptionRowType.NONE);
	}

	/**
	 * Creates sub dialog interval
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createIntervalOptions(OptionDialog dialog) {
		dialog.addOption(OptionTitle.Daily, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(OptionTitle.Weekly, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(OptionTitle.Monthly, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(OptionTitle.Yearly, OptionRowType.CHOOSE_BUTTON);
	}

	/**
	 * Creates sub dialog sub color options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createSubColorOptions(OptionDialog dialog) {
		dialog.addOption(ColorType.Black, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.Blue, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.LightBlue, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.Cyan, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.DarkGray, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.LightGray, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.Gray, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.Green, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.Magenta, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.Red, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.White, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(ColorType.Yellow, OptionRowType.CHOOSE_BUTTON);
	}

	/**
	 * Creates sub dialog security options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createSecurityOptions(OptionDialog dialog) {
		dialog.addOption(OptionTitle.ClearUsernameAndPassword, OptionRowType.CHOOSE_BUTTON);
		dialog.addOption(OptionTitle.RememberLoginCredentials, OptionRowType.CHECK_BOX);
	}

	/**
	 * Creates sub dialog color options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createColorOptions(OptionDialog dialog) {
		dialog.addOption(OptionTitle.TextColor, OptionRowType.NONE);
		dialog.addOption(OptionTitle.BackgroundColor, OptionRowType.NONE);
		dialog.addOption(OptionTitle.CellBackgroundColor, OptionRowType.NONE);
		dialog.addOption(OptionTitle.CellBorderColor, OptionRowType.NONE);
	}

	/**
	 * Creates sub dialog root options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createRootOptions() {
		root.addOption(OptionTitle.SecurityOptions, OptionRowType.NONE);
		root.addOption(OptionTitle.ColorOptions, OptionRowType.NONE);
		root.addOption(OptionTitle.ReportOptions, OptionRowType.NONE);
	}
}
