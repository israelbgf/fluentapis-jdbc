package org.fluentapis.jdbc.dsl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.fluentapis.jdbc.file.StatementsFile;

public class StatementsFileBuilder {

	public static StatementBuilder fromClassLoader(String pathname) {
		InputStream inputStream = StatementsFileBuilder.class.getClassLoader().getResourceAsStream(pathname);
		if(inputStream == null){
			throw new RuntimeException("File not found with current classloader.");
		}
		return new StatementBuilder(inputStream);
	}
	
	public static StatementBuilder fromFile(String pathname) {
		return new StatementBuilder(pathname);
	}

	public static StatementBuilder fromFile(File file) {
		return new StatementBuilder(file);
	}
	
	public static class StatementBuilder{

		private String pathname;
		private File file;
		private InputStream stream;

		public StatementBuilder(String pathname) {
			this.pathname = pathname;
		}
		
		public StatementBuilder(File file) {
			this.file = file;
		}

		public StatementBuilder(InputStream stream) {
			this.stream = stream;
		}

		public String named(String name){
			
			if(stream != null){
				return new StatementsFile(stream).get(name);
			}
			
			try {
				if(pathname == null){
					return new StatementsFile(file).get(name);
				}else{
					return new StatementsFile(pathname).get(name);
				}
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
}
