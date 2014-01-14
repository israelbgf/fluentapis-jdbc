package org.fluentapis.jdbc;

import static org.fluentapis.jdbc.dsl.Statements.asList;
import static org.fluentapis.jdbc.dsl.Statements.asMap;
import static org.fluentapis.jdbc.dsl.Statements.createQuery;
import static org.fluentapis.jdbc.dsl.StatementsFileBuilder.fromFile;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.fluentapis.jdbc.util.MemoryDatabaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryTest extends MemoryDatabaseTest{
	
	private static final String SAMPLE_STATEMENT_1 
		= "select * from test where id = ? and first_name = ?";
	
	private static final String SAMPLE_STATEMENT_2 
		= "select * from test where id = :id1 and first_name = :name";

	private static final String SAMPLE_STATEMENT_3 
		= "select first_name, id, second_name from test";

	@BeforeClass
	public static void before() throws SQLException{
		java.sql.Statement statement = connection.createStatement();
		statement.execute("insert into test values(1, '1', 'A')");
		statement.execute("insert into test values(2, '1', 'B')");
		statement.execute("insert into test values(3, '2', 'C')");
	}
	
	@Test
    public void listWithPositionalParameters() throws SQLException{
		
		List<Object[]> resultList = createQuery(SAMPLE_STATEMENT_1).on(connection)
										.withValues(1, "1")
										.execute(asList());
		
		assertEquals(1, resultList.size());
		assertEquals(1L, resultList.get(0)[0]);
    }

	@Test
    public void listWithNamedParameters() throws SQLException{
		
		List<Object[]> resultList = createQuery(SAMPLE_STATEMENT_2).on(connection)
										.with("id1", 1)
										.with("name", "1")
										.execute(asList());
		
		assertEquals(1, resultList.size());
		assertEquals(1L, resultList.get(0)[0]);
    }
	
	@Test
    public void mapWithPositionalParameters() throws SQLException{
		
		Map<Object, List<Object[]>> resultMap = createQuery(SAMPLE_STATEMENT_3).on(connection)
													.execute(asMap());
		
		assertEquals(2, resultMap.size());
		assertArrayEquals(new Object[]{1L, "A"}, resultMap.get("1").get(0));
		assertArrayEquals(new Object[]{2L, "B"}, resultMap.get("1").get(1));
		assertArrayEquals(new Object[]{3L, "C"}, resultMap.get("2").get(0));
    }
	
	@Test
	public void fullSample1(){
		
		List<Object[]> result = createQuery(fromFile("src/test/resources/sample.xml").named("simple_example"))
									.on(connection).execute(asList());
		
		assertEquals(3, result.size());
		assertArrayEquals(new Object[]{1L, "1", "A"}, result.get(0));
		
	}
	
}
