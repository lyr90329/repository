package servlet;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.engine.DefaultObjectSupplier;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import cn.org.act.sdp.bpmnengine.common.*;

import java.util.*;
/**
 * ExecuteResultQueryingClient use to query the execution result .
 * 
 * <pre>
 * ExecuteResultQueryingClient client = new ExecuteResultQueryingClient(url);
 * </pre>
 * 
 * @author <a href="mailto:zoujn@act.buaa.edu.cn">Zou Jianing</a>
 * @author <a href="mailto:xiongyk@act.buaa.edu.cn">Xiong Yunkun</a>
 * @version $Revision: 1.1 $ $Date: 2010-06-25 02:27:01 $
 */
public class ExecuteResultQueryingClient {

	/**
	 * set the destination service
	 */
	private EndpointReference targetEPR;
	/**
	 * set the soap options,
	 */
	private Options options;
	/**
	 * the client
	 */
	private ServiceClient sender;
	/**
	 * the omFactory which contains OMNamespace
	 */
	private OMFactory omFactory;
	/**
	 * the OMNamespace
	 */
	private OMNamespace omNs;
	/**
	 * log the information
	 */
	static Logger logger = Logger.getLogger(ExecuteResultQueryingClient.class
			.getName());
	/**
	 * init the ExecuteResultStoringClient
	 * 
	 * @param url
	 *            the service address to invoke in the bus
	 */
	public ExecuteResultQueryingClient(String url) {
		try {
			PropertyConfigurator.configure("log4j.properties");
			targetEPR = new EndpointReference(url);
			options = new Options();
			options.setTo(targetEPR);
			options.setAction("urn:getExecuteResult");
			sender = new ServiceClient();
			sender.setOptions(options);

			omFactory = OMAbstractFactory.getOMFactory();
			omNs = omFactory.createOMNamespace(
					"http://repositoryservice.sdp.act.org.cn",
					"tns");

		} catch (Exception axisFault) {
			axisFault.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * get the job's result file's absolute path
	 * 
	 * @param jobId
	 *            the job's id
	 * @throws Exception
	 */
	public LinkedList<Parameter> getExecuteResult(long jobId) {
		logger.info("***********总线上的一次查询,jobId：" + jobId + "*********");
		// String resultFilePath = null;

		// 构造报文
		OMElement data = omFactory.createOMElement("getExecuteResult", omNs);
		OMElement jId = omFactory.createOMElement("jobId", omNs);
		jId.setText(Long.toString(jobId));
		data.addChild(jId);
		logger.info(data);

		OMElement response = null;
		try {
			response = sender.sendReceive(data);
		} catch (AxisFault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("执行的结果文件："+response);
		OMElement result = response;
		logger.info(result);
		logger.info("*********************************");
		LinkedList<Parameter> parmData = new LinkedList();
		OMElement omElement1 = getFirstChildElementWithName(result,
				"compositeServiceName");
		String compositeServiceName = omElement1.getText();
		logger.info(compositeServiceName);
		OMElement omElement2 = getFirstChildElementWithName(result,
				"Parameters");
		logger.info(omElement2);
		OMElement omElement3 = getFirstChildElementWithName(omElement2,
				"parameter");
		logger.info(omElement3);
		while (omElement3 != null) {
			logger.info(omElement3);
			Parameter parameter = null;
			try {
				parameter = (Parameter) BeanUtil.processObject(omElement3,
						Parameter.class, null, true,
						new DefaultObjectSupplier());
			} catch (AxisFault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(parameter);
			parmData.add(parameter);
			omElement3 = (OMElement) omElement3.getNextOMSibling();
		}
		System.out.println("jobId" + "  " + "compositeServiceName" + "  "
				+ "resultName" + "  " + "resultType" + "  " + "resultValue");
		for (int i = 0; i < parmData.size(); i++) {
			Parameter parm = parmData.get(i);
			System.out.println(jobId + "  " + compositeServiceName + "  "
					+ parm.getParamName() + "  " + parm.getParamType() + "  "
					+ parm.getParamValue());
		}
		return parmData;
	}
	/**
	 * get first child element with input name from input element
	 * 
	 * @param element
	 *            contains the child element to seperate with input name
	 * @param localPart
	 *            use to tag the element to seperate
	 * @return omElement
	 */
	private OMElement getFirstChildElementWithName(OMElement element,
			String localPart) {

		for (Iterator it = element.getChildElements(); it.hasNext();) {

			OMNode omNode = (OMNode) it.next();
			if (omNode.getType() == OMNode.ELEMENT_NODE) {
				OMElement omElement = (OMElement) omNode;
				if (omElement.getLocalName().equalsIgnoreCase(localPart)) {
					return omElement;
				}

			}
		}
		return null;
	}

	public static void main(String[] args) {
		ExecuteResultQueryingClient client = new ExecuteResultQueryingClient(
				"http://192.168.3.133:8080/axis2/services/ResultService");
		client.getExecuteResult(249);
	}
}
