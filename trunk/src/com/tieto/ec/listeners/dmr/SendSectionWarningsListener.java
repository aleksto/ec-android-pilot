package com.tieto.ec.listeners.dmr;

import java.util.HashMap;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dialogs.ChooseSectionsToSendDialog;
import com.tieto.ec.gui.dialogs.ChooseSectionsToSendDialog.SectionBoxState;
import com.tieto.ec.logic.InitiateWarningText;
import com.tieto.ec.model.SectionWarning;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SendSectionWarningsListener implements OnClickListener {

	private DailyMorningReport dailyMorningReport;
	private HashMap<String, SectionBoxState> showSection;
	private ChooseSectionsToSendDialog chooseSectionsToSendDialog;
	private StringBuilder body = new StringBuilder();


	/**
	 * This class makes a new intent where the user is able to send the section warnings as a mail. 
	 * @param chooseSectionsToSendDialog
	 * @param dailyMorningReport
	 * @param showSection
	 */
	public SendSectionWarningsListener(ChooseSectionsToSendDialog chooseSectionsToSendDialog, DailyMorningReport dailyMorningReport, HashMap<String, SectionBoxState> showSection){
		this.chooseSectionsToSendDialog = chooseSectionsToSendDialog;
		this.dailyMorningReport = dailyMorningReport;
		this.showSection = showSection;
		
	}
	
	/**
	 * When the user presses the "Ok" button in the {@link ChooseSectionsToSendDialog} this method will check which check boxes are selected in the options,
	 * and further on include these sections in the email intent. 
	 */
	public void onClick(View v) {
		boolean show = false;
		body.append("MESSAGE:");

		for (SectionWarning sectionWarning : dailyMorningReport.getWarnings()) {
			try{
				Log.d("tieto", "Show Section: " + sectionWarning.getSectionTitle() + ": " + showSection.get(sectionWarning.getSectionTitle()));
				if(showSection.get(sectionWarning.getSectionTitle()) == SectionBoxState.Checked){
					//Adding section title to each section
					body.append("\n \n \n" + sectionWarning.getSectionTitle().toUpperCase() + "\n \n");
					
					//Getting sectionWarning comments
					InitiateWarningText initiateWarningText = new InitiateWarningText(sectionWarning);
					body.append(initiateWarningText.getInfo());
					show = true;
				}	
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
			
		
		if(show){
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"Energy.Components@tieto.com"});
			i.putExtra(Intent.EXTRA_SUBJECT, "Energy Components: Warning Raport");
			i.putExtra(Intent.EXTRA_TEXT, body.toString());
			try {
				dailyMorningReport.startActivity(Intent.createChooser(i, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
			    Toast.makeText(dailyMorningReport, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
			chooseSectionsToSendDialog.dismiss();
		}
		else{
			Toast toast = Toast.makeText(dailyMorningReport, "Please select atleast one section", Toast.LENGTH_LONG);
			toast.show();
		}

	}

}
