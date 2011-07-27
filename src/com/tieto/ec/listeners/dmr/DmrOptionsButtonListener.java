package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.ColorType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.TimeType;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.gui.dialogs.OptionRow.OptionRowType;

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
		createUpdateIntervalDialog(updateIntervalDialog);
		createIntervalOptions(intervalDialog);
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
		dialog.addOptionRow(TimeType.off, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.min15, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.min30, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.min45, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.hour1, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.hour2, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.hour5, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.hour10, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.hour12, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.day1, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.day2, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.day3, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow("Debug: 20 sec", OptionRowType.CHOOSE_BUTTON);
	}

	/**
	 * Creates sub dialog interval
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createIntervalOptions(OptionDialog dialog) {
		dialog.addOptionRow(OptionTitle.Daily, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(OptionTitle.Weekly, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(OptionTitle.Monthly, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(OptionTitle.Yearly, OptionRowType.CHOOSE_BUTTON);
	}

	/**
	 * Creates sub dialog sub color options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createSubColorOptions(OptionDialog dialog) {
		dialog.addOptionRow(ColorType.Black, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.Blue, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.LightBlue, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.Cyan, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.DarkGray, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.LightGray, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.Gray, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.Green, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.Magenta, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.Red, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.White, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(ColorType.Yellow, OptionRowType.CHOOSE_BUTTON);
	}

	/**
	 * Creates sub dialog security options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createSecurityOptions(OptionDialog dialog) {
		dialog.addOptionRow(OptionTitle.ClearUsernameAndPassword, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(OptionTitle.RememberLoginCredentials, OptionRowType.CHECK_BOX);
	}

	/**
	 * Creates sub dialog color options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createColorOptions(OptionDialog dialog) {
		dialog.addOptionRow(OptionTitle.TextColor, OptionRowType.NONE);
		dialog.addOptionRow(OptionTitle.BackgroundColor, OptionRowType.NONE);
		dialog.addOptionRow(OptionTitle.CellBackgroundColor, OptionRowType.NONE);
		dialog.addOptionRow(OptionTitle.CellBorderColor, OptionRowType.NONE);
	}

	/**
	 * Creates sub dialog root options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private void createRootOptions() {
		root.addOptionRow(OptionTitle.SecurityOptions, OptionRowType.NONE);
		root.addOptionRow(OptionTitle.ColorOptions, OptionRowType.NONE);
	}
}
