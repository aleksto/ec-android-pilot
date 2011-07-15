package com.tieto.ec.logic;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.List;

public class NameFormat extends Format {
	
	/**
	 * @author bogeeoiv
	 */
	private static final long serialVersionUID = 1L;
	private List<String> titles;
	
	public NameFormat(List<String> titles){
		this.titles = titles;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		Number num = (Number) obj;

		// using num.intValue() will floor the value, so we add 0.5 to round instead:
		int roundNum = (int) (num.floatValue() + 0.5f);
		toAppendTo.append(titles.get(roundNum));
		return toAppendTo;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return null;  // We don't use this so just return null for now.
	}
}