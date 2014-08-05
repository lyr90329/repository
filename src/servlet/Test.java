package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import cn.org.act.sdp.bpmnengine.common.Parameter;

public class Test {

	public static void main(String[] args) throws SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder
				.parse("D:\\Project\\Workspace\\MyEclipse\\ServiceCloud\\bpmn\\eStore2.bpmn");
		doc.normalize();
		NodeList links = doc.getElementsByTagName("supportingElements");

		int flag = 0, tempFlag = 0;
		String argMessage = null;

		// String[] tempArgName={null,null,null,null};
		// String[] tempArgType={null,null,null,null};

		List<String> tempArgName = new ArrayList<String>();
		List<String> tempArgType = new ArrayList<String>();

		int argNumber;
		String[] argNameTemp;
		List<String> argType = new ArrayList<String>();
		List<String> argName = new ArrayList<String>();

		for (int i = 0; i < links.getLength(); i++) {
			// Element link=(Element) links.item(i);
			// System.out.println(links.getLength());
			// System.out.print("Content: ");
			// System.out.println(link.getElementsByTagName("game").item(0).getFirstChild().getNodeValue());

			Node aNode = links.item(i);
			// System.out.println(aNode.getFirstChild().getNodeValue());
			NamedNodeMap attributes = aNode.getAttributes();
			for (int a = 0; a < attributes.getLength(); a++) {
				Node theAttribute = attributes.item(a);
				// System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
				if (theAttribute.getNodeName().equals("xsi:type")
						&& theAttribute.getNodeValue().equals("bpmn:OutputSet")) {
					System.out.println(theAttribute.getNodeName() + "="
							+ theAttribute.getNodeValue());
					flag = i;
					System.out.println(flag);
				}
			}
		}
		Node aNode = links.item(flag);
		NamedNodeMap attributes = aNode.getAttributes();
		for (int a = 0; a < attributes.getLength(); a++) {
			Node theAttribute = attributes.item(a);
			if (theAttribute.getNodeName().equals("propertyOutputs"))
				argMessage = theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp = argMessage.split(" ");
		System.out.println("argNameTemp=" + argNameTemp.length);
		for (int i = 0; i < argNameTemp.length; i++) {
			System.out.println(argNameTemp[i]);
		}

		for (int j = 0; j < argNameTemp.length; j++) {
			for (int i = 0; i < links.getLength(); i++) {
				Node tempNode = links.item(i);
				NamedNodeMap tempAttributes = tempNode.getAttributes();
				for (int a = 0; a < tempAttributes.getLength(); a++) {
					Node tempTheAttribute = tempAttributes.item(a);
					// System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
					if (tempTheAttribute.getNodeName().equals("id")
							&& tempTheAttribute.getNodeValue().equals(
									argNameTemp[j])) {
						System.out.println(tempTheAttribute.getNodeName() + "="
								+ tempTheAttribute.getNodeValue());
						tempFlag = i;
						System.out.println(tempFlag);
					}
				}
			}
			Node tempNode = links.item(tempFlag);
			NamedNodeMap tempAttributes = tempNode.getAttributes();
			System.out.println("tempAttributes.getLength()="
					+ tempAttributes.getLength());
			for (int a = 0; a < tempAttributes.getLength(); a++) {
				Node tempTheAttribute = tempAttributes.item(a);
				if (tempTheAttribute.getNodeName().equals("name"))
					tempArgName.add(j, tempTheAttribute.getNodeValue());
				if (tempTheAttribute.getNodeName().equals("type"))
					tempArgType.add(j, tempTheAttribute.getNodeValue());
			}
		}

		System.out.println("-------------------------");
		for (int i = 0; i < tempArgName.size(); i++) {
			System.out.println(tempArgName.get(i));
		}
		for (int i = 0; i < tempArgType.size(); i++) {
			System.out.println(tempArgType.get(i));
		}
		for (int i = 0; i < tempArgName.size(); i++) {
			argName.add(i, tempArgName.get(i));

		}

		for (int i = 0; i < tempArgType.size(); i++) {
			argType.add(i, tempArgType.get(i));
		}
		for (int i = 0; i < tempArgType.size(); i++) {
			System.out.println(argName.get(i) + ":" + argType.get(i));

		}
		// List<String> temp = new ArrayList<String>();
		// List<String> resultValue = new ArrayList<String>();
		// String
		// rp="outputnum=10&name=goodName&type=String&value=def&name=orderId&type=String&value=wer&name=password&type=String&value=sdf&name=userName&type=String&value=sdf&name=loginResult&type=String&value=Failed&name=findResultStatus&type=String&value=Trying to find good in eBay&name=bankInfo&type=String&value=6222000200121702203##jiyipeng&name=payResult&type=String&value=true&name=deliverResult&type=String&value=true&name=result&type=String&value=true";
		// String[] resultSegment=rp.split("&");
		//		
		// for(int i=0;i<resultSegment.length;i++){
		// if(resultSegment[i].regionMatches(5, "result", 0, 6))
		// resultValue.add(resultSegment[i+2]);
		// }
		// System.out.println("--------resultValue.get(i)---------");
		// for(int i=0;i<resultValue.size();i++)
		// System.out.println(resultValue.get(i));
	}

}
