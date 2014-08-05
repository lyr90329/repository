package test.cn.sdp.act.appengine.monitor;

import java.sql.SQLException;
import java.util.*;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

import config.Config;

import cn.sdp.act.appengine.monitor.DataBaseUtils;
import cn.sdp.act.appengine.monitor.Parameter;
import cn.sdp.act.appengine.monitor.ResultRecord;

public class BPMNResultClient {

	private static String namespace = "http://sdp.act.buaa.edu.cn/servicecloud";

	private static String url = Config.getBPMNMonitorUrl()+"appengine/BPMNMonitor/";

	public static List<Parameter> getResult(String id)
			throws ClassNotFoundException, SQLException, AxisFault {

		OMFactory omFactory = OMAbstractFactory.getOMFactory();

		OMElement data = omFactory.createOMElement("request", null);
		OMElement jobIdEle = omFactory.createOMElement("jobId", null);
		jobIdEle.setText(id);
		data.addChild(jobIdEle);

		EndpointReference targetEPR = new EndpointReference(url);
		Options options = new Options();
		options.setTo(targetEPR);
		ServiceClient sender = new ServiceClient();
		sender.setOptions(options);

		// call executeResultProxy service
		String action = namespace + "/BPMNResultInterface"
				+ "/BPMNResultEndpoint";
		options.setAction(action);

		try {

			System.out.println(data);
			OMElement response = sender.sendReceive(data);
			List<Parameter> ps = parseParams(response);
			// isSucceeded = Boolean.parseBoolean(response.getText());//

		} catch (Exception e) {

			e.printStackTrace();
			try {
				DataBaseUtils.init();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ResultRecord rr = DataBaseUtils.getResultRecordByJobId(id);
			if (rr != null) {
				return rr.getPs();
			}
			DataBaseUtils.close();

		} finally {
			sender.cleanupTransport();
		}

		return null;
	}

	private static List<Parameter> parseParams(OMElement data) {
		List<Parameter> ps = new ArrayList<Parameter>();
		Iterator it = data.getChildrenWithLocalName("parameter");
		while (it.hasNext()) {
			OMElement p = (OMElement) it.next();
			OMElement name = getChildWithName(p, "name");
			OMElement type = getChildWithName(p, "type");
			OMElement value = getChildWithName(p, "value");

			Parameter tp = new Parameter();
			tp.setParameterName(name.getText());
			tp.setParameterType(type.getText());
			tp.setParameterValue(value.getText());

			ps.add(tp);
		}

		return ps;
	}

	private static OMElement getChildWithName(OMElement p, String name) {
		Iterator it = p.getChildrenWithLocalName(name);
		while (it.hasNext()) {

			Object o = it.next();

			if (o instanceof OMElement) {
				return (OMElement) o;
			}
		}
		return null;
	}

}
