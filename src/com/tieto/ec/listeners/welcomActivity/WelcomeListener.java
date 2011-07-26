package com.tieto.ec.listeners.welcomActivity;

import com.tieto.ec.activities.Login;
import com.tieto.ec.gui.animation3D.WelcomeAnimation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class WelcomeListener implements OnClickListener {

	private Context context;
	
	/**
	 * Creates a new {@link OnClickListener} for {@link WelcomeAnimation}
	 * @param context {@link Context} used for Android framework actions
	 */
	public WelcomeListener(Context context) {
		this.context = context;
	}

	/**
	 * Runs when the user clicks the {@link View} attached with this listener
	 * This method then starts the {@link Login} activity
	 */
	public void onClick(View arg0) {
		Intent intent = new Intent(context, Login.class);
		context.startActivity(intent);
	}
}
