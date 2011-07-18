package com.tieto.ec.gui.dialogs;

import java.io.IOException;

import com.tieto.ec.listeners.loginOptions.webserviceUrlDialog.CancelListener;
import com.tieto.ec.listeners.loginOptions.webserviceUrlDialog.OkListener;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;

public class WebserviceUrlDialog extends Dialog{

	public WebserviceUrlDialog(Context context) {
		//Super
		super(context);
		
		//Init
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		EditText url = new EditText(context);
		Button ok = new Button(context);
		Button cancel = new Button(context);
		
		//this
		setTitle("Enter new webservice url");
		setContentView(scroll);
		
		//Childs
		scroll.addView(table);
		table.addView(url);
		table.addView(ok);
		table.addView(cancel);
		
		//Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		try {
			url.setText(FileManager.readPath(context, "com.ec.prod.android.pilot.service.WebserviceUrl"));
		} catch (IOException e) {
			url.setText("http://");
			e.printStackTrace();
		}
		
		//Listeners
		ok.setOnClickListener(new OkListener(context, url, this));
		cancel.setOnClickListener(new CancelListener(this));
	}
}
