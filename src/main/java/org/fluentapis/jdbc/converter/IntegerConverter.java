package org.fluentapis.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerConverter implements ResultSetConverter<Integer>{

	public Integer convert(ResultSet resultSet) throws SQLException {
		resultSet.next();
		return ((Number)resultSet.getObject(1)).intValue();
	}

}
