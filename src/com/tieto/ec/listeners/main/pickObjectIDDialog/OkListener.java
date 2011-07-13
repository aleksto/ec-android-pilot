package com.tieto.ec.listeners.main.pickObjectIDDialog;

import com.tieto.ec.activities.WellPeriod;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class OkListener implements OnClickListener{

	private WellPeriod main;
	private EditText objectIDField;
	private Dialog parent;
	
	public OkListener(WellPeriod main, EditText objectIDField, Dialog parent) {
		this.main = main;
		this.objectIDField = objectIDField;
		this.parent = parent;
	}

	
	public void onClick(View v) {
		String objectID = objectIDField.getText().toString();
		main.runWebservice(objectID, "", "");
		parent.hide();
	}

}
