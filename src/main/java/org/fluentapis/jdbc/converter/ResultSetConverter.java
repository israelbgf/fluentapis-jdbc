package org.fluentapis.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetConverter<T> {

	T convert(ResultSet resultSet) throws SQLException;
	
}
