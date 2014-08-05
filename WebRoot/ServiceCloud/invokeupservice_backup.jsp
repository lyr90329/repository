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
	   
	   System.out.println("�滻������ı��ģ� "+soapContent);
	   String insoap = soapContent;
	   //ServiceDao UploadService=new ServiceDao();
	   //UploadService upservice=UploadService.findUploadServiceById(Integer.parseInt(serviceid));
       
	   try
		{
		//ȡ��wsdl�ĵ�ַ
		ManageServiceClient msc=new ManageServiceClient(Config.getWSNameQueryUrl());	
		wsdlURL=msc.queryWsdlByID(deployid).get(0);
		wsdlURL=wsdlURL.substring(0,wsdlURL.length()-1);
		wsdlURL+="?wsdl";
		//WSDL��ʼ����wsRunner����ء�����WSDL�ļ�
		WebServiceRunner wsRunner = new WebServiceRunner(wsdlURL);
		
		//��ȡ����Binding��Э��
		WsdlInterface[] results = wsRunner.getWSDLInterfaces();
		
		//�������е�Э��
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
		//��ȡ��Э���µ�Operation
		WsdlOperation wsdlop = myiface.getOperationByName(operation);
		
		//��ȡOperation������SOAP���ĸ�ʽ
		//System.out.println(wsRunner.getRequestFormat(wsdlop));	
		
		//����������ڻ�ȡ������SOAP���ĸ�ʽ�Ļ���������������������ֵ����������ʱ�����SOAP����
		//wsRunner.run.runOperation(wsdlop, newInputString);
		
		//���÷��������õ�����ֵ		
		String soaptype2=myiface.getBindingName().getLocalPart();
		String ope=wsdlop.getBindingOperationName();
		
		WebServiceInvokeClient wsInvokeClient=new WebServiceInvokeClient("/home/sdp/testData/localServiceRequest.xml",Config.getWebServiceInvokeUrl());
		rp=wsInvokeClient.invokeByID(insoap,deployid,soaptype2,ope,userName);
		//rp=wsRunner.runOperation(wsdlop, insoap);
		System.out.println("������õĽ��2:"+rp);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	   
		rp="<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\"><soapenv:Body>"+rp;
		rp=rp+"</soapenv:Body></soapenv:Envelope>";	
		System.out.println("������õĽ��3:"+rp);
		SOAPParser parser = new SOAPParser(rp);
		out.print(parser.getUpHTMLContent());
 %>
