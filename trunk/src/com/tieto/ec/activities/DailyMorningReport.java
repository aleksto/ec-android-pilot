package com.tieto.ec.activities;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;
import com.ec.prod.android.pilot.model.Resolution;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.service.ExampleViewService;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.R;
import com.tieto.ec.enums.OptionTitle;
import com.tieto.ec.enums.Webservice;
import com.tieto.ec.gui.dialogs.LoadingDialog;
import com.tieto.ec.gui.dmr.ButtonRow;
import com.tieto.ec.gui.dmr.DateRow;
import com.tieto.ec.listeners.dmr.DmrOptionsButtonListener;
import com.tieto.ec.listeners.dmr.DmrWarningButtonListener;
import com.tieto.ec.listeners.dmr.SendWarningsByMailListener;
import com.tieto.ec.logic.DateConverter;
import com.tieto.ec.logic.DateIntervalCalculator;
import com.tieto.ec.logic.FileManager;
import com.tieto.ec.logic.SectionBuilder;
import com.tieto.ec.logic.SectionSaver;
import com.tieto.ec.logic.WarningChecker;
import com.tieto.ec.logic.DateConverter.Type;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.service.EcService;

public class DailyMorningReport extends Activity{

	private Intent serviceIntent;

	private List<Section> sections;
	private String username, password, namespace, url;
	private TableLayout table, main;
	private int backgroundColor, textColor, cellTextColor, cellBackgroundColor, cellBorderColor;
	private int resolution;
	private ScrollView scroll;
	private Date toDate, fromDate;

	private ViewService webservice;
	private DateRow dateRow;
	private ButtonRow buttonRow;
	private SectionBuilder sectionBuilder;
	private WarningChecker warningChecker;
	private SectionSaver saveManager;
	private Dialog warningDialog;
	private LoadingDialog progressDialog;

	private List<SectionWarning> warnings;

	private TranslateAnimation animation;

	public List<SectionWarning> getWarnings(){
		return this.warnings;
	}

	/**
	 * Main class for the daily morning report, this is the class started when you have logged in.
	 * OnCreate is the constructor for the Super class Activity, and there all the initialization is.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);
		
		//Log
		Log.d("tieto", "onCreate");
		
		//EXTRAS
		Bundle extras = getIntent().getExtras();
		username = extras.getString(Webservice.username.toString());
		password = extras.getString(Webservice.password.toString());
		namespace = extras.getString(Webservice.namespace.toString());
		url = extras.getString(Webservice.url.toString());
		
		//Optionally extras
		if (extras.containsKey("toDate")) {
			toDate = DateConverter.parse(extras.getString("toDate"), Type.DATE);
		}else{
			toDate = new Date(System.currentTimeMillis());
			toDate.setDate(toDate.getDate()-1);
		}
		if(extras.containsKey("resolution")){
			resolution = extras.getInt("resolution");
		}else{
			try {
				this.resolution = Integer.valueOf(FileManager.readPath(this, "DMR Report.Resolution"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("tieto", "Setting default resolution");
				this.resolution = Resolution.DAILY;
				FileManager.writePath(this, "DMR Report.Resolution", Resolution.DAILY+"");
				e.printStackTrace();
			}
		}
		
		//Exit service
		serviceIntent = new Intent(this, EcService.class);
		serviceIntent.putExtra(Webservice.username.toString(), username);
		serviceIntent.putExtra(Webservice.password.toString(), password);
		serviceIntent.putExtra(Webservice.namespace.toString(), namespace);
		serviceIntent.putExtra(Webservice.url.toString(), url);	
		if(serviceIntent != null){
			stopService(serviceIntent);
		}


		
		//Init 
		if(url.equalsIgnoreCase("debug")){
			Log.d("tieto", "Starting report with example view service");
			webservice = new ExampleViewService();
		}else{
			Log.d("tieto", "Starting report with webservice");
			webservice = new DMRViewServiceUnmarshalled(username, password, namespace, url);
		}

		table = new TableLayout(this);
		sectionBuilder = new SectionBuilder(this);
		saveManager = new SectionSaver(this);
		dateRow = new DateRow(this, resolution);
		warningChecker = new WarningChecker(saveManager, resolution);
		scroll = new ScrollView(this);
		main = new TableLayout(this);
		buttonRow = new ButtonRow(this);
		progressDialog = new LoadingDialog(this);
		

		//Getting sections
		sections = webservice.getSections();
		
		//Starting first screen dialog
		setToDate(toDate);
		
		//Sections Builder
		sectionBuilder.updateColors();

		//This
		setContentView(main);

		//Scroll
		scroll.addView(table);

		//Animation
		animation = new TranslateAnimation(0, 0, 0, 0, 0, -50, 0, 0);
		animation.setDuration(1000);
		dateRow.setAnimation(animation);
		
		//Main
		main.addView(dateRow);
		main.addView(scroll);
		main.setBackgroundColor(backgroundColor);
	}

	/**
	 * This method is executed when the user presses the menu button on the phone, and it builds up the menu,
	 * and adds onClick listeners to the menu buttons
	 * @param Menu menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.dmr_menu, menu);
		
		MenuItem optionButton = menu.findItem(R.id.dmr_options);
		optionButton.setOnMenuItemClickListener(new DmrOptionsButtonListener(this));
		optionButton.setIcon(android.R.drawable.ic_menu_manage);

		MenuItem warningsButton = menu.findItem(R.id.dmr_status);
		warningsButton.setOnMenuItemClickListener(new DmrWarningButtonListener(this));
		warningsButton.setIcon(android.R.drawable.ic_dialog_alert);
		
		MenuItem sendButton = menu.findItem(R.id.dmr_send_warnings);
		sendButton.setOnMenuItemClickListener(new SendWarningsByMailListener(this));
		sendButton.setIcon(android.R.drawable.ic_menu_send);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * This runs when the user presses the back button on the phone,
	 * This hides the {@link ButtonRow} if visible, else it exits the application
	 */
	@Override
	public void onBackPressed() {
		//Log
		Log.d("tieto", "onBackPressed");
		
		if(main.getChildAt(1) == buttonRow){
			toogleSubButtonRow();
		}else{
			//Service
			restartService();
			if(!animation.hasEnded()){
				animation.cancel();
			}
			super.onBackPressed();					
		}
	}

	@Override
	protected void onDestroy() {
		//Super
		super.onDestroy();
		
		//Log
		Log.d("tieto", "onDestroy");
	}
	
	@Override
	protected void onResume() {
		//Super
		super.onResume();
		
		//Log
		Log.d("tieto", "onResume");
	}
	
	/**
	 * Setting the date showed in the button row at top of activity
	 * @param date
	 */
	public void setToDate(Date date){
		this.toDate = date;
		this.fromDate = DateIntervalCalculator.calcFromDate(toDate, resolution);
		refresh(true);
		
		//WarningChecker
		refreshWarningDialog();
	}

	/**
	 * Creates a {@link Dialog} which show the warnings for each section
	 */
	private void refreshWarningDialog() {
		warnings = warningChecker.checkForWarnings(fromDate, toDate);
		if(warnings.size() > 0){
			warningDialog = warningChecker.createWarningDialog(this, warnings);
		}
		
		//Checking if the user want to automatically display warning dialog
		try {
			if(Boolean.valueOf(FileManager.readPath(this, OptionTitle.Options + "." + OptionTitle.DisplayWarnings))){
				warningDialog.show();			
			}
		} catch (IOException e) {
			FileManager.writePath(this, OptionTitle.Options + "." + OptionTitle.DisplayWarnings, "true");
			setToDate(toDate);
			e.printStackTrace();
		}
	}

	/**
	 * Getting the date showed in the button row at top of activity
	 * @return Current Date in button row
	 */
	public Date getToDate(){
		return toDate;
	}
	
	/**
	 * Sets the from date {@link Date} for the report
	 * @param fromDate
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	/**
	 * @return The from date {@link Date} for this report
	 */
	public Date getFromDate(){
		return fromDate;
	}

	/**
	 * Restarts the background service, used when user sets new update interval on the service
	 */
	public void restartService() {
//		stopService(serviceIntent);
		startService(serviceIntent);
	}

	/**
	 * Refreshing the sections and colors of the report
	 */
	public void refresh(boolean newWebserviceValues){
		progressDialog.show();
		sectionBuilder.updateColors();
		sectionBuilder.listSections(newWebserviceValues);
		progressDialog.hide();
	}

	/**
	 * Sets the background color of the report, but doesn't update the UI {@link refresh()} 
	 * @param backgroundColor
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the background color of the report {@link Color}
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the text color of the report, but doesn't update the UI {@link refresh()}
	 * @param backgroundColor
	 */
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	/**
	 * @return the text color of the report {@link Color}
	 */
	public int getTextColor() {
		return textColor;
	}

	/**
	 * Sets the cell text color of the report, but doesn't update the UI {@link refresh()} 
	 * @param backgroundColor
	 */
	public void setCellTextColor(int cellTextColor) {
		this.cellTextColor = cellTextColor;
	}

	/**
	 * @return the cell text color of the report {@link Color}
	 */
	public int getCellTextColor() {
		return cellTextColor;
	}

	/**
	 * Sets the cell background color of the report, but doesn't update the UI {@link refresh()} 
	 * @param backgroundColor
	 */
	public void setCellBackgroundColor(int cellBackgroundColor) {
		this.cellBackgroundColor = cellBackgroundColor;
	}

	/**
	 * @return the cell background color of the report {@link Color}
	 */
	public int getCellBackgroundColor() {
		return cellBackgroundColor;
	}

	/**
	 * Sets the cell border color of the report, but doesn't update the UI {@link refresh()} 
	 * @param backgroundColor
	 */
	public void setCellBorderColor(int cellBorderColor) {
		this.cellBorderColor = cellBorderColor;
	}

	/**
	 * @return the cell border color of the report {@link Color}
	 */
	public int getCellBorderColor() {
		return cellBorderColor;
	}

	/**
	 * @return the table {@link TableLayout} for the report, this is used for adding each section
	 */
	public TableLayout getTable(){
		return table;
	}

	/**
	 * @return The main {@link TableLayout} containing one {@link DateRow} and the rest off the report
	 * This is the root layout
	 */
	public TableLayout getMainTable(){
		return main;
	}

	/**
	 * @return the scrollView {@link ScrollView} of the report, used in {@link SectionBuilder} to set color 
	 */
	public ScrollView getScrollView(){
		return scroll;
	}

	/**
	 * @return The webservice instance, used in {@link SectionBuilder} to build the section UI.
	 */
	public ViewService getWebservice(){
		return webservice;
	}

	/**
	 * @return The sections, used in {@link SectionBuilder} to build the section UI.
	 */
	public List<Section> getSections() {
		return sections;
	}

	/**
	 * @return The Currentday Label {@link TextView}
	 */
	public TextView getCurrentdayLabel(){
		return dateRow.getCurrentDayLabel();
	}

	/**
	 * Toggles if the {@link ButtonRow} should be visible
	 */
	public void toogleSubButtonRow() {
		if(main.getChildAt(1) == buttonRow){
			main.removeView(buttonRow);
		}else{
			buttonRow.setBackgroundColor(backgroundColor);
			buttonRow.refreshButtonsState();
			main.addView(buttonRow, 1);
		}
	}

	/**
	 * Sets the {@link Resolution} for the report
	 * @param resolution {@link Resolution}
	 */
	public void setResolution(int resolution) {
		this.resolution = resolution;
		refreshWarningDialog();
		refresh(true);
		dateRow.updateTitle(resolution);
	}
	
	/**
	 * @return The {@link Resolution} for the report
	 */
	public int getResolution() {
		return resolution;
	}

	/**
	 * @return The warning dialog
	 */
	public Dialog getWarningDialog() {
		return warningDialog;
	}

	/**
	 * @return A class responsible for holding save data 
	 */
	public SectionSaver getSaveManager(){
		return saveManager;
	}
	
	
}
