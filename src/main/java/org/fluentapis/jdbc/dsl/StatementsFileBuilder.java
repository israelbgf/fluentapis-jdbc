package org.fluentapis.jdbc.dsl;

import java.io.File;

import org.fluentapis.jdbc.StatementsFile;

public class StatementsFileBuilder {

	public static StatementBuilder fromFile(String pathname) {
		return new StatementBuilder(pathname);
	}

	public static StatementBuilder fromFile(File file) {
		return new StatementBuilder(file);
	}
	
	public static class StatementBuilder{

		private String pathname;
		private File file;

		public StatementBuilder(String pathname) {
			this.pathname = pathname;
		}
		
		public StatementBuilder(File file) {
			this.file = file;
		}

		public String named(String name){
			if(pathname == null){
				return new StatementsFile(file).get(name);
			}else{
				return new StatementsFile(pathname).get(name);
			}
		}
		
	}
	
}
