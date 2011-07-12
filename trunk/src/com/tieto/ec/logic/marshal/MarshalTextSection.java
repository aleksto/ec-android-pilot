package com.tieto.ec.logic.marshal;

import java.io.IOException;

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.ec.prod.android.pilot.model.TextSection;

public class MarshalTextSection implements Marshal {

	public Object readInstance(XmlPullParser arg0, String arg1, String arg2,
			PropertyInfo arg3) throws IOException, XmlPullParserException {
		// TODO Auto-generated method stub
		return null;
	}

	public void register(SoapSerializationEnvelope arg0) {
		arg0.addMapping(arg0.xsd, TextSection.class.getSimpleName(), TextSection.class, this);
	}

	public void writeInstance(XmlSerializer arg0, Object arg1)
	throws IOException {
		// TODO Auto-generated method stub

	}

}
