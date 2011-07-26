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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tieto.R;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.login.LoginSlider;
import com.tieto.ec.listeners.login.LoginListener;
import com.tieto.ec.listeners.login.LoginOptionsListener;
import com.tieto.ec.logic.FileManager;

public class Login extends Activity{

	private boolean quit;
	private Handler handler;
	
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
    	quit = false;
    	
    	//Image
    	ImageView image = (ImageView) findViewById(R.id.background);
    	image.setScaleType(ScaleType.CENTER_INSIDE);
    	
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
    	LoginSlider slider = new LoginSlider(this, new LoginListener(username, password, this), "Slide to login");
    	relativ.addView(slider);
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
	 * This method will set the quit state. If the state is true the method will quit in the
	 * onRestart() in this class. 
	 * @param quit
	 */
	public void setQuit(boolean quit) {
		this.quit = quit;
	}
}
