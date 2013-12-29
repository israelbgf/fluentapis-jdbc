package org.fluentapis.jdbc;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class StatementFile {

	private Document document;

	public StatementFile(File file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.parse(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		document.getDocumentElement().normalize();
	}

	public StatementFile(String pathname) {
		this(new File(pathname));
	}

	public String get(String name) {
		NodeList nodes = document.getElementsByTagName("statement");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element= (Element) nodes.item(i);
			if(element.getAttribute("name").equals(name)){
				return element.getTextContent().trim();
			}
		}
		
		throw new StatementNotFoundException(name);
	}

}
