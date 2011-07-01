package com.tieto.ec.listeners.main.pickObjectIDDialog;

import com.tieto.ec.activities.Main;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class OkListener implements OnClickListener{

	private Main main;
	private EditText objectIDField;
	private Dialog parent;
	
	public OkListener(Main main, EditText objectIDField, Dialog parent) {
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
