package com.tieto.ec.logic;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class IndexFormat extends Format {

	private static final long serialVersionUID = 1L;
	private final int numberOfValues;
	private int count;
	
	/**
	 * Format used in {@link BarGraph}, this will set a list of values
	 * along the X-axis on the {@link BarGraph}
	 * @param numberOfValues 
	 * @param titles
	 */
	public IndexFormat(int numberOfValues){
		this.numberOfValues = numberOfValues;
		count = 1;
	}

	/**
	 * Builds up the String
	 */
	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if(count <= numberOfValues){
			toAppendTo.append(count);
			count++;
		}else{
			count = 1;
			format(obj, toAppendTo, pos);
		}
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