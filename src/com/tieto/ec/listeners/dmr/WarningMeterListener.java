package com.tieto.ec.listeners.dmr;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.ec.prod.android.pilot.model.Section;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.SectionWarningsDialog;
import com.tieto.ec.gui.dmr.WarningMeter;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.Warning;

public class WarningMeterListener implements OnClickListener{

	private final SectionWarningsDialog dialog;

	/**
	 * Creates a {@link OnClickListener} for a {@link WarningMeter}
	 * @param dmr {@link DailyMorningReport} getting {@link Context} from here used for Android framework actions
	 * @param sectionWarning A {@link SectionWarning} containing all the {@link Warning}s for the {@link Section}
	 */
	public WarningMeterListener(Activity activity, SectionWarning sectionWarning){
		dialog = new SectionWarningsDialog(activity, sectionWarning);
	}	

	/**
	 * Runs when user clicks the {@link View} with this {@link OnClickListener} attached
	 */
	public void onClick(View v) {
		dialog.show();
	}
}
