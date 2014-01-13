package org.fluentapis.jdbc;

import static org.fluentapis.jdbc.converter.ResultSetConverters.asList;
import static org.fluentapis.jdbc.converter.ResultSetConverters.asMap;
import static org.fluentapis.jdbc.dsl.StatementBuilderFactory.createQuery;
import static org.fluentapis.jdbc.dsl.StatementsFileBuilder.fromFile;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryTest{
	
	private static final String SAMPLE_STATEMENT_1 
		= "select * from test where id = ? and first_name = ?";
	
	private static final String SAMPLE_STATEMENT_2 
		= "select * from test where id = :id1 and first_name = :name";

	private static final String SAMPLE_STATEMENT_3 
		= "select * from test";

	
	private static Connection connection;

	@BeforeClass
	public static void before() throws SQLException{
		connection = DriverManager.getConnection("jdbc:h2:mem:test");
		
		Statement statement = connection.createStatement();
		statement.execute("create table test(id int, first_name varchar(255), second_name varchar(255))");
		statement.execute("insert into test values(1, '1', 'A')");
		statement.execute("insert into test values(1, '1', 'B')");
		statement.execute("insert into test values(2, '2', 'C')");
	}
	
	@Test
    public void listWithPositionalParameters() throws SQLException{
		
		List<Object[]> resultList = createQuery(SAMPLE_STATEMENT_1).on(connection)
										.withValues(1, "1")
										.execute(asList());
		
		assertEquals(2, resultList.size());
		assertEquals(1, resultList.get(0)[0]);
    }

	@Test
    public void listWithNamedParameters() throws SQLException{
		
		List<Object[]> resultList = createQuery(SAMPLE_STATEMENT_2).on(connection)
										.with("id1", 1)
										.with("name", "1")
										.execute(asList());
		
		assertEquals(2, resultList.size());
		assertEquals(1, resultList.get(0)[0]);
    }
	
	@Test
    public void mapWithPositionalParameters() throws SQLException{
		
		Map<Object, List<Object[]>> resultMap = createQuery(SAMPLE_STATEMENT_3).on(connection)
													.execute(asMap());
		
		assertEquals(2, resultMap.size());
		assertArrayEquals(new String[]{"1", "A"}, resultMap.get(1).get(0));
		assertArrayEquals(new String[]{"1", "B"}, resultMap.get(1).get(1));
		assertArrayEquals(new String[]{"2", "C"}, resultMap.get(2).get(0));
    }
	
	@Test
	public void fullSample1(){
		
		List<Object[]> result = createQuery(fromFile("src/test/resources/sample.xml").named("simple_example"))
									.on(connection).execute(asList());
		
		assertEquals(3, result.size());
		assertArrayEquals(new Object[]{1, "1", "A"}, result.get(0));
		
	}

	@AfterClass
	public static void after() throws SQLException{
		connection.close();
	}
	
}
