package com.tieto.ec.listeners.loginOptions.webserviceNamespaceDialog;

import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class OkListener implements OnClickListener{

	private Context context;
	private EditText namespace;
	private Dialog dialog;
	
	public OkListener(Context context, EditText namespace, Dialog dialog) {
		this.context = context;
		this.namespace = namespace;
		this.dialog = dialog;
	}

	public void onClick(View v) {
		FileManager.writePath(context, "com.ec.prod.android.pilot.service.WebserviceNamespace", namespace.getText().toString());
		dialog.hide();
	}
}
