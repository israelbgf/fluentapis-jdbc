package org.fluentapis.jdbc.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.fluentapis.jdbc.dsl.StatementNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class StatementsFile {

	private Document document;

	public StatementsFile(InputStream stream) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.parse(stream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		document.getDocumentElement().normalize();
	}
	
	public StatementsFile(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}

	public StatementsFile(String pathname) throws FileNotFoundException {
		this(new File(pathname));
	}

	public String get(String statementName) {
		NodeList nodes = document.getElementsByTagName("statement");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element= (Element) nodes.item(i);
			if(element.getAttribute("name").equals(statementName)){
				return element.getTextContent()
							.trim()
							.replaceAll("[\\n\\t\\r\\t]", " ")
							.replaceAll("\\s+", " "); // Some drivers like ORACLE doesn't allow special characters in the query.
			}
		}
		
		throw new StatementNotFoundException(statementName);
	}

}
