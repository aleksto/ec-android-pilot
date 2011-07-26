package com.tieto.ec.listeners.dmr;

import android.app.Activity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.gui.dialogs.InfoDialog;

public class DmrMapButtonListener implements OnMenuItemClickListener {

	private Activity activity;
	
	/**
	 * Creates a {@link OnMenuItemClickListener} for displaying a new Map {@link Activity}
	 * This will be used in the future if we want a Map
	 * @param activity {@link Activity} for Android framework actions
	 */
	public DmrMapButtonListener(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Runs when the user clicks on the {@link MenuItem} with this listener attached
	 */
	public boolean onMenuItemClick(MenuItem item) {
//		Intent intent = new Intent(activity, Map.class);	
//		activity.startActivity(intent);
		InfoDialog.showInfoDialog(activity, "This function will become available in the future");
		return false;
	}
}
