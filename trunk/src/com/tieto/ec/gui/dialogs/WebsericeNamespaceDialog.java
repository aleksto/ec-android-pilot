package com.tieto.ec.gui.dialogs;

import java.io.IOException;

import com.tieto.ec.listeners.loginOptions.webserviceNamespaceDialog.CancelListener;
import com.tieto.ec.listeners.loginOptions.webserviceNamespaceDialog.OkListener;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;

public class WebsericeNamespaceDialog extends Dialog{

	public WebsericeNamespaceDialog(Context context) {
		//Super
		super(context);
		
		//Init
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		EditText namespace = new EditText(context);
		Button ok = new Button(context);
		Button cancel = new Button(context);
		
		//this
		setTitle("Enter new webservice namespace");
		setContentView(scroll);
		
		//Childs
		scroll.addView(table);
		table.addView(namespace);
		table.addView(ok);
		table.addView(cancel);
		
		//Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		try {
			namespace.setText(FileManager.readPath(context, "com.ec.prod.android.pilot.service.WebserviceNamespace"));
		} catch (IOException e) {
			namespace.setText("http://");
			e.printStackTrace();
		}
		
		//Listeners
		ok.setOnClickListener(new OkListener(context, namespace, this));
		cancel.setOnClickListener(new CancelListener(this));
	}
}
