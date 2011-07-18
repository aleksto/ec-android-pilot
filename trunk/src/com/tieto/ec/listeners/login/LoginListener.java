package com.tieto.ec.listeners.login;

import java.io.IOException;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.activities.Login;
import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.logic.FileManager;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginListener implements OnClickListener {

	//Webservice
	private ViewService service;
	private String namespace, url; 
	private EditText username, password;
	private Login login;

	public LoginListener(EditText username, EditText password, Login login){
		//Init
		this.login = login;
		this.password = password;
		this.username = username;
		this.password = password;

		//Reading saved data
		try {
			namespace = FileManager.readPath(login, "com.ec.prod.android.pilot.service.WebserviceNamespace");
			url = FileManager.readPath(login, "com.ec.prod.android.pilot.service.WebserviceUrl");
			String usernameRead = FileManager.readPath(login, "com.tieto.ec.username");
			String passwordRead = FileManager.readPath(login, "com.tieto.ec.password");

			if(!usernameRead.equalsIgnoreCase("") && !passwordRead.equalsIgnoreCase("")){
				login(usernameRead, passwordRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onClick(View v){
		if(namespace != null && url != null){
			service = new DMRViewServiceUnmarshalled(username.getText().toString(), password.getText().toString(), namespace, url);

			try{
				if(service.getSections() == null){
					login.toastFromOtherThreads("Not valid username/password");
				}else{
					try {
						if(FileManager.readPath(login, "com.tieto.ec.options.rememberUsernameAndPassword").equalsIgnoreCase("true")){
							//Writing username and password
							FileManager.writePath(login, "com.tieto.ec.username", username.getText().toString());
							FileManager.writePath(login, "com.tieto.ec.password", password.getText().toString());				
						}

						//Loggin inn
						login(username.getText().toString(), password.getText().toString());
					} catch (IOException e) {
						FileManager.writePath(login, "com.tieto.ec.options.rememberUsernameAndPassword", "true");
						onClick(v);
						e.printStackTrace();
					}
				}
			} catch(java.lang.NullPointerException e){
				InfoDialog.showInfoDialog(login, "Check webservice url and/or namespace");
			}
		}else{
			InfoDialog.showInfoDialog(login, "if this is the first startup, please go to \nmenu->Options\n and set up url and namespace");
		}
	}

	private void login(String username, String password) {
		//Setting login to quit when resuming
		login.setQuit(true);
		
		//Starting new intent
		Intent intent = new Intent(login, DailyMorningReport.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		intent.putExtra("namespace", namespace);
		intent.putExtra("url", url);
		login.startActivity(intent);
	}
}
