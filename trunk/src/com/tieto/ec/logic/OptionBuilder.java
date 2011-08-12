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
		OptionDialog send = new OptionDialog(dailyMorningReport, OptionTitle.SentOptions);

		//Level3
		OptionDialog textColor = new OptionDialog(dailyMorningReport, OptionTitle.TextColor);
		OptionDialog backgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.BackgroundColor);
		OptionDialog cellTextColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBackgroundColor);
		OptionDialog cellBackgroundColor = new OptionDialog(dailyMorningReport, OptionTitle.CellBorderColor);
		OptionDialog timeDeterminedNotification = new OptionDialog(dailyMorningReport, OptionTitle.TimeDeterminedNotification);
		OptionDialog intervalDeterminedNotification = new OptionDialog(dailyMorningReport, OptionTitle.IntervalDeterminedNotification);



		// Root options
		createRootOptions(root);
		createColorOptions(color);
		createSecurityOptions(security);
		createEmailOptions(send);
		createNotificationOptions(notification);
		createSubColorOptions(textColor);
		createSubColorOptions(backgroundColor);
		createSubColorOptions(cellBackgroundColor);
		createSubColorOptions(cellTextColor);
		createIntervalDeterminedNotification(intervalDeterminedNotification);
		createTimeDeterminedNotification(timeDeterminedNotification);

		root.addChild(security);
		root.addChild(color);
		root.addChild(notification);
		root.addChild(send);
	
		color.addChild(textColor);
		color.addChild(backgroundColor);
		color.addChild(cellTextColor);
		color.addChild(cellBackgroundColor);
		
		notification.addChild(timeDeterminedNotification);
		notification.addChild(intervalDeterminedNotification);

		//Listeners
		DmrRefreshListener listener = new DmrRefreshListener(dailyMorningReport);
		root.setOnDismissListener(listener);
//		textColor.setOnDismissListener(listener);
//		backgroundColor.setOnDismissListener(listener);
//		cellTextColor.setOnDismissListener(listener);
//		cellBackgroundColor.setOnDismissListener(listener);
//		color.setOnDismissListener(listener);
		return root;
	}
	
	



	private static void createTimeDeterminedNotification(OptionDialog dialog) {
		dialog.addOptionRow(OptionTitle.SetTime, OptionRowType.Time_Button);
		dialog.addOptionRow(OptionTitle.Monday, OptionRowType.CHECK_BOX);
		dialog.addOptionRow(OptionTitle.Tuesday, OptionRowType.CHECK_BOX);
		dialog.addOptionRow(OptionTitle.Wednsday, OptionRowType.CHECK_BOX);
		dialog.addOptionRow(OptionTitle.Thursday, OptionRowType.CHECK_BOX);
		dialog.addOptionRow(OptionTitle.Friday, OptionRowType.CHECK_BOX);
		dialog.addOptionRow(OptionTitle.Saturday, OptionRowType.CHECK_BOX);
		dialog.addOptionRow(OptionTitle.Sunday, OptionRowType.CHECK_BOX);

		
	}

	/**
	 * Creates sub dialog root options
	 * @param data.dialog {@link OptionDialog} dialog
	 */
	private static void createRootOptions(OptionDialog root) {
		root.addOptionRow(OptionTitle.DisplayWarnings, OptionRowType.CHECK_BOX);
//		root.addOptionRow(OptionTitle.NotificationOptions, OptionRowType.NONE);
//		root.addOptionRow(OptionTitle.SentOptions, OptionRowType.NONE);
//		root.addOptionRow(OptionTitle.ColorOptions, OptionRowType.NONE);
//		root.addOptionRow(OptionTitle.SecurityOptions, OptionRowType.NONE);
	}
	
	private static void createEmailOptions(OptionDialog dialog) {
		dialog.addOptionRow(OptionTitle.StandardReceiver, OptionRowType.X_EDIT_BUTTON);
		dialog.addOptionRow(OptionTitle.StandardTopic, OptionRowType.EDIT_BUTTON);
		dialog.addOptionRow(OptionTitle.Signature, OptionRowType.EDIT_BUTTON);
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
		dialog.addOptionRow(OptionTitle.Default, OptionRowType.DEFAULT);
//		dialog.addOptionRow(OptionTitle.TextColor, OptionRowType.NONE);
//		dialog.addOptionRow(OptionTitle.BackgroundColor, OptionRowType.NONE);
//		dialog.addOptionRow(OptionTitle.CellBackgroundColor, OptionRowType.NONE);
//		dialog.addOptionRow(OptionTitle.CellBorderColor, OptionRowType.NONE);
	}
	
	private static void createNotificationOptions(OptionDialog notification) {
//		notification.addOptionRow(OptionTitle.IntervalDeterminedNotification, OptionRowType.NONE);
//		notification.addOptionRow(OptionTitle.TimeDeterminedNotification, OptionRowType.NONE);
		
	}
	
	private static void createIntervalDeterminedNotification(OptionDialog dialog) {
		dialog.addOptionRow(TimeType.off, OptionRowType.CHOOSE_BUTTON);
		dialog.addOptionRow(TimeType.sec20, OptionRowType.CHOOSE_BUTTON);
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
		
	}
	
	


}
