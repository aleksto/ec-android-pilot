package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.gui.dialogs.OptionDialog;

import android.view.View;
import android.view.View.OnClickListener;

public class ExitOptionTree implements OnClickListener {

	
	private OptionDialog optionDialog;

	public ExitOptionTree(OptionDialog optionDialog) {
		this.optionDialog = optionDialog;
	}

	public void onClick(View v) {
		dismissParent(optionDialog);
	}

	private void dismissParent(OptionDialog dialog) {
		if(dialog.hasParent()){
			dismissParent(dialog.getParent()) ;
		}
		dialog.dismiss();
	}
}
