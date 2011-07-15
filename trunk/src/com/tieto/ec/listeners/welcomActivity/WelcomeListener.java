package com.tieto.ec.listeners.welcomActivity;

import com.tieto.ec.activities.Login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class WelcomeListener implements OnClickListener {

	private Context context;
	
	public WelcomeListener(Context context) {
		this.context = context;
	}

	public void onClick(View arg0) {
		Intent intent = new Intent(context, Login.class);
		context.startActivity(intent);
		
	}

}
