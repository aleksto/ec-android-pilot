package com.tieto.ec.listeners.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.kobjects.base64.Base64;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.ec.prod.android.pilot.client.AndroidViewServiceMarshalled;
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

	/**
	 * Creates a new {@link Runnable} which performs the login,
	 * if username and password is saved on the phone, the constructor will automatically login;
	 * @param username {@link EditText} where user types in the username
	 * @param password {@link EditText} where user types in the password
	 * @param context {@link Context} used for Android framework actions 
	 */
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

	/**
	 * This method will login to the webservice,
	 * and start the {@link DailyMorningReport} 
	 * @param username The typed in username
	 * @param password The typed in password
	 */
	private void login(String username, String password) {		
		//Saving username and password
		try {
			if(Boolean.valueOf(FileManager.readPath(login, "DMR Report.Security Options.Remember Login\nCredentials"))){
				if(!username.equalsIgnoreCase("") && !password.equalsIgnoreCase("")){
					FileManager.writePath(login, "DMR Report.Security Options", username + "¤#@#¤" + password);		
				}
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
		intent.putExtra("namespace", getNamespace(url, username, password));
		intent.putExtra("url", url);
		login.startActivity(intent);
	}

	/**
	 * This method will check the given username and password against the webservice
	 * and login if successful
	 */
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
				if(validUsernameAndPassword(username.getText().toString(), password.getText().toString())){
					login(username.getText().toString(), password.getText().toString());					
				}else{
					checkConnectionFaultReason();
				}
			}
		}else{
			//URL and namespace is not defined
			InfoDialog.showInfoDialog(login, "if this is the first startup, please go to \nmenu->Options\n and set up url and namespace");
		}
	}

	private void checkConnectionFaultReason() {
		try {
			URL urlObject = new URL(url);
			URLConnection connection = urlObject.openConnection();
			if(connection.getHeaderField(0).contains("404")){
				login.toastFromOtherThreads("Webservice not found");
			}else if(connection.getHeaderField(0).contains("401")){
				login.toastFromOtherThreads("Username and/or password not valid");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean validUsernameAndPassword(String username, String password) {
		AndroidViewServiceMarshalled webservice = new AndroidViewServiceMarshalled(username, password, getNamespace(url, username, password), url);
		List<String> sections = webservice.getSections();
		return sections != null;
	}

	private String getNamespace(String url, String username, String password) {
		try {
			URL urlObj = new URL(url);
			URLConnection connection = urlObj.openConnection();
			String userpass = username + ":" + password;
			String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes()));
			connection.setRequestProperty ("Authorization", basicAuth);	
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String read = "";
			while((read = in.readLine()) != null && !read.contains("targetNamespace")){
				Log.d("tieto", "Read line: " + read);
			}
			
			String split = read.split("targetNamespace='")[1];
			return split.substring(0, split.indexOf("'"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "GETTING NAMESPACE FAILED";
	}
}
