package servlet;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import config.Config;

public class DeploymentProxyClient {
	/** variable of RPCServiceClient */
	RPCServiceClient serviceClient;
	/** Manage service location */
	String location;
	/** variable of EndpointReference */
	EndpointReference targetEPR;

	public DeploymentProxyClient(String location) throws AxisFault {
		if (checkURL(location)) {
			this.location = location;
			serviceClient = new RPCServiceClient();

			targetEPR = new EndpointReference(location);
			System.out.println("targetEPR" + targetEPR);
		}
	}

	private boolean checkURL(String location) {

		return true;
	}

	private static OMElement buildSOAPEnvelope(String serviceName, File file) {

		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = omFactory.createOMNamespace(
				"http://servicedeployment.sdp.act.org.cn", "loadRemoteService");
		OMElement element = omFactory
				.createOMElement("loadRemoteService", omNs);
		OMElement fileContent = omFactory.createOMElement("fileContent", omNs);
		FileDataSource dataSource = new FileDataSource(file);
		DataHandler dh = new DataHandler(dataSource);
		OMText textData = omFactory.createOMText(dh, true);
		fileContent.addChild(textData);
		OMElement fileName = omFactory.createOMElement("fileName", omNs);
		fileName.setText(serviceName);
		OMElement fileType = omFactory.createOMElement("fileType", omNs);
		fileType.setText("bpmn");
		element.addChild(fileName);
		element.addChild(fileType);
		element.addChild(fileContent);
		System.out.println("buildSE");
		return element;
	}

	public static boolean deploy(String REGISTER_SERVICE_EPR, String filePath)
			throws ClassNotFoundException {
		RPCServiceClient serviceClient = null;
		String operationName = "loadRemoteService";
		File file = new File(filePath);
		int start = filePath.lastIndexOf("/");
		int end = filePath.lastIndexOf(".");
		System.out.println("##########start:" + start + "edn:" + end);
		System.out.println("##########filePath:" + filePath);
		String serviceName = filePath.substring(start + 1, end);

		OMElement message = buildSOAPEnvelope(serviceName, file);
		try {
			serviceClient = new RPCServiceClient();
		} catch (AxisFault e) {
			e.printStackTrace();
			return false;
		}
		Options options = serviceClient.getOptions();
		EndpointReference targetEPR = new EndpointReference(
				REGISTER_SERVICE_EPR);
		options.setTo(targetEPR);
		options.setAction("urn:" + operationName); //$NON-NLS-1$
		QName operationQName = new QName(
				"http://servicedeployment.sdp.act.org.cn", operationName);
		try {
			Class[] returnTypes = new Class[] {};
			Object[] opSendInfo = new Object[] { message };
			Object[] successful = serviceClient.invokeBlocking(operationQName,
					opSendInfo, returnTypes);
		} catch (AxisFault e) {
			System.out.println("Axis2出异常！");
			System.out.println(e);
			return false;
		}
		return true;
	}

	public static void main(String[] arg) {
		try {
			DeploymentProxyClient c = new DeploymentProxyClient(
					"http://192.168.3.66:8080/axis2/services/deploymentService");
			c
					.deploy(
							"http://192.168.3.66:8080/axis2/services/deploymentService",
							Config.getPath("bpmn/service1.bpmn"));
			System.out.println(Config.getPath("bpmn/service1.bpmn"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
