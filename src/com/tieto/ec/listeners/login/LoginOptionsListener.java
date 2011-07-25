package com.tieto.ec.listeners.login;

import android.content.Context;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.gui.dialogs.OptionDialog;

public class LoginOptionsListener implements OnMenuItemClickListener {

	private Context context;
	
	public LoginOptionsListener(Context context){
		this.context = context;
	}
	
	public boolean onMenuItemClick(MenuItem arg0) {
		// Root
		OptionDialog dialog = new OptionDialog(context, "Input Options");
		dialog.addOption("Webservice URL", OptionRowType.EDIT_BUTTON);
		dialog.addOption("Webservice Namespace", OptionRowType.EDIT_BUTTON);
		
		// Dialog
		dialog.show();
		return false;
	}
}
