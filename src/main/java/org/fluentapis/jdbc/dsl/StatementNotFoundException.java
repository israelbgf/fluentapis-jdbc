package org.fluentapis.jdbc.dsl;

@SuppressWarnings("serial")
public class StatementNotFoundException extends RuntimeException {

	public StatementNotFoundException(String parameterName) {
		super(String.format("Statement named '%s' not found in the statement!", parameterName));
	}

}
