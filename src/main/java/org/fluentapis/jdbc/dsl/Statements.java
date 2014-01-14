package org.fluentapis.jdbc.dsl;

import org.fluentapis.jdbc.converter.ListConverter;
import org.fluentapis.jdbc.converter.MapConverter;
import org.fluentapis.jdbc.converter.NumberConverter;
import org.fluentapis.jdbc.converter.ResultSetConverter;


public class Statements {

	public static QueryStatementBuilder createQuery(String statement){
		QueryStatementBuilder builder = new QueryStatementBuilder();
		builder.rawStatement = statement;
		return builder;
	}
	
	public static InsertStatementBuilder createInsert(String statement){
		InsertStatementBuilder builder = new InsertStatementBuilder();
		builder.rawStatement = statement;
		return builder;
	}
	
	public static DeleteOrUpdateStatementBuilder createDelete(String statement){
		return createDeleteOrUpdate(statement);
	}

	public static DeleteOrUpdateStatementBuilder createUpdate(String statement){
		return createDeleteOrUpdate(statement);
	}
	
	private static DeleteOrUpdateStatementBuilder createDeleteOrUpdate(String statement){
		DeleteOrUpdateStatementBuilder builder = new DeleteOrUpdateStatementBuilder();
		builder.rawStatement = statement;
		return builder;
	}
	
	public static ReturningParameterBuilder returning(String column) {
		return new ReturningParameterBuilder(new String[] { column });
	}
	
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
