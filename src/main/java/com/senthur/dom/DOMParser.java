package com.senthur.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMParser {
	
	public Element removeElement(Document document, String elementName, String attributeName, String attributeValue) {
		NodeList availableElements = document.getElementsByTagName(elementName);
		return removeElement(availableElements, elementName, attributeName, attributeValue);
	}
	
	private Element removeElement(NodeList availableElements, String elementName, String attributeName, String attributeValue) {
		Element element = null;
		for (int i = 0; i < availableElements.getLength(); i++) {
			element = (Element)availableElements.item(i);
			if (element.getAttribute(attributeName).equals(attributeValue)) {
				element.getParentNode().removeChild(element);
				break;
			}
		}
		return element;
	}
}
