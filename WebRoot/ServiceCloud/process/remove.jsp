<%@ page language="java" import="db.dao.Data,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String parentid=(String)request.getParameter("id");      
//       String userName=(String)session.getAttribute("userName");	   
	   boolean bool=Data.getInstance().removeBPMN(Long.parseLong(parentid));
	   
	   if(!bool)
	   {
	      rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	    
	   }
	   else
	   {
	     rp="<return status=\"true\"></return>";
	   }
	   
	out.print(rp);

 %>
