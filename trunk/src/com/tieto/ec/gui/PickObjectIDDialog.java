package com.tieto.ec.gui;

import com.tieto.ec.activities.Main;
import com.tieto.ec.listeners.main.pickObjectIDDialog.CancelListener;
import com.tieto.ec.listeners.main.pickObjectIDDialog.OkListener;

import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

public class PickObjectIDDialog extends Dialog{

	public PickObjectIDDialog(Main main) {
		super(main);
		TableLayout layout = new TableLayout(main);
		EditText editText = new EditText(main);
		Button okButton = new Button(main);
		Button cancelButton = new Button(main);
		
		setContentView(layout);
		setTitle("Choose Object ID");

		okButton.setText("Ok");
		cancelButton.setText("Cancel");
		
		layout.addView(editText);
		layout.addView(okButton);
		layout.addView(cancelButton);
		
		okButton.setOnClickListener(new OkListener(main, editText, this));
		cancelButton.setOnClickListener(new CancelListener(this));
	}
}
