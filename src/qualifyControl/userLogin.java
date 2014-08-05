package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class userLogin {
	public userLogin() {

	}

	public void test() {
		Document doc = new Document(), response;
		Element root, username, password;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/userControl/";

		root = new Element("login");
		doc.setRootElement(root);

		username = new Element("userName");
		username.setText("user1");
		root.addContent(username);

		password = new Element("password");
		password.setText("111111");
		root.addContent(password);

		response = tool.getResult(doc, url);
	}

	public static void main(String[] args) {
		userLogin login = new userLogin();
		login.test();
	}
}
