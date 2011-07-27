package com.ec.prod.android.pilot.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphPoint;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableColumn;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextElement;
import com.ec.prod.android.pilot.model.TextSection;

/**
 * This class generates typical data which we may receive in a Daily Morning Report.
 * This would normally come from a Webservice.
 */
public class ExampleViewService implements ViewService {

	private HashMap<Section, Object> data;
	private final boolean saveData;

	public ExampleViewService(boolean saveData){
		this.saveData = saveData;
		data = new HashMap<Section, Object>();
	}

	/**
	 * Generates example of sections headers from a Daily Morning Report
	 */
	public List<Section> getSections() {
		List<Section> sectionList = new LinkedList<Section>();
		sectionList.add(new TextSection("Operational Comments"));
		sectionList.add(new GraphSection("Yesterday"));
		sectionList.add(new TableSection("Well Status"));
		sectionList.add(new TableSection("Safety"));
		sectionList.add(new TableSection("Personnel On Board"));
		sectionList.add(new TableSection("Leak Spill"));
		sectionList.add(new GraphSection("Export Curves"));
		sectionList.add(new GraphSection("Import Curves"));
		sectionList.add(new TableSection("Ship Movement"));
		sectionList.add(new TableSection("Weather"));
		
		return sectionList;
		
	}

	/**
	 * Generates example of table data from a Daily Morning Report
	 */
	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution) {
		Log.d("tieto", "Webservice: contains table " + section.getSectionHeader() + ": " + data.containsKey(section));
		if(data.containsKey(section)){
			return (TableData) data.get(section);
		}else{
			if(section.getSectionHeader().equalsIgnoreCase("Well Status")){				
				List<TableColumn> tableColumns = new LinkedList<TableColumn>();
				tableColumns.add(new TableColumn("Well"));
				tableColumns.add(new TableColumn("On Stream Hours"));
				tableColumns.add(new TableColumn("AVG Well Head Temperature"));
				tableColumns.add(new TableColumn("AVG Well Head Pressure"));
				tableColumns.add(new TableColumn("Allocated Oil Volume"));
				tableColumns.add(new TableColumn("Allocated Gas Volume"));
				tableColumns.add(new TableColumn("Allocated Water Volume"));				
				TableData data = new TableData(tableColumns);
				data.addTableRow(new TableRow("Well PH65R", Integer.toString((int) (18 + Math.random() * 11.0)), Integer.toString((int) (50 + Math.random() * 20.0)), Integer.toString((int) (150 + Math.random() * 51.0)), Double.toString(5000.1 + Math.random() * 1000.1).substring(0, 6), Double.toString(600.1 + Math.random() * 100.1).substring(0, 5), Double.toString(200.1 + Math.random() * 400.1).substring(0, 5)));
				data.addTableRow(new TableRow("Well PH65R", Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7)));
				data.addTableRow(new TableRow("Well PR87D", Integer.toString((int) (18 + Math.random() * 11.0)), Integer.toString((int) (50 + Math.random() * 20.0)), Integer.toString((int) (150 + Math.random() * 51.0)), Double.toString(6000.1 + Math.random() * 1000.1).substring(0, 6), Double.toString(800.1 + Math.random() * 100.1).substring(0, 5), Double.toString(200.1 + Math.random() * 100.1).substring(0, 5)));
				data.addTableRow(new TableRow("Well PP12A", Integer.toString((int) (18 + Math.random() * 11.0)), Integer.toString((int) (50 + Math.random() * 20.0)), Integer.toString((int) (150 + Math.random() * 51.0)), Double.toString(3000.1 + Math.random() * 1000.1).substring(0, 6), Double.toString(800.1 + Math.random() * 900.1).substring(0, 5), Double.toString((int) (150 + Math.random() * 51.0))));
				data.addTableRow(new TableRow("Well PH65R", Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7), Double.toString(Math.random()*2344).substring(0, 7)));
				if(saveData){
					this.data.put(section, data);					
				}
				return data;			
			}else if(section.getSectionHeader().equalsIgnoreCase("Safety")){
				List<TableColumn> tableColumns = new LinkedList<TableColumn>();
				tableColumns.add(new TableColumn("HSE Event"));
				tableColumns.add(new TableColumn("Quantity"));
				tableColumns.add(new TableColumn("YTD"));			
				TableData data = new TableData(tableColumns);
				data.addTableRow(new TableRow("Medical treatment injury", "0", Integer.toString((int) (3 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("First aid", Integer.toString((int) (3 + Math.random() * 11.0)), Integer.toString((int) (18 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("Lost time injury", Integer.toString((int) (3 + Math.random() * 11.0)), Integer.toString((int) (3 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("Sick onboard", "0", "0"));
				data.addTableRow(new TableRow("Sick sent ashore", "0", "0"));
				data.addTableRow(new TableRow("Life boats out of service", "0", "0"));
				data.addTableRow(new TableRow("Restricted work injury", "0", "0"));
				data.addTableRow(new TableRow("Safrty critical equipment", "0", "0"));
				if(saveData){
					this.data.put(section, data);					
				}
				return data;		
			}else if(section.getSectionHeader().equalsIgnoreCase("Personnel On Board")){
				List<TableColumn> tableColumns = new LinkedList<TableColumn>();
				tableColumns.add(new TableColumn("Job Category"));
				tableColumns.add(new TableColumn("Head Count"));
				tableColumns.add(new TableColumn("Comments"));			
				TableData data = new TableData(tableColumns);
				data.addTableRow(new TableRow("HEAD_COUNT", "10", "COMMENT ON PERSONNEL ONBOARD - POB - SKRAV_FPSO"));
				data.addTableRow(new TableRow("OIM", "1", "Kaptein Sabeltan"));
				if(saveData){
					this.data.put(section, data);					
				}
				return data;	
			}else if(section.getSectionHeader().equalsIgnoreCase("Leak Spill")){
				List<TableColumn> tableColumns = new LinkedList<TableColumn>();
				tableColumns.add(new TableColumn("Time"));
				tableColumns.add(new TableColumn("Spill Type"));
				tableColumns.add(new TableColumn("Quantity"));		
				tableColumns.add(new TableColumn("Unit"));		
				tableColumns.add(new TableColumn("Comments"));			
				TableData data = new TableData(tableColumns);
				data.addTableRow(new TableRow("00:00", "GAS", "0", "M3", "COMMENT ON FCTY SPILL EVENT - GAS"));
				data.addTableRow(new TableRow("10:00", "GAS", "1", "M3", ""));
				data.addTableRow(new TableRow("11:00", "OIL", "2", "M3", ""));
				if(saveData){
					this.data.put(section, data);					
				}
				return data;	
			}else if(section.getSectionHeader().equalsIgnoreCase("Ship Movement")){
				List<TableColumn> tableColumns = new LinkedList<TableColumn>();
				tableColumns.add(new TableColumn("Ship Movements"));
				tableColumns.add(new TableColumn("Item Value"));		
				TableData data = new TableData(tableColumns);
				data.addTableRow(new TableRow("Heading(deg)", Integer.toString((int) (18 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("Heave(m)", ""));
				data.addTableRow(new TableRow("Pitch(deg)", Integer.toString((int) (18 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("Roll(deg)", Integer.toString((int) (3 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("(deg)", Integer.toString((int) (3 + Math.random() * 11.0))));
				if(saveData){
					this.data.put(section, data);					
				}
				return data;	
			}else{
				List<TableColumn> tableColumns = new LinkedList<TableColumn>();
				tableColumns.add(new TableColumn("Item Type"));
				tableColumns.add(new TableColumn("Item Code Text"));		
				tableColumns.add(new TableColumn("Item Value"));		
				TableData data = new TableData(tableColumns);
				data.addTableRow(new TableRow("AIR", "Barometer(hPa)", Integer.toString((int) (1 + Math.random() * 2.0))));
				data.addTableRow(new TableRow("AIR", "Temp(´C)", Integer.toString((int) (18 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("LIGHTNING", "Intensity", Integer.toString((int) (5400 + Math.random() * 201))));
				data.addTableRow(new TableRow("LIGHTNING", "Quantity (#/hrs)", Integer.toString((int) (3 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("MISC_WEATHER", "Misc. Item 2", "0"));
				data.addTableRow(new TableRow("MISC_WEATHER", "Visibility (m)", "0"));
				data.addTableRow(new TableRow("PRECIPITATION", "Rainfall (cm)", Integer.toString((int) (3 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("SWELL", "Direction", Integer.toString((int) (18 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("WAVES", "Maximum Height (m)", Integer.toString((int) (1 + Math.random() * 2.0))));
				data.addTableRow(new TableRow("WAVES", "Significant Height (m)", Integer.toString((int) (1 + Math.random() * 2.0))));
				data.addTableRow(new TableRow("WAVES", "Spectral Peak Period (s)", Integer.toString((int) (3 + Math.random() * 11.0))));
				data.addTableRow(new TableRow("WIND", "Direction (deg)", Integer.toString((int) (190 + Math.random() * 51.0))));
				data.addTableRow(new TableRow("WIND", "Speed (m/s)", Integer.toString((int) (3 + Math.random() * 11.0))));
				if(saveData){
					this.data.put(section, data);					
				}
				return data;	
			}
		}
	}

	/**
	 * Generates example of graph data from a Daily Morning Report
	 */
	public TextData getTextData(TextSection section, Date fromDate, Date toDate, int resolution) {
		Log.d("tieto", "Webservice: contains table " + section.getSectionHeader() + ": " + data.containsKey(section));
		if(data.containsKey(section)){
			return (TextData) data.get(section);
		}else{
			TextData textData = new TextData();
			
			
			if(Math.random() > 0.5){
				Date date1 = new Date(Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth(), Calendar.getInstance().getTime().getDay(), 
									  (int)(1 + Math.random() * 3.0), (int)(1 + Math.random() * 60));
				textData.addTextElement(new TextElement(date1, "Facility not producing optimally"));
			
				Date date2 = new Date(Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth(), Calendar.getInstance().getTime().getDay(), 
						  (int)(4 + Math.random() * 3.0), (int)(1 + Math.random() * 60));
				textData.addTextElement(new TextElement(date2, "Facility is again producing optimally "));	
			}
			
			if(Math.random() > 0.5){
				Date date3 = new Date(Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth(), Calendar.getInstance().getTime().getDay(), 
						  (int)(8 + Math.random() * 3.0), (int)(1 + Math.random() * 60));
				textData.addTextElement(new TextElement(date3, "High Oil level"));
				
				Date date4 = new Date(Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth(), Calendar.getInstance().getTime().getDay(), 
						  (int)(12 + Math.random() * 3.0), (int)(1 + Math.random() * 60));	
				textData.addTextElement(new TextElement(date4, "Oil level under control"));	
			}
			
			if(Math.random() > 0.5){
				Date date5 = new Date(Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth(), Calendar.getInstance().getTime().getDay(), 
						  (int)(16 + Math.random() * 3.0), (int)(1 + Math.random() * 60));
				textData.addTextElement(new TextElement(date5, "Engineer injured on facility"));
				
				Date date6 = new Date(Calendar.getInstance().getTime().getYear(), Calendar.getInstance().getTime().getMonth(), Calendar.getInstance().getTime().getDay(), 
						  (int)(20 + Math.random() * 3.0), (int)(1 + Math.random() * 60));
				textData.addTextElement(new TextElement(date6, "Engineer is brought to the hospital"));			
			}
			
			
			if(saveData){
				this.data.put(section, textData);					
			}
			return textData;
			
			
		}
	}

	/**
	 * Generates example of graph data from a Daily Morning Report
	 */
	public GraphData getGraphDataBySection(GraphSection section, Date fromDate,	Date toDate, int resolution) {
		Log.d("tieto", "Webservice: contains table " + section.getSectionHeader() + ": " + data.containsKey(section));
		if(data.containsKey(section)){
			return (GraphData) data.get(section);
		}else{
			if(section.getSectionHeader().equalsIgnoreCase("Export Curves")){
				GraphData data = new GraphData();
				data.setPointAttributes("OIL", "GAS", "WATER");
				fromDate.setHours(0);
				for (int i = 0; i < 23; i++) {
					fromDate.setHours(fromDate.getHours()+1);
					GraphPoint graphPoint1 = new GraphPoint(new Date(fromDate.getYear(), fromDate.getMonth(), fromDate.getDate(), fromDate.getHours(), fromDate.getMinutes()));
					graphPoint1.addValue("OIL", "" + Math.random()*10000);
					graphPoint1.addValue("GAS", "" + Math.random()*5000);
					graphPoint1.addValue("WATER", "" + Math.random()*2000);
					data.addGraphPoint(graphPoint1);
				}				
				if(saveData){
					this.data.put(section, data);					
				}
				return data;
			}else if(section.getSectionHeader().equalsIgnoreCase("Import Curves")){
				GraphData data = new GraphData();
				data.setPointAttributes("OIL", "GAS", "WATER");
				fromDate.setHours(0);
				for (int i = 0; i < 23; i++) {
					fromDate.setHours(fromDate.getHours()+1);
					GraphPoint graphPoint1 = new GraphPoint(new Date(fromDate.getYear(), fromDate.getMonth(), fromDate.getDate(), fromDate.getHours(), fromDate.getMinutes()));
					graphPoint1.addValue("OIL", "" + Math.random()*10000);
					graphPoint1.addValue("GAS", "" + Math.random()*5000);
					graphPoint1.addValue("WATER", "" + Math.random()*2000);
					data.addGraphPoint(graphPoint1);
				}				
				if(saveData){
					this.data.put(section, data);					
				}
				return data;
			} else {				
				GraphData data = new GraphData();
				data.setPointAttributes("OIL", "GAS", "WATER");
				GraphPoint graphPoint1 = new GraphPoint(fromDate);
				graphPoint1.addValue("OIL", "" + Math.random()*10000);
				graphPoint1.addValue("GAS", "" + Math.random()*5000);
				graphPoint1.addValue("WATER", "" + Math.random()*2000);
				data.addGraphPoint(graphPoint1);
				if(saveData){
					this.data.put(section, data);					
				}
				return data;
			}
		}
	}

	public GraphData getGraphDataByRow(TableRow row, Date fromDate,
			Date toDate, int resolution) {
		// TODO Auto-generated method stub
		return null;
	}
}
