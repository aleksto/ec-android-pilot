package com.tieto.ec.listeners.login;

import java.io.IOException;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.activities.LogIn;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.webServices.PwelDayStatusService;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginListener implements OnClickListener {

	//Webservice
	private PwelDayStatusService service;
	private String namespace, url; 
	private EditText username, password;
	private LogIn login;

	public LoginListener(EditText username, EditText password, LogIn login){
		//Init
		this.login = login;
		this.password = password;
		this.username = username;
		namespace = "http://pweldaystatuswsp.service.generated.ws.frmw.ec.com/";
		url = "http://wv001927.eu.tieto.com/com.ec.frmw.ws.generated/PwelDayStatusWspService?";
		
		try {
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
		service = new PwelDayStatusService(username.getText().toString(), password.getText().toString(), namespace, url);
		
		if(service.findByPK("", "") != null){
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
	}

	private void login(String username, String password) {
		Intent intent = new Intent(login, DailyMorningReport.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		intent.putExtra("namespace", namespace);
		intent.putExtra("url", url);
		login.startActivity(intent);
	}
}
