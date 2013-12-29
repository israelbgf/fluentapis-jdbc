package org.fluentapis.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListConverter implements ResultSetConverter<List<Object[]>>{

	public List<Object[]> convert(ResultSet resultSet) throws SQLException {
		List<Object[]> result = new ArrayList<Object[]>();
		while(resultSet.next()){
			int columnCount = resultSet.getMetaData().getColumnCount();
			Object[] row = new Object[columnCount];
			
			for(int i = 0; i < columnCount; i++){
				row[i] = resultSet.getObject(i + 1);
			}
			
			result.add(row);
		}
		return result;
	}

}
