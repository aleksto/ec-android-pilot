package com.ec.prod.android.pilot.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.ec.prod.android.pilot.model.GraphData;
import com.ec.prod.android.pilot.model.GraphSection;
import com.ec.prod.android.pilot.model.Section;
import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TableRow;
import com.ec.prod.android.pilot.model.TableSection;
import com.ec.prod.android.pilot.model.TextData;
import com.ec.prod.android.pilot.model.TextSection;

@WebService (serviceName="DMRService")
public class DMRViewServiceMarshalled implements ViewServiceMarshalled{

	private ViewService viewService = new DMRViewService();	

	public void setViewService(ViewService viewService) {
		this.viewService = viewService;
	}

	@WebMethod
	public List<String> getSections() {
		List<Section> sections = viewService.getSections();
		return MarshalService.marshalSections(sections);
	}

	@WebMethod
	public List<String> getTableData(@WebParam String section, Date fromdate, Date toDate, int resolution) {
		TableSection tableSection = MarshalService.unMarshalTableSection(section);
		TableData tableData = viewService.getTableData(tableSection, fromdate, toDate, resolution);
		return MarshalService.marshalTableData(tableData);
	}

	@WebMethod
	public List<String> getGraphDataBySection(@WebParam String section, Date fromDate, Date toDate, int resolution) {
		GraphSection tableSection = MarshalService.unMarshalGraphSection(section);
		GraphData graphData = viewService.getGraphDataBySection(tableSection, fromDate, toDate, resolution);
		return MarshalService.marshalGraphData(graphData);
	}

	@WebMethod
	public List<String> getGraphDataByRow(@WebParam String row, Date fromDate, Date toDate, int resolution) {
		TableRow tableRow = MarshalService.unMarshalTableRow(row);
		GraphData graphData = viewService.getGraphDataByRow(tableRow, fromDate, toDate, resolution);
		return MarshalService.marshalGraphData(graphData);
	}

	@WebMethod
	public List<String> getTextData(@WebParam String section, Date fromDate, Date toDate, int resolution) {
		TextSection sectionObject = MarshalService.unMarshalTextSection(section);
		TextData textData = viewService.getTextData(sectionObject, fromDate, toDate, resolution);
		return MarshalService.marshalTextData(textData);
	}


}
