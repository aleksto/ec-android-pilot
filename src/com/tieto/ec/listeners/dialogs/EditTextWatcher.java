package com.tieto.ec.listeners.dialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.tieto.ec.logic.FileManager;

public class EditTextWatcher implements TextWatcher {

	private final Context context;
	private final EditText editText;
	private final String path;

	/**
	 * Creates a new {@link TextWatcher} for a given {@link EditText}
	 * @param context {@link Context} needed for Android framework actions
	 * @param editText Given {@link EditText} for getting the text
	 * @param path {@link String} path for the file 
	 */
	public EditTextWatcher(Context context, EditText editText, String path) {
		this.context = context;
		this.editText = editText;
		this.path = path;
	}

	/**
	 * Called after text is changed in given {@link EditText}
	 * This method is writing the text to given path
	 */
	public void afterTextChanged(Editable arg0) {
		Log.d("tieto", "Writing text: \"" + editText.getText().toString() + "\" to path: " + path);
		FileManager.writePath(context, path, editText.getText().toString());
	}

	/**
	 * Not used
	 */
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

	/**
	 * Not used
	 */
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

}
