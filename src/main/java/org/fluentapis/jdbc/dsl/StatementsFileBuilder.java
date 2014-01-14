package org.fluentapis.jdbc.dsl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.fluentapis.jdbc.file.StatementsFile;

public class StatementsFileBuilder {

	private String pathname;
	private File file;
	private InputStream stream;

	public StatementsFileBuilder(String pathname) {
		this.pathname = pathname;
	}

	public StatementsFileBuilder(File file) {
		this.file = file;
	}

	public StatementsFileBuilder(InputStream stream) {
		this.stream = stream;
	}

	public String named(String name) {

		if (stream != null) {
			return new StatementsFile(stream).get(name);
		}

		try {
			if (pathname == null) {
				return new StatementsFile(file).get(name);
			} else {
				return new StatementsFile(pathname).get(name);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
