package com.tieto.ec.listeners.dmr;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.ColorType;
import com.tieto.ec.enums.OptionTitle;
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
		DmrRefreshListener listener = new DmrRefreshListener(dailyMorningReport);
		textColor.setOnDismissListener(listener);
		backgroundColor.setOnDismissListener(listener);
		cellTextColor.setOnDismissListener(listener);
		cellBackgroundColor.setOnDismissListener(listener);
		color.setOnDismissListener(listener);

		root.show();
		return false;
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
		dialog.addOptionRow(OptionTitle.Default, OptionRowType.DEFAULT);
	}

	/**
	 * Creates sub dialog root options
	 * @param data.dialog {@link OptionDialog} dialog
	 */
	private void createRootOptions() {
		root.addOptionRow(OptionTitle.SecurityOptions, OptionRowType.NONE);
		root.addOptionRow(OptionTitle.ColorOptions, OptionRowType.NONE);
		root.addOptionRow(OptionTitle.DisplayWarnings, OptionRowType.CHECK_BOX);
	}
}
