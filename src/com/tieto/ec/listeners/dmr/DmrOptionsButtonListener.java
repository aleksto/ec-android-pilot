package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.listActivities.DmrOptionActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class DmrOptionsButtonListener implements OnMenuItemClickListener {

	private Activity activity;
	
	public DmrOptionsButtonListener(Activity activity) {
		this.activity = activity;
	}

	public boolean onMenuItemClick(MenuItem arg0) {
		Intent intent = new Intent(activity, DmrOptionActivity.class);
		activity.startActivity(intent);
		return false;
	}
}
