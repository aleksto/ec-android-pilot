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
	
	/**
	 * In this class we use a thread to show a loading (process dialog) for heavy calculations.
	 * @param context
	 */
	public LoadingDialog(Context context) {		
		//Init
		this.context = context;
	}

	/**
	 * The thread where the process dialog screen is made and shown. There is also used a Looper 
	 * which checks if this thread has any runnables in the que
	 */
	public void run() {
		Looper.prepare();
		
		//Init
		handler = new Handler(Looper.myLooper());
		dialog = new ProgressDialog(context);		
		
		//Dialog
		dialog.setTitle("Loading report");
		dialog.setMessage("Please wait....");
		dialog.show();
		
		Looper.loop();
	}
	
	/**
	 * This method starts the thread where the process dialog screen is shown. 
	 */
	public void show(){
		//Thread
		thread = new Thread(this);
		thread.start();	
	}
	
	/**
	 * This method can be called when the process dialog needs to be dismissed, in the 
	 * end of a heavy calculation/process.
	 */
	public void hide(){
		handler.post(new Runnable() {
			public void run() {
				dialog.dismiss();
			}
		});
	}
}
