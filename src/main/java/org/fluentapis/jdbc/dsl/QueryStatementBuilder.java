package org.fluentapis.jdbc.dsl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.fluentapis.jdbc.Statement;
import org.fluentapis.jdbc.converter.ConverterFactory;
import org.fluentapis.jdbc.converter.ResultSetConverter;

public class QueryStatementBuilder extends BaseStatementBuilder<QueryStatementBuilder>{

	QueryStatementBuilder() {
		super();
	}
	
	public List<Object[]> execute(){
		return execute(ConverterFactory.asList());
	}
	
	public <T> T execute(ResultSetConverter<T> converter){
		validate();
		try {
			Statement statement = new Statement(rawStatement);
			PreparedStatement preparedStatement = connection.prepareStatement(statement.getNativeStatement());
			applyParameters(statement, preparedStatement);;
			preparedStatement.executeQuery();
			return converter.convert(preparedStatement.getResultSet());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
}
