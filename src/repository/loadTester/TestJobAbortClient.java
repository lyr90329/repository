package repository.loadTester;

import java.util.Iterator;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class TestJobAbortClient {
	public static String abort(String jobId) {
		OMElement request = createRequest(jobId);
		System.out.println(request);
		String targetIp = "http://124.205.18.169:8192/cloudtest/TestJobAbort/";
		System.out.println("还好");
		return sendMsg(targetIp, request).toString();
		
	}

	private static OMElement createRequest(String jobId) {
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMElement request = fac.createOMElement("TestJobAbortRequest", null);
		OMElement jobIdEl = fac.createOMElement("jobId", null);
		jobIdEl.setText(jobId);
		request.addChild(jobIdEl);
		return request;
	}

	private static OMElement sendMsg(String targetIp, OMElement request) {
		OMElement response = null;
		try {
			RPCServiceClient client = new RPCServiceClient();
			Options options = client.getOptions();
			EndpointReference target = new EndpointReference(targetIp);
			options.setTo(target);
			client.setOptions(options);
			response = client.sendReceive(request);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		return response;
	}

	public boolean abortTask(String jobId) {
		boolean result = false;
		System.out.println("---->abort the load test task!");
		OMElement request = createRequest(jobId);
		System.out.println("request soap: " + request);
		String targetIp = "http://124.205.18.169:8192/cloudtest/TestJobAbort/";
		OMElement res = sendMsg(targetIp, request);
		System.out.println("response message: " + res);
		Iterator iter = res.getChildElements();
		while (iter.hasNext()) {
			OMElement elem = (OMElement) iter.next();
			if (elem.getLocalName().equals("issuccessful")) {
				if ("true".equals(elem.getText()))
					result = true;
				else
					result = false;
			} else if (elem.getLocalName().equals("exception")) {
				System.out.println("abort task exception: " + elem.getText());
			} else {
				System.out.println("parse abort task response error!");
			}
		}
		return result;
	}
}
