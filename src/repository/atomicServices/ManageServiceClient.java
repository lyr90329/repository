package repository.atomicServices;



import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMText;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class ManageServiceClient {

	/** variable of RPCServiceClient */
	RPCServiceClient serviceClient = new RPCServiceClient();
	/** Manage service location */
	String location;
	/** variable of EndpointReference */
	EndpointReference targetEPR;
	
	public ManageServiceClient()throws AxisFault{
		
	}

	public ManageServiceClient(String location) throws AxisFault {
		this.location = location;
		targetEPR = new EndpointReference(location);
	}

	public ArrayList<String> deployRemoteService(String availability, File serviceArchive, String userName) throws AxisFault {
		OMElement envelope = buildSOAPEnvelope(availability, serviceArchive, userName);
		Options options = buildOptions();
		options.setTimeOutInMilliSeconds(60000*5);
		ServiceClient sc = new ServiceClient();
		sc.setOptions(options);
		System.out.println("开始进行远程调用！");
		OMElement message = sc.sendReceive(envelope);
		//System.out.println("response:"+message);
		ArrayList<String> result = new ArrayList<String>();
		result=getResults(message);
		return result;
	}
	
	/**
	 * 解析返回报文中的wsdl列表
	 * 
	 **/
	private static ArrayList<String> parseWsdlUrl(OMElement element)
	{
		if(element==null){
		       return null;
		   }
		Iterator iterator=element.getChildElements();
		ArrayList<String> WSDLlist=new ArrayList<String>();
		while(iterator.hasNext())
		{
			OMNode omNode=(OMNode)iterator.next();
			if(omNode.getType()==OMNode.ELEMENT_NODE)
	          {
	        	  OMElement omElement=(OMElement)omNode;
	        	  if(omElement.getLocalName().equals("services"))
	                 {
	                     String temp=omElement.getText().trim();
	                     System.out.println("进入services中，其子节点为wsdl地址");
	                      
	                     Iterator iter=omElement.getChildElements();
	              		 while(iter.hasNext())
	              		 {
	              			OMNode wsdlNode=(OMNode)iter.next();
	              			if(wsdlNode.getType()==OMNode.ELEMENT_NODE)
	              			{
	              				OMElement wsdl=(OMElement)wsdlNode;
	              				if(wsdl.getLocalName().equals("url"))
	              				{
	              					String url=wsdl.getText().trim();
	              					System.out.println("url:"+url);
	              					WSDLlist.add(url);
	              				}
	              			}
	              		 }
	                 }   
	          }     
		}
		return WSDLlist;
	}
	
	/**
	 * 解析返回报文中的Service Id列表
	 * 
	 **/
	private static ArrayList<ServiceInfo> parseServiceID(OMElement element)
	{
		if(element==null){
		       return null;
		   }
		Iterator iterator=element.getChildElements();
		ArrayList<ServiceInfo> serviceInfo=new ArrayList<ServiceInfo>();
		while(iterator.hasNext())
		{
			OMNode omNode=(OMNode)iterator.next();
			if(omNode.getType()==OMNode.ELEMENT_NODE)
	          {
	        	  OMElement omElement=(OMElement)omNode;
	        	  if(omElement.getLocalName().equals("services"))
	                 {
	        		  	if(omElement.getAttribute(new QName("length")).getAttributeValue().equals("0"))
	        		  		return serviceInfo;
	        		  	else
	        		  	{
	        		  		Iterator iter=omElement.getChildElements();
		              		 while(iter.hasNext())
		              		 {
		              			OMNode serviceNode=(OMNode)iter.next();
		              			if(serviceNode.getType()==OMNode.ELEMENT_NODE)
		              			{
		              				OMElement service=(OMElement)serviceNode;
		              				if(service.getLocalName().equals("service"))
		              				{
		              					String serviceID=service.getAttribute(new QName("id")).getAttributeValue();
		              					String serviceName=service.getAttribute(new QName("name")).getAttributeValue();
		              					//System.out.println("id: "+serviceID);
		              					//System.out.println("Name: "+serviceName);
		              					serviceInfo.add(new ServiceInfo(serviceID, serviceName));
		              				}
		              			}
		              		 }
	        		  	}
	                 }   
	          }     
		}
		return serviceInfo;
	} 
	
	private static ArrayList<String> getResults(OMElement element)
	{
		if(element==null){
		       return null;
		   }
		Iterator iterator=element.getChildElements();
		ArrayList<String> list=new ArrayList<String>();
		while(iterator.hasNext())
		{
			OMNode omNode=(OMNode)iterator.next();
	          if(omNode.getType()==OMNode.ELEMENT_NODE)
	          {
	        	  OMElement omElement=(OMElement)omNode;
	                 if(omElement.getLocalName().equals("serviceID"))
	                 {
	                      String temp=omElement.getText().trim();
	                      list.add(temp);
	                 } 
	                 if(omElement.getLocalName().equals("isSuccessful"))
	                 {
	                      String temp=omElement.getText().trim();
	                      list.add(temp);
	                 }
	                 if(omElement.getLocalName().equals("deployResultInfo"))
	                 {
	                      String temp=omElement.getText().trim();
	                      list.add(temp);
	                 }
	          }     
		}
		return list;
	} 
	
	private OMElement buildSOAPEnvelope(String avail, File file, String userName) {

		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMElement element = omFactory.createOMElement("deployRemoteService",null);
		
		OMElement fileContent = omFactory.createOMElement("fileData", null);
		FileDataSource dataSource = new FileDataSource(file);
		DataHandler dh = new DataHandler(dataSource);
		OMText textData = omFactory.createOMText(dh, true);
		fileContent.addChild(textData);
		
		OMElement fileName = omFactory.createOMElement("fileName", null);
		fileName.setText(file.getName());
		
		OMElement availability = omFactory.createOMElement("availability", null);
		availability.setText(avail);
		
		OMElement consistency = omFactory.createOMElement("consistency", null);
		consistency.setText("1");
		
		OMElement responseTimeConstraint = omFactory.createOMElement("responseTimeConstraint", null);
		responseTimeConstraint.setText("10");
		
		OMElement sn = omFactory.createOMElement("serviceName", null);   /*现在使用就版本  暂时加上name*/
		sn.setText(file.getName());
		
		OMElement un = omFactory.createOMElement("userName", null); 
		un.setText(userName);
		
		element.addChild(sn);
		element.addChild(fileName);	
		element.addChild(un);
		
		element.addChild(fileContent);
		element.addChild(availability);
		element.addChild(consistency);
		element.addChild(responseTimeConstraint);
		
		return element;
	}

	private OMElement buildUndeployEnvelope(String id, String name) 
	{
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMElement element = omFactory.createOMElement("undeployRequest",null);
		OMAttribute attr = omFactory.createOMAttribute("type", null, "webservice");
		element.addAttribute(attr);
		OMElement serviceID = omFactory.createOMElement("serviceID", null);
		serviceID.setText(id);
		element.addChild(serviceID);
		
		OMElement userName = omFactory.createOMElement("userName", null);
		userName.setText(name);
		element.addChild(userName);
		
		System.out.println("undeploy:"+element);
		return element;
	}

	public boolean undeploy(String id, String userName) throws AxisFault {
		OMElement request = this.buildUndeployEnvelope(id, userName);
		Options options = serviceClient.getOptions();
		options.setTo(targetEPR);
		options.setAction("urn:unDeploy");
		QName opName = new QName("http://manageServices.serviceCloud.sdp.act.org.cn", "unDeploy");
		OMElement[]  req=new OMElement[]{request};
		OMElement response = serviceClient.invokeBlocking(opName, req);
		System.out.println("undeploy response:"+response);
		Iterator<OMElement> child=response.getChildrenWithName(new QName("isSuccessful"));
		OMElement ome=child.next();
		System.out.println("undeploy response:"+ome.getText().toString().trim());
		if("true".equals(ome.getText().toString().trim()))
			return true;
		else
			return false;
	}

	private Options buildOptions() throws AxisFault {
		
		Options options = new Options();
		options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		options.setTo(targetEPR);
		// enabling MTOM in the client side
		options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
		options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
		return options;
		
	}
	
	public void serviceUndeploy(String serviceName) throws AxisFault {
		if (serviceClient == null)
			throw new NullPointerException();

		Options options = serviceClient.getOptions();
		options.setTo(targetEPR);
		options.setAction("urn:unDeploy");
		QName opName = new QName(
				"http://manageServices.serviceCloud.sdp.act.org.cn", "unDeploy");
		String[] list = new String[] { serviceName };
		OMElement response = serviceClient.invokeBlocking(opName, list);
		System.out.println(response);
		
	}
	
	/**
	 * 构造soap报文，根据服务的ID（例如WS_xxx）查询该服务的部署地址列表
	 * 
	 * 
	 **/
	private OMElement buildQueryWsdlSoap(String ID) throws AxisFault
	{
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMElement element = omFactory.createOMElement("ContainerQueryRequestForExecution",null);
		OMAttribute attr = omFactory.createOMAttribute("type", null,"WebService");
		element.addAttribute(attr);
		OMElement serviceId = omFactory.createOMElement("serviceID",null);
		serviceId.setText(ID);
		element.addChild(serviceId);
		System.out.println("查询服务WSDL部署地址的报文:"+element);
		return element;
	}
	
	/**
	 * 构造soap报文，根据用户名（例如user1）查询该用户部署的服务的ID
	 * 
	 */
	private OMElement buildQueryServiceIDSoap(String userName) throws AxisFault
	{
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMElement element = omFactory.createOMElement("WSQueryRequestByUserName",null);
		OMAttribute attr = omFactory.createOMAttribute("type", null,"WebService");
		element.addAttribute(attr);
		OMElement name = omFactory.createOMElement("userName",null);
		name.setText(userName);
		element.addChild(name);
		System.out.println("根据用户名查询服务ID的报文:"+element);
		return element;
	}
	
	/**
	 * 依据服务ID调用远端服务器，返回该服务的部署地址列表
	 * 
	 */
	public ArrayList<String> queryWsdlByID(String ID) throws AxisFault
	{
		OMElement element=buildQueryWsdlSoap(ID);
		Options options = buildOptions();
		ServiceClient sc = new ServiceClient();
		sc.setOptions(options);
		System.out.println("开始进行远程调用！");
		OMElement message = sc.sendReceive(element);
		System.out.println("服务调用的结果response:"+message);
		
		//解析返回的报文，提取报文中的wsdlURL地址
		ArrayList<String> wsdlList=parseWsdlUrl(message);
		return wsdlList;
	}
	
	/**
	 * 
	 * 
	 */
	
	public ArrayList<ServiceInfo> queryServiceIDByUserName(String userName) throws AxisFault
	{
		OMElement element=buildQueryServiceIDSoap(userName);
		Options options = buildOptions();
		ServiceClient sc = new ServiceClient();
		sc.setOptions(options);
		System.out.println("开始根据用户名查询服务ID！");
		OMElement message = sc.sendReceive(element);
		System.out.println("查询服务ID的结果："+message);
		ArrayList<ServiceInfo> serviceIdList=parseServiceID(message);
		return serviceIdList;
	}

	public static void main(String args[]) throws AxisFault {

		/*deploy*/
//		ManageServiceClient msc1=new ManageServiceClient("http://192.168.3.220:8192/deploy/");
//		msc1.deployRemoteService("0.9", new File("C:\\serviceUpload\\TestServiceForServiceMix.aar"));
      
		
		/*undeploy*/
		 
		 ManageServiceClient msc=new ManageServiceClient("http://192.168.104.115:8192/undeploy/");	
		 msc.undeploy("WS_918", "user1");
		 
		
		//构造查询服务部署地址列表的soap报文
//		ManageServiceClient msc=new ManageServiceClient("http://192.168.3.220:8192/WSNameQuerybyID/");	
//		ArrayList<String> list=msc.queryWsdlByID("WS_234");
//		System.out.println("wsdl列表中的第一项："+list.get(0));

//		ManageServiceClient msc = new ManageServiceClient("http://192.168.3.173:8192/WSNameQuerybyID/");
//		ArrayList<ServiceInfo> list = msc.queryServiceIDByUserName("user1");
//		System.out.println("服务描述列表中的第一项："+list.get(0).getId()+"  "+list.get(0).getName());
	}
}
