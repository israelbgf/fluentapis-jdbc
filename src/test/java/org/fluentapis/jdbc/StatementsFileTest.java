package org.fluentapis.jdbc;

import static org.fluentapis.jdbc.dsl.StatementBuilderFactory.createQuery;
import static org.fluentapis.jdbc.dsl.StatementsFileBuilder.fromClassLoader;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.fluentapis.jdbc.dsl.StatementsFileBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class StatementsFileTest {
	
	private static final String SAMPLE_FILE = "src/test/resources/sample.xml";
	private static final String EXPECTED_STATEMENT = "select * from test";

	private static Connection connection;

	@BeforeClass
	public static void before() throws SQLException{
		connection = DriverManager.getConnection("jdbc:h2:mem:test");
		
		Statement statement = connection.createStatement();
		statement.execute("create table test(id int, first_name varchar(255), second_name varchar(255))");
	}
		
	
	@Test
	public void get() throws FileNotFoundException{
		StatementsFile file;
		
		file = new StatementsFile(SAMPLE_FILE);
		assertEquals(EXPECTED_STATEMENT, file.get("simple_example"));
		
		file = new StatementsFile(new File(SAMPLE_FILE));
		assertEquals(EXPECTED_STATEMENT, file.get("simple_example"));
	}

	@Test
	public void testDsl(){
		String statement;
		
		statement = StatementsFileBuilder.fromFile(SAMPLE_FILE).named("simple_example");
		assertEquals(EXPECTED_STATEMENT, statement);
		
		statement = StatementsFileBuilder.fromFile(new File(SAMPLE_FILE)).named("simple_example");
		assertEquals(EXPECTED_STATEMENT, statement);

		statement = StatementsFileBuilder.fromClassLoader("sample.xml").named("simple_example");
		assertEquals(EXPECTED_STATEMENT, statement);
		
		statement = StatementsFileBuilder.fromClassLoader("sample.xml").named("complex_example");
		assertEquals(EXPECTED_STATEMENT, statement);
		
	}
	
	@Test
	public void sampleQuery(){

		List<Object[]> list = createQuery(fromClassLoader("sample.xml").named("complex_example")).on(connection)
									.withValues("Seven", "Seventh")
									.execute();
		
		assertEquals(0, list.size());
		
	}
	
}
