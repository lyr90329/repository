<%@ page language="java" import="java.util.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.Data,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*" pageEncoding="gb2312"%>
<%@ page import="parser.*"%>
<%
       String rp = "";
       String serviceid=(String)request.getParameter("serviceid"); 
       String soaptype=(String)request.getParameter("soaptype");
       String operation=(String)request.getParameter("operation");
	   String insoap = (String)request.getParameter("soapContent");
	   String wsdlurl=(String)request.getParameter("wsdlurl");
       String servicename=(String)request.getParameter("servicename");    
	   
       ServiceTBean stbean=Data.getInstance().getwebservicebyId(Long.parseLong(serviceid));
          
	   try
		{
//		for(int i=0;i<100;i++)
//		{
		//WSDL��ʼ����wsRunner����ء�����WSDL�ļ�
		WebServiceRunner wsRunner = new WebServiceRunner(wsdlurl);
		
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

		rp=wsRunner.runOperation(wsdlop, insoap);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	   ////////////////////////////////////////////////////////////////////////////
	   
		System.out.print(rp);		
		SOAPParser parser = new SOAPParser(rp);
		out.print(parser.getHTMLContent());
 %>
