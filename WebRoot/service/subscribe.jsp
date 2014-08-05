<%@ page language="java" import="db.dao.Data,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String id=(String)request.getParameter("serviceid"); 
       String serviceName=(String)request.getParameter("serviceName"); 
       String url=(String)request.getParameter("wsdlurl");
       String userName=(String)session.getAttribute("userName");
       
       if(userName==null||userName=="")
	   {
	   out.print("You have not login!");
	   return;
       }
       if(Data.getInstance().validateServiceById(id)){
       out.print("Service "+serviceName+" has been Subscribed!");
       return;
       }	  
	   boolean bool=Data.getInstance().subscribeService(id,serviceName,url,userName);
	   
	   if(bool)
	   {
	      rp="Subscribe Service "+serviceName+" Successfully!";
	    
	   }
	   else
	   {
	     rp="Subscribing Service "+serviceName+" failed!";
	   }
	   
	   out.print(rp);

 %>
