package servlet;

import cn.org.act.sdp.bpmnengine.common.Parameter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;

import config.Config;

import db.dao.BpmnDao;
import db.entity.Bpmn;
import db.entity.UploadBpmn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.servicebus.ServiceBusClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class UploadBpmnOperate extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadBpmnOperate() {

		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.err.println("===========a==============");
		System.out.println("enter UploadBpmnOperate!");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		String bpmnIdStr = request.getParameter("bpmnId");
		int bpmnId = Integer.valueOf(bpmnIdStr);
		String userName = request.getParameter("userName"); // get entity from
		// database
		BpmnDao dao = new BpmnDao();
		UploadBpmn trial;
		trial = dao.findByUpBPMNIdAndUserName(bpmnId, userName);

		// System.out.println(trial.getBpmnName());
		JSONObject data = new JSONObject();
		System.out.println("1");
		try {

			data.put("action", action);
			data.put("userName", userName);
			data.put("bpmnId", bpmnId);
			data.put("bpmnName", trial.getBpmnName());
			data.put("ret", false);
			if (action.equals("deployid")) {
				System.out.println("deploy");	
				if (trial.getDeployStatus().equals("true")) {
					data.put("ret", false);
					data.put("msg", "Alert! Bpmn " + trial.getBpmnName()
							+ " has been already deployed .");
					System.out.println("true");
				} else {
					System.out.println("false");
					if (deploy(trial, userName)) {
						data.put("variable", "deployStatus");
						data.put("ret", true);
						data.put("value", "true");
					}
				}
			} else if (action.equals("undeployid")) {
				System.out.println(trial.getDeployStatus());
				if (trial.getDeployStatus().equals("false")) {
					System.out.println("2");
					data.put("ret", false);
					data.put("msg", "Alert! BPMN " + trial.getBpmnName()
							+ " has been already undeployed .");
				} else {
					if (undeploy(trial)) {
						data.put("variable", "deployStatus");
						data.put("ret", true);
						data.put("value", "false");
					}
				}
			}// else if ...

			else if (action.equals("implementid")) {
				try {

					// System.out.println("flag:"+getArgNumber(trial,userName));
					data.put("argNumber", getArgNumber(trial, userName));
					for (int i = 0; i < getArgNumber(trial, userName); i++) {
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!"
								+ getArgNumber(trial, userName)
								+ "!!!!!!!!!!!!!!!!!!!!!!!!");
						data.put("argName" + i, getArgName(trial, userName)
								.get(i));
						List aaa = getArgType(trial, userName);
						List bbb = getArgName(trial, userName);
						data.put("argType" + i, getArgType(trial, userName)
								.get(i));
					}
					data.put("ret", true);

					System.out.println("3");
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					data.put("ret", false);
					data.put("msg", "Alert! BPMN " + trial.getBpmnName()
							+ " has errors .");
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					data.put("ret", false);
					data.put("msg", "Alert! BPMN " + trial.getBpmnName()
							+ " has errors .");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					data.put("ret", false);
					data.put("msg", "Alert! BPMN " + trial.getBpmnName()
							+ " has errors .");
				}

			}

			else if (action.equals("submit")) {
				String argnostr = request.getParameter("argno");
				int argno = Integer.parseInt(argnostr);
				String argvalue = request.getParameter("argvalue");
				String name = request.getParameter("name");
				String type = request.getParameter("type");

				String value = null;
				try {
					value = implement(trial, argno, name, type, argvalue,
							userName);
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// System.out.println(name+";;;"+type);

				//
				data.put("value", value);
				// TODO read jobId

				String jobId = String.valueOf(trial.getJobId());

				data.put("jobId", jobId);
				System.out.println("@@@@@@@@@@@@@@@@@@@");
				System.out.println(jobId);

				// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			} else if (action.equals("monitoring")) {
				data.put("monitorMessage", monitoring(trial));
				System.out.println(monitoring(trial));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			out.print(data.toString());
			out.flush();
			out.close();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	private boolean deploy(UploadBpmn trial, String userName) {
		// TODO deploy operation
		String bpmnName = trial.getBpmnName();
		bpmnName += ".bpmn";
		bpmnName = "bpmn/" + userName + "/" + bpmnName;
		try {

			// BpmnTool tool;
			// tool=new BpmnTool();
			// ArrayList<BpmnBean> bpmns= new ArrayList() ;
			// bpmns.add(0,tool.getBpmnByID(trial.getBpmnId()));
			// System.out.println(trial.getBpmnName());
			// System.out.println(bpmnName);
			// FileOutputStream fos = null;
			// InputStream is = null;
			// File file = null;
			// int i = 0;
			// for (Iterator iter = bpmns.iterator(); iter.hasNext();) {
			// i++;
			// BpmnBean element = (BpmnBean) iter.next();
			// try {
			// file = new File(Config.getPath(bpmnName));
			// fos = new FileOutputStream(file);
			// is = element.getBpmnStream();
			// int size;
			//
			// if (!file.exists()) {
			// file.createNewFile(); //
			// }
			//                 
			// // byte[] Buffer = new byte[4096];
			// // while ((size = is.read(Buffer)) != -1) {
			// // // System.out.println(size);
			// // fos.write(Buffer, 0, size);
			// // }
			// } catch (FileNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } finally {
			//					
			// try {
			// if (is != null)
			// is.close();
			// if (fos != null)
			// fos.close();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// }
			//		

			DeploymentProxyClient c = new DeploymentProxyClient(
					"http://192.168.3.138:8080/axis2/services/DeploymentService");
			// File file = new File("bpmn/service.bpmn");
			// file.createNewFile();
			// new FileWriter(file).write("this is a bpmn file");

			c
					.deploy(
							"http://192.168.3.138:8080/axis2/services/DeploymentService",
							Config.getPath(bpmnName));
			System.out.println(bpmnName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// update database
		BpmnDao dao = new BpmnDao();
		System.out.println("testdeploy");
		trial.setDeployStatus("true");
		if (dao.update2(trial) < 0)
			return false;
		return true;
	}

	private boolean undeploy(UploadBpmn trial) {
		// TODO undeploy operation
		// ...
		// update database
		BpmnDao dao = new BpmnDao();
		trial.setDeployStatus("false");
		if (dao.update2(trial) < 0)
			return false;
		return true;
	}

	private int getArgNumber(UploadBpmn trial, String userName)
			throws ParserConfigurationException, SAXException, Exception {
		// TODO getArgNumber operation
		// return 3;
		// ******************************************************//

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(Config.getPath("bpmn/" + userName + "/"
				+ trial.getBpmnName() + ".bpmn"));
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
						&& theAttribute.getNodeValue().equals("bpmn:InputSet")) {
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
			if (theAttribute.getNodeName().equals("propertyInputs"))
				argMessage = theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp = argMessage.split(" ");
		System.out.println(argNameTemp.length);
		return argNameTemp.length;
		// ***************************************************//
	}

	private List<String> getArgName(UploadBpmn trial, String userName)
			throws ParserConfigurationException, SAXException, IOException {
		// TODO getArgName operation
		// List<String> argName=new ArrayList<String>( );
		// argName.add(0, "arg0");
		// argName.add(1, "arg1");
		// argName.add(2, "arg2");
		// System.out.println("bpmnName");
		// return argName;
		// ******************************************************//
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(Config.getPath("bpmn/" + userName + "/"
				+ trial.getBpmnName() + ".bpmn"));
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
						&& theAttribute.getNodeValue().equals("bpmn:InputSet")) {
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
			if (theAttribute.getNodeName().equals("propertyInputs"))
				argMessage = theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp = argMessage.split(" ");
		System.out.println(argNameTemp.length);
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
		return argName;
		// *****************************************************//
	}

	private List<String> getArgType(UploadBpmn trial, String userName)
			throws ParserConfigurationException, SAXException, IOException {
		// TODO getArgName operation
		// List<String> argType=new ArrayList<String>( );
		// argType.add(0, "String");
		// argType.add(1, "String");
		// argType.add(2, "String");
		// ****************************************//
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(Config.getPath("bpmn/" + userName + "/"
				+ trial.getBpmnName() + ".bpmn"));
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
						&& theAttribute.getNodeValue().equals("bpmn:InputSet")) {
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
			if (theAttribute.getNodeName().equals("propertyInputs"))
				argMessage = theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp = argMessage.split(" ");
		System.out.println(argNameTemp.length);
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
		return argType;
		// *********************************************//
	}

	private String implement(UploadBpmn trial, int argno, String name,
			String type, String value, String userName)
			throws ParserConfigurationException, SAXException, IOException {
		// String[] namearr=name.split(",");
		// for (int i = 0; i < namearr.length; i++) {
		// System.out.println(namearr[i]);
		// }
		// String[] valueList=value.split(",");
		//		
		// for (int i = 0; i < valueList.length; i++) {
		// System.out.println(valueList[i]);
		// }
		// // TODO implement operation
		// String resultvalue="resultValueBus";
		//		
		// return resultvalue;
		String rp = "";
		String[] nameList = name.split(",");
		String[] typeList = type.split(",");
		String[] valueList = value.split(",");
		LinkedList<Parameter> parmData = new LinkedList<Parameter>();
		for (int i = 0; i < argno; i++) {
			Parameter p1 = new Parameter();
			p1.setParamName(nameList[i]);
			p1.setParamType(typeList[i]);
			p1.setParamValue(valueList[i]);
			parmData.add(p1);

		}
		for (int i = 0; i < argno; i++) {
			System.out.println("parameter");
			System.out.println(parmData.get(i).getParamName()
					+ parmData.get(i).getParamType()
					+ parmData.get(i).getParamValue());
			System.out.println("parameter");
		}

		String busAddress = Config.getBusService();
		ServiceBusClient client = new ServiceBusClient(busAddress);
		System.out.println(trial.getBpmnName());
		String jobId = client.invokeService(trial.getBpmnName(), parmData,
				Config.getMonitor());

		BpmnDao dao = new BpmnDao();
		trial.setJobId(Integer.parseInt(jobId));
		dao.updateJobId2(trial);

		System.out.println("*****************" + "jobId=" + jobId
				+ "*****************");

		String csresults = "";
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int time = 60, etime = 0;
		while (true) {
			etime++;

			if (etime > time) {
				rp = "outputnum=-1";
				break;
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ExecuteResultQueryingClient reclient = new ExecuteResultQueryingClient(
					Config.getResultService());

			LinkedList<Parameter> parmdata = reclient.getExecuteResult(Long
					.parseLong(jobId));
			rp = "outputnum=" + parmdata.size();
			for (int i = 0; i < parmdata.size(); i++) {
				Parameter parm = parmdata.get(i);
				rp = rp + "&name=" + parm.getParamName() + "&type="
						+ parm.getParamType() + "&value="
						+ parm.getParamValue();

			}

			if (parmdata.size() > 0)
				break;
		}
		// out.print(rp);
		System.out.println(rp);
		// ************************************
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(Config.getPath("bpmn/" + userName + "/"
				+ trial.getBpmnName() + ".bpmn"));
		doc.normalize();
		NodeList links = doc.getElementsByTagName("supportingElements");

		int flag = 0, tempFlag = 0;
		String argMessage = null;

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

		// *********************************

		// String resultvalue="resultValueBus";

		String[] resultSegment;
		String result = "";
		List<String> temp = new ArrayList<String>();
		List<String> resultValue = new ArrayList<String>();
		resultSegment = rp.split("&");

		System.out.println("------resultSegment[i]------");
		for (int i = 0; i < resultSegment.length; i++)
			System.out.println(resultSegment[i]);

		for (int j = 0; j < argName.size(); j++) {
			for (int i = 0; i < resultSegment.length; i++) {
				if (resultSegment[i].regionMatches(5, argName.get(j), 0, 6))
					resultValue.add(resultSegment[i + 2]);
			}
		}

		System.out.println("--------argName.get(i)---------");
		for (int i = 0; i < argName.size(); i++)
			System.out.println(argName.get(i));

		System.out.println("--------resultValue.get(i)---------");
		for (int i = 0; i < resultValue.size(); i++)
			System.out.println(resultValue.get(i));

		for (int i = 0; i < resultValue.size(); i++)
			temp.add(i, resultValue.get(i).toString().substring(6));

		System.out.println("--------temp.get(i)---------");
		for (int i = 0; i < temp.size(); i++)
			System.out.println(temp.get(i));

		for (int i = 0; i < resultValue.size(); i++)
			result = argName.get(i) + "=" + temp.get(i) + "   ";

		System.out.println(result);
		return result;
	}

	private String monitoring(UploadBpmn trial) {
		// TODO implement operation
		String monitorMesg = "Monitoring...";
		return monitorMesg;
	}

	public static void main(String[] args) {
		// String busAddress =
		// "http://192.168.3.133:8080/axis2/services/BusService";
		// ServiceBusClient client = new ServiceBusClient(busAddress);
		// LinkedList<Parameter> parmData = new LinkedList<Parameter>();
		//
		// Parameter name = new Parameter();
		// Parameter password = new Parameter();
		// Parameter bookIsbn = new Parameter();
		// Parameter VIP = new Parameter("orderId", "String", "sp");
		//
		// name.setParamName("userName");
		// name.setParamType("String");
		// name.setParamValue("jiyipeng");
		//
		// password.setParamName("password");
		// password.setParamType("String");
		// password.setParamValue("jiyipeng");
		//
		// bookIsbn.setParamName("goodName");
		// bookIsbn.setParamType("String");
		// bookIsbn.setParamValue("thinking in java");
		//
		// parmData.add(name);
		// parmData.add(password);
		// parmData.add(bookIsbn);
		// parmData.add(VIP);
		// String compositeServiceName = "eStore";
		// String IP = "http://192.168.3.133:8070";
		// System.out.println(parmData.get(0).getParamName()+parmData.get(0).getParamType()+parmData.get(0).getParamValue());
		// System.out.println(parmData.get(1).getParamName()+parmData.get(1).getParamType()+parmData.get(1).getParamValue());
		// System.out.println(parmData.get(2).getParamName()+parmData.get(2).getParamType()+parmData.get(2).getParamValue());
		// System.out.println(parmData.get(3).getParamName()+parmData.get(3).getParamType()+parmData.get(3).getParamValue());
		// String jobId = client.invokeService(compositeServiceName, parmData,
		// IP);
		// System.out.println(jobId);
		try {
			DeploymentProxyClient c = new DeploymentProxyClient(
					"http://192.168.3.138:8080/axis2/services/DeploymentService");
			// File file = new File("bpmn/service.bpmn");
			// file.createNewFile();
			// new FileWriter(file).write("this is a bpmn file");

			c
					.deploy(
							"http://192.168.3.138:8080/axis2/services/DeploymentService",
							"D:/Project/Workspace/MyEclipse/repository/bpmn/eStore2.bpmn");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
