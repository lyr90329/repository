package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class undeployQualify {
	public undeployQualify() {

	}

	public void test() {
		Document doc = new Document(), response;
		Element root, username, password;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/qualifyControl/";

		root = new Element("undeployQualification");
		doc.setRootElement(root);

		username = new Element("userName");
		username.setText("gdl");
		root.addContent(username);

		password = new Element("serviceId");
		password.setText("ws_2");
		root.addContent(password);

		response = tool.getResult(doc, url);
	}

	public static void main(String[] args) {
		undeployQualify qualify = new undeployQualify();
		qualify.test();
	}
}
