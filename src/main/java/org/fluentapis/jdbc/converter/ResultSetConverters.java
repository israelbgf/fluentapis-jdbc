package org.fluentapis.jdbc.converter;

public class ResultSetConverters {

	public static ListConverter asList(){
		return new ListConverter();
	}
	
	public static MapConverter asMap(){
		return new MapConverter();
	}

	public static ResultSetConverter<Number> asNumber() {
		return new NumberConverter();
	}
	
}
