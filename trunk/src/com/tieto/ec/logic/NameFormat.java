package com.tieto.ec.logic;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.List;

import com.tieto.ec.gui.graphs.BarGraph;

public class NameFormat extends Format {

	private static final long serialVersionUID = 1L;
	private List<String> titles;
	
	/**
	 * Format used in {@link BarGraph}, this will set a list of values
	 * along the X-axis on the {@link BarGraph}
	 * @param titles
	 */
	public NameFormat(List<String> titles){
		this.titles = titles;
	}

	/**
	 * Builds up the String
	 */
	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		Number num = (Number) obj;

		// using num.intValue() will floor the value, so we add 0.5 to round instead:
		int roundNum = (int) (num.floatValue() + 0.5f);
		toAppendTo.append(titles.get(roundNum));
		return toAppendTo;
	}
	
	/**
	 * Not used
	 */
	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}
}