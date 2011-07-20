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

	public DialogActionListener(OptionDialog dialog, boolean goBack) {
		this.dialog = dialog;
		this.goBack = goBack;
	}

	public void onClick(View v) {
		if(goBack){
			FileManager.writePath(dialog.getContext(), dialog.getPath(), dialog.getOptionTitle());
			dialog.dismiss();
		}
		else{
			nextState = dialog.getNextState();
			nextState.show();
		}	
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean value) {
		Log.d("tieto", "Writing value: " + value + " to path: " + dialog.getPath());
		FileManager.writePath(dialog.getContext(), dialog.getPath(), Boolean.toString(value));
	}

}
