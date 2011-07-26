package com.tieto.ec.listeners.dialogs;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.FileManager;


public class DialogActionListener implements OnClickListener, OnCheckedChangeListener {

	private Dialog nextState;
	private OptionDialog dialog;
	private boolean goBack;
	private String optionTitle;
	private String path;

	public DialogActionListener(OptionDialog dialog, boolean goBack, String optionTitle, Dialog nextState) {
		this.dialog = dialog;
		this.goBack = goBack;
		
		this.optionTitle = optionTitle;
		path = dialog.getPath();
		if(!goBack){
			this.nextState = nextState;			
		}
	}

	public void onClick(View v) {
		if(goBack){
			FileManager.writePath(dialog.getContext(), path, optionTitle);
			Log.d("tieto", "Writing value: " + optionTitle + " to path: " + path);
			dialog.dismiss();
		}
		else{
			nextState.show();
		}	
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean value) {
		Log.d("tieto", "Writing value: " + value + " to path: " + path + "." + optionTitle);
		FileManager.writePath(dialog.getContext(), path + "." + optionTitle, Boolean.toString(value));
	}

}
