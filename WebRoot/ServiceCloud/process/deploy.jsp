<%@ page language="java" import="db.dao.Data,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String id=(String)request.getParameter("id");      
       String userName=(String)session.getAttribute("userName");
       String bpmnname=Data.getInstance().getBPMNNameById(Long.parseLong(id));
	   boolean bool=Data.getInstance().subscribe(Long.parseLong(id),userName,bpmnname);
	   if(!bool)
	   {
	   	  System.out.println("bpmn deploy fail!");
	      rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	   }
	   else
	   {
	   	 System.out.println("bpmn deploy success!");
	     rp="<return status=\"true\"></return>";
	   }
	   out.print(rp);
 %>
