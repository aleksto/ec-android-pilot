package com.tieto.ec.gui.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class LoadingDialog implements Runnable{

	private Handler handler;
	
	private Thread thread;
	private Context context;
	private ProgressDialog dialog; 
	
	public LoadingDialog(Context context) {		
		//Init
		this.context = context;	
	}

	public void run() {
		Looper.prepare();
		
		//Init
		handler = new Handler(Looper.myLooper());
		
		//Dialog
		dialog = new ProgressDialog(context);
		dialog.setTitle("Loading Report");
		dialog.setMessage("Please Wait...");
		dialog.show();
		
		Looper.loop();
	}
	
	public void show(){
		//Thread
		thread = new Thread(this);
		thread.start();
	}
	
	public void hide(){
		handler.post(new Runnable(){
			public void run(){
				dialog.hide();
			}
		});
	}

}
