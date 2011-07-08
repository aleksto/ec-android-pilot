package com.tieto.ec.listeners.main.pickObjectIDDialog;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class CancelListener implements OnClickListener{

	private Dialog parent;
	
	public CancelListener(Dialog parent) {
		this.parent = parent;
	}

	public void onClick(View v) {
		parent.hide();
	}
}
