package org.fluentapis.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberConverter implements ResultSetConverter<Number>{

	public Number convert(ResultSet resultSet) throws SQLException {
		resultSet.next();
		return ((Number)resultSet.getObject(1)).intValue();
	}

}
