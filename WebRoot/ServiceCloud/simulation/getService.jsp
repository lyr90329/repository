<%@ page language="java" import="javax.wsdl.*,com.eviware.soapui.model.iface.*,db.dao.*,db.entity.*,cn.org.act.sdp.wsdl.WebServiceRunner,java.util.*,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*" pageEncoding="gb2312"%>
<%
       String rp = "";    
//       String userName="user1";	
       ServiceDao serviceDao=new ServiceDao();
       String userName=(String)session.getAttribute("userName");
      List<db.entity.Service> list=serviceDao.findByProperty("userName",userName);
      db.entity.Service service;
   if(list != null){
      rp="<return status=\"true\">";
	for(int n=0;n < list.size(); n++){
	    
		service = list.get(n); 
		ServiceTBean stbean=Data.getInstance().getwebservicebyId(service.getServiceId());
        
        WebServiceRunner wsRunner=null; 
		wsRunner = new WebServiceRunner(stbean.getWsdlUrl());
		
		System.out.println(stbean.getWsdlUrl());
		//获取所有Binding的协议
		WsdlInterface[] results = wsRunner.getWSDLInterfaces();		
		for( WsdlInterface iface : results )
		{
//		    List oplist=iface.getOperationList();
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
		    rp+="</item>";
		    }
		    break;
//		    String opname=iop.getName();
//		    rp=stbean.getName()+" "+opname;
//		    String input=iop.getInputName();
//		     String output=iop.getOutputName();		   
//		   
//		       rp+=" input: "+input+" ";
//		   
//		    
//		       rp+=" out: "+output;
//		    
//		    System.out.println(rp);
           
//		   }
		  
		}
     }
     }
	 rp+="</return>";  
//	   if(!bool)
//	   {
//	      rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	    
//	   }
//	   else
//	   {
//	     rp="<return status=\"true\"></return>";
//	   }
	   
	out.print(rp);

 %>
