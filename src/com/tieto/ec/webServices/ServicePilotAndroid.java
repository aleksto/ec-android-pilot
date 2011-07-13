package com.tieto.ec.webServices;

import java.util.Date;
import java.util.List;

import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;

import android.util.Log;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextSection;
import com.ec.prod.android.pilot.service.ViewService;
import com.tieto.ec.logic.WebserviceParser;
import com.tieto.ec.logic.marshal.MarshalTableSection;
import com.tieto.ec.logic.marshal.MarshalTextSection;

public class ServicePilotAndroid extends Webservice implements ViewService {
	
	public ServicePilotAndroid(String username, String password, String namespace, String url) {
		super(username, password, namespace, url);
	}


	public List<Section> getSections() {
		Object bodyIn = executeWebservice("getSections", null);
		List<Section> sections = WebserviceParser.parseSections(bodyIn);
		return sections;
	}


	public TableData getTableData(TableSection section, Date fromdate, Date toDate, int resolution) {
		//Properties
		PropertyInfo sectionProperties = new PropertyInfo();
		PropertyInfo fromDateProperties = new PropertyInfo();
		PropertyInfo toDateProperties = new PropertyInfo();
		PropertyInfo resolutionProperties = new PropertyInfo();
		
		//Properties Population
		sectionProperties.setName("arg0");
		sectionProperties.setValue(section);
		fromDateProperties.setName("arg1");
		fromDateProperties.setValue(fromdate);
		toDateProperties.setName("arg2");
		toDateProperties.setValue(toDate);
		resolutionProperties.setName("arg3");
		resolutionProperties.setValue(resolution);

		addMarshal(new MarshalTableSection());
		addMarshal(new MarshalDate());
		
		Object list = executeWebservice("getTableData", sectionProperties, fromDateProperties, toDateProperties, resolutionProperties);
		return WebserviceParser.parseGetTableData(list);
	}

	public GraphData getGraphData(GraphSection section, Date fromDate, Date toDate, int resolution) {
		return null;
	}


	public GraphData getGraphData(TableRow row, Date fromDate, Date toDate,	int resolution) {
		return null;
	}
	
	public TextData getTextData(TextSection section, Date fromDate,	Date toDate, int resolution) {
		//Properties
		PropertyInfo sectionProperties = new PropertyInfo();
		PropertyInfo fromDateProperties = new PropertyInfo();
		PropertyInfo toDateProperties = new PropertyInfo();
		PropertyInfo resolutionProperties = new PropertyInfo();
		
		//Properties Population
		sectionProperties.setName("arg0");
		sectionProperties.setValue(section);
		fromDateProperties.setName("arg1");
		fromDateProperties.setValue(fromDate);
		toDateProperties.setName("arg2");
		toDateProperties.setValue(toDate);
		resolutionProperties.setName("arg3");
		resolutionProperties.setValue(resolution);

		addMarshal(new MarshalTextSection());
		addMarshal(new MarshalDate());
		
		Object list = executeWebservice("getTextData", sectionProperties, fromDateProperties, toDateProperties, resolutionProperties);
		return WebserviceParser.parseGetTextData(list);
	}



	

}
