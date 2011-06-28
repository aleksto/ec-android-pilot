package com.tieto.ec.webServices;

import java.util.HashMap;
import org.ksoap2.serialization.SoapObject;

public class PwelDayStatusService extends Webservice{

	public PwelDayStatusService(String username, String password, String namespace, String url){
		//Init
		super(username, password, namespace, url);
	}

	public HashMap<String, String> findByPK(String daytime, String objectID){
		SoapObject soapObject = executeWebservice("findByPK",	"daytime", daytime,
				"objectid", objectID);
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}

	public HashMap<String, String> findByPKCode(String daytime, String objectcode){
		SoapObject soapObject = executeWebservice("findByPKCode",	"daytime", daytime, 
				"objectcode", objectcode);
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}

	public HashMap<String, String> findByPKCodeTimeRange(String daytime, String objectcode, String fromDate, String toDate){
		SoapObject soapObject = executeWebservice("findByPKCodeTimeRange",	"daytime", daytime, 
				"objectcode", objectcode,
				"fromdate", fromDate,
				"todate", toDate);
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}

	public HashMap<String, String> findByPKLatest(String objectID, String before){
		SoapObject soapObject = executeWebservice("findByPKLatest", "objectid", objectID,
				"before", before);
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}

	public HashMap<String, String> findByPKTimeRange(String objectid, String fromDate, String toDate){
		SoapObject soapObject = executeWebservice("findByPKTimeRange",	"objectid", objectid,
				"fromdate", fromDate,
				"todate", toDate);
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}

	public HashMap<String, String> findLatestByPKCode(String daytime, String objectcode, String before){
		SoapObject soapObject = executeWebservice("findByPKLatest", "daytime", daytime,
				"objectcode", objectcode,
				"before", before);
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}

	public HashMap<String, String> getMetaData(){
		SoapObject soapObject = executeWebservice("getMetaData");
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}

	public HashMap<String, String> insert(String acFrequency, String allocCondFactor, String allocGasEnergy, String allocGasFactor, 
			String allocGasVol, String allocOilFactor, String allocOilVol, String allocWaterFactor, String allocWaterVol, String avgBhPress, 
			String avgBhTemp, String avgChokeSize, String avgDhPumpPower, String avgDhPumpSpeed, String avgGasEnergy, String avgGasGcv, 
			String avgGasRate, String avgGlRate, String avgLiqVol, String avgOilRate, String avgWaterRate, String avgWhPress, String avgWhTemp, 
			String calcOnStreamHrs, String chokeUom, String dataClassName, String daytime, String intakePress, String intakeTemp, 
			String objectCode, String objectId, String onStreamHrs, String outletPress, String outletTemp, String phaseCurrent, 
			String phaseVoltage, String sortOrder, String theorGasEnergy, String theorGasVol, String theorOilVol, String theorWaterVol){
		SoapObject soapObject = executeWebservice("insert", "acFrequency", acFrequency,
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
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}
	
	public HashMap<String, String> remove(String acFrequency, String allocCondFactor, String allocGasEnergy, String allocGasFactor, 
			String allocGasVol, String allocOilFactor, String allocOilVol, String allocWaterFactor, String allocWaterVol, String avgBhPress, 
			String avgBhTemp, String avgChokeSize, String avgDhPumpPower, String avgDhPumpSpeed, String avgGasEnergy, String avgGasGcv, 
			String avgGasRate, String avgGlRate, String avgLiqVol, String avgOilRate, String avgWaterRate, String avgWhPress, String avgWhTemp, 
			String calcOnStreamHrs, String chokeUom, String dataClassName, String daytime, String intakePress, String intakeTemp, 
			String objectCode, String objectId, String onStreamHrs, String outletPress, String outletTemp, String phaseCurrent, 
			String phaseVoltage, String sortOrder, String theorGasEnergy, String theorGasVol, String theorOilVol, String theorWaterVol){
		SoapObject soapObject = executeWebservice("remove", "acFrequency", acFrequency,
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
		HashMap<String, String> hashMap = soapObjectToHashMap(soapObject);
		return hashMap;
	}
}