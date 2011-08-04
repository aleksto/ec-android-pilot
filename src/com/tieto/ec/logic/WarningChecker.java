package com.tieto.ec.logic;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableCell;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.gui.dmr.WarningMeter;
import com.tieto.ec.listeners.dialogs.HideDialogListener;
import com.tieto.ec.listeners.dmr.WarningMeterListener;
import com.tieto.ec.logic.SectionSaver.Location;
import com.tieto.ec.model.GraphWarning;
import com.tieto.ec.model.SectionWarning;
import com.tieto.ec.model.TableWarning;
import com.tieto.ec.model.Warning;
import com.tieto.ec.model.Warning.Type;

public class WarningChecker {

	private final DailyMorningReport dmr;

	/**
	 * Creates a new {@link WarningChecker} this class is used for checking if the {@link DailyMorningReport} contains any {@link Warning}
	 * @param dmr {@link DailyMorningReport}
	 */
	public WarningChecker(DailyMorningReport dmr){
		this.dmr = dmr;
	}

	/**
	 * Creates a {@link Dialog} displaying each section with a warning
	 * @param sectionWarnings A list containing the warnings
	 * @return A new {@link Dialog} which displays the warnings
	 */
	public Dialog createWarningDialog(List<SectionWarning> sectionWarnings){
		//Init
		Dialog dialog = new Dialog(dmr);
		ScrollView scroll = new ScrollView(dmr);
		TableLayout main = new TableLayout(dmr);
		TableLayout table = new TableLayout(dmr);

		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 200, 1f);
		for (int i = 0; i<sectionWarnings.size(); i+=2) {
			//Init
			 LinearLayout row1 = new LinearLayout(dmr);
			 LinearLayout row2 = new LinearLayout(dmr);
			
			for (int j = 0; j < 2 && i+j < sectionWarnings.size(); j++) {
				TextView title = new TextView(dmr);
				
				//Text
				title.setPadding(0, 20, 0, 10);
				title.setTextSize(20);
				title.setText(sectionWarnings.get(i+j).getSectionTitle() + ": " + (getNumberOfWarnings(sectionWarnings.get(i+j).getWarnings()) + getNumberOfCriticals(sectionWarnings.get(i+j).getWarnings())));
				
				//WarningBar
				WarningMeter bar = new WarningMeter(dmr, sectionWarnings.get(i+j));
				bar.setOnClickListener(new WarningMeterListener(dmr, sectionWarnings.get(i+j)));
				
				//Childs
				row1.addView(title, params1);
				row2.addView(bar, params2);
			}

			table.addView(row1);
			table.addView(row2);
		}

		//Dialog
		dialog.setTitle("Warnings");
		dialog.setContentView(main);
		scroll.addView(table);
		
		table.setStretchAllColumns(true);

		//Button
		Button button = new Button(dmr);
		button.setText("Ok");
		button.setOnClickListener(new HideDialogListener(dialog));
		main.addView(button);
		main.addView(scroll);

		return dialog;
	}

	/**
	 * Checks each {@link Section} in the report for any warnings
	 * @return A List of {@link SectionWarnings} containing a list of {@link Warning} for each section
	 */
	public List<SectionWarning> checkForWarnings(){
		List<SectionWarning> sectionWarnings = new ArrayList<SectionWarning>();
		List<Section> sections = dmr.getSections();

		for (Section section : sections) {
			List<Warning> warnings = checkSectionForWarnings(section);
			if(getNumberOfCriticals(warnings) > 0 || getNumberOfWarnings(warnings) > 0){
				sectionWarnings.add(new SectionWarning(section.getSectionHeader(), warnings));
			}
		}

		return sectionWarnings;
	}

	/**
	 * Checks a {@link Section} for warnings
	 * @param section The {@link Section} to check
	 * @return List {@link Warning} for this {@link Section}
	 */
	public List<Warning> checkSectionForWarnings(Section section) {
		if(section instanceof TableSection){
			return checkTableForWarnings((TableSection) section);
		}else if(section instanceof GraphSection){
			return checkGraphForWarnings((GraphSection) section);
		}else{
			return new ArrayList<Warning>();			
		}
	}

	/**
	 * Checks a {@link GraphSection} for any warnings
	 * @param section The {@link GraphSection} to check
	 * @return A list of warnings for this section
	 */
	private List<Warning> checkGraphForWarnings(GraphSection section) {
		double actualValue, targetValue, differential;
		List<Warning> warnings = new ArrayList<Warning>();
		GraphData actual = (GraphData) dmr.getSaveManager().load(section, Location.ACTUAL);
		GraphData target = (GraphData) dmr.getSaveManager().load(section, Location.TARGET);
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
					warnings.add(new GraphWarning(Type.WARNING, attribute, actualValue, targetValue, pointActual.getPointComment(attribute)));
				}else if(differential < 0.9){
					warnings.add(new GraphWarning(Type.CRITICAL, attribute, actualValue, targetValue, pointActual.getPointComment(attribute)));
				}
			}
		}

		return warnings;
	}

	/**
	 * Checks a {@link TableSection} for any warnings
	 * @param section The {@link GraphSection} to check
	 * @return A list of warnings for this section
	 */
	private List<Warning> checkTableForWarnings(TableSection section) {
		double actualValue, targetValue, differential;
		List<Warning> warnings = new ArrayList<Warning>();
		TableData actualData = (TableData) dmr.getSaveManager().load(section, Location.ACTUAL);
		TableData targetData = (TableData) dmr.getSaveManager().load(section, Location.TARGET);

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
						warnings.add(new TableWarning(Type.WARNING, targetData.getTableColumns().get(idx2).getHeader(), actuals.get(0).getValue(), actualValue, targetValue, actual.getComment()));
					}else if(differential < 0.9){
						warnings.add(new TableWarning(Type.CRITICAL, targetData.getTableColumns().get(idx2).getHeader(), actuals.get(0).getValue(), actualValue, targetValue, actual.getComment()));
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
}
