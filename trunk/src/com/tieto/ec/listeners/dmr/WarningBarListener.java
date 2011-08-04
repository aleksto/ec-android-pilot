package com.tieto.ec.listeners.dmr;

import android.view.View;
import android.view.View.OnClickListener;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.InfoDialog;
import com.tieto.ec.logic.SectionWarning;
import com.tieto.ec.logic.Warning;
import com.tieto.ec.logic.Warning.Type;

public class WarningBarListener implements OnClickListener{

	private final DailyMorningReport dmr;
	private StringBuilder info;

	public WarningBarListener(DailyMorningReport dmr, SectionWarning sectionWarning){
		this.dmr = dmr;
		info = new StringBuilder();
		
		
		boolean first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			if(warning.getType() == Type.CRITICAL && first){
				info.append("Critical:\n");
				info.append("- " + warning.getComment() + "\n\n");
				first = false;
			}else if(warning.getType() == Type.CRITICAL){
				info.append("- " + warning.getComment() + "\n\n");
			}
		}

		
		first = true;
		for (Warning warning : sectionWarning.getWarnings()) {
			if(warning.getType() == Type.WARNING && first){
				info.append("\nWarning:\n");
				info.append("- " + warning.getComment() + "\n\n");
				first = false;
			}else if(warning.getType() == Type.WARNING){
				info.append("- " + warning.getComment() + "\n\n");
			}
		}
	}

	public void onClick(View v) {
		InfoDialog.showInfoDialog(dmr, info.toString());
	}
}
