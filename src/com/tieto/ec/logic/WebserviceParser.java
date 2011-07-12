package com.tieto.ec.logic;

import android.util.Log;

import com.ec.prod.android.pilot.model.TableData;
import com.ec.prod.android.pilot.model.TextData;

public class WebserviceParser {

	public static TableData parseGetTableData(Object bodyIn){
		String[] splitt = bodyIn.toString().split("return=");

		Log.d("tieto", bodyIn.toString());
//		for (int i = 0; i < splitt.length; i++) {
//			Log.d("tieto", splitt[i]);
//		}
		return null;
	}
	
	public static TextData parseGetTextData(Object bodyIn){
		String[] splitt = bodyIn.toString().split("return=");

		Log.d("tieto", bodyIn.toString());
		for (int i = 0; i < splitt.length; i++) {
			Log.d("tieto", splitt[i]);
		}
		return null;
	}
}
