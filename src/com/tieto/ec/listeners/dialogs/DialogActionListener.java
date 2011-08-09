package com.tieto.ec.listeners.dialogs;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.gui.dialogs.OptionRow;
import com.tieto.ec.logic.FileManager;


public class DialogActionListener implements OnClickListener, OnCheckedChangeListener {

	private Dialog nextState;
	private OptionDialog dialog;
	private boolean goBack;
	private String optionTitle;
	private String path;
	private boolean setDefault;

	/**
	 * This Listener listens for actions in instances of {@link OptionDialog}. When a action takes place
	 * in any {@link OptionRow}s it decides the options actions and if the option tree should go one step back, 
	 * stay as it is or step forward to nextState. 
	 * @param optionRow 
	 * @param dialog
	 * @param goBack
	 * @param optionTitle
	 * @param nextState
	 * @param setDefault 
	 */
	public DialogActionListener(OptionDialog dialog, boolean goBack, String optionTitle, Dialog nextState, boolean setDefault) {
		this.dialog = dialog;
		this.goBack = goBack;
		
		this.optionTitle = optionTitle;
		this.setDefault = setDefault;
		path = dialog.getPath();
		if(!goBack){
			this.nextState = nextState;			
		}
	}

	/**
	 * This method decides the action taken when an {@link OptionRow} is pressed in an {@link OptionDialog}
	 */
	public void onClick(View v) {
		if(goBack){
			dialog.dismiss();		
			if(!setDefault){
				FileManager.writePath(dialog.getContext(), path, optionTitle);
				Log.d("tieto", "Writing value: " + optionTitle + " to path: " + path);				
			}
			else {
				dialog.setDefaultValues(dialog, path);
			}
			dialog.refresh();
		}
		else{
			nextState.show();
		}	
	}

	/**
	 * This method decides the action taken when checking a checkbox in an {@link OptionRow} inside an {@link OptionDialog}
	 */
	public void onCheckedChanged(CompoundButton buttonView, boolean value) {
		Log.d("tieto", "Writing value: " + value + " to path: " + path + "." + optionTitle);
		FileManager.writePath(dialog.getContext(), path + "." + optionTitle, Boolean.toString(value));
	}

}
