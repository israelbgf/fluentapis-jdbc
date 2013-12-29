package org.fluentapis.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapConverter implements ResultSetConverter<Map<Object, List<Object[]>>> {

	public Map<Object, List<Object[]>> convert(ResultSet resultSet) throws SQLException {
		
		Map<Object, List<Object[]>> map = new HashMap<Object, List<Object[]>>();
		List<Object[]> list;
		
		while(resultSet.next()){
			int columnCount = resultSet.getMetaData().getColumnCount();
			Object[] row = new Object[columnCount - 1];
			
			for(int i = 1; i < columnCount; i++){
				row[i - 1] = resultSet.getObject(i + 1);
			}
			
			Object key = resultSet.getObject(1);
			list = map.get(key);
			if(list == null){
				list = new ArrayList<Object[]>();
				map.put(key, list);
			}
			list.add(row);
		}
		
		return map;
	}

}
