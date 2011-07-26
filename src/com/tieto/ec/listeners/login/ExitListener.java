package com.tieto.ec.listeners.login;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ExitListener implements OnClickListener{

	private Activity activity;
	
	/**
	 * Creates a {@link OnClickListener} for a {@link View}
	 * It will act like you press back button
	 * @param activity
	 */
	public ExitListener(Activity activity){
		this.activity = activity;
	}
	
	/**
	 * Runs when the user clicks the {@link View} with this listener attached
	 */
	public void onClick(View v) {
		activity.onBackPressed();
	}
}
