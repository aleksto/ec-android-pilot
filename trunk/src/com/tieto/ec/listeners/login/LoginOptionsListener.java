package com.tieto.ec.listeners.login;

import android.app.Dialog;
import android.content.Context;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;

import com.tieto.ec.enums.OptionRowType;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.OptionDialog;

public class LoginOptionsListener implements OnMenuItemClickListener {


	private OptionDialog dialog;

	/**
	 * Creates a new {@link OnMenuItemClickListener} for a {@link View}
	 * This listener will display a {@link Dialog} where user can type in
	 * webservice url and namespace.
	 * @param context {@link Context} used for Android framework actions
	 */
	public LoginOptionsListener(Context context){
		dialog = new OptionDialog(context, OptionTitle.InputOptions);
		dialog.addOptionRow(OptionTitle.WebserviceURL, OptionRowType.EDIT_BUTTON);
		dialog.addOptionRow(OptionTitle.WebserviceNamespace, OptionRowType.EDIT_BUTTON);
	}

	/**
	 * Displays the dialog
	 */
	public boolean onMenuItemClick(MenuItem arg0) {
		dialog.show();
		return false;
	}
}
