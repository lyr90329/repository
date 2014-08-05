package org.cn.act.sdp.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.cn.act.sdp.app.AppInfo;

public class Client {
	private String targetUrl;

	public Client(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public List<AppInfo> appQuery(String userName) {
		OMElement request = OMBuilder.buildOMForQuery(userName);
		EndpointReference targetEPR = buildEPR();
		Options options = buildOptions(targetEPR);

		List<AppInfo> apps = new ArrayList<AppInfo>();
		RPCServiceClient client;
		try {
			client = new RPCServiceClient();
			client.setOptions(options);
			OMElement response = client.sendReceive(request);
			System.out.println(response);
			apps = parseQueryResp(response);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return apps;
	}

	public void undeployWebApp(String serviceID, String userName) {
		OMElement request = OMBuilder.builderOMForUnDeploy(serviceID, userName);
		EndpointReference targetEPR = buildEPR();
		Options options = buildOptions(targetEPR);

		RPCServiceClient client;
		try {
			client = new RPCServiceClient();
			client.setOptions(options);
			OMElement response = client.sendReceive(request);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public AppInfo deployWebApp(File file, String userName, String level) {
		// by tangyu
		OMElement reqest = OMBuilder.buildOMForDeploy(file, userName, level);
		EndpointReference targetEPR = buildEPR();
		Options options = buildOptions(targetEPR);

		RPCServiceClient client;
		try {
			client = new RPCServiceClient();
			client.setOptions(options);
			OMElement response = client.sendReceive(reqest);
			if (response.getFirstChildWithName(new QName("isSuccessful"))
					.getText().equals("true")) {// ����ɹ�
				return parseDeployResponse(response);
			} else {
				return null;
			}

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private List<AppInfo> parseQueryResp(OMElement response) {
		List<AppInfo> apps = new ArrayList<AppInfo>();
		for (Iterator<OMElement> iter = response
				.getChildrenWithLocalName("app"); iter.hasNext();) {
			OMElement element = iter.next();
			String appID = element
					.getFirstChildWithName(new QName("serviceID")).getText();
			String appName = element.getFirstChildWithName(
					new QName("serviceName")).getText();
			String url = element.getFirstChildWithName(new QName("invokeUrl"))
					.getText();

			AppInfo app = new AppInfo(appID, appName, url);
			apps.add(app);
		}
		System.out.println(apps.size());
		return apps;
	}

	private AppInfo parseDeployResponse(OMElement feedback) {
		String serviceID = feedback.getFirstChildWithName(
				new QName("serviceID")).getText();
		String serviceName = feedback.getFirstChildWithName(
				new QName("serviceName")).getText();
		String url = feedback.getFirstChildWithName(new QName("invokeUrl"))
				.getText();

		return new AppInfo(serviceID, serviceName, url);
	}

	private EndpointReference buildEPR() {
		return new EndpointReference(targetUrl);
	}

	private Options buildOptions(EndpointReference epr) {
		Options options = new Options();
		options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setTo(epr);
		options.setTimeOutInMilliSeconds(6000000);
		// enabling MTOM in the client side
		options.setProperty(Constants.Configuration.ENABLE_MTOM,
				Constants.VALUE_TRUE);
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);

		return options;
	}
}
