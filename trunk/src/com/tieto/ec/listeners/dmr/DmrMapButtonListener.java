package com.tieto.ec.listeners.dmr;

import com.tieto.ec.activities.Map;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class DmrMapButtonListener implements OnMenuItemClickListener {

	private Activity activity;
	
	public DmrMapButtonListener(Activity activity) {
		this.activity = activity;
	}

	public boolean onMenuItemClick(MenuItem item) {
		Intent intent = new Intent(activity, Map.class);	
		activity.startActivity(intent);
		return false;
	}
}
