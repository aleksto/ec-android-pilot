package com.tieto.ec.listeners.loginOptions.webserviceUrlDialog;

import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class OkListener implements OnClickListener{

	private Context context;
	private EditText url;
	private Dialog dialog;

	public OkListener(Context context, EditText url, Dialog dialog) {
		this.url = url;
		this.dialog = dialog;
		this.context = context;
	}

	public void onClick(View v) {
		FileManager.writePath(context, "com.ec.prod.android.pilot.service.WebserviceUrl", url.getText().toString());
		dialog.hide();
	}
}
