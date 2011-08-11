package com.tieto.ec.listeners.dialogs;

import java.util.List;

import com.tieto.ec.gui.dialogs.XEditTextDialog;
import com.tieto.ec.logic.FileManager;

import android.view.View;
import android.view.View.OnClickListener;

public class XEditTextOkListener implements OnClickListener {

	private final XEditTextDialog xEditTextDialog;

	public XEditTextOkListener(XEditTextDialog xEditTextDialog) {
		this.xEditTextDialog = xEditTextDialog;
		
	}

	public void onClick(View v) {
		List<String> rows = xEditTextDialog.getInsertedText();
		
		String read = "";
		for (int i = 0; i < rows.size(); i++) {
			read = rows.get(i);			
			FileManager.writePath(xEditTextDialog.getContext(), xEditTextDialog.getPath() + "." + i, read);
		}
		
		xEditTextDialog.dismiss();
	}
}
