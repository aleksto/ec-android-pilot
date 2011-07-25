package com.tieto.ec.listeners.dmr;

import com.tieto.ec.gui.dialogs.InfoDialog;

import android.app.Activity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class DmrMapButtonListener implements OnMenuItemClickListener {

	private Activity activity;
	
	public DmrMapButtonListener(Activity activity) {
		this.activity = activity;
	}

	public boolean onMenuItemClick(MenuItem item) {
//		Intent intent = new Intent(activity, Map.class);	
//		activity.startActivity(intent);
		InfoDialog.showInfoDialog(activity, "Text");
		return false;
	}
}
