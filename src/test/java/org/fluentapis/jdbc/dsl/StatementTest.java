package org.fluentapis.jdbc.dsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fluentapis.jdbc.Statement;
import org.junit.Test;

public class StatementTest {

	private String input = "select * from test where name = :name1 and id = :id2 and id <> ?";
	
	@Test
	public void stringParsing(){
		Statement statement = new Statement(input);
		assertEquals("select * from test where name = ? and id = ? and id <> ?", statement.getNativeStatement());
	}

	@Test
	public void parametersParsing(){
		Statement statement = new Statement(input);
		
		List<String> parameters = statement.getParameters();
		System.out.println(parameters);
		
		assertEquals(3, parameters.size());
		assertTrue(parameters.contains("name1"));
		assertTrue(parameters.contains("id2"));
		assertTrue(parameters.contains("?3"));
	}

	
}
