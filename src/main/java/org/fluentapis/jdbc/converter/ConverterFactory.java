package org.fluentapis.jdbc.converter;

public class ConverterFactory {

	public static ListConverter asList(){
		return new ListConverter();
	}
	
	public static MapConverter asMap(){
		return new MapConverter();
	}
	
}
