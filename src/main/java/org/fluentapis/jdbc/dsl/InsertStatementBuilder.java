package org.fluentapis.jdbc.dsl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fluentapis.jdbc.Statement;
import org.fluentapis.jdbc.dsl.ReturningParameterBuilder.ReturningParameter;

public class InsertStatementBuilder extends BaseStatementBuilder<InsertStatementBuilder>{

	InsertStatementBuilder() {
		super();
	}
	
	public <T> T execute(){
		return execute(new ReturningParameter<T>(new String[0], null));
	}
	
	public <T> T execute(ReturningParameter<T> returningParameter){
		try {
			Statement statement = new Statement(rawStatement);
			
			PreparedStatement jdbcStatement;
			
			if(returningParameter.getColumns() == null){
				jdbcStatement = connection.prepareStatement(
					statement.getNativeStatement(),
					PreparedStatement.RETURN_GENERATED_KEYS
				);
			}else{
				jdbcStatement = connection.prepareStatement(
					statement.getNativeStatement(), 
					returningParameter.getColumns()
				);
			}
			
			applyParameters(statement, jdbcStatement);
			jdbcStatement.executeUpdate();
			ResultSet resultSet = jdbcStatement.getGeneratedKeys();
			
			return returningParameter.getConverter().convert(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}