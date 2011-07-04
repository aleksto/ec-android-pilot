package com.tieto.ec.listeners.login;

import com.tieto.ec.activities.LogIn;
import com.tieto.ec.activities.Main;
import com.tieto.ec.webServices.PwelDayStatusService;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginListener implements OnClickListener, Runnable{

	//Thread
	private Thread thread;

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
	}

	public void onClick(View v){
		thread = new Thread(this);
		thread.start();
	}

	public void run(){
		
		/*
		service = new PwelDayStatusService(username.getText().toString(), password.getText().toString(), namespace, url);
		
		try{
			service.findByPK("", "");
			login.toastFromOtherThreads("Not valid username/password");
		}catch(java.lang.NullPointerException e){
			loggin(username.getText().toString(), password.getText().toString());
		}
		*/
		login("sysadmin", "sysadmin");
	}

	private void login(String username, String password) {
		Intent intent = new Intent(login, Main.class);
		intent.putExtra("username", username);
		intent.putExtra("password", password);
		login.startActivity(intent);
	}
}