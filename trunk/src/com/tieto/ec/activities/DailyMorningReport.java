package com.tieto.ec.activities;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.client.DMRViewServiceUnmarshalled;
import com.ec.prod.android.pilot.model.Resolution;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.service.ExampleViewService;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.R;
import com.tieto.ec.enums.Webservice;
import com.tieto.ec.gui.dmr.DateRow;
import com.tieto.ec.gui.dmr.ButtonRow;
import com.tieto.ec.listeners.dmr.DmrMapButtonListener;
import com.tieto.ec.listeners.dmr.DmrOptionsButtonListener;
import com.tieto.ec.logic.ResolutionConverter;
import com.tieto.ec.logic.SectionBuilder;
import com.tieto.ec.service.EcService;

public class DailyMorningReport extends Activity{

	private Intent serviceIntent;

	private List<Section> sections;
	private ViewService webservice;
	private String username, password, namespace, url;
	private TableLayout table, main;
	private int backgroundColor, textColor, cellTextColor, cellBackgroundColor, cellBorderColor;
	private int resolution;
	private ScrollView scroll;

	private DateRow buttonRow;
	private ButtonRow subButtonRow;
	private Date date;
	private SectionBuilder sectionBuilder;
	/**
	 * Main class for the daily morning report, this is the class started when you have logged in.
	 * OnCreate is the constructor for the Super class Activity, and there all the initialization is.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Super
		super.onCreate(savedInstanceState);

		//Date
		date = new Date(System.currentTimeMillis());
		date.setDate(date.getDate()-1);

		//Init
		main = new TableLayout(this);
		sectionBuilder = new SectionBuilder(this);
		buttonRow = new DateRow(this);
		subButtonRow = new ButtonRow(this);
		username = getIntent().getExtras().getString(Webservice.username.toString());
		password = getIntent().getExtras().getString(Webservice.password.toString());
		namespace = getIntent().getExtras().getString(Webservice.namespace.toString());
		url = getIntent().getExtras().getString(Webservice.url.toString());

		if(url.equalsIgnoreCase("debug") && namespace.equalsIgnoreCase("debug")){
			webservice = new ExampleViewService(true);
		}else{
			webservice = new DMRViewServiceUnmarshalled(true, username, password, namespace, url);
		}

		//Service
		if(serviceIntent == null){
			restartService();			
		}

		//This
		setContentView(main);

		//Background
		scroll = new ScrollView(this);
		table = new TableLayout(this);

		//Main
		main.addView(buttonRow);
		main.addView(scroll);

		//Scroll
		scroll.addView(table);

		//Getting sections
		sections = webservice.getSections();

		//Sections Builder
		sectionBuilder.updateColors();
		sectionBuilder.listSections();
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

		MenuItem mapButton = menu.findItem(R.id.dmr_map);
		mapButton.setOnMenuItemClickListener(new DmrMapButtonListener(this));
		mapButton.setIcon(android.R.drawable.ic_menu_mapmode);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * This method is executed when the activity is resumed, and refreshes all the values in the activity
	 */
	@Override
	protected void onResume() {
		super.onResume();
		refreshWebserviceValues();
	}
	
	/**
	 * This runs when the user presses the back button on the phone,
	 * This hides the {@link ButtonRow} if visible, else it exits the application
	 */
	@Override
	public void onBackPressed() {
		if(main.getChildAt(1) == subButtonRow){
			toogleSubButtonRow();
		}else{
			super.onBackPressed();					
		}
	}
	
	/**
	 * Setting the date showed in the button row at top of activity
	 * @param date
	 */
	public void setDate(Date date){
		this.date = date;
	}

	/**
	 * Getting the date showed in the button row at top of activity
	 * @return Current Date in button row
	 */
	public Date getDate(){
		return date;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	/**
	 * Refreshes all value in the entire activity
	 */
	public void refreshWebserviceValues(){
		onCreate(null);
	}

	/**
	 * Restarts the background service, used when user sets new update interval on the service
	 */
	public void restartService() {
		if(serviceIntent == null){
			serviceIntent = new Intent(this, EcService.class);
			serviceIntent.putExtra(Webservice.username.toString(), username);
			serviceIntent.putExtra(Webservice.password.toString(), password);
			serviceIntent.putExtra(Webservice.namespace.toString(), namespace);
			serviceIntent.putExtra(Webservice.url.toString(), url);			
		}else{
			stopService(serviceIntent);
		}
		startService(serviceIntent);
	}

	/**
	 * Refreshing the sections and colors of the report
	 */
	public void refresh(){
		//Log
		Log.d("tieto", "\nDisplaying report with date: " + date);
		sectionBuilder.updateColors();
		sectionBuilder.listSections();
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
		return buttonRow.getCurrentDayLabel();
	}


	/**
	 * Toggles if the {@link ButtonRow} should be visible
	 */
	public void toogleSubButtonRow() {
		if(main.getChildAt(1) == subButtonRow){
			main.removeView(subButtonRow);
		}else{
			main.addView(subButtonRow, 1);
			subButtonRow.refreshButtonsState();
			subButtonRow.setBackgroundColor(backgroundColor);
		}
	}


	/**
	 * Sets the {@link Resolution} for the report
	 * @param resolution {@link Resolution}
	 */
	public void setResolution(int resolution) {
		Log.d("tieto", "Setting resolution: " + ResolutionConverter.convert(resolution));
		this.resolution = resolution;
	}
	
	/**
	 * @return The {@link Resolution} for the report
	 */

	public int getResolution() {
		return resolution;
	}
}
