package org.fluentapis.jdbc;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.fluentapis.jdbc.dsl.StatementsFileBuilder;
import org.junit.Test;

public class StatementsFileTest {
	
	private static final String SAMPLE_FILE = "src/test/resources/sample.xml";
	private static final String EXPECTED_STATEMENT = "select * from test";

	@Test
	public void get(){
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

	}
	
}
