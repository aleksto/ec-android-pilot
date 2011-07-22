package com.ec.prod.android.pilot.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextSection;
import com.ec.prod.android.pilot.service.MarshalService;
import com.ec.prod.android.pilot.service.ViewService;
import com.ec.prod.android.pilot.service.ViewServiceMarshalled;

public class DMRViewServiceUnmarshalled implements ViewService {

	private HashMap<Section, Object> data;
	private ViewServiceMarshalled viewService;
	private final boolean saveData;

	public DMRViewServiceUnmarshalled(boolean saveData, String username, String password, String namespace, String url){
		this.saveData = saveData;
		data = new HashMap<Section, Object>();
		viewService = new AndroidViewServiceMarshalled(username, password, namespace, url);
	}

	public List<Section> getSections() {
		List<String> sections = viewService.getSections();
		return MarshalService.unMarshalSections(sections);
	}

	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution) {	
		Log.d("tieto", "Webservice: contains table " + section.getSectionHeader() + ": " + data.containsKey(section));
		if(data.containsKey(section)){
			return (TableData) data.get(section);
		}else{			
			String tableSection = MarshalService.marshalTableSection(section);		
			List<String> tableData = viewService.getTableData(tableSection, fromdate, toDate, resolution);
			TableData unMarshalTableData = MarshalService.unMarshalTableData(tableData);
			if(saveData){
				data.put(section, unMarshalTableData);				
			}
			return unMarshalTableData;			
		}
	}

	public GraphData getGraphDataBySection(GraphSection section, Date fromDate, Date toDate, int resolution) {
		Log.d("tieto", "Webservice: contains table " + section.getSectionHeader() + ": " + data.containsKey(section));
		if(data.containsKey(section)){
			return (GraphData) data.get(section);
		}else{	
			String graphSection = MarshalService.marshalGraphSection(section);		
			List<String> graphData = viewService.getGraphDataBySection(graphSection, fromDate, toDate, resolution);
			GraphData unMarshalGraphData = MarshalService.unMarshalGraphData(graphData);
			if(saveData){
				data.put(section, unMarshalGraphData);				
			}
			return unMarshalGraphData;
		}
	}

	public GraphData getGraphDataByRow(TableRow row, Date fromDate, Date toDate, int resolution) {
		String tableRowSection = MarshalService.marshalTableRow(row);		
		List<String> graphData = viewService.getGraphDataByRow(tableRowSection, fromDate, toDate, resolution);
		return MarshalService.unMarshalGraphData(graphData);
	}

	public TextData getTextData(TextSection section, Date fromDate, Date toDate, int resolution) {
		Log.d("tieto", "Webservice: contains table " + section.getSectionHeader() + ": " + data.containsKey(section));
		if(data.containsKey(section)){
			return (TextData) data.get(section);
		}else{	
			String textSection = MarshalService.marshalTextSection(section);		
			List<String> textData = viewService.getTextData(textSection, fromDate, toDate, resolution);
			TextData unMarshalTextData = MarshalService.unMarshalTextData(textData);
			if(saveData){
				data.put(section, unMarshalTextData);				
			}
			return unMarshalTextData;
		}
	}

}
