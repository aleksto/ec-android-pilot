package com.tieto.ec.activities;

import java.io.IOException;

import com.tieto.R;
import com.tieto.ec.listeners.loginOptions.NamespaceButtonsListener;
import com.tieto.ec.listeners.loginOptions.UrlButtonsListener;
import com.tieto.ec.logic.FileManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LoginOptions extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//This
		setContentView(R.layout.login_options);
		
		//Buttons
		Button urlButton = (Button) findViewById(R.id.loginOptionsRelativ1Button);
		Button namespaceButton = (Button) findViewById(R.id.loginOptionsRelativ2Button);
		urlButton.setOnClickListener(new UrlButtonsListener(this));
		namespaceButton.setOnClickListener(new NamespaceButtonsListener(this));

		//Text
		TextView url = (TextView) findViewById(R.id.loginOptionsRelativ1Subtext);	
		TextView namespace = (TextView) findViewById(R.id.loginOptionsRelativ2Subtext);
		
		try {
			url.setText(FileManager.readPath(this, "com.ec.prod.android.pilot.service.WebserviceUrl"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			namespace.setText(FileManager.readPath(this, "com.ec.prod.android.pilot.service.WebserviceNamespace"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
