package com.tieto.ec.gui.dialogs;

import java.io.IOException;

import com.tieto.ec.listeners.dialogs.CancelListener;
import com.tieto.ec.listeners.dialogs.EditTextWatcher;
import com.tieto.ec.listeners.dialogs.OkListener;
import com.tieto.ec.logic.FileManager;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;

public class EditTextDialog extends Dialog{

	public EditTextDialog(Context context, OptionDialog optionDialog, String basePath, String title) {
		//Super
		super(context);
		
		//Init
		ScrollView scroll = new ScrollView(context);
		TableLayout table = new TableLayout(context);
		EditText editText = new EditText(context);
		Button ok = new Button(context);
		Button cancel = new Button(context);
		
		//this
		setTitle(title);
		setContentView(scroll);
		
		//Childs
		scroll.addView(table);
		table.addView(editText);
		table.addView(ok);
		table.addView(cancel);
		
		//Text
		ok.setText("Ok");
		cancel.setText("Cancel");
		try {
			editText.setText(FileManager.readPath(context, basePath + "." + title));
		} catch (IOException e) {
			editText.setText("http://");
			e.printStackTrace();
		}
		
		//Listeners
		editText.addTextChangedListener(new EditTextWatcher(context, editText, basePath + "." + title));
		ok.setOnClickListener(new OkListener(this, optionDialog));
		cancel.setOnClickListener(new CancelListener(this, context, basePath, title));
	}
}
