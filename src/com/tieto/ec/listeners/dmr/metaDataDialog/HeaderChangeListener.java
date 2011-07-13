package com.tieto.ec.listeners.dmr.metaDataDialog;

import java.io.IOException;

import com.tieto.ec.logic.FileManager;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class HeaderChangeListener implements OnCheckedChangeListener{

	private Context context;
	private String title, header;
	
	public HeaderChangeListener(Context context, String title, String header) {
		this.context = context;
		this.title = title;
		this.header = header;
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		try {
			String headers = FileManager.readPath(context, "com.tieto.ec.tables." + title);
			
			if(isChecked && !headers.contains(header)){
				headers = headers + header + "#@#";					
			}else if(!isChecked){
				headers = headers.replace(header + "#@#", "");
			}
			FileManager.writePath(context, "com.tieto.ec.tables." + title, headers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
