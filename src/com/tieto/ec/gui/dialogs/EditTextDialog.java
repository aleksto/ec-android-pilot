package com.tieto.ec.gui.dialogs;

import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.tieto.ec.listeners.dialogs.CancelListener;
import com.tieto.ec.listeners.dialogs.EditTextWatcher;
import com.tieto.ec.listeners.dialogs.OkListener;
import com.tieto.ec.logic.FileManager;

public class EditTextDialog extends Dialog{

	private final float BUTTON_WEIGHT = 1f;
	
	/**
	 * Creates a dialog where user can enter text. The text is stored in local memory.
	 * Context is needed for Androids framework actions. 
	 * @param context
	 * @param optionDialog
	 * @param basePath
	 * @param title
	 */
	public EditTextDialog(Context context, OptionDialog optionDialog, String basePath, String title) {
		//Super
		super(context);
		//Init
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		EditText editText = new EditText(context);
		LinearLayout buttonLayout = new LinearLayout(context);
		Button ok = new Button(context);
		Button cancel = new Button(context);
		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, BUTTON_WEIGHT);

		//this
		setTitle(title);
		setContentView(scroll);
		
		//Button Layout
		buttonLayout.setBackgroundResource(android.R.drawable.bottom_bar);
		param2.gravity = Gravity.CENTER_VERTICAL;
		
		//Childs
		scroll.addView(table);
		table.addView(editText);
		table.addView(buttonLayout, param1);
		buttonLayout.addView(ok, param2);
		buttonLayout.addView(cancel, param2);
		
	
		//Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		try {
			editText.setText(FileManager.readPath(context, basePath + "." + title));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Listeners
		editText.addTextChangedListener(new EditTextWatcher(context, editText, basePath + "." + title));
		ok.setOnClickListener(new OkListener(this, optionDialog));
		cancel.setOnClickListener(new CancelListener(this, context, basePath, title));
	}
}
