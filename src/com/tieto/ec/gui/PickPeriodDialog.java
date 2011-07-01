package com.tieto.ec.gui;

import com.tieto.ec.activities.Main;
import com.tieto.ec.listeners.main.pickPeriodDialog.CancelListener;
import com.tieto.ec.listeners.main.pickPeriodDialog.OkListener;

import android.app.Dialog;
import android.view.Surface;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;

public class PickPeriodDialog extends Dialog{

	public PickPeriodDialog(Main main) {
		super(main);
		TableLayout layout = new TableLayout(main);
		TableRow row1 = new TableRow(main);
		TableRow row2 = new TableRow(main);
		
		DatePicker fromDate = new DatePicker(main);
		DatePicker toDate = new DatePicker(main);
		Button okButton = new Button(main);
		Button cancelButton = new Button(main);
		
		setContentView(layout);
		setTitle("Choose Period");

		okButton.setText("Ok");
		cancelButton.setText("Cancel");

		int rotation = main.getWindowManager().getDefaultDisplay().getRotation();
		if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
			layout.addView(fromDate);
			layout.addView(toDate);
			layout.addView(okButton);
			layout.addView(cancelButton);
		}else{
			layout.addView(row1);
			layout.addView(row2);
			
			row1.addView(fromDate);
			row1.addView(toDate);
			row2.addView(okButton);
			row2.addView(cancelButton);
		}
		
		
		okButton.setOnClickListener(new OkListener(main, fromDate, toDate, this));
		cancelButton.setOnClickListener(new CancelListener(this));
	}

}
