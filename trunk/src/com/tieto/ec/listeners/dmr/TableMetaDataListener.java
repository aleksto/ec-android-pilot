package com.tieto.ec.listeners.dmr;

import com.tieto.ec.gui.TableMetaDataDialog;
import com.tieto.frmw.model.TableData;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class TableMetaDataListener implements OnClickListener{

	private TableMetaDataDialog dialog;
	
	public TableMetaDataListener(Context context, String title, TableData data){
		dialog = new TableMetaDataDialog(context, title, data);
	}
	
	public void onClick(View v) {
		dialog.show();
	}
}
