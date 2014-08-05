<%@ page language="java" import="java.util.*,config.Config,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.*,config.Config,db.entity.*,com.eviware.soapui.impl.wsdl.*,repository.atomicServices.*,
org.apache.axiom.om.OMElement,org.apache.axiom.om.impl.builder.StAXOMBuilder,org.apache.axis2.addressing.EndpointReference,org.apache.axis2.AxisFault,
org.apache.axis2.client.Options,org.apache.axis2.client.ServiceClient,org.apache.axis2.transport.http.HTTPConstants,
java.io.*,org.jdom.Document,org.jdom.Element,org.jdom.JDOMException,org.jdom.Namespace,org.jdom.input.SAXBuilder,org.jdom.output.XMLOutputter,
org.xml.sax.InputSource" pageEncoding="gb2312"%>
<%@ page import="parser.*"%>
<%
       String rp = "";
       String wsdlURL="";
       String userName=(String)request.getParameter("userName");
       System.out.println("invokeupservice  userName:"+userName);
       String serviceName=(String)request.getParameter("serviceName"); 
       String deployid=(String)request.getParameter("deployid");
       String soaptype=(String)request.getParameter("soaptype");
       String operation=(String)request.getParameter("operation");       
       
	   String soapContent = (String)request.getParameter("soapContent");
	   String valueList = (String)request.getParameter("paramList");
	      
//--------------- DRP_1   
	   System.out.println("\n valueList = " + valueList +"\n");
//--------------- DRP_2  

	   try
	   {
//--------------- DRP_1  
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%   3");
			if(valueList!=null) {
				String[] values = valueList.split(",");
				soapContent = ParameterParse.replaceValues(soapContent, values, '?');//填入参数
			} 
			else {
				System.out.println("valuelist is null");
			}
			   
			System.out.println("替换参数后的报文： "+soapContent);
			String insoap = soapContent;
			//ServiceDao UploadService=new ServiceDao();
			//UploadService upservice=UploadService.findUploadServiceById(Integer.parseInt(serviceid));
//--------------- DRP_2

			//取得wsdl的地址
			ManageServiceClient msc=new ManageServiceClient(Config.getWSNameQueryUrl());	
			wsdlURL=msc.queryWsdlByID(deployid).get(0);
//--------------- DRP_1 
			if(!wsdlURL.endsWith("?wsdl")) {
				wsdlURL=wsdlURL.substring(0,wsdlURL.length()-1);
				wsdlURL+="?wsdl";
			}
//--------------- DRP_2

			//WSDL初始化，wsRunner会加载、解析WSDL文件
			WebServiceRunner wsRunner = new WebServiceRunner(wsdlURL);
			
			//获取所有Binding的协议
			WsdlInterface[] results = wsRunner.getWSDLInterfaces();
			
			//遍历所有的协议
			WsdlInterface myiface = null;		
			for( WsdlInterface iface : results )
			{
				System.out.println(iface.getBindingName().getLocalPart());	
				if(iface.getBindingName().getLocalPart().equals(soaptype))
				{
					myiface = iface;
					break;
				}
			}
			if(myiface != null)
			{
				//获取该协议下的Operation
				
				WsdlOperation wsdlop = myiface.getOperationByName(operation);
				
				//获取Operation的输入SOAP报文格式
				//System.out.println(wsRunner.getRequestFormat(wsdlop));	
				
				//在这里，可以在获取的输入SOAP报文格式的基础上填入各个输入参数的值，产生调用时输入的SOAP报文
				//wsRunner.run.runOperation(wsdlop, newInputString);
				
				//调用方法，并得到返回值		
				String soaptype2=myiface.getBindingName().getLocalPart();
				String ope=wsdlop.getBindingOperationName();
				
		//		WebServiceInvokeClient wsInvokeClient=new WebServiceInvokeClient("/home/sdp/testData/localServiceRequest.xml",Config.getWebServiceInvokeUrl());
		//		rp=wsInvokeClient.invokeByID(insoap,deployid,soaptype2,ope,userName);
//--------------- DRP_1
				System.out.println("------------------------------\n"+insoap+
				"\n------------------------------\n"+wsdlURL+
				"\n------------------------------\n"+"operation = "+ operation);
		//		System.out.println("\n before*************************\n");
				
		//		rp=wsRunner.runOperation(wsdlop, insoap);
   
				ServiceClient sender = null;
				String insoapBody = null;	//从带有参数的soap报文中获取需要的报文内容
				try {
					StringReader read = new StringReader(insoap);
					//创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
					InputSource source = new InputSource(read);
					SAXBuilder saxb = new SAXBuilder();
					Document doc = saxb.build(source);
					Element root = doc.getRootElement();
					System.out.println("\n root*************"+root.getName());
					Namespace ns = root.getNamespace();
					List otherNS = root.getAdditionalNamespaces();
					if(!otherNS.isEmpty())
					{
						System.out.println("\n AdditionalNamespaces*************"+otherNS);
					}
					Element soapBody = root.getChild("Body", ns);
					List children = soapBody.getChildren();
					if(!children.isEmpty())
					{
						XMLOutputter xmlOut = new XMLOutputter();
						insoapBody= xmlOut.outputString(children);
						System.out.println("\n insoapBody*************"+insoapBody);
					}
					String endpoint =  wsdlURL.split("\\?")[0];
					Options options = new Options();
					options.setTo(new EndpointReference(endpoint));
					options.setTransportInProtocol("SOAP");
					options.setAction(operation);
					options.setProperty(HTTPConstants.CHUNKED, "false");
					sender = new ServiceClient();
					sender.setOptions(options);
					OMElement body = new StAXOMBuilder(new ByteArrayInputStream(insoapBody.getBytes("UTF-8"))).getDocumentElement();

					System.out.println(body);
					OMElement result = sender.sendReceive(body);

					rp =result.toString();
					System.out.println("rp=" + rp);
					System.out.println(result);
					if (null != sender)
					{
						sender.cleanupTransport();
						sender.cleanup();
					}							
				}catch (JDOMException e) {  
					e.printStackTrace();  
				}catch (AxisFault axisFault){
					axisFault.printStackTrace();
				}
//--------------- DRP_2
			
			}// end if
		}catch(Exception e){
			e.printStackTrace();
		}
	   
		try {	
			rp="<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\"><soapenv:Body>"+rp;
			rp=rp+"</soapenv:Body></soapenv:Envelope>";	
			System.out.println("response:"+rp);
			SOAPParser parser = new SOAPParser(rp);
			out.print(parser.getUpHTMLContent());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
 %>
