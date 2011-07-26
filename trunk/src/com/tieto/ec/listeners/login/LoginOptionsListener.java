package com.tieto.ec.listeners.login;

import android.content.Context;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.OptionDialog;

public class LoginOptionsListener implements OnMenuItemClickListener {

	private Context context;
	
	public LoginOptionsListener(Context context){
		this.context = context;
	}
	
	public boolean onMenuItemClick(MenuItem arg0) {
		// Root
		OptionDialog dialog = new OptionDialog(context, OptionTitle.InputOptions);
		dialog.addOptionRow(OptionTitle.WebserviceURL, OptionRowType.EDIT_BUTTON);
		dialog.addOptionRow(OptionTitle.WebserviceNamespace, OptionRowType.EDIT_BUTTON);
		
		// Dialog
		dialog.show();
		return false;
	}
}
