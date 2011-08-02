package com.ec.prod.android.pilot.client;

import java.util.Date;
import java.util.List;

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

	private ViewServiceMarshalled viewService;

	public DMRViewServiceUnmarshalled(String username, String password, String namespace, String url){
		viewService = new AndroidViewServiceMarshalled(username, password, namespace, url);
	}

	public List<Section> getSections() {
		List<String> sections = viewService.getSections();
		return MarshalService.unMarshalSections(sections);
	}

	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution, int type) {	
		String tableSection = MarshalService.marshalTableSection(section);		
		List<String> tableData = viewService.getTableData(tableSection, fromdate, toDate, resolution);
		TableData unMarshalTableData = MarshalService.unMarshalTableData(tableData);
		return unMarshalTableData;			
	}

	public GraphData getGraphDataBySection(GraphSection section, Date fromDate, Date toDate, int resolution, int type) {
		String graphSection = MarshalService.marshalGraphSection(section);		
		List<String> graphData = viewService.getGraphDataBySection(graphSection, fromDate, toDate, resolution);
		GraphData unMarshalGraphData = MarshalService.unMarshalGraphData(graphData);
		return unMarshalGraphData;
	}

	public GraphData getGraphDataByRow(TableRow row, Date fromDate, Date toDate, int resolution, int type) {
		String tableRowSection = MarshalService.marshalTableRow(row);		
		List<String> graphData = viewService.getGraphDataByRow(tableRowSection, fromDate, toDate, resolution);
		return MarshalService.unMarshalGraphData(graphData);
	}

	public TextData getTextData(TextSection section, Date fromDate, Date toDate, int resolution) {
		String textSection = MarshalService.marshalTextSection(section);		
		List<String> textData = viewService.getTextData(textSection, fromDate, toDate, resolution);
		TextData unMarshalTextData = MarshalService.unMarshalTextData(textData);
		return unMarshalTextData;
	}
}
