<%@ page language="java" import="java.util.*,config.Config,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.*,config.Config,db.entity.*,com.eviware.soapui.impl.wsdl.*,repository.atomicServices.*" pageEncoding="gb2312"%>
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
	   String[] values = valueList.split(",");
	   soapContent = ParameterParse.replaceValues(soapContent, values, '?');
	   
	   System.out.println("替换参数后的报文： "+soapContent);
	   String insoap = soapContent;
	   //ServiceDao UploadService=new ServiceDao();
	   //UploadService upservice=UploadService.findUploadServiceById(Integer.parseInt(serviceid));
       
	   try
		{
		//取得wsdl的地址
		ManageServiceClient msc=new ManageServiceClient(Config.getWSNameQueryUrl());	
		wsdlURL=msc.queryWsdlByID(deployid).get(0);
		wsdlURL=wsdlURL.substring(0,wsdlURL.length()-1);
		wsdlURL+="?wsdl";
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
		
		WebServiceInvokeClient wsInvokeClient=new WebServiceInvokeClient("/home/sdp/testData/localServiceRequest.xml",Config.getWebServiceInvokeUrl());
		rp=wsInvokeClient.invokeByID(insoap,deployid,soaptype2,ope,userName);
		//rp=wsRunner.runOperation(wsdlop, insoap);
		System.out.println("服务调用的结果2:"+rp);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	   
		rp="<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\"><soapenv:Body>"+rp;
		rp=rp+"</soapenv:Body></soapenv:Envelope>";	
		System.out.println("服务调用的结果3:"+rp);
		SOAPParser parser = new SOAPParser(rp);
		out.print(parser.getUpHTMLContent());
 %>
