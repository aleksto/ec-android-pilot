package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class DialogActionListener implements OnClickListener, OnCheckedChangeListener {

	private Dialog nextState;
	private OptionDialog dialog;
	private boolean goBack;
	private String optionTitle;
	private String path;

	public DialogActionListener(OptionDialog dialog, boolean goBack) {
		this.dialog = dialog;
		this.goBack = goBack;
		
		optionTitle = dialog.getOptionTitle();
		path = dialog.getPath();
		if(!goBack){
			nextState = dialog.getNextState();			
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
