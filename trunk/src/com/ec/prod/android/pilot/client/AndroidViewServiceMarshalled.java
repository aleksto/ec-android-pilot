package com.ec.prod.android.pilot.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ec.prod.android.pilot.service.ViewServiceMarshalled;
import com.tieto.ec.logic.DateFormat;
import com.tieto.ec.webServices.Webservice;

public class AndroidViewServiceMarshalled extends Webservice implements ViewServiceMarshalled{

	public AndroidViewServiceMarshalled(String username, String password, String namespace, String url) {
		super(username, password, namespace, url);
	}

	public List<String> getSections() {
		Object response = executeWebservice("getSections", "");
		String[] responseList = response.toString().split("return=");
		List<String> list = new ArrayList<String>();

		int idx;
		for (int i = 1; i < responseList.length; i++) {
			idx = responseList[i].lastIndexOf(";");
			list.add(responseList[i].substring(0, idx));
		}
		return list;
	}

	public List<String> getTableData(String section, Date fromdate, Date toDate, int resolution) {
		Object response = executeWebservice("getTableData", "arg0", section,
				"arg1", DateFormat.parse(fromdate.getTime()),
				"arg2", DateFormat.parse(toDate.getTime()),
				"arg3", Integer.toString(resolution));

		String[] responseList = response.toString().split("return=");
		List<String> list = new ArrayList<String>();

		int idx;
		for (int i = 1; i < responseList.length; i++) {
			idx = responseList[i].lastIndexOf(";");
			list.add(responseList[i].substring(0, idx));
		}
		return list;
	}

	public List<String> getGraphDataBySection(String section, Date fromDate, Date toDate, int resolution) {
		Object response = executeWebservice("getGraphDataBySection", "arg0", section,
				"arg1", DateFormat.parse(fromDate.getTime()),
				"arg2", DateFormat.parse(toDate.getTime()),
				"arg3", Integer.toString(resolution));

		String[] responseList = response.toString().split("return=");
		List<String> list = new ArrayList<String>();

		int idx;
		for (int i = 1; i < responseList.length; i++) {
			idx = responseList[i].lastIndexOf(";");
			list.add(responseList[i].substring(0, idx));
		}
		return list;
	}

	public List<String> getGraphDataByRow(String row, Date fromDate, Date toDate, int resolution) {
		//TODO
		return null;
	}

	public List<String> getTextData(String section, Date fromDate, Date toDate,	int resolution) {
		Object response = executeWebservice("getTextData", "arg0", section,
				"arg1", DateFormat.parse(fromDate.getTime()),
				"arg2", DateFormat.parse(toDate.getTime()),
				"arg3", Integer.toString(resolution));

		String[] responseList = response.toString().split("return=");
		List<String> list = new ArrayList<String>();

		int idx;
		for (int i = 1; i < responseList.length; i++) {
			idx = responseList[i].lastIndexOf(";");
			list.add(responseList[i].substring(0, idx));
		}
		return list;
	}
}
