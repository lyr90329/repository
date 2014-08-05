package WSDL_Structure;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.impl.llom.factory.OMXMLBuilderFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.util.StreamWrapper;
import org.apache.log4j.Logger;

public class ServiceIn {
	private EndpointReference targetEPR;
	private RPCServiceClient serviceClient = null;
	private String location = null;
	static Logger logger = Logger.getLogger(ServiceIn.class.getName());

	public ServiceIn(String location) throws AxisFault {
		if (checkURL(location)) {
			this.location = location;
			serviceClient = new RPCServiceClient();
			targetEPR = new EndpointReference(location);
		}
	}

	public long invokeServiceExecute(List<WSDL_ParameterInfo> list)
			throws AxisFault {
		targetEPR = new EndpointReference(location);

		Options options = serviceClient.getOptions();
		options.setTo(targetEPR);
		options.setAction("http://ws.fraudlabs.com/MailBoxValidator");
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);

		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace("http://ws.fraudlabs.com/",
				"MailBoxValidator");

		OMElement data = fac.createOMElement("MailBoxValidator", omNs);
		OMElement executeInfo = buildExecuteInforEnvelope(list);
		data.addChild(executeInfo);
		OMElement response = sender.sendReceive(data);
		logger.info(response);
		long jobId = Long.parseLong(response.getText());
		return jobId;
	}

	/*
	 * public long invokeServiceExecute(String compositeServiceName) throws
	 * AxisFault { Options options = serviceClient.getOptions();
	 * options.setTo(targetEPR); options.setAction("http://ws.fraudlabs.com/");
	 * QName opName = new QName("http://ws.fraudlabs.com/",
	 * "http://ws.fraudlabs.com/"); String[] in = { compositeServiceName };
	 * OMElement response = serviceClient.invokeBlocking(opName, in);
	 * System.out.println(response); long jobId =
	 * Long.parseLong(response.getText()); return jobId; }
	 * 
	 * public boolean invokeServiceExecute(long jobId, String
	 * compositeServiceName, LinkedList list) throws AxisFault { targetEPR = new
	 * EndpointReference(location);
	 * 
	 * Options options = serviceClient.getOptions(); options.setTo(targetEPR);
	 * options.setAction("urn:typeInputParam"); ServiceClient sender = new
	 * ServiceClient(); sender.setOptions(options);
	 * 
	 * OMNamespace omNs = fac.createOMNamespace(
	 * "http://serviceExecuteProxy.sdp.act.org.cn", "typeInputParam");
	 * 
	 * OMElement data = fac.createOMElement("typeInputParam", omNs); OMElement
	 * executeInfo = buildExecuteInforEnvelope(jobId, compositeServiceName,
	 * list); data.addChild(executeInfo); OMElement response =
	 * sender.sendReceive(data); boolean isSuccess = new
	 * Boolean(response.getText()); return isSuccess; }
	 */

	private boolean checkURL(String location) {
		/*
		 * try { if (location.startsWith("http://") && location.indexOf(":" +
		 * "8080" + "/" + "axis2/services/") != -1) { new URL(location); return
		 * true; } return false; } catch (MalformedURLException e) { return
		 * false; }
		 */
		return true;
	}

	private static OMElement buildExecuteInforEnvelope(
			List<WSDL_ParameterInfo> parmData) {

		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = omFactory.createOMNamespace(
				"http://ws.fraudlabs.com/", "ailBoxValidator");
		OMElement executeInfo = omFactory.createOMElement("ailBoxValidator",
				omNs);
		// InfoUtil infoArray = new InfoUtil();
		// infoArray.setCompositeServiceName(compositeServiceName);

		/*
		 * javax.xml.stream.XMLStreamReader reader = BeanUtil
		 * .getPullParser(parmData); StreamWrapper parser = new
		 * StreamWrapper(reader); StAXOMBuilder stAXOMBuilder =
		 * OMXMLBuilderFactory.createStAXOMBuilder(
		 * OMAbstractFactory.getOMFactory(), parser); OMElement invokeInfo =
		 * stAXOMBuilder.getDocumentElement(); executeInfo.addChild(invokeInfo);
		 */
		OMElement parameters = buildParamDataEnvelope(parmData);
		if (parameters != null) {

			executeInfo.addChild(parameters);
		}
		logger.info(parameters);
		return executeInfo;
	}

	/*
	 * private static OMElement buildExecuteInforEnvelope(long jobId, String
	 * compositeServiceName, LinkedList<WSDL_ParameterInfo> parmData) {
	 * 
	 * OMFactory omFactory = OMAbstractFactory.getOMFactory(); OMNamespace omNs
	 * = omFactory.createOMNamespace(
	 * "http://serviceExecuteProxy.sdp.act.org.cn", "execute"); OMElement
	 * executeInfo = omFactory.createOMElement("execute", omNs); InfoUtil
	 * infoUtil = new InfoUtil();
	 * infoUtil.setCompositeServiceName(compositeServiceName);
	 * infoUtil.setJobId(jobId); javax.xml.stream.XMLStreamReader reader =
	 * BeanUtil .getPullParser(infoUtil); StreamWrapper parser = new
	 * StreamWrapper(reader); StAXOMBuilder stAXOMBuilder =
	 * OMXMLBuilderFactory.createStAXOMBuilder(
	 * OMAbstractFactory.getOMFactory(), parser); OMElement invokeInfo =
	 * stAXOMBuilder.getDocumentElement(); executeInfo.addChild(invokeInfo);
	 * OMElement parameters = buildParamDataEnvelope(parmData); if (parameters
	 * != null) {
	 * 
	 * executeInfo.addChild(parameters); } logger.info(parameters); return
	 * executeInfo; }
	 */

	private static OMElement buildParamDataEnvelope(
			List<WSDL_ParameterInfo> paramData) {

		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = omFactory.createOMNamespace(
				"http://ws.fraudlabs.com/", "MailBoxValidator");
		OMElement parameters = omFactory.createOMElement("parameters", omNs);

		if (paramData == null) {
			return null;
		}

		for (Iterator<WSDL_ParameterInfo> it = paramData.iterator(); it
				.hasNext();) {

			WSDL_ParameterInfo p = it.next();
			javax.xml.stream.XMLStreamReader reader = BeanUtil.getPullParser(p);
			StreamWrapper parser = new StreamWrapper(reader);
			StAXOMBuilder stAXOMBuilder = OMXMLBuilderFactory
					.createStAXOMBuilder(OMAbstractFactory.getOMFactory(),
							parser);
			OMElement parameter = stAXOMBuilder.getDocumentElement();
			parameters.addChild(parameter);

		}

		return parameters;
	}

	public static void main(String[] args) throws AxisFault {

		ServiceIn client = new ServiceIn(
				"http://ws2.fraudlabs.com/mailboxvalidator.asmx?wsdl");

		List<WSDL_ParameterInfo> parameterList = new LinkedList<WSDL_ParameterInfo>();

		WSDL_ParameterInfo clientName = new WSDL_ParameterInfo();
		WSDL_ParameterInfo memberId = new WSDL_ParameterInfo();
		WSDL_ParameterInfo bookIsbn = new WSDL_ParameterInfo();

		clientName.setName("EMAIL");
		clientName.setType("String");
		clientName.setValue("wuqian30000@yahoo.com.cn");

		memberId.setName("LICENCSE");
		memberId.setType("String");
		memberId.setValue("123456");

		parameterList.add(clientName);
		parameterList.add(memberId);
		parameterList.add(bookIsbn);
		long jobId = client.invokeServiceExecute(parameterList);
		System.out.println(jobId);

	}
}
