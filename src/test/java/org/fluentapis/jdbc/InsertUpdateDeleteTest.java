package org.fluentapis.jdbc;

import static org.fluentapis.jdbc.dsl.ReturningParameterBuilder.returning;
import static org.fluentapis.jdbc.dsl.StatementBuilderFactory.createDelete;
import static org.fluentapis.jdbc.dsl.StatementBuilderFactory.createInsert;
import static org.fluentapis.jdbc.dsl.StatementBuilderFactory.createQuery;
import static org.fluentapis.jdbc.dsl.StatementBuilderFactory.createUpdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InsertUpdateDeleteTest {

	private static Connection connection;

	@BeforeClass
	public static void beforeClass() throws SQLException{
		connection = DriverManager.getConnection("jdbc:h2:mem:test");
		
		Statement statement = connection.createStatement();
		statement.execute("create table test(id identity, first_name varchar(255), second_name varchar(255))");
	}
	
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
	
	@AfterClass
	public static void after() throws SQLException{
		connection.close();
	}
	
}
