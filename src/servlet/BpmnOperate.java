package servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;

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

import test.cn.sdp.act.appengine.samanager.client.BPMNExecutionClient;


import config.Config;

import db.dao.BpmnDao;
import db.entity.Bpmn;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.sdp.act.appengine.monitor.DataBaseUtils;
import cn.sdp.act.appengine.monitor.ResultRecord;
import cn.sdp.act.appengine.monitor.client.BPMNMonitorClientUtils;

public class BpmnOperate extends HttpServlet {
	
	/**
	 * Constructor of the object.
	 */
	public BpmnOperate() {
		
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("===========a==============");
		// 2013-9
		response.setContentType( "text/html" );
		response.setCharacterEncoding( "GBK" );
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		System.out.println("Action Type:"+action);
		String bpmnIdStr = request.getParameter("bpmnId");
		int bpmnId = Integer.valueOf(bpmnIdStr);
		String userName = request.getParameter("userName"); // get entity from
		String myBpmnId= request.getParameter("myBpmnId");
		System.out.println("BpmnOperate myBpmnId:"+myBpmnId);
		// database
		BpmnDao dao = new BpmnDao();
		Bpmn trial;
		trial = dao.findByBPMNIdAndUserName(bpmnId, userName);
		JSONObject data = new JSONObject();
		try 
		{
			data.put("action", action);
			data.put("userName", userName);
			data.put("bpmnId", bpmnId);
			data.put("bpmnName", trial.getBpmnName());
			data.put("ret", false);
			if (action.equals("deployid")) 
			{
				System.out.println("deploy");
				if (trial.getDeployStatus().equals("true")) {
					data.put("ret", false);
					data.put("msg", "Alert! Bpmn " + trial.getBpmnName()
							+ " has been already deployed .");
					System.out.println("true");
				} else {
					System.out.println("false");
					if (deploy(trial,userName)) {
						data.put("variable", "deployStatus");
						data.put("ret", true);
						data.put("value", "true");
					}
				}
			} 
			else if (action.equals("undeployid")) 
			{
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
			}
			else if(action.equals("implementid"))
			{
			try {
					data.put("argNumber", getArgNumber(trial,userName));
					for(int i=0;i<getArgNumber(trial,userName);i++){
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!"+getArgNumber(trial,userName)+"!!!!!!!!!!!!!!!!!!!!!!!!");
						data.put("argName"+i, getArgName(trial,userName).get(i));
						List aaa = getArgType(trial,userName);
						List bbb = getArgName(trial,userName);
						data.put("argType"+i, getArgType(trial,userName).get(i));
					}
					data.put("ret", true);
					
					System.out.println("3");
				} catch (ParserConfigurationException e) {
					//e.printStackTrace();
					data.put("ret", false);
					data.put("msg", "Alert! BPMN " + trial.getBpmnName()
							+ " has errors .");
				} catch (SAXException e) {
					//e.printStackTrace();
					data.put("ret", false);
					data.put("msg", "Alert! BPMN " + trial.getBpmnName()
							+ " has errors .");
				} catch (Exception e) {
					//e.printStackTrace();
					data.put("ret", false);
					data.put("msg", "Alert! BPMN " + trial.getBpmnName()
							+ " has errors .");
				}
			}
			
			else if(action.equals("submit"))
			{
				String argnostr=request.getParameter("argno");
				int argno=Integer.parseInt(argnostr);
				String argvalue=request.getParameter("argvalue");
				String name=request.getParameter("name");
				String type=request.getParameter("type");
				
				//----------by tangyu
				argvalue = new String(argvalue.getBytes("GBK"));

				String value=null;
				try {
					value = implement(trial,argno,name,type,argvalue,userName,myBpmnId);
					System.out.println("BPMN执行的结果："+value);
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				}
				data.put("value", value);
				String jobId=String.valueOf(trial.getJobId());
				data.put("jobId", jobId);
				System.out.println("@@@@@@@@@@@@@@@@@@@");
				System.out.println(jobId);
			}
			else if(action.equals("monitoring"))
			{
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


	private boolean deploy(Bpmn trial,String userName) {
		String bpmnName=trial.getBpmnName();
		bpmnName+=".bpmn";
		bpmnName="bpmn/"+userName+"/"+bpmnName;
		try 
		{
			DeploymentProxyClient c = new DeploymentProxyClient("http://localhost:8080/axis2/services/DeploymentService");
			c.deploy("http://localhost:8080/axis2/services/DeploymentService",
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
		if (dao.update(trial) < 0)
			return false;
		return true;
	}

	private boolean undeploy(Bpmn trial) {
		BpmnDao dao = new BpmnDao();
		trial.setDeployStatus("false");
		if (dao.update(trial) < 0)
			return false;
		return true;
	}
	private int getArgNumber(Bpmn trial , String userName) throws ParserConfigurationException, SAXException, Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.parse(Config.getPath("bpmn/"+userName+"/"+trial.getBpmnName()+".bpmn"));
		doc.normalize();
		NodeList links =doc.getElementsByTagName("supportingElements");
	    
		int flag=0,tempFlag=0;
		String argMessage=null;
		
		List<String> tempArgName=new ArrayList<String>( );
		List<String> tempArgType=new ArrayList<String>( );
		
		int argNumber;
		String[] argNameTemp;
		List<String> argType=new ArrayList<String>( );
		List<String> argName=new ArrayList<String>( );
		
		
		for (int i=0;i<links.getLength();i++){

			Node aNode=links.item(i);
			NamedNodeMap attributes= aNode.getAttributes();
			for(int a=0;a<attributes.getLength();a++){
				Node theAttribute=attributes.item(a);
				if(theAttribute.getNodeName().equals("xsi:type")&&theAttribute.getNodeValue().equals("bpmn:InputSet"))
				{
					System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
					flag=i;
					System.out.println(flag);
				}
			}
		}
		Node aNode=links.item(flag);
		NamedNodeMap attributes= aNode.getAttributes();
		for(int a=0;a<attributes.getLength();a++){
			Node theAttribute=attributes.item(a);
			if(theAttribute.getNodeName().equals("propertyInputs"))
				argMessage=theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp=argMessage.split(" "); 
		System.out.println(argNameTemp.length);
		return argNameTemp.length;
	}
	
	private List<String> getArgName(Bpmn trial,String userName) throws ParserConfigurationException, SAXException, IOException{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.parse(Config.getPath("bpmn/"+userName+"/"+trial.getBpmnName()+".bpmn"));
		doc.normalize();
		NodeList links =doc.getElementsByTagName("supportingElements");
	    
		int flag=0,tempFlag=0;
		String argMessage=null;
		List<String> tempArgName=new ArrayList<String>( );
		List<String> tempArgType=new ArrayList<String>( );
		
		int argNumber;
		String[] argNameTemp;
		List<String> argType=new ArrayList<String>( );
		List<String> argName=new ArrayList<String>( );
		
		
		for (int i=0;i<links.getLength();i++){
			Node aNode=links.item(i);
//			System.out.println(aNode.getFirstChild().getNodeValue());
			NamedNodeMap attributes= aNode.getAttributes();
			for(int a=0;a<attributes.getLength();a++){
				Node theAttribute=attributes.item(a);
//				System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
				if(theAttribute.getNodeName().equals("xsi:type")&&theAttribute.getNodeValue().equals("bpmn:InputSet"))
				{	
					System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
					flag=i;
					System.out.println(flag);
				}
			}
		}
		Node aNode=links.item(flag);
		NamedNodeMap attributes= aNode.getAttributes();
		for(int a=0;a<attributes.getLength();a++){
			Node theAttribute=attributes.item(a);
			if(theAttribute.getNodeName().equals("propertyInputs"))
				argMessage=theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp=argMessage.split(" "); 
		System.out.println(argNameTemp.length);		
		for(int i=0;i<argNameTemp.length;i++){
			System.out.println(argNameTemp[i]);
		}
		
		for (int j=0;j<argNameTemp.length;j++){
			for (int i=0;i<links.getLength();i++){
				Node tempNode=links.item(i);
				NamedNodeMap tempAttributes= tempNode.getAttributes();
				for(int a=0;a<tempAttributes.getLength();a++){
					Node tempTheAttribute=tempAttributes.item(a);
					if(tempTheAttribute.getNodeName().equals("id")&&tempTheAttribute.getNodeValue().equals(argNameTemp[j]))
					{	
						System.out.println(tempTheAttribute.getNodeName()+"="+tempTheAttribute.getNodeValue());
						tempFlag=i;
						System.out.println(tempFlag);
					}					
				}
			}
			Node tempNode=links.item(tempFlag);
			NamedNodeMap tempAttributes= tempNode.getAttributes();
			for(int a=0;a<tempAttributes.getLength();a++){
				Node tempTheAttribute=tempAttributes.item(a);
				if(tempTheAttribute.getNodeName().equals("name"))
					tempArgName.add(j, tempTheAttribute.getNodeValue());
				
				if(tempTheAttribute.getNodeName().equals("type"))
					tempArgType.add(j, tempTheAttribute.getNodeValue());
			}
		}
		
		System.out.println("-------------------------");
		for(int i=0;i<tempArgName.size();i++){
			System.out.println(tempArgName.get(i));
		}
		for(int i=0;i<tempArgType.size();i++){
			System.out.println(tempArgType.get(i));
		}
		for(int i=0;i<tempArgName.size();i++){
			argName.add(i, tempArgName.get(i));
			
		}
		for(int i=0;i<tempArgType.size();i++){
			argType.add(i, tempArgType.get(i));
		}
		for(int i=0;i<tempArgType.size();i++){
			System.out.println(argName.get(i)+":"+argType.get(i));
			
		}
		return argName;
	}
	
	private List<String> getArgType(Bpmn trial, String userName) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.parse(Config.getPath("bpmn/"+userName+"/"+trial.getBpmnName()+".bpmn"));
		doc.normalize();
		NodeList links =doc.getElementsByTagName("supportingElements");
	    
		int flag=0,tempFlag=0;
		String argMessage=null;
		List<String> tempArgName=new ArrayList<String>( );
		List<String> tempArgType=new ArrayList<String>( );
		
		int argNumber;
		String[] argNameTemp;
		List<String> argType=new ArrayList<String>( );
		List<String> argName=new ArrayList<String>( );
		
		
		for (int i=0;i<links.getLength();i++){
			Node aNode=links.item(i);
			NamedNodeMap attributes= aNode.getAttributes();
			for(int a=0;a<attributes.getLength();a++){
				Node theAttribute=attributes.item(a);
				if(theAttribute.getNodeName().equals("xsi:type")&&theAttribute.getNodeValue().equals("bpmn:InputSet"))
				{	
					System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
					flag=i;
					System.out.println(flag);
				}
			}
		}
		Node aNode=links.item(flag);
		NamedNodeMap attributes= aNode.getAttributes();
		for(int a=0;a<attributes.getLength();a++){
			Node theAttribute=attributes.item(a);
			if(theAttribute.getNodeName().equals("propertyInputs"))
				argMessage=theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp=argMessage.split(" "); 
		System.out.println(argNameTemp.length);		
		for(int i=0;i<argNameTemp.length;i++){
			System.out.println(argNameTemp[i]);
		}
		
		for (int j=0;j<argNameTemp.length;j++){
			for (int i=0;i<links.getLength();i++){
				Node tempNode=links.item(i);
				NamedNodeMap tempAttributes= tempNode.getAttributes();
				for(int a=0;a<tempAttributes.getLength();a++){
					Node tempTheAttribute=tempAttributes.item(a);
	//				System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
					if(tempTheAttribute.getNodeName().equals("id")&&tempTheAttribute.getNodeValue().equals(argNameTemp[j]))
					{	
						System.out.println(tempTheAttribute.getNodeName()+"="+tempTheAttribute.getNodeValue());
						tempFlag=i;
						System.out.println(tempFlag);
					}					
				}
			}
			Node tempNode=links.item(tempFlag);
			NamedNodeMap tempAttributes= tempNode.getAttributes();
			for(int a=0;a<tempAttributes.getLength();a++){
				Node tempTheAttribute=tempAttributes.item(a);
				if(tempTheAttribute.getNodeName().equals("name"))
					tempArgName.add(j, tempTheAttribute.getNodeValue());
				
				if(tempTheAttribute.getNodeName().equals("type"))
					tempArgType.add(j, tempTheAttribute.getNodeValue());
					
			}
		}
		
		System.out.println("-------------------------");
		for(int i=0;i<tempArgName.size();i++){
			System.out.println(tempArgName.get(i));
		}
		for(int i=0;i<tempArgType.size();i++){
			System.out.println(tempArgType.get(i));
		}
		for(int i=0;i<tempArgName.size();i++){
			argName.add(i, tempArgName.get(i));
			
		}
		for(int i=0;i<tempArgType.size();i++){
			argType.add(i, tempArgType.get(i));
		}
		for(int i=0;i<tempArgType.size();i++){
			System.out.println(argName.get(i)+":"+argType.get(i));
			
		}
		return argType;

	}
	private String implement(Bpmn trial,int argno,String name,String type,String value,String userName,String myBpmnId) throws ParserConfigurationException, SAXException, IOException 
	{
		System.out.println("argno："+argno);
		System.out.println("name："+name);
		System.out.println("type："+type);
		System.out.println("value："+value);
		System.out.println("userName："+userName);
		System.out.println("myBpmnId："+myBpmnId);
		System.out.println("----------------------------------");
		
		String rp="";
		String[] nameList=name.split(",");
		String[] typeList=type.split(",");
		String[] valueList=value.split(",");
	
		List<test.cn.sdp.act.appengine.samanager.client.Parameter> parmData = new ArrayList<test.cn.sdp.act.appengine.samanager.client.Parameter>();

		for(int i=0;i<argno;i++)
		{
			test.cn.sdp.act.appengine.samanager.client.Parameter p1 = new test.cn.sdp.act.appengine.samanager.client.Parameter();
		    p1.setParamName(nameList[i]);
		    p1.setParamType(typeList[i]);
		    p1.setParamValue(valueList[i]); 
		    parmData.add(p1);  
		    
		}
		for(int i=0;i<argno;i++){
			System.out.println("parmData变量：");
			System.out.println(parmData.get(i).getParamName()+"   "+parmData.get(i).getParamType()+"    "+parmData.get(i).getParamValue());
		}
		BPMNExecutionClient client = new BPMNExecutionClient();
		String response="";
		try {
				response=client.testBPMNExecutionQuery(trial.getBpmnName(),myBpmnId, parmData);
				System.out.println("更换接口后的返回结果："+response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//解析response中的jobId
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document docWithJobID=builder.parse(new ByteArrayInputStream(response.getBytes()));
		NodeList jobIdList=docWithJobID.getElementsByTagName("jobId");
		System.out.println("jobIdList:"+jobIdList.item(0).getTextContent());
		String jobId=jobIdList.item(0).getTextContent();

		BpmnDao dao = new BpmnDao();
		trial.setJobId(Integer.parseInt(jobId));
		dao.updateJobId(trial);
		System.out.println("*****************"+"jobId="+jobId+"*****************");
		String csresults="";
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int time=60,etime=0;
		while(true)
		{
		   etime++;
		   if(etime>time)
		   {
		      rp="outputnum=-1";
		      break;
		   }
		   try{
			Thread.sleep(5000);
		   } catch (InterruptedException e) {
			   e.printStackTrace();
		   }
		   //从引擎获取monitor的结果
		   List<cn.sdp.act.appengine.monitor.Parameter> plist=null;
		   ResultRecord resultRecord = BPMNMonitorClientUtils.getResultRecordByJobId(jobId);
		   if(resultRecord==null)
			   plist=null;
		   else
			   plist=resultRecord.getPs();
		   
		   if(plist==null) continue;
		   rp="outputnum="+plist.size();
		   for (int i = 0; i < plist.size(); i++) 
		   {
			   cn.sdp.act.appengine.monitor.Parameter parm =(cn.sdp.act.appengine.monitor.Parameter)plist.get(i);
				rp=rp+"&name="+parm.getParameterName()+"&type="+parm.getParameterType()+"&value="+parm.getParameterValue();
		   }
			
		   if(plist.size()>0)
		   {
			   System.out.println("当前结果size："+plist.size());
			   break;
		   }
		   else
			   System.out.println("循环继续："+plist.size());
		}
		System.out.println("查询执行结果，以供监控："+rp); 
		factory = DocumentBuilderFactory.newInstance();
		builder=factory.newDocumentBuilder();
		Document doc=builder.parse(Config.getPath("bpmn/"+userName+"/"+trial.getBpmnName()+".bpmn"));
		doc.normalize();
		NodeList links =doc.getElementsByTagName("supportingElements");
		    
		int flag=0,tempFlag=0;
		String argMessage=null;

		List<String> tempArgName = new ArrayList<String>();
		List<String> tempArgType = new ArrayList<String>();
		
		int argNumber;
		String[] argNameTemp;
		List<String> argType=new ArrayList<String>( );
		List<String> argName=new ArrayList<String>( );
		
		
		for (int i=0;i<links.getLength();i++)
		{
			Node aNode=links.item(i);
			NamedNodeMap attributes= aNode.getAttributes();
			for(int a=0;a<attributes.getLength();a++){
				Node theAttribute=attributes.item(a);
				if(theAttribute.getNodeName().equals("xsi:type")&&theAttribute.getNodeValue().equals("bpmn:OutputSet"))
				{	
					System.out.println(theAttribute.getNodeName()+"="+theAttribute.getNodeValue());
					flag=i;
					System.out.println(flag);
				}
			}
		}
		Node aNode=links.item(flag);
		NamedNodeMap attributes= aNode.getAttributes();
		for(int a=0;a<attributes.getLength();a++){
			Node theAttribute=attributes.item(a);
			if(theAttribute.getNodeName().equals("propertyOutputs"))
				argMessage=theAttribute.getNodeValue();
		}
		System.out.println(argMessage);
		argNameTemp=argMessage.split(" "); 
		System.out.println("argNameTemp="+argNameTemp.length);
		for(int i=0;i<argNameTemp.length;i++){
			System.out.println(argNameTemp[i]);
		}
		
		for (int j=0;j<argNameTemp.length;j++){
		for (int i=0;i<links.getLength();i++){
				Node tempNode=links.item(i);
				NamedNodeMap tempAttributes= tempNode.getAttributes();
				for(int a=0;a<tempAttributes.getLength();a++){
					Node tempTheAttribute=tempAttributes.item(a);
					if(tempTheAttribute.getNodeName().equals("id")&&tempTheAttribute.getNodeValue().equals(argNameTemp[j]))
					{	
						System.out.println(tempTheAttribute.getNodeName()+"="+tempTheAttribute.getNodeValue());
						tempFlag=i;
						System.out.println(tempFlag);
					}					
				}
			}
			Node tempNode=links.item(tempFlag);
			NamedNodeMap tempAttributes= tempNode.getAttributes();
			System.out.println("tempAttributes.getLength()="+tempAttributes.getLength());		
			for(int a=0;a<tempAttributes.getLength();a++){
				Node tempTheAttribute=tempAttributes.item(a);
				if(tempTheAttribute.getNodeName().equals("name"))
					tempArgName.add(j, tempTheAttribute.getNodeValue());
				if(tempTheAttribute.getNodeName().equals("type"))
					tempArgType.add(j, tempTheAttribute.getNodeValue());
			}
		}
		
		System.out.println("-------argName:argType--------");
		for(int i=0;i<tempArgName.size();i++){
			argName.add(i, tempArgName.get(i));
		}
		for(int i=0;i<tempArgType.size();i++){
			argType.add(i, tempArgType.get(i));
		}
		for(int i=0;i<tempArgType.size();i++){
			System.out.println(argName.get(i)+":"+argType.get(i));
			
		}		 

		String[] resultSegment;
		String result="";
		List<String> temp = new ArrayList<String>();
		List<String> resultValue = new ArrayList<String>();
		resultSegment=rp.split("&");
		List<String> resultSegmentTemp = new ArrayList<String>();
		String[] SegmentSplit;
		
		System.out.println("------resultSegment[i]------");
		for(int i=0;i<resultSegment.length;i++)
		{
			System.out.println(resultSegment[i]);
			SegmentSplit=resultSegment[i].split("=");
			for(String s:SegmentSplit)
				resultSegmentTemp.add(s);
		}
		
		for(int j=0;j<argName.size();j++){
			for(int i=0;i<resultSegmentTemp.size();i++){
				if(resultSegmentTemp.get(i).equals(argName.get(j)))
					resultValue.add(resultSegmentTemp.get(i+4));
			}
		}
		
		System.out.println("--------resultValue.get(i)---------");
		for(int i=0;i<resultValue.size();i++)
			System.out.println(resultValue.get(i));
		
		for(int i=0;i<resultValue.size();i++)
			result+=argName.get(i)+"="+resultValue.get(i)+"  ";
		
		System.out.println("最终的结果为："+result);
		dao.setJobIdFalse(trial);//将本次结果设为false，防止下次直接读取上次结果
		return result;
	}
	private String monitoring(Bpmn trial){
		String monitorMesg="Monitoring...";
		return monitorMesg;
	}
	
	public static void main(String[] args)
	{
		try
		{
			DeploymentProxyClient c = new DeploymentProxyClient("http://192.168.3.138:8080/axis2/services/DeploymentService");
			c.deploy("http://192.168.3.138:8080/axis2/services/DeploymentService","D:/Project/Workspace/MyEclipse/repository/bpmn/eStore2.bpmn");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
