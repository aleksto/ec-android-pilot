package com.tieto.ec.listeners.login;

import java.io.IOException;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.activities.Login;
import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.logic.FileManager;

public class LoginListener implements Runnable {

	//Webservice
	//private ViewService service;
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
			namespace = FileManager.readPath(login, "Input Options.Webservice Namespace");
			url = FileManager.readPath(login, "Input Options.Webservice URL");
			String usernameAndPassword = FileManager.readPath(login, "DMR Report.Security Options");
			
			if(!usernameAndPassword.equalsIgnoreCase("Clear Username\nAnd Password")){
				String[] split = usernameAndPassword.split("¤#@#¤");
				if(split.length == 2){
					login(split[0], split[1]);					
				}
			}
		} catch (IOException e) {
			Log.d("tieto", "Found no username and password");
			e.printStackTrace();
		}
	}

	private void login(String username, String password) {		
		//Saving username and password
		try {
			if(Boolean.valueOf(FileManager.readPath(login, "DMR Report.Security Options.Remember Login\nCredentials"))){
				if(!username.equalsIgnoreCase("") && !password.equalsIgnoreCase("")){
					FileManager.writePath(login, "DMR Report.Security Options", username + "¤#@#¤" + password);		
					Log.d("tieto", "Writing username and password");			
				}
			}else{
				Log.d("tieto", "Not writing username and password");
			}
		} catch (IOException e) {
			FileManager.writePath(login, "DMR Report.Security Options.Remember Login\nCredentials", "true");
			login(username, password);
			e.printStackTrace();
		}
		
		//Starting new intent
		Intent intent = new Intent(login, DailyMorningReport.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		intent.putExtra("namespace", namespace);
		intent.putExtra("url", url);
		login.startActivity(intent);
	}

	public void run() {
		try {
			//Reading new url and namespace if it is changed
			namespace = FileManager.readPath(login, "Input Options.Webservice Namespace");
			url = FileManager.readPath(login, "Input Options.Webservice URL");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(namespace != null && url != null){
			if(url.equalsIgnoreCase("debug") && namespace.equalsIgnoreCase("debug")){
				//DEBUG loggin
				login(username.getText().toString(), password.getText().toString());				
			}else{
				//Normal login
				login(username.getText().toString(), password.getText().toString());
			}
		}else{
			//URL and namespace is not defined
			InfoDialog.showInfoDialog(login, "if this is the first startup, please go to \nmenu->Options\n and set up url and namespace");
		}
	}
}
