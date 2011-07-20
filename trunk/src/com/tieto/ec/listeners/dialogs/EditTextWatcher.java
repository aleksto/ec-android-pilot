package com.tieto.ec.listeners.dialogs;

import com.tieto.ec.logic.FileManager;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class EditTextWatcher implements TextWatcher {

	private final Context context;
	private final EditText editText;
	private final String path;

	public EditTextWatcher(Context context, EditText editText, String path) {
		this.context = context;
		this.editText = editText;
		this.path = path;
	}

	public void afterTextChanged(Editable arg0) {
		Log.d("tieto", "Writing text: \"" + editText.getText().toString() + "\" to path: " + path);
		FileManager.writePath(context, path, editText.getText().toString());
	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

}
