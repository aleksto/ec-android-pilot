package com.tieto.ec.listeners;


import com.tieto.ec.activities.LogIn;
import com.tieto.ec.webServices.PwelDayStatusService;

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
		namespace = "http://pweldaystatus.service.generated.ws.frmw.ec.com/";
		url = "http://wv001927.eu.tieto.com/com.ec.frmw.ws.generated/PwelDayStatusService?wsdl";
	}

	public void onClick(View v){
		thread = new Thread(this);
		thread.start();
	}

	public void run(){
		service = new PwelDayStatusService(username.getText().toString(), password.getText().toString(), namespace, url);
//		if(service.validate(username.getText().toString(), password.getText().toString())){
//			login.toastFromOtherThreads("True");			
//		}else{
//			login.toastFromOtherThreads("False");
//		}
	}
}
