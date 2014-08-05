package org.enqu.xml.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.InputStreamEntity;
import org.dom4j.DocumentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtils {

	private static Log logger = LogFactory.getLog(XMLUtils.class);

	public static Transformer createNewTranformer() {
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			return factory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't create the transformer: " + e.getMessage());

			return null;
		}
	}

	public static Document createNewDocument() {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't create a new document", e);
			return null;
		}
	}

	public static InputStream retrieveInputStream(Document doc) {

		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);

		Transformer transformer = XMLUtils.createNewTranformer();
		try {
			transformer.transform(source, result);

			return new ByteArrayInputStream(writer.toString().getBytes("UTF-8"));
		} catch (TransformerException e) {
			logger.warn(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage());
		}

		return null;
	}

	public static Document constructXml(InputStream input) {

		if (input == null) {
			logger
					.warn("The input stream is null, so can't construct the document");
			return null;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(input);

			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't create the document from the input stream", e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't create the document from the input stream", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't create the document from the input stream", e);
		}

		return null;

	}

	public static InputStreamEntity retrieveInputStreamEntity(Document doc) {

		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);

		Transformer transformer = XMLUtils.createNewTranformer();
		try {
			transformer.transform(source, result);

			InputStream input = new ByteArrayInputStream(writer.toString()
					.getBytes("UTF-8"));
			int l = writer.toString().getBytes().length;
			InputStreamEntity entity = new InputStreamEntity(input, l);
			return entity;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage());
		}

		return null;

	}

	private final static String internal = " ";

	public static void logXMLDocument(Document doc, OutputStream output) {
		logXMLNode(doc, output);
	}

	public static String retrieveDocumentAsString(Document doc) {
		Node node = doc.getDocumentElement();
		return retrieveNodeAsString(node);
	}

	public static String retrieveNodeAsString(Node node) {
		String nodeStr = new String("<");
		nodeStr += node.getNodeName() + internal;
		if (node.hasAttributes()) {
			NamedNodeMap attrs = node.getAttributes();
			// add the attrubite name-value pairs
			for (int i = 0; i < attrs.getLength(); i++) {
				Node a = attrs.item(i);
				nodeStr += a.getNodeName() + "=" + a.getNodeValue() + internal;
			}
		}

		if (node.hasChildNodes()) {
			nodeStr += ">\n";
			NodeList ns = node.getChildNodes();
			for (int i = 0; i < ns.getLength(); i++) {
				nodeStr += logXMLSubNode(ns.item(i), 1);
			}
			nodeStr += "<" + node.getNodeName() + "/>\n";
		} else {
			nodeStr += "/>\n";
		}
		return nodeStr;
	}

	public static void logXMLNode(Document doc, OutputStream output) {
		String nodeStr = retrieveDocumentAsString(doc);

		try {
			output.write(nodeStr.getBytes());
			output.close();
		} catch (IOException ioe) {
			System.out
					.println("There happens some error when reading the xml fragment");
		}
	}

	/**
	 * output the xml fragment into the output stream maybe as a string or bytes
	 * 
	 * @param doc
	 * @param output
	 */
	public static void logXMLFragment(Document doc, OutputStream output) {
		logXMLNode(doc, output);

	}

	private static String logXMLSubNode(Node node, int deepth) {

		int i;
		String nodeStr = new String();
		String interStr = new String();
		for (i = 0; i < deepth; i++)
			interStr += "\t";

		nodeStr += interStr + "<" + node.getNodeName() + internal;
		if (node.hasAttributes()) {
			NamedNodeMap attrs = node.getAttributes();

			// add the attrubite name-value pairs
			for (i = 0; i < attrs.getLength(); i++) {
				Node a = attrs.item(i);
				nodeStr += a.getNodeName() + "=" + a.getNodeValue() + internal;
			}
		}
		if (node.hasChildNodes()) {
			nodeStr += ">\n";
			NodeList ns = node.getChildNodes();
			for (i = 0; i < ns.getLength(); i++) {
				nodeStr += logXMLSubNode(ns.item(i), deepth + 1);
			}
			nodeStr += interStr + "</" + node.getNodeName() + ">\n";
		} else {
			if (node.getNodeValue() != null) {
				nodeStr += ">" + node.getNodeValue() + "<" + node.getNodeName();
			}
			nodeStr += "/>\n";
		}
		return nodeStr;
	}

	public static Document readXMLFromFile(String filePath) throws Exception {
		File dFile = new File(filePath);
		if (!dFile.exists()) {
			throw new Exception("The target file doesn't exist!");

		}
		return readXMLFromFile(dFile);
	}

	public static Document readXMLFromFile(File dFile) throws Exception {
		if (dFile == null) {
			throw new Exception("The file point is null!");
		}
		if (!dFile.exists()) {
			throw new Exception("The target file doesn't exist!");
		}
		if (dFile.isDirectory() || !dFile.getName().endsWith(".xml")) {
			throw new Exception(
					"The file is not a xml file! Maybe it is a directory!");
		}

		Document doc = null;
		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = builder.parse(dFile);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return doc;
	}

	public static org.dom4j.Document newDocument() {
		return DocumentHelper.createDocument();
	}

}
