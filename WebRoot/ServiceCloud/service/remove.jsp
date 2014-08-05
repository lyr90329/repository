<%@ page language="java" import="db.dao.Data,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String serviceid=(String)request.getParameter("serviceid"); 
       String serviceName=(String)request.getParameter("serviceName");
       String userName=(String)session.getAttribute("userName");
          
	   boolean bool=Data.getInstance().removeService(Long.parseLong(serviceid),userName);
	   
	   if(bool)
	   {
	      rp="Remove Service "+serviceName+" Successfully!";
	    
	   }
	   else
	   {
	     rp="Removing Service "+serviceName+" failed!";
	   }
	   
	out.print(rp);

 %>
