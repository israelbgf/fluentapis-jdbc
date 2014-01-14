package org.fluentapis.jdbc.dsl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrUpdateStatementBuilder extends BaseStatementBuilder<DeleteOrUpdateStatementBuilder>{

	DeleteOrUpdateStatementBuilder() {
		super();
	}
	
	public int execute(){
		try {
			StatementHolder statement = new StatementHolder(rawStatement);
			
			PreparedStatement jdbcStatement =
					connection.prepareStatement(statement.getNativeStatement());
			
			applyParameters(statement, jdbcStatement);
			return jdbcStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
