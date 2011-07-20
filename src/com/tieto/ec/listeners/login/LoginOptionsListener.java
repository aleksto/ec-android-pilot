package com.tieto.ec.listeners.login;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.gui.dialogs.OptionDialog;
import com.tieto.ec.logic.DialogSection;

import android.content.Context;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class LoginOptionsListener implements OnMenuItemClickListener {

	private Context context;
	
	public LoginOptionsListener(Context context){
		this.context = context;
	}
	
	public boolean onMenuItemClick(MenuItem arg0) {
		// Root
		DialogSection root = new DialogSection("Input Options");
		root.addOption("Webservice URL", OptionRowType.EDIT_BUTTON);
		root.addOption("Webservice Namespace", OptionRowType.EDIT_BUTTON);
		
		// Dialog
		OptionDialog dialog = new OptionDialog(context, root );
		dialog.show();
		return false;
	}
}
