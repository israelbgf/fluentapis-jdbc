package org.fluentapis.jdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Statement {

	private String originalStatement;
	private String nativeStatement;
	private List<String> parameters = new ArrayList<String>();

	public Statement(String statement) {
		this.originalStatement = statement;
	}

	private String convertToNativeStatement() {
		if(nativeStatement == null){
			Pattern pattern = Pattern.compile("(:\\w+|\\?)");
			Matcher matcher = pattern.matcher(originalStatement);
			
			int index = 1;
			while(matcher.find()){
				String name = matcher.group(1);
				parameters.add(name.startsWith(":") ? name.substring(1) : name + index);	
				index++;
			}
			
			nativeStatement = originalStatement.replaceAll(":\\w+", "?");
		}
		return nativeStatement;
	}

	public List<String> getParameters() {
		if(nativeStatement == null){
			convertToNativeStatement();
		}
		return Collections.unmodifiableList(parameters);
	}
	
	public String getNativeStatement() {
		if(nativeStatement == null){
			convertToNativeStatement();
		}
		return nativeStatement;
	}
	
	public String getOriginalStatement() {
		return originalStatement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nativeStatement == null) ? 0 : nativeStatement.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statement other = (Statement) obj;
		return getNativeStatement().equals(other.getNativeStatement());
	}
	
	@Override
	public String toString() {
		return getNativeStatement();
	}
	
}
