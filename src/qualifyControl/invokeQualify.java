package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class invokeQualify {
	public invokeQualify() {

	}

	public void test() {
		Document doc = new Document(), response;
		Element root, username, password;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/qualifyControl/";

		root = new Element("invokeQualification");
		doc.setRootElement(root);

		username = new Element("userName");
		username.setText("YY");
		root.addContent(username);

		password = new Element("serviceId");
		password.setText("ws1");
		root.addContent(password);

		response = tool.getResult(doc, url);
	}

	public static void main(String[] args) {
		invokeQualify qualify = new invokeQualify();
		qualify.test();
	}
}