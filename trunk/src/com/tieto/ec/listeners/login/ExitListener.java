package com.tieto.ec.listeners.login;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ExitListener implements OnClickListener{

	private Activity activity;
	
	public ExitListener(Activity activity){
		this.activity = activity;
	}
	
	public void onClick(View v) {
		activity.onBackPressed();
	}
}
