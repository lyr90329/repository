package loginfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import config.Config;

import test.cn.sdp.act.appengine.samanager.client.BPMNExecutionClient;
import test.cn.sdp.act.appengine.samanager.client.Parameter;

public class LogFileComponentClient {

	/**
	 * @param args
	 */
	private final String soapNS = "http://www.w3.org/2003/05/soap-envelope";
	private final String executeNS = "http://servicebus.sdp.act.org.cn";

	private Document createRequest(String serviceName, String userName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		try {

			builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElement("root");
			doc.appendChild(root);

			Element userID = doc.createElement(Constants.USER_ID);
			userID.setTextContent(userName);
			root.appendChild(userID);

			Element type = doc.createElement(Constants.TYPE);
			type.setTextContent(Constants.USERDEBUGLOG);
			root.appendChild(type);

			Element wsName = doc.createElement("wsName");
			wsName.setTextContent(serviceName);
			root.appendChild(wsName);

			return doc;
		} catch (ParserConfigurationException e) {
			System.out.println("Can't create the Execution Request Document"
					+ e.getMessage());
		}
		return null;

	}

	public File createRequestFile(String serviceName, String userName)
			throws IOException {
		Document doc = createRequest(serviceName, userName);
		if (doc == null) {
			System.out.println("Can't create the document for bpmn execution");
			return null;
		}

		File t = new File("tmp.xml");
		if (!t.exists()) {
			t.createNewFile();
		}

		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer transformer = factory.newTransformer();
			DOMSource source = new DOMSource(doc);

			FileOutputStream output = new FileOutputStream(t);
			StreamResult sr = new StreamResult(output);

			transformer.transform(source, sr);
			output.close();

			return t;
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public static void main(String[] args) {

		LogFileComponentClient client = new LogFileComponentClient();
		try {
			System.out.println("enter log info!");
			String logContent = client.testLogFile("WS_1551", "Emma");
			System.out.println("LogInfo:" + logContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String testLogFile(String serviceName, String userName)
			throws Exception {

		File xmlFile = createRequestFile(serviceName, userName);

		PostMethod post = new PostMethod(
				Config.getLogFileUrl());
		RequestEntity entity = new FileRequestEntity(xmlFile,
				"text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		HttpClient client = new HttpClient();

		try {
			int result = client.executeMethod(post);
			System.out.println("Response status code: " + result);
			System.out.println("Response body:\n "
					+ post.getResponseBodyAsString());
		} finally {
			post.releaseConnection();
		}
		return post.getResponseBodyAsString();
	}

}
