package org.fluentapis.jdbc.dsl;

import java.io.File;
import java.io.InputStream;

import org.fluentapis.jdbc.converter.ListConverter;
import org.fluentapis.jdbc.converter.MapConverter;
import org.fluentapis.jdbc.converter.NumberConverter;
import org.fluentapis.jdbc.converter.ResultSetConverter;

/**
 * This class acts as a Facade to the DSL. With this single class you can access
 * all the features of FluentJDBC.
 * 
 * @author Israel Fonseca
 * 
 */
public class FluentJDBC {

	/*
	 * Entry Points
	 */
	
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
	
	public static ReturningParameterBuilder returning(String... columns) {
		return new ReturningParameterBuilder(columns);
	}
	
	/*
	 * Converters
	 */
	
	public static ListConverter asList(){
		return new ListConverter();
	}
	
	public static MapConverter asMap(){
		return new MapConverter();
	}

	public static ResultSetConverter<Number> asNumber() {
		return new NumberConverter();
	}
	
	/*
	 * Statements File
	 */
	
	public static StatementsFileBuilder fromClassLoader(String pathname) {
		InputStream inputStream = StatementsFileBuilder.class.getClassLoader().getResourceAsStream(pathname);
		if(inputStream == null){
			throw new RuntimeException("File not found with current classloader.");
		}
		return new StatementsFileBuilder(inputStream);
	}
	
	public static StatementsFileBuilder fromFile(String pathname) {
		return new StatementsFileBuilder(pathname);
	}

	public static StatementsFileBuilder fromFile(File file) {
		return new StatementsFileBuilder(file);
	}
	
}
