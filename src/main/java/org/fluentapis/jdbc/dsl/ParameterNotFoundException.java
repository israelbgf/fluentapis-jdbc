package org.fluentapis.jdbc.dsl;

@SuppressWarnings("serial")
public class ParameterNotFoundException extends RuntimeException {

	public ParameterNotFoundException(String parameterName) {
		super(String.format("Parameter named '%s' not found in the statement!", parameterName));
	}

}
