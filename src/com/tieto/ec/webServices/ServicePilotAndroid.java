package com.tieto.ec.webServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.tieto.frmw.model.GraphData;
import com.tieto.frmw.model.GraphSection;
import com.tieto.frmw.model.Section;
import com.tieto.frmw.model.TableData;
import com.tieto.frmw.model.TableRow;
import com.tieto.frmw.model.TableSection;
import com.tieto.frmw.model.TextData;
import com.tieto.frmw.model.TextSection;
import com.tieto.frmw.service.ViewService;

public class ServicePilotAndroid extends Webservice implements ViewService {
	
	public ServicePilotAndroid(String username, String password, String namespace, String url) {
		super(username, password, namespace, url);
	}

	public List<Section> getSections() {
		//ArrayList<HashMap<String, Object>> list = executeWebservice("getSections", "");
		ArrayList<HashMap<String, Object>> sections = executeWebservice("getSections", "", "");
		//Log.d("tieto", sections.size()+"");
		//for (HashMap<String, Object> hashMap : sections) {
		//	Object section = hashMap.get("sectionHeader");
		//	Log.d("tieto", section+"");
		//}

		return null;
	}

	public TableData getTableData(TableSection section, Date fromdate,
			Date toDate, int resolution) {
		ArrayList<HashMap<String, Object>> list = executeWebservice("getTableData", "");
		return null;
	}

	public GraphData getGraphData(GraphSection section, Date fromDate,
			Date toDate, int resolution) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public GraphData getGraphData(TableRow row, Date fromDate, Date toDate,
			int resolution) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TextData getTextData(TextSection section, Date fromDate,
			Date toDate, int resolution) {
		ArrayList<HashMap<String, Object>> list = executeWebservice("getTextData", "");
		return null;
	}


	

}
