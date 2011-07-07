package com.tieto.ec.listeners.login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.activities.LogIn;
import com.tieto.ec.webServices.PwelDayStatusService;

import android.app.Activity;
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
		
		String usernameRead = readPath("com.tieto.ec.username");
		String passwordRead = readPath("com.tieto.ec.password");
		
		if(!usernameRead.equalsIgnoreCase("") && !passwordRead.equalsIgnoreCase("")){
			login(usernameRead, passwordRead);
		}
	}

	public void onClick(View v){
		service = new PwelDayStatusService(username.getText().toString(), password.getText().toString(), namespace, url);
		
		if(service.findByPK("", "") != null){
			login.toastFromOtherThreads("Not valid username/password");
		}else{
			//Writing username and password
			writePath("com.tieto.ec.username", username.getText().toString());
			writePath("com.tieto.ec.password", password.getText().toString());
			
			//Loggin inn
			login(username.getText().toString(), password.getText().toString());
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

	public void writePath(String path, String text) {
		try {
			byte[] write = text.getBytes();
			FileOutputStream ut = login.openFileOutput(path, Activity.MODE_PRIVATE);
			ut.write(write);
			ut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readPath(String path){
		try {
			FileInputStream inn = login.openFileInput(path);
			ArrayList<Byte> tallene = new ArrayList<Byte>();
			byte ch;
			while((ch = (byte) inn.read()) != -1){
				tallene.add(ch);
			}

			byte[] x = new byte[tallene.size()];
			int tempTeller = 0;
			for(Byte z: tallene){
				x[tempTeller++] = z;
			}

			String a = new String(x);
			inn.close();
			return a;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
