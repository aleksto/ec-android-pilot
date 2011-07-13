package com.tieto.ec.activities;

import com.tieto.R;
import com.tieto.ec.listeners.login.ExitListener;
import com.tieto.ec.listeners.login.LoginListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;	
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LogIn extends Activity{

	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	//Super
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.login);
    	
    	//Init
    	handler = new Handler(Looper.getMainLooper());
    	
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
    	
    	usernameLabel.setTextColor(Color.BLACK);
    	passwordLabel.setTextColor(Color.BLACK);
    	username.setTextColor(Color.BLACK);
    	password.setTextColor(Color.BLACK);
    	username.setBackgroundResource(R.drawable.border);
    	password.setBackgroundResource(R.drawable.border);
    	
    	//Buttons
    	Button login = (Button) findViewById(R.id.login);
    	Button exit = (Button) findViewById(R.id.exit);
    	
    	login.setOnClickListener(new LoginListener(username, password, this));
    	exit.setOnClickListener(new ExitListener(this));
    }
	
	@Override
	protected void onRestart() {
		super.onResume();
		onBackPressed();
	}
	
	public void toastFromOtherThreads(final String msg){
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(LogIn.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
