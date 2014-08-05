package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class deployService {
	public deployService() {

	}

	public void test() {
		Document doc = new Document(), response;
		Element root, username, servicelist, serviceid;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/serviceControl/";

		root = new Element("deployService");
		doc.setRootElement(root);
		root.setAttribute("type", "webservice");

		username = new Element("userName");
		username.setText("gdl");
		root.addContent(username);

		servicelist = new Element("serviceList");
		root.addContent(servicelist);

		serviceid = new Element("serviceId");
		serviceid.setText("ws1");
		servicelist.addContent(serviceid);

		serviceid = new Element("serviceId");
		serviceid.setText("ws2");
		servicelist.addContent(serviceid);

		response = tool.getResult(doc, url);
	}

	public static void main(String[] args) {
		deployService deploy = new deployService();
		deploy.test();
	}
}
