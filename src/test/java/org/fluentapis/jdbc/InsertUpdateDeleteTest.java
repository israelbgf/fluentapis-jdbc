package org.fluentapis.jdbc;

import static org.fluentapis.jdbc.dsl.Statements.returning;
import static org.fluentapis.jdbc.dsl.Statements.createDelete;
import static org.fluentapis.jdbc.dsl.Statements.createInsert;
import static org.fluentapis.jdbc.dsl.Statements.createQuery;
import static org.fluentapis.jdbc.dsl.Statements.createUpdate;

import java.sql.SQLException;
import java.sql.Statement;

import org.fluentapis.jdbc.util.MemoryDatabaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InsertUpdateDeleteTest extends MemoryDatabaseTest{

	@Before
	public void before() throws SQLException{
		Statement statement = connection.createStatement();
		statement.execute("delete from test");
		statement.execute("insert into test values(1, '1', 'A')");
	}
	
	@Test
	public void returningInsert() throws SQLException{
		
		Number returnedValue = createInsert("insert into test(first_name, second_name) values(?, ?)").on(connection)
									.withValues("Seven", "Seventh")
									.execute(returning("id").asNumber());
		
		connection.commit();
		
		Assert.assertEquals(2, returnedValue.intValue());
		Assert.assertEquals(1, createQuery("select * from test where id = ?").on(connection)
									.withValues(returnedValue)
									.execute().size());
	}
	
	@Test
	public void simpleInsert() throws SQLException{
		
		Integer returnedValue = createInsert("insert into test(first_name, second_name) values(?, ?)").on(connection)
									.withValues("Seven", "Seventh")
									.execute();
		
		connection.commit();
		
		Assert.assertEquals(1, returnedValue.intValue());
		Assert.assertEquals(1, createQuery("select * from test where id = ?").on(connection)
									.withValues(returnedValue)
									.execute().size());
	}

	@Test
	public void update(){
		
		int affectedRows = createUpdate("update test set second_name = 'Juca' where id = ?").on(connection)
									.withValues(1)
									.execute();

		
		Assert.assertEquals(1, affectedRows);
	}
	
	@Test
	public void delete(){
		
		int affectedRows = createDelete("delete test where id = :id").on(connection)
									.with("id", 1)
									.execute();

		
		Assert.assertEquals(1, affectedRows);
	}
	
}
