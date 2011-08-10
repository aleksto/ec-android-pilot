package com.tieto.ec.activities;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tieto.R;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.gui.login.Slider;
import com.tieto.ec.listeners.login.LoginListener;
import com.tieto.ec.listeners.login.LoginOptionsListener;
import com.tieto.ec.logic.FileManager;

public class Login extends Activity{

	private Handler handler;
	private boolean quit;
	
	/**
	 * Main class for the login, this is the class started after introduction animation
	 * OnCreate is the constructor for the Super class Activity, and where all initializations start.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	//Super
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.login);
    	
    	//Init
    	handler = new Handler(Looper.getMainLooper());
    	
    	//Log
    	boolean previousDay;
    	if(getIntent().getExtras() != null){
    		previousDay = true;
    	}else{
    		previousDay = false;
    	}
    	
    	//Image
    	ImageView image = (ImageView) findViewById(R.id.background);
    	image.setScaleType(ScaleType.CENTER_INSIDE);
    	
    	try{
    		image.setImageResource(R.drawable.tieto);    		
    	}catch(java.lang.OutOfMemoryError e){
    		toastFromOtherThreads("Not enough memory to display background image");
    	}
    	
    	//Background
    	RelativeLayout relativ = (RelativeLayout) findViewById(R.id.relativeLayout2);
    	relativ.setBackgroundColor(Color.WHITE);
    	
    	//Username & Password 
    	TextView usernameLabel = (TextView) findViewById(R.id.usernamelabel);
    	TextView passwordLabel = (TextView) findViewById(R.id.passwordlabel);
    	EditText username = (EditText) findViewById(R.id.username);
    	EditText password = (EditText) findViewById(R.id.password);
    	username.requestFocus();
    	
    	usernameLabel.setTextColor(Color.BLACK);
    	passwordLabel.setTextColor(Color.BLACK);
    	username.setTextColor(Color.BLACK);
    	password.setTextColor(Color.BLACK);
    	
    	//Login slider
    	Slider slider = new Slider(this, new LoginListener(this, username, password, previousDay), "Slide to login");
    	relativ.addView(slider);
    	
    	//Animation
    	TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 0, 0, 200, 0, 0);
    	animation.setDuration(2000);
    	slider.setAnimation(animation);
    	
    	//Information
    	try {
			FileManager.readPath(this, "Login.First Time");
		} catch (IOException e) {
			InfoDialog.showInfoDialog(this, "Welcome to Energy Components pilot for Android. To start, click the menu button and fill in the webservice url and namespace");
			FileManager.writePath(this, "Login.First Time", "Shown welcome message");
			e.printStackTrace();
		}
    }
	
	/**
	 * This method is executed when the user presses back in the subsequent activity. It allows the application to quit 
	 * instead of returning to the login menu (which should only be active when entering the application). 
	 */
	@Override
	protected void onRestart() {
		super.onResume();
		try {
			String exist = FileManager.readPath(this, OptionTitle.DMRReport + "." + OptionTitle.SecurityOptions);
			if(exist.equalsIgnoreCase(OptionTitle.ClearUsernameAndPassword.toString())){
				onCreate(null);	
			}
			else{
				onBackPressed();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method is executed when the user presses the menu button on the phone, and it builds up the menu,
	 * and adds onClick listeners to the menu buttons
	 * @param Menu menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.login_menu, menu);
		
		MenuItem optionButton = menu.findItem(R.id.login_option);
		optionButton.setOnMenuItemClickListener(new LoginOptionsListener(this));
		optionButton.setIcon(android.R.drawable.ic_menu_manage);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * Used in {@link LoginListener} when wrong username or password is written
	 * @param msg
	 */
	public void toastFromOtherThreads(final String msg){
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	 * This runs when the app is resumed, and this makes sure of quiting the
	 * app if the {@link TranslateAnimation} in the {@link DailyMorningReport} is running
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if(quit){
			onBackPressed();			
		}
	}

	/**
	 * If true, the {@link Login} activity will quit on resume
	 * @param quit
	 */
	public void setQuit(boolean quit) {
		this.quit = quit;
	}
}
