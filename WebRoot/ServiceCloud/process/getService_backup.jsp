<%@ page language="java" import="repository.atomicServices.ManageServiceClient,config.Config,javax.wsdl.*,com.eviware.soapui.model.iface.*,db.dao.*,db.entity.*,cn.org.act.sdp.wsdl.WebServiceRunner,java.util.*,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*,repository.atomicServices.*" pageEncoding="gb2312"%>
<%
     String rp = "";    
     ServiceDao serviceDao=new ServiceDao();
     String userName=(String)session.getAttribute("userName");
     List<db.entity.Service> list=serviceDao.findByProperty("userName",userName);
     System.out.println("userName"+userName);
     db.entity.Service service;
     rp="<return status=\"true\">";
     if(list != null){
	 for(int n=0;n < list.size(); n++)
	 {
		service = list.get(n); 
		ServiceTBean stbean=Data.getInstance().getwebservicebyId(service.getServiceId());
        WebServiceRunner wsRunner=null; 
		wsRunner = new WebServiceRunner(stbean.getWsdlUrl());
		//获取所有Binding的协议
		WsdlInterface[] results = wsRunner.getWSDLInterfaces();		
		for( WsdlInterface iface : results )
		{
            if(iface.getSoapVersion().getName().startsWith("SOAP")==false)
                    continue;
            Binding binding=iface.getBinding();
		    PortType porttype=binding.getPortType();
		    List oplist=porttype.getOperations();	
		    	    
		    for(int m=0;m<oplist.size();m++)
		   {
		    rp+="<item>";		    
		    rp+="<servicename>"+stbean.getName()+"</servicename>";
		    javax.wsdl.Operation iop=(javax.wsdl.Operation)oplist.get(m);
		    rp+="<operation>"+iop.getName()+"</operation>";
		    rp+="<url>"+iface.getConfig().getEndpoints().getEndpointArray(0)+"</url>";
		    rp+="<definition>"+iface.getDefinition()+"</definition>";		    
		    rp+="<description>"+iface.getDescription()+"</description>";
		    rp+="<targetnamespace>"+iface.getWsdlContext().getInterfaceDefinition().getTargetNamespace()+"</targetnamespace>";
		    rp+="<source>subscribe</source>";
		    rp+="</item>";
		    }
		    break;
		}
     }
     }
     //System.out.println("------------------------------------------------- ");
	 ServiceInfo uploadService;
	 ManageServiceClient msc = new ManageServiceClient(Config.getWSNameQueryUrl());
	 ArrayList<ServiceInfo> list2 = msc.queryServiceIDByUserName(userName);
	 
	 if( null!=list2 && list2.size()!=0)
	 {
	 	for(int i=0;i < list2.size(); i++)
	 	{
			uploadService = list2.get(i);
			String wsdlURL=msc.queryWsdlByID(uploadService.getId()).get(0);
			String upServiceId=uploadService.getId();
	 		wsdlURL=wsdlURL.substring(0,wsdlURL.length()-1);
	 		wsdlURL+="?wsdl";
	 		System.out.println("URL是："+wsdlURL);
			WebServiceRunner wsRunner2=null; 
			wsRunner2 = new WebServiceRunner(wsdlURL);
			//获取所有Binding的协议
			WsdlInterface[] results = wsRunner2.getWSDLInterfaces();		
			for( WsdlInterface iface : results )
			{
            	if(iface.getSoapVersion().getName().startsWith("SOAP")==false)
                    continue;
            	Binding binding=iface.getBinding();
		    	PortType porttype=binding.getPortType();
		    	List oplist=porttype.getOperations();	
		    	    
		    	for(int m=0;m<oplist.size();m++)
		   		{
		    		rp+="<item>";		    
		    		rp+="<servicename>"+uploadService.getName()+"</servicename>";
		    		javax.wsdl.Operation iop=(javax.wsdl.Operation)oplist.get(m);
		    		rp+="<operation>"+iop.getName()+"</operation>";
		    		rp+="<url>"+iface.getConfig().getEndpoints().getEndpointArray(0)+"</url>";
		    		rp+="<definition>"+iface.getDefinition()+"</definition>";		    
		    		rp+="<description>"+iface.getDescription()+"</description>";
		    		rp+="<targetnamespace>"+iface.getWsdlContext().getInterfaceDefinition().getTargetNamespace()+"</targetnamespace>";
		    		rp+="<source>upload</source>";
		    		rp+="<serviceid>"+upServiceId+"</serviceid>";
		    		rp+="</item>";
		    	}
		    	break;
			}
		}
	 }
	 rp+="</return>";  
	out.print(rp);
 %>
