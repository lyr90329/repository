package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class deployQualify {
	public deployQualify() {

	}

	public void test() {
		Document doc = new Document(), response;
		Element root, username, password;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/qualifyControl/";
		
		root = new Element("deployQualification");
		doc.setRootElement(root);

		username = new Element("userName");
		username.setText("YY");
		root.addContent(username);

		password = new Element("serviceId");
		password.setText("ws3");
		root.addContent(password);

		response = tool.getResult(doc, url);
		System.out.println(response);
	}

	public static void main(String[] args) {

		deployQualify qualify = new deployQualify();
		qualify.test();
	}
}
