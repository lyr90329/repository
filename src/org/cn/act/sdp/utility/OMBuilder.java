package org.cn.act.sdp.utility;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMText;

public class OMBuilder {
	public static OMElement buildOMForDeploy(File file, String userName, String level) {
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMElement element = omFactory.createOMElement("deploywebapp", null);

		OMElement fileContent = omFactory.createOMElement("fileData", null);
		FileDataSource dataSource = new FileDataSource(file);
		DataHandler dh = new DataHandler(dataSource);
		OMText textData = omFactory.createOMText(dh, true);
		fileContent.addChild(textData);

		OMElement fileName = omFactory.createOMElement("fileName", null);
		fileName.setText(file.getName().split(".war")[0]);

		OMElement userNameOM = omFactory.createOMElement("userName", null);
		userNameOM.setText(userName);
		
		// by tangyu
		OMElement levelOM = omFactory.createOMElement("availability", null);
		levelOM.setText(level);

		element.addChild(fileName);
		element.addChild(fileContent);
		element.addChild(userNameOM);
		element.addChild(levelOM);

		return element;
	}

	public static OMElement buildOMForQuery(String userName) {
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMElement element = omFactory.createOMElement("queryRequest", null);

		OMElement nameOM = omFactory.createOMElement("userName", null);
		nameOM.setText(userName);
		element.addChild(nameOM);

		return element;

	}

	public static OMElement builderOMForUnDeploy(String serviceID,
			String userName) {
		OMFactory omFactory = OMAbstractFactory.getOMFactory();

		OMElement element = omFactory.createOMElement("undeployRequest", null);
		OMAttribute attr = omFactory.createOMAttribute("type", null, "app");

		element.addAttribute(attr);
		OMElement serviceIDOM = omFactory.createOMElement("serviceID", null);
		serviceIDOM.setText(serviceID);
		OMElement userNameOM = omFactory.createOMElement("userName", null);
		userNameOM.setText(userName);
		element.addChild(serviceIDOM);
		element.addChild(userNameOM);
		return element;
	}
}
