package com.ec.prod.android.pilot.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ec.prod.android.pilot.service.ViewServiceMarshalled;

public class AndroidViewServiceMarshalled extends Webservice implements ViewServiceMarshalled{

	public AndroidViewServiceMarshalled(String username, String password, String namespace, String url) {
		super(username, password, namespace, url);
	}

	public List<String> getSections() {	
		Object response = executeWebservice("getSections", "");
		return generateResponseList(response);
	}

	public List<String> getTableData(String section, Date fromdate, Date toDate, int resolution) {
		Object response = executeWebservice("getTableData",  "arg0", section,
														 	 "arg1", WebserviceDateConverter.parse(fromdate),
															 "arg2", WebserviceDateConverter.parse(toDate),
															 "arg3", Integer.toString(resolution));
				return generateResponseList(response);
	}

	public List<String> getGraphDataBySection(String section, Date fromDate, Date toDate, int resolution) {
		Object response = executeWebservice("getGraphDataBySection",  "arg0", section,
																 	  "arg1", WebserviceDateConverter.parse(fromDate),
																	  "arg2", WebserviceDateConverter.parse(toDate),
																	  "arg3", Integer.toString(resolution));
		return generateResponseList(response);
	}

	public List<String> getGraphDataByRow(String row, Date fromDate,
			Date toDate, int resolution) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getTextData(String section, Date fromDate, Date toDate, int resolution) {
		Object response = executeWebservice("getTextData",  "arg0", section,
															"arg1", WebserviceDateConverter.parse(fromDate),
															"arg2", WebserviceDateConverter.parse(toDate),
															"arg3", Integer.toString(resolution));
		return generateResponseList(response);
	}


	private List<String> generateResponseList(Object response) {
		List<String> list = new ArrayList<String>();
		String responseString = response.toString();
		String[] responseList = responseString.toString().split("return=");
		
		int idx;
		for (int i = 1; i < responseList.length; i++) {
			idx = responseList[i].lastIndexOf(";");
			list.add(responseList[i].substring(0, idx));
		}		
		return list;
	}
	
	
	
	
	
	
	
	
}
