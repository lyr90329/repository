<%@ page language="java" import="db.dao.Data,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String id=(String)request.getParameter("id");      
       String diagramxml=(String)request.getParameter("diagramxml");
       String bpmnxml=(String)request.getParameter("bpmnxml");
       String bpidexml=(String)request.getParameter("bpidexml");
       //String userName=(String)session.getAttribute("userName");
	   String userName = "user1";
	   boolean bool=Data.getInstance().savaBPMN(Long.parseLong(id),userName,bpmnxml,bpidexml,diagramxml);
	   if(!bool)
	   {
	      rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	   }
	   else
	   {
	     rp="<return status=\"true\">save successfully</return>";
	   }
	   out.print(rp);
%>
