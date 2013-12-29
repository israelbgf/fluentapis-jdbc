package org.fluentapis.jdbc.dsl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.fluentapis.jdbc.Statement;

public class DeleteOrUpdateStatementBuilder extends BaseStatementBuilder<DeleteOrUpdateStatementBuilder>{

	DeleteOrUpdateStatementBuilder() {
		super();
	}
	
	public int execute(){
		try {
			Statement statement = new Statement(rawStatement);
			
			PreparedStatement jdbcStatement =
					connection.prepareStatement(statement.getNativeStatement());
			
			applyParameters(statement, jdbcStatement);
			return jdbcStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
