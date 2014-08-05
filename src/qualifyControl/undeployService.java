package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class undeployService {
	public undeployService() {

	}

	public void test() {
		Document doc = new Document(), response;
		Element root, username, password, servicelist, serviceid;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/serviceControl/";

		root = new Element("undeployService");
		doc.setRootElement(root);
		root.setAttribute("type", "webservice");

		username = new Element("userName");
		username.setText("YY");
		root.addContent(username);

		servicelist = new Element("serviceList");
		root.addContent(servicelist);

		serviceid = new Element("serviceId");
		serviceid.setText("ws1");
		servicelist.addContent(serviceid);

		/*
		 * serviceid=new Element("serviceId"); serviceid.setText("ws_2");
		 * servicelist.addContent(serviceid);
		 */

		response = tool.getResult(doc, url);
	}

	public static void main(String[] args) {
		undeployService undeploy = new undeployService();
		undeploy.test();
	}
}
