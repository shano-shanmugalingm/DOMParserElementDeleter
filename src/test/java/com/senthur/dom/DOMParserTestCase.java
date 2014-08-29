package com.senthur.dom;

import java.io.IOException;
import java.net.URLClassLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParserTestCase {
	
	@Test
	public void testDeleteElement() {
		DOMParser domParser = new DOMParser();
		Document document = getTestDocument(false);
		Element removedElement = domParser.removeElement(document, "test", "id", "RemoveMe");
		printDocument(document);
		printDocument(removedElement);
	}

	@Test
	public void testReadComments() {
		Document document = getTestDocument(true);
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
	        if (nodeList.item(i).getNodeType() == Element.COMMENT_NODE) {
	            Comment comment = (Comment) nodeList.item(i);
	            System.out.println(comment.getData());
	        }
	    }
	}
	
	@Test
	public void testWriteComments() {
		Document document = getTestDocument(true);
		NodeList nodeList = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Element.ELEMENT_NODE) {
				Comment elementComment = document.createComment("This is a comment for testing");
				((Element)nodeList.item(i)).insertBefore(elementComment, nodeList.item(i).getFirstChild());
			}
	    }
		printDocument(document);
	}
	
	private Document getTestDocument(boolean enableComments) {
		Document document = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		if (!enableComments)
			documentBuilderFactory.setIgnoringComments(true);
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(URLClassLoader.getSystemResourceAsStream("TestXML.xml"));
			document.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			Assert.fail("Exception : " + e.getMessage());
		} catch (SAXException e) {
			Assert.fail("Exception : " + e.getMessage());
		} catch (IOException e) {
			Assert.fail("Exception : " + e.getMessage());
		}
		return document;
	}
	
	private void printDocument(Node node) {
		TransformerFactory tranFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tranFactory.newTransformer();
			Source src = new DOMSource(node);
			Result dest = new StreamResult(System.out);
			transformer.transform(src, dest);
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
	}
	
}
