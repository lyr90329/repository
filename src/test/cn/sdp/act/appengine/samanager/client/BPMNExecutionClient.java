package test.cn.sdp.act.appengine.samanager.client;

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

public class BPMNExecutionClient {

	private final String soapNS = "http://www.w3.org/2003/05/soap-envelope";
	private final String executeNS = "http://servicebus.sdp.act.org.cn";

	private Document createRequest(String serviceName, String url,
			String serviceId, List<Parameter> params) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		try {

			builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element root = doc.createElementNS(soapNS, "env:Envelope");
			doc.appendChild(root);

			Element body = doc.createElement("env:Body");
			root.appendChild(body);

			Element firstExecute = doc.createElementNS(executeNS,
					"execute:execute");
			firstExecute.setAttribute("serviceID", serviceId);
			firstExecute.setAttribute("serviceName", serviceName);
			body.appendChild(firstExecute);

			Element secExecute = doc.createElement("execute:execute");
			firstExecute.appendChild(secExecute);

			Element info = doc.createElement("InfoUtil");
			secExecute.appendChild(info);

			Element csName = doc.createElement("compositeServiceName");
			csName.setTextContent(serviceName);
			info.appendChild(csName);

			Element jobId = doc.createElement("jobId");
			jobId.setTextContent("-1");
			info.appendChild(jobId);

			// execute IP
			Element ip = doc.createElement("execute:IP");
			ip.setTextContent(url);
			info.appendChild(ip);

			Element psElement = doc.createElement("execute:parameters");
			secExecute.appendChild(psElement);

			if (params != null) {
				for (Parameter p : params) {
					Element pElement = doc.createElement("parameter");
					psElement.appendChild(pElement);

					Element cElement = doc.createElement("counter");
					cElement.setTextContent("0");
					pElement.appendChild(cElement);

					Element fElement = doc.createElement("messageFlag");
					fElement.setTextContent("false");
					pElement.appendChild(fElement);

					Element pnElment = doc.createElement("paramName");
					pnElment.setTextContent(p.getParamName());
					pElement.appendChild(pnElment);

					Element ptElment = doc.createElement("paramType");
					ptElment.setTextContent(p.getParamType());
					pElement.appendChild(ptElment);

					Element pvElment = doc.createElement("paramValue");
					pvElment.setTextContent(p.getParamValue());
					pElement.appendChild(pvElment);
					System.out.println("--------------\n"+p.getParamName()+"  "+p.getParamValue());
				}
			}

			return doc;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't create the Execution Request Document"
					+ e.getMessage());

		}
		return null;

	}

	public File createRequestFile(String serviceName, String url,
			String serviceId, List<Parameter> params) throws IOException {
		Document doc = createRequest(serviceName, url, serviceId, params);
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
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return null;
	}

	public String testBPMNExecutionQuery(String serviceName, String bpmnId,
			List<Parameter> ps) throws Exception {

		String url = Config.getBPMNMonitorInfoFeedbackReceiverUrl();

		File xmlFile = createRequestFile(serviceName, url, bpmnId, ps);

		PostMethod post = new PostMethod(Config.getBPMNExecuteUrl());
//		RequestEntity entity = new FileRequestEntity(xmlFile,
//				"text/xml; charset=ISO-8859-1");
		RequestEntity entity = new FileRequestEntity(xmlFile,
				"text/xml; charset=utf-8");
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

	public static void main(String[] args) {

		BPMNExecutionClient client = new BPMNExecutionClient();

	}
}
