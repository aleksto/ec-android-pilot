package com.tieto.ec.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.DataType;
import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Resolution;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableCell;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dmr.WarningMeter;
import com.tieto.ec.listeners.dialogs.HideDialogListener;
import com.tieto.ec.listeners.dmr.WarningMeterListener;
import com.tieto.ec.model.GraphWarning;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.CellWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class WarningChecker {

	private final ViewService viewService;
	private int resolution;

	/**
	 * Creates a new {@link WarningChecker} this class is used for checking if the {@link DailyMorningReport} contains any {@link Warning}
	 * @param dmr {@link DailyMorningReport}
	 */
	public WarningChecker(ViewService viewService, int resolution){
		this.viewService = viewService;
		this.setResolution(resolution);
	}

	/**
	 * Creates a {@link Dialog} displaying each section with a warning
	 * @param sectionWarnings A list containing the warnings
	 * @return A new {@link Dialog} which displays the warnings
	 */
	public Dialog createWarningDialog(Activity activity, List<SectionWarning> sectionWarnings){
		//Init
		Dialog dialog = new Dialog(activity);
		RelativeLayout main = new RelativeLayout(activity);
		TableLayout table = new TableLayout(activity);

		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 200, 1f);
		for (int i = 0; i<sectionWarnings.size(); i+=2) {
			//Init
			LinearLayout row1 = new LinearLayout(activity);
			LinearLayout row2 = new LinearLayout(activity);

			for (int j = 0; j < 2 && i+j < sectionWarnings.size(); j++) {
				TextView title = new TextView(activity);

				//Text
				title.setPadding(0, 20, 0, 10);
				title.setTextSize(20);
				title.setText(sectionWarnings.get(i+j).getSectionTitle() + ": " + (getNumberOfWarnings(sectionWarnings.get(i+j).getWarnings()) + getNumberOfCriticals(sectionWarnings.get(i+j).getWarnings())));

				//WarningBar
				WarningMeter bar = new WarningMeter(activity, sectionWarnings.get(i+j));
				bar.setOnClickListener(new WarningMeterListener(activity, sectionWarnings.get(i+j)));

				//Childs
				row1.addView(title, params1);
				row2.addView(bar, params2);
			}

			table.addView(row1);
			table.addView(row2);
		}

		//Dialog
		dialog.setTitle("Status");
		dialog.setContentView(main);
		table.setStretchAllColumns(true);

		//Standard linearLayout params
		LinearLayout.LayoutParams standardParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

		//Content
		RelativeLayout content = new RelativeLayout(activity);

		//Scroll
		LinearLayout scrollLayout = new LinearLayout(activity);
		RelativeLayout.LayoutParams scrollLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		final int SCROLL_BOTTOM_MARGIN = 80;
		scrollLayoutParams.bottomMargin = SCROLL_BOTTOM_MARGIN;
		ScrollView scroll = new ScrollView(activity);
		int scrollId = 1;
		scroll.setId(scrollId);

		//Exit button
		LinearLayout okLayout = new LinearLayout(activity);
		okLayout.setBackgroundResource(R.drawable.bottom_bar);
		final int BUTTON_LAYOUT_HEIGHT = 75;
		RelativeLayout.LayoutParams okLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_LAYOUT_HEIGHT);
		okLayoutParams.addRule(RelativeLayout.BELOW, scrollId);
		okLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);	
		Button okButton = new Button(activity);
		okButton.setText("Ok");
		okButton.setBackgroundResource(android.R.drawable.btn_default);
		final int BUTTON_HEIGHT = 70;
		LinearLayout.LayoutParams okParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, BUTTON_HEIGHT);
		final int BUTTON_MARGIN = 5;
		okParams.setMargins(BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN, BUTTON_MARGIN);
		okButton.setOnClickListener(new HideDialogListener(dialog));

		//Table
		LinearLayout tableLayout = new LinearLayout(activity);
		RelativeLayout.LayoutParams tableLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);		

		//Childs
		dialog.setContentView(main, standardParams);
		main.addView(content, standardParams);
		content.addView(scrollLayout, scrollLayoutParams);
		content.addView(okLayout, okLayoutParams);
		scrollLayout.addView(scroll, standardParams);
		scroll.addView(tableLayout, tableLayoutParams);
		tableLayout.addView(table, standardParams);
		okLayout.addView(okButton, okParams);


		return dialog;
	}

	/**
	 * Checks each {@link Section} in the report for any warnings
	 * @param fromDate 
	 * @param toDate 
	 * @return A List of {@link SectionWarnings} containing a list of {@link Warning} for each section
	 */
	public List<SectionWarning> checkForWarnings(Date fromDate, Date toDate){
		List<SectionWarning> sectionWarnings = new ArrayList<SectionWarning>();
		List<Section> sections = viewService.getSections();

		for (Section section : sections) {
			List<Warning> warnings = checkSectionForWarnings(section, fromDate, toDate);
			if(getNumberOfCriticals(warnings) > 0 || getNumberOfWarnings(warnings) > 0){
				sectionWarnings.add(new SectionWarning(section.getSectionHeader(), warnings));
			}
		}

		return sectionWarnings;
	}

	/**
	 * Checks a {@link Section} for warnings
	 * @param section The {@link Section} to check
	 * @param fromDate 
	 * @param toDate 
	 * @return List {@link Warning} for this {@link Section}
	 */
	public List<Warning> checkSectionForWarnings(Section section, Date fromDate, Date toDate) {
		if(section instanceof TableSection){
			return checkTableForWarnings((TableSection) section, fromDate, toDate);
		}else if(section instanceof GraphSection){
			return checkGraphForWarnings((GraphSection) section, fromDate, toDate);
		}else{
			return new ArrayList<Warning>();			
		}
	}

	/**
	 * Checks a {@link GraphSection} for any warnings
	 * @param section The {@link GraphSection} to check
	 * @param fromDate 
	 * @param toDate 
	 * @return A list of warnings for this section
	 */
	private List<Warning> checkGraphForWarnings(GraphSection section, Date fromDate, Date toDate) {
		double actualValue, targetValue, differential;
		List<Warning> warnings = new ArrayList<Warning>();
		GraphData actual = (GraphData) viewService.getGraphDataBySection(section, fromDate, toDate, resolution, DataType.ACTUAL);
		GraphData target = (GraphData) viewService.getGraphDataBySection(section, fromDate, toDate, resolution, DataType.TARGET);
		List<String> attributes = actual.getPointAttributes();
		List<GraphPoint> pointsActual = actual.getGraphPoints();
		List<GraphPoint> pointsTarget = target.getGraphPoints();

		for (GraphPoint pointActual : pointsActual) {
			int idx = pointsActual.indexOf(pointActual);
			GraphPoint pointTarget = pointsTarget.get(idx);


			for (String attribute : attributes) {
				actualValue = Double.valueOf(pointActual.getValue(attribute));
				targetValue = Double.valueOf(pointTarget.getValue(attribute));
				differential = actualValue/targetValue; 

				if(differential > 0.95){
					//					warnings.add(new Warning(Type.OK, actualValue, targetValue, pointActual.getPointComment(attribute)));
				}else if(differential < 0.95 && differential >= 0.9){
					String time = "Could not find date";
					time = DateConverter.parse(pointActual.getDaytime(), DateConverter.Type.TIME, resolution);
					warnings.add(new GraphWarning(Type.WARNING, attribute, time, actualValue, targetValue, pointActual.getPointComment(attribute)));
				}else if(differential < 0.9){
					String time = "Could not find date";
					time = DateConverter.parse(pointActual.getDaytime(), DateConverter.Type.TIME, resolution);
					warnings.add(new GraphWarning(Type.CRITICAL, attribute, time, actualValue, targetValue, pointActual.getPointComment(attribute)));
				}
			}
		}

		return warnings;
	}

	/**
	 * Checks a {@link TableSection} for any warnings
	 * @param section The {@link GraphSection} to check
	 * @param fromdate 
	 * @param toDate 
	 * @return A list of warnings for this section
	 */
	private List<Warning> checkTableForWarnings(TableSection section, Date fromdate, Date toDate) {
		double actualValue, targetValue, differential;
		List<Warning> warnings = new ArrayList<Warning>();
		TableData actualData = (TableData) viewService.getTableData(section, fromdate, toDate, resolution, DataType.ACTUAL);
		TableData targetData = (TableData) viewService.getTableData(section, fromdate, toDate, resolution, DataType.TARGET);

		List<TableRow> actualRows = actualData.getTableRows();
		List<TableRow> targetRows = targetData.getTableRows();

		for (TableRow tableRow : actualRows) {
			int idx = actualRows.indexOf(tableRow);
			List<TableCell> actuals = tableRow.getValues();
			List<TableCell> targets = targetRows.get(idx).getValues();

			for (TableCell actual : actuals) {
				try{
					int idx2 = actuals.indexOf(actual);
					TableCell target = targets.get(idx2);

					actualValue = Double.valueOf(actual.getValue());
					targetValue = Double.valueOf(target.getValue());
					differential = actualValue/targetValue;

					if(differential > 0.95){
						//						warnings.add(new Warning(Type.OK, actualValue, targetValue, actual.getComment()));
					}else if(differential < 0.95 && differential >= 0.9){
						warnings.add(new CellWarning(Type.WARNING, targetData.getTableColumns().get(idx2).getHeader(), actuals.get(0).getValue(), actualValue, targetValue, actual.getComment()));
					}else if(differential < 0.9){
						warnings.add(new CellWarning(Type.CRITICAL, targetData.getTableColumns().get(idx2).getHeader(), actuals.get(0).getValue(), actualValue, targetValue, actual.getComment()));
					}
				}catch(java.lang.NumberFormatException e){
					//Nothing neccesary to do
				}
			}
		}


		return warnings;
	}

	/**
	 * Calculates the number of warnings for a section
	 * @param section {@link SectionWarnings} 
	 * @return Number of warnings
	 */
	private int getNumberOfWarnings(List<Warning> warnings){
		int count = 0;

		for (Warning warning : warnings) {
			if(warning.getType() == Warning.Type.WARNING){
				count++;
			}
		}

		return count;
	}

	/**
	 * Calculates the number of critical for a section
	 * @param section {@link SectionWarnings} 
	 * @return Number of criticals
	 */
	private int getNumberOfCriticals(List<Warning> warnings){
		int count = 0;

		for (Warning warning : warnings) {
			if(warning.getType() == Warning.Type.CRITICAL){
				count++;
			}
		}

		return count;
	}

	/**
	 * Sets the {@link Resolution} for the {@link WarningChecker}
	 * @param resolution
	 */
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}
}
