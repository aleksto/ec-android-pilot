package com.tieto.ec.listeners.dmr;

import java.io.IOException;
import java.util.HashMap;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.gui.dialogs.ChooseSectionsToSendDialog;
import com.tieto.ec.gui.dialogs.ChooseSectionsToSendDialog.SectionBoxState;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.InitiateWarningText;
import com.tieto.ec.model.SectionWarning;

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
		
		String signature;
		try {
			signature = FileManager.readPath(chooseSectionsToSendDialog.getContext(), OptionTitle.Options + "." + OptionTitle.SentOptions + "." + OptionTitle.Signature);
		} catch (IOException e1) {
			signature = "";
		}
		body.append(signature);
			
		
		if(show){
			//Read
			
			
			//Intent
			Intent sentIntent = new Intent(Intent.ACTION_SEND);
			sentIntent.setType("text/plain");
			
			//From email
			String email;
			try {
				email = FileManager.readPath(chooseSectionsToSendDialog.getContext(), OptionTitle.Options + "." + OptionTitle.SentOptions + "." + OptionTitle.StandardReceiver);
				Log.d("tieto", "TRY OK");
			} catch (IOException e) {
				Log.d("tieto", "CATCHING");
				email = "";
			}
			Log.d("tieto", "EMAIL: " + email);
			sentIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
			
			//Topic
			String topic;
			try {
				topic = FileManager.readPath(chooseSectionsToSendDialog.getContext(), OptionTitle.Options + "." + OptionTitle.SentOptions + "." + OptionTitle.StandardTopic);
			} catch (IOException e) {
				topic = "Energy Components: Warning Report";
			}
			sentIntent.putExtra(Intent.EXTRA_SUBJECT, topic);
			
			//Body
			sentIntent.putExtra(Intent.EXTRA_TEXT, body.toString());
			
			try {
				dailyMorningReport.startActivity(Intent.createChooser(sentIntent, "Send mail..."));
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
