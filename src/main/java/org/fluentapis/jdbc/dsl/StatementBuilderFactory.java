package org.fluentapis.jdbc.dsl;

public class StatementBuilderFactory {

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
	
}
