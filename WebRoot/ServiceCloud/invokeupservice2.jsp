<%@ page language="java" import="java.util.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.*,config.Config,db.entity.*,com.eviware.soapui.impl.wsdl.*,repository.atomicServices.*" pageEncoding="gb2312"%>
<%@ page import="parser.*"%>
<%
       String rp = "";
       String wsdlURL="";
       String serviceName=(String)request.getParameter("serviceName"); 
       String soaptype=(String)request.getParameter("soaptype");
       String operation=(String)request.getParameter("operation");
	   String insoap = (String)request.getParameter("soapContent");
	   String deployid=(String)request.getParameter("deployid");
	   //ServiceDao UploadService=new ServiceDao();
	   //UploadService upservice=UploadService.findUploadServiceById(Integer.parseInt(serviceid));
       
       
	   try
		{
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%   4");
			//ȡ��wsdl�ĵ�ַ
			ManageServiceClient msc=new ManageServiceClient(Config.getWSNameQueryUrl());	
			wsdlURL=msc.queryWsdlByID(deployid).get(0);
		
//--------------- DRP_1
			if(!wsdlURL.endsWith("?wsdl")){
				wsdlURL=wsdlURL.substring(0,wsdlURL.length()-1);
				wsdlURL+="?wsdl";
			}
//--------------- DRP_2

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
				System.out.println("------------------------------\n"+insoap+
				"\n------------------------------\n"+wsdlURL+
				"\n------------------------------\n"+operation);
			
				rp=wsRunner.runOperation(wsdlop, insoap);
			}
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	   
		SOAPParser parser = new SOAPParser(rp);
		out.print(parser.getHTMLContent());
 %>
