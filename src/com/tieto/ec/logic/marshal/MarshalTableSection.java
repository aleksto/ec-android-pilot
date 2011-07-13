package com.tieto.ec.logic.marshal;

import java.io.IOException;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;

import com.ec.prod.android.pilot.model.TableSection;

public class MarshalTableSection implements Marshal{

	public Object readInstance(XmlPullParser arg0, String arg1, String arg2, PropertyInfo arg3) throws IOException, XmlPullParserException {
		Log.d("tieto", "readInstance - MarshalTableSection");
		return null;
	}

	public void register(SoapSerializationEnvelope arg0) {
		arg0.addMapping(arg0.xsd, TableSection.class.getSimpleName(), TableSection.class, this);
	}

	public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
		TableSection table = (TableSection) obj;
		writer.text("<sectionHeader>" + table.getSectionHeader() +"</sectionHeader>");
	}
}
