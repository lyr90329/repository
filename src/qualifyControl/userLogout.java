package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class userLogout {
	public userLogout() {

	}

	public void test() {
		Document doc = new Document(), response;
		Element root, username, password;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/userControl/";

		root = new Element("logout");
		doc.setRootElement(root);

		username = new Element("userName");
		username.setText("user1");
		root.addContent(username);

		response = tool.getResult(doc, url);
	}

	public static void main(String[] args) {
		userLogout logout = new userLogout();
		logout.test();
	}
}
