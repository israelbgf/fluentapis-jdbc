package org.fluentapis.jdbc;

import org.fluentapis.jdbc.file.StatementsFile;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({ 
			InsertUpdateDeleteTest.class, 
			QueryTest.class,
			StatementsFile.class 
		})
public class All {

}
