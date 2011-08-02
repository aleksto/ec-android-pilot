package com.ec.prod.android.pilot.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ec.prod.android.pilot.service.ViewServiceMarshalled;
import com.tieto.ec.activities.DailyMorningReport;
import com.tieto.ec.logic.DateConverter;

public class AndroidViewServiceMarshalled extends Webservice implements ViewServiceMarshalled{

	/**
	 * This class extends {@link Webservice} and handles the passing and receiving of data from an external webservice. 
	 * It passes username, password, namespace and url to parent so that {@link Webservice} can find correct server and 
	 * be authorized.
	 * @param username
	 * @param password
	 * @param namespace
	 * @param url
	 */
	public AndroidViewServiceMarshalled(String username, String password, String namespace, String url) {
		super(username, password, namespace, url);
	}

	/**
	 * This method gets the sections which is to be shown in {@link DailyMorningReport}. Inside a section there
	 * can be text data, graph data or table data. 
	 * The method passes the response to generateResonseList() which parses the response string into a set of strings
	 * which it returns in a list
	 */
	public List<String> getSections() {	
		Object response = executeWebservice("getSections", "");
		return generateResponseList(response);
	}

	/**
	 * This method gets the table data which is to be shown in {@link DailyMorningReport}
	 * The method passes the response to generateResonseList() which parses the response string into a set of strings
	 * which it returns in a list
	 */
	public List<String> getTableData(String section, Date fromdate, Date toDate, int resolution) {
		Object response = executeWebservice("getTableData",  "arg0", section,
														 	 "arg1", DateConverter.parse(fromdate, DateConverter.Type.DATE),
															 "arg2", DateConverter.parse(toDate, DateConverter.Type.DATE),
															 "arg3", Integer.toString(resolution));
				return generateResponseList(response);
	}

	/**
	 * This method gets the graph data which is to be shown in {@link DailyMorningReport}
	 * The method passes the response to generateResonseList() which parses the response string into a set of strings
	 * which it returns in a list
	 */
	public List<String> getGraphDataBySection(String section, Date fromDate, Date toDate, int resolution) {
		Object response = executeWebservice("getGraphDataBySection",  "arg0", section,
																 	  "arg1", DateConverter.parse(fromDate, DateConverter.Type.DATE),
																	  "arg2", DateConverter.parse(toDate, DateConverter.Type.DATE),
																	  "arg3", Integer.toString(resolution));
		return generateResponseList(response);
	}

	public List<String> getTextData(String section, Date fromDate, Date toDate, int resolution) {
		Object response = executeWebservice("getTextData",  "arg0", section,
															"arg1", DateConverter.parse(fromDate, DateConverter.Type.DATE),
															"arg2", DateConverter.parse(toDate, DateConverter.Type.DATE),
															"arg3", Integer.toString(resolution));
		return generateResponseList(response);
	}

	/**
	 * Not yet implemented
	 */
	public List<String> getGraphDataByRow(String row, Date fromDate,
			Date toDate, int resolution) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method parses the response string which getSections(), getTableData(), getGraphDataBySection() and getTextData() passes it.
	 * These methods can be found in this class. 
	 * @param response
	 * @return
	 */
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