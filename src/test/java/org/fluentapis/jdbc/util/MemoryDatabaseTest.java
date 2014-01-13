package org.fluentapis.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class MemoryDatabaseTest {

	protected static Connection connection;

	@BeforeClass
	public static void beforeClass() throws SQLException{
		connection = DriverManager.getConnection("jdbc:h2:mem:test");
		
		Statement statement = connection.createStatement();
		statement.execute("create table test(id identity, first_name varchar(255), second_name varchar(255))");
	}
	
	
	@AfterClass
	public static void after() throws SQLException{
		connection.close();
	}
	
}
