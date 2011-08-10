package com.tieto.ec.logic;

import android.content.Context;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.ColorType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.TimeType;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.gui.dialogs.OptionRow.OptionRowType;
import com.tieto.ec.listeners.dmr.DmrRefreshListener;

public class OptionBuilder {

	public static OptionDialog buildLoginOption(Context context){
		OptionDialog dialog = new OptionDialog(context, OptionTitle.InputOptions);
		dialog.addOptionRow(OptionTitle.WebserviceURL, OptionRowType.EDIT_BUTTON);
		dialog.addOptionRow(OptionTitle.WebserviceNamespace, OptionRowType.EDIT_BUTTON);
		return dialog;
	}
	
	public static OptionDialog buildDailyMorningReportOption(DailyMorningReport dailyMorningReport){
		//Level1
		OptionDialog root = new OptionDialog(dailyMorningReport, OptionTitle.Options);

		//Level2
		OptionDialog security = new OptionDialog(dailyMorningReport, OptionTitle.SecurityOptions);
		OptionDialog color = new OptionDialog(dailyMorningReport, OptionTitle.ColorOptions);
		OptionDialog notification = new OptionDialog(dailyMorningReport, OptionTitle.NotificationOptions);

		//Level3
		OptionDialog textColor = new OptionDialog(dailyMorningReport, OptionTitle.TextColor);
		OptionDialog backgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.BackgroundColor);
		OptionDialog cellTextColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBackgroundColor);
		OptionDialog cellBackgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBorderColor);


		// Root options
		createRootOptions(root);
		createColorOptions(color);
		createSecurityOptions(security);
		createSubColorOptions(textColor);
		createSubColorOptions(backgroundColor);
		createSubColorOptions(cellBackgroundColor);
		createSubColorOptions(cellTextColor);
		createServiceOptions(notification);

		root.addChild(security);
		root.addChild(color);
		root.addChild(notification);

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
		return root;
	}
	
	/**
	 * Creates sub dialog root options
	 * @param data.dialog {@link OptionDialog} dialog
	 */
	private static void createRootOptions(OptionDialog root) {
		root.addOptionRow(OptionTitle.SecurityOptions, OptionRowType.NONE);
		root.addOptionRow(OptionTitle.ColorOptions, OptionRowType.NONE);
		root.addOptionRow(OptionTitle.DisplayWarnings, OptionRowType.CHECK_BOX);
		root.addOptionRow(OptionTitle.NotificationOptions, OptionRowType.NONE);
	}

	/**
	 * Creates sub dialog sub color options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private static void createSubColorOptions(OptionDialog dialog) {
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
	private static void createSecurityOptions(OptionDialog dialog) {
		dialog.addOptionRow(OptionTitle.ClearUsernameAndPassword, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(OptionTitle.RememberLoginCredentials, OptionRowType.CHECK_BOX);
	}

	/**
	 * Creates sub dialog color options
	 * @param dialog {@link OptionDialog} dialog
	 */
	private static void createColorOptions(OptionDialog dialog) {
		dialog.addOptionRow(OptionTitle.TextColor, OptionRowType.NONE);
		dialog.addOptionRow(OptionTitle.BackgroundColor, OptionRowType.NONE);
		dialog.addOptionRow(OptionTitle.CellBackgroundColor, OptionRowType.NONE);
		dialog.addOptionRow(OptionTitle.CellBorderColor, OptionRowType.NONE);
		dialog.addOptionRow(OptionTitle.Default, OptionRowType.DEFAULT);
	}
	
	private static void createServiceOptions(OptionDialog service) {
		service.addOptionRow(TimeType.off, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.sec20, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.min15, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.min30, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.min45, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.hour1, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.hour2, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.hour5, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.hour10, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.hour12, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.day1, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.day2, OptionRowType.CHOOSE_BUTTON);
		service.addOptionRow(TimeType.day3, OptionRowType.CHOOSE_BUTTON);

	}


	
	
		
	
}
