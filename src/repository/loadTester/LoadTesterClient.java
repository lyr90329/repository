package repository.loadTester;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class LoadTesterClient {
	private String type;
	private String category;
	private int num = 0;
	private int startNum = 0;
	private int endNum = 0;
	private int step = 0;
	private String accuracy;
	private String timeout;
	private String except_body;

	public String getExcept_body() {
		return except_body;
	}

	public void setExcept_body(String except_body) {
		this.except_body = except_body;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LoadTesterClient(String type, String category) {
		this.type = type;
		this.category = category;
	}

	public OMElement invoke(ArrayList<String> iplist, String caseType,
			String url, String operation, OMElement soap, String compUrl)
			throws Exception {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMElement testRequest = fac.createOMElement("testTask", null);
		OMAttribute attr_type;
		OMAttribute attr_category;
		if ("static".equals(type))
			attr_type = fac.createOMAttribute("type", null, "static");
		else if ("step".equals(type))
			attr_type = fac.createOMAttribute("type", null, "step");
		else if ("maximal".equals(type))
			attr_type = fac.createOMAttribute("type", null, "maximal");
		else
			throw new Exception();

		if ("single".equals(category))
			attr_category = fac.createOMAttribute("category", null, "single");
		else if ("multiple".equals(category))
			attr_category = fac.createOMAttribute("category", null, "multiple");
		else
			throw new Exception();

		testRequest.addAttribute(attr_type);
		testRequest.addAttribute(attr_category);

		OMElement ipList = fac.createOMElement("ipList", null);
		for (String ip : iplist) {
			OMElement ipElement = fac.createOMElement("ip", null);
			ipElement.setText(ip);
			ipList.addChild(ipElement);
		}
		testRequest.addChild(ipList);

		OMElement testCase = fac.createOMElement("testCase", null);
		OMAttribute invokeType = fac.createOMAttribute("type", null, caseType);
		OMAttribute invokeUrl = fac.createOMAttribute("url", null, url);
		OMAttribute invokeOperation = fac.createOMAttribute("operation", null,
				operation);
		testCase.addAttribute(invokeType);
		testCase.addAttribute(invokeUrl);
		testCase.addAttribute(invokeOperation);

		OMElement requestSoap = fac.createOMElement("requestSoap", null);
		requestSoap.addChild(soap);
		testCase.addChild(requestSoap);

		OMElement expextResult = fac.createOMElement("expectResult", null);
		OMAttribute attr_accuracy = fac.createOMAttribute("accuracy", null,
				this.accuracy);
		OMAttribute attr_timeout = fac.createOMAttribute("timeout", null,
				this.timeout);
		expextResult.addAttribute(attr_accuracy);
		expextResult.addAttribute(attr_timeout);
		OMElement resultBody = fac.createOMElement("body", null);
		resultBody.setText(this.except_body);
		expextResult.addChild(resultBody);
		testCase.addChild(expextResult);
		testRequest.addChild(testCase);

		if ("static".equals(type)) {
			OMElement numOM = fac.createOMElement("num", null);
			numOM.setText(String.valueOf(num));
			testRequest.addChild(numOM);
		}
		if ("step".equals(type) || "maximal".equals(type)) {
			OMElement startOM = fac.createOMElement("startnum", null);
			startOM.setText(String.valueOf(startNum));
			OMElement stepOM = fac.createOMElement("step", null);
			stepOM.setText(String.valueOf(step));
			testRequest.addChild(startOM);
			testRequest.addChild(stepOM);
		}
		if ("step".equals(type)) {
			OMElement endOM = fac.createOMElement("endnum", null);
			endOM.setText(String.valueOf(endNum));
			OMElement stepOM = fac.createOMElement("step", null);
			stepOM.setText(String.valueOf(step));
			testRequest.addChild(endOM);
			testRequest.addChild(stepOM);
		}

		System.out.println("request soap:" + testRequest);

		Options options = new Options();
		EndpointReference epr = new EndpointReference(compUrl);
		options.setTo(epr);
		options.setTimeOutInMilliSeconds(200000);
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);
		OMElement responseOME = sender.sendReceive(testRequest);

		return responseOME;
	}

	public static OMElement XML2OMElement(File file) {
		if (file == null) {
			System.err.println("Request file does not exit");
		} else {
			try {
				XMLStreamReader parser = XMLInputFactory.newInstance()
						.createXMLStreamReader(new FileInputStream(file));
				StAXOMBuilder builder = new StAXOMBuilder(parser);
				OMDocument doc = builder.getDocument();
				OMElement creElement = doc.getOMDocumentElement();
				System.out.println();
				return creElement;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XMLStreamException e) {
				e.printStackTrace();
			} catch (FactoryConfigurationError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public OMElement String2OMElement(String xmlStr, String encoding) {
		OMElement xmlValue;
		try {
			xmlValue = new StAXOMBuilder(new ByteArrayInputStream(xmlStr
					.getBytes(encoding))).getDocumentElement();
			return xmlValue;
		} catch (Exception e) {
			return null;
		}
	}

	public OMElement toInnerRequestSoap(String insoap, String serviceid,
			String operation, String userName) {
		OMElement initSoap = String2OMElement(insoap, "utf-8");
		OMFactory fac = OMAbstractFactory.getOMFactory();
		// add attribute of userID and userType
		// ((OMElement)initSoap.getChildrenWithLocalName("Body").next()).addAttribute("userID",
		// userName, null);
		// ((OMElement)initSoap.getChildrenWithLocalName("Body").next()).addAttribute("userType",
		// "owner", null);
		Iterator iter = initSoap.getChildElements();
		iter.next();
		OMElement body = (OMElement) iter.next();
		body.addAttribute("userID", userName, null);
		body.addAttribute("userType", "owner", null);
		// ((OMElement)initSoap.getChildrenWithLocalName("soap:Body").next()).addAttribute("userID",
		// userName, null);
		// ((OMElement)initSoap.getChildrenWithLocalName("soap:Body").next()).addAttribute("userType",
		// "owner", null);

		OMNamespace omNs = fac.createOMNamespace(
				"http://schemas.xmlsoap.org/soap/envelope/", "soap");
		OMElement envelope = fac.createOMElement("Envelope", omNs);
		OMElement head = fac.createOMElement("Header", omNs);
		OMElement bd = fac.createOMElement("Body", omNs);
		OMElement req = fac.createOMElement("invokeRequest", null);
		req.addAttribute("type", "serviceID", null);
		OMElement serviceID = fac.createOMElement("serviceID", null);
		OMElement ope = fac.createOMElement("operation", null);
		ope.setText(operation);
		serviceID.setText(serviceid);
		req.addChild(serviceID);
		req.addChild(ope);

		OMElement soap = fac.createOMElement("requestSoap", null);
		soap.addChild(initSoap);
		req.addChild(soap);
		bd.addChild(req);
		envelope.addChild(head);
		envelope.addChild(bd);

		System.out.println("warpped soap:" + envelope);
		return envelope;
	}

	public static void main(String[] args) {
		LoadTesterClient client = new LoadTesterClient("static", "single");

		client.setAccuracy("1");
		client.setExcept_body("15.770383220312253");
		client.setTimeout("10000");
		client.setNum(5);
		client.setStartNum(0);
		client.setEndNum(0);
		client.setStep(0);

		ArrayList<String> iplist = new ArrayList<String>();
		iplist.add("192.168.3.172");
		iplist.add("192.168.3.173");
		try {
			OMElement insoap = XML2OMElement(new File("C:\\testData\\soap.xml"));
			// OMElement
			// wrapped=client.toInnerRequestSoap(insoap,"WS_1748","convert","jianyu");
			OMElement rs = client.invoke(iplist, "external",
					"http://192.168.3.114:8080/axis2/services/ConvertMoney",
					"convert", insoap,
					"http://124.205.18.169:8192/cloudtest/TestJobDeployment/");
			System.out.println("���Խ��" + rs);

		} catch (Exception e) {
			System.out.println("there are something wrong!");
			e.printStackTrace();
		}
	}

}