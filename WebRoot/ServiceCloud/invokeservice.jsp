<%@ page language="java" import="java.util.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.Data,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*,repository.atomicServices.*" pageEncoding="gb2312"%>
<%@ page import="parser.*"%>
<%
       String rp = "";
       String serviceid=(String)request.getParameter("serviceid"); 
       String soaptype=(String)request.getParameter("soaptype");
       String operation=(String)request.getParameter("operation");
       String wsdlurl=(String)request.getParameter("wsdlurl");
       String servicename=(String)request.getParameter("servicename");       
       
	   String soapContent = (String)request.getParameter("soapContent");
	   System.out.println("soapContent222:"+soapContent);
		String valueList = (String)request.getParameter("paramList");
		String[] values = valueList.split(",");
		soapContent = ParameterParse.replaceValues(soapContent, values, '?');
		//
		System.out.println(soapContent);
	   String insoap = soapContent;
	   System.out.println("insoap:"+insoap);
       ServiceTBean stbean=Data.getInstance().getwebservicebyId(Long.parseLong(serviceid));
          
	   try
		{
//		for(int i=0;i<100;i++)
//		{
		//WSDL初始化，wsRunner会加载、解析WSDL文件
		WebServiceRunner wsRunner = new WebServiceRunner(wsdlurl);
		
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
		System.out.println("++++++++++++++");
		System.out.println(wsdlop.getId());
		System.out.println(wsdlop.getAction());
		System.out.println(wsdlop.getAnonymous());
		System.out.println(wsdlop.getBindingOperationName());
		System.out.println(wsdlop.getInputName());
		System.out.println(wsdlop.getOutputName());
		System.out.println(wsdlop.getName());
		System.out.println("++++++++++++++");
		//获取Operation的输入SOAP报文格式
		//System.out.println(wsRunner.getRequestFormat(wsdlop));	
		
		//在这里，可以在获取的输入SOAP报文格式的基础上填入各个输入参数的值，产生调用时输入的SOAP报文
		//wsRunner.run.runOperation(wsdlop, newInputString);
		
		//调用方法，并得到返回值		
		
		rp=wsRunner.runOperation(wsdlop, insoap);
		System.out.println("服务调用的结果1:"+rp);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	   ////////////////////////////////////////////////////////////////////////////
	   
		SOAPParser parser = new SOAPParser(rp);
		out.print(parser.getHTMLContent());
 %>