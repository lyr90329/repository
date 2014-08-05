package qualifyControl;

import org.jdom.Document;
import org.jdom.Element;

public class userRegister {
	public void test() {
		Document doc = new Document(), response;
		Element root, username, password;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/userControl/";

		root = new Element("register");
		doc.setRootElement(root);

		username = new Element("userName");
		username.setText("user4");
		root.addContent(username);

		password = new Element("password");
		password.setText("111111");
		root.addContent(password);

		response = tool.getResult(doc, url);
	}

	public boolean register(String userName, String userPassword) {
		System.out.println("111");
		Document doc = new Document(), response;
		Element root, username, password;
		xmlTool tool = new xmlTool();
		String url = "http://" + Constants.ipAddress
				+ ":8192/Authentication/userControl/";

		root = new Element("register");
		doc.setRootElement(root);

		username = new Element("userName");
		username.setText(userName);
		root.addContent(username);

		password = new Element("password");
		password.setText(userPassword);
		root.addContent(password);
		System.out.println("222");
		response = tool.getResult(doc, url);
		System.out
				.println("用户注册结果："
						+ response.getRootElement().getChild("isSuccessful")
								.getValue());
		if ("true".equals(response.getRootElement().getChild("isSuccessful")
				.getValue()))
			return true;
		else if ("false".equals(response.getRootElement().getChild(
				"isSuccessful").getValue()))
			return false;
		else
			return true;
	}

	public static void main(String[] args) {
		userRegister register = new userRegister();
		System.out.println(register.register("yang@126.com", "111111"));
	}
}
