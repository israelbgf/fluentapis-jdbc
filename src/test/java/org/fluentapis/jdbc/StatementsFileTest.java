package org.fluentapis.jdbc;

import static org.fluentapis.jdbc.dsl.FluentJDBC.createQuery;
import static org.fluentapis.jdbc.dsl.FluentJDBC.fromClassLoader;
import static org.fluentapis.jdbc.dsl.FluentJDBC.fromFile;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.fluentapis.jdbc.file.StatementsFile;
import org.fluentapis.jdbc.util.MemoryDatabaseTest;
import org.junit.Test;

public class StatementsFileTest extends MemoryDatabaseTest {
	
	private static final String SAMPLE_FILE = "src/test/resources/sample.xml";
	private static final String EXPECTED_STATEMENT = "select * from test";

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
		
		statement = fromFile(SAMPLE_FILE).named("simple_example");
		assertEquals(EXPECTED_STATEMENT, statement);
		
		statement = fromFile(new File(SAMPLE_FILE)).named("simple_example");
		assertEquals(EXPECTED_STATEMENT, statement);

		statement = fromClassLoader("sample.xml").named("simple_example");
		assertEquals(EXPECTED_STATEMENT, statement);
		
		statement = fromClassLoader("sample.xml").named("complex_example");
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
