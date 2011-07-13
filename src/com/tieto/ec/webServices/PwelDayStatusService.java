package com.tieto.ec.webServices;

import java.util.ArrayList;
import java.util.HashMap;

public class PwelDayStatusService extends Webservice{

	public PwelDayStatusService(String username, String password, String namespace, String url){
		//Init
		super(username, password, namespace, url);
	}

	/*
	public ArrayList<HashMap<String, Object>> findByPK(String daytime, String objectID){
		ArrayList<HashMap<String, Object>> list = executeWebservice("findByPK", "daytime", daytime,
				"objectid", objectID);

		return list;
	}

	public ArrayList<HashMap<String, Object>> findByPKCode(String daytime, String objectcode){
		ArrayList<HashMap<String, Object>> list = executeWebservice("findByPKCode",	"daytime", daytime, 
				"objectcode", objectcode);
		return list;
	}

	public ArrayList<HashMap<String, Object>> findByPKCodeTimeRange(String daytime, String objectcode, String fromDate, String toDate){
		ArrayList<HashMap<String, Object>> list = executeWebservice("findByPKCodeTimeRange",	"daytime", daytime, 
				"objectcode", objectcode,
				"fromdate", fromDate,
				"todate", toDate);
		return list;
	}

	public ArrayList<HashMap<String, Object>> findByPKLatest(String objectID, String before){
		ArrayList<HashMap<String, Object>> list = executeWebservice("findByPKLatest", "objectid", objectID,
				"before", before);
		return list;
	}

	public ArrayList<HashMap<String, Object>> findByPKTimeRange(String objectid, String fromDate, String toDate){
		ArrayList<HashMap<String, Object>> list = executeWebservice("findByPKTimeRange",	"objectid", objectid,
				"fromdate", fromDate,
				"todate", toDate);
		return list;
	}

	public ArrayList<HashMap<String, Object>> findLatestByPKCode(String daytime, String objectcode, String before){
		ArrayList<HashMap<String, Object>> list = executeWebservice("findByPKLatest", "daytime", daytime,
				"objectcode", objectcode,
				"before", before);
		return list;
	}

	public ArrayList<HashMap<String, Object>> getMetaData(){
		ArrayList<HashMap<String, Object>> list = executeWebservice("getMetaData");
		return list;
	}

	public ArrayList<HashMap<String, Object>> insert(String acFrequency, String allocCondFactor, String allocGasEnergy, String allocGasFactor, 
			String allocGasVol, String allocOilFactor, String allocOilVol, String allocWaterFactor, String allocWaterVol, String avgBhPress, 
			String avgBhTemp, String avgChokeSize, String avgDhPumpPower, String avgDhPumpSpeed, String avgGasEnergy, String avgGasGcv, 
			String avgGasRate, String avgGlRate, String avgLiqVol, String avgOilRate, String avgWaterRate, String avgWhPress, String avgWhTemp, 
			String calcOnStreamHrs, String chokeUom, String dataClassName, String daytime, String intakePress, String intakeTemp, 
			String objectCode, String objectId, String onStreamHrs, String outletPress, String outletTemp, String phaseCurrent, 
			String phaseVoltage, String sortOrder, String theorGasEnergy, String theorGasVol, String theorOilVol, String theorWaterVol){
		ArrayList<HashMap<String, Object>> list = executeWebservice("insert", "acFrequency", acFrequency,
															"allocCondFactor", allocCondFactor,
															"allocGasEnergy",allocGasEnergy,
															"allocGasFactor",allocGasFactor,
															"allocGasVol",allocGasVol,
															"allocOilFactor",allocOilFactor,
															"allocOilVol",allocOilVol,
															"allocWaterFactor",allocWaterFactor,
															"allocWaterVol",allocWaterVol,
															"avgBhPress",avgBhPress,
															"avgBhTemp",avgBhTemp,
															"avgChokeSize",avgChokeSize,
															"avgDhPumpPower",avgDhPumpPower,
															"avgDhPumpSpeed",avgDhPumpSpeed,
															"avgGasEnergy",avgGasEnergy,
															"avgGasGcv",avgGasGcv,
															"avgGasRate",avgGasRate,
															"avgGlRate",avgGlRate,
															"avgLiqVol",avgLiqVol,
															"avgOilRate",avgOilRate,
															"avgWaterRate",avgWaterRate,
															"avgWhPress",avgWhPress,
															"avgWhTemp",avgWhTemp,
															"calcOnStreamHrs",calcOnStreamHrs,
															"chokeUom",chokeUom,
															"dataClassName",dataClassName,
															"daytime",daytime,
															"intakePress",intakePress,
															"intakeTemp",intakeTemp,
															"objectCode",objectCode,
															"objectId",objectId,
															"onStreamHrs",onStreamHrs,
															"outletPress",outletPress,
															"outletTemp",outletTemp,
															"phaseCurrent",phaseCurrent,
															"phaseVoltage",phaseVoltage,
															"sortOrder",sortOrder,
															"theorGasEnergy",theorGasEnergy,
															"theorGasVol",theorGasVol,
															"theorOilVol",theorOilVol,
															"theorWaterVol",theorWaterVol);
		return list;
	}
	
	public ArrayList<HashMap<String, Object>> remove(String acFrequency, String allocCondFactor, String allocGasEnergy, String allocGasFactor, 
			String allocGasVol, String allocOilFactor, String allocOilVol, String allocWaterFactor, String allocWaterVol, String avgBhPress, 
			String avgBhTemp, String avgChokeSize, String avgDhPumpPower, String avgDhPumpSpeed, String avgGasEnergy, String avgGasGcv, 
			String avgGasRate, String avgGlRate, String avgLiqVol, String avgOilRate, String avgWaterRate, String avgWhPress, String avgWhTemp, 
			String calcOnStreamHrs, String chokeUom, String dataClassName, String daytime, String intakePress, String intakeTemp, 
			String objectCode, String objectId, String onStreamHrs, String outletPress, String outletTemp, String phaseCurrent, 
			String phaseVoltage, String sortOrder, String theorGasEnergy, String theorGasVol, String theorOilVol, String theorWaterVol){
		ArrayList<HashMap<String, Object>> list = executeWebservice("remove", "acFrequency", acFrequency,
															"allocCondFactor", allocCondFactor,
															"allocGasEnergy",allocGasEnergy,
															"allocGasFactor",allocGasFactor,
															"allocGasVol",allocGasVol,
															"allocOilFactor",allocOilFactor,
															"allocOilVol",allocOilVol,
															"allocWaterFactor",allocWaterFactor,
															"allocWaterVol",allocWaterVol,
															"avgBhPress",avgBhPress,
															"avgBhTemp",avgBhTemp,
															"avgChokeSize",avgChokeSize,
															"avgDhPumpPower",avgDhPumpPower,
															"avgDhPumpSpeed",avgDhPumpSpeed,
															"avgGasEnergy",avgGasEnergy,
															"avgGasGcv",avgGasGcv,
															"avgGasRate",avgGasRate,
															"avgGlRate",avgGlRate,
															"avgLiqVol",avgLiqVol,
															"avgOilRate",avgOilRate,
															"avgWaterRate",avgWaterRate,
															"avgWhPress",avgWhPress,
															"avgWhTemp",avgWhTemp,
															"calcOnStreamHrs",calcOnStreamHrs,
															"chokeUom",chokeUom,
															"dataClassName",dataClassName,
															"daytime",daytime,
															"intakePress",intakePress,
															"intakeTemp",intakeTemp,
															"objectCode",objectCode,
															"objectId",objectId,
															"onStreamHrs",onStreamHrs,
															"outletPress",outletPress,
															"outletTemp",outletTemp,
															"phaseCurrent",phaseCurrent,
															"phaseVoltage",phaseVoltage,
															"sortOrder",sortOrder,
															"theorGasEnergy",theorGasEnergy,
															"theorGasVol",theorGasVol,
															"theorOilVol",theorOilVol,
															"theorWaterVol",theorWaterVol);
		return list;
	}
	*/
}