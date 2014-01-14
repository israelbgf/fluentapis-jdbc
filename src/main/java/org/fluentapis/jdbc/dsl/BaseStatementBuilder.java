package org.fluentapis.jdbc.dsl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
abstract class BaseStatementBuilder<T extends BaseStatementBuilder<?>> {

	protected String rawStatement;
	protected Connection connection;
	protected Map<String, Object> parameters = new HashMap<String, Object>();

	public T on(Connection connection) {
		this.connection = connection;
		return (T) this;
	}
	
	public T withValues(Object... parameters) {
		for(int index = 0; index < parameters.length; index++){
			this.parameters.put("?" + (index + 1), parameters[index]);
		}
		return (T) this;
	}
	
	public T with(String parameterName, Object value) {
		parameters.put(parameterName, value);
		return (T) this;
	}

	protected void applyParameters(StatementHolder statement, PreparedStatement jdbcStatement) throws SQLException {
		int index = 1;
		for(String parameterName : statement.getParameters()){
			Object value = parameters.get(parameterName);
			if(value == null){
				throw new ParameterNotFoundException(parameterName);
			}
			
			jdbcStatement.setObject(index, value);	
			index++;
		}		
	}
	
	protected void validate(){
		if(connection == null){
			throw new IllegalStateException("You must define a connection first!");
		}
	}
}
