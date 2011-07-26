package com.tieto.ec.listeners.login;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.logic.FileManager;

public class LoginListener implements Runnable {

	//Webservice
	//private ViewService service;
	private String namespace, url; 
	private EditText username, password;
	private Context context;

	/**
	 * Creates a new {@link Runnable} which performs the login,
	 * if username and password is saved on the phone, the constructor will automatically login;
	 * @param username {@link EditText} where user types in the username
	 * @param password {@link EditText} where user types in the password
	 * @param context {@link Context} used for Android framework actions 
	 */
	public LoginListener(EditText username, EditText password, Context context){
		//Init
		this.context = context;
		this.password = password;
		this.username = username;
		this.password = password;

		//Reading saved data
		try {
			namespace = FileManager.readPath(context, "Input Options.Webservice Namespace");
			url = FileManager.readPath(context, "Input Options.Webservice URL");
			String usernameAndPassword = FileManager.readPath(context, "DMR Report.Security Options");
			
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

	/**
	 * This method will login to the webservice,
	 * and start the {@link DailyMorningReport} 
	 * @param username The typed in username
	 * @param password The typed in password
	 */
	private void login(String username, String password) {		
		//Saving username and password
		try {
			if(Boolean.valueOf(FileManager.readPath(context, "DMR Report.Security Options.Remember Login\nCredentials"))){
				if(!username.equalsIgnoreCase("") && !password.equalsIgnoreCase("")){
					FileManager.writePath(context, "DMR Report.Security Options", username + "¤#@#¤" + password);		
					Log.d("tieto", "Writing username and password");			
				}
			}else{
				Log.d("tieto", "Not writing username and password");
			}
		} catch (IOException e) {
			FileManager.writePath(context, "DMR Report.Security Options.Remember Login\nCredentials", "true");
			login(username, password);
			e.printStackTrace();
		}
		
		//Starting new intent
		Intent intent = new Intent(context, DailyMorningReport.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		intent.putExtra("namespace", namespace);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	/**
	 * This method will check the given username and password against the webservice
	 * and login if successful
	 */
	public void run() {
		try {
			//Reading new url and namespace if it is changed
			namespace = FileManager.readPath(context, "Input Options.Webservice Namespace");
			url = FileManager.readPath(context, "Input Options.Webservice URL");
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
			InfoDialog.showInfoDialog(context, "if this is the first startup, please go to \nmenu->Options\n and set up url and namespace");
		}
	}
}
