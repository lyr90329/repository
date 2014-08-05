<%@ page language="java" import="db.dao.Data,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String id=(String)request.getParameter("bpmnid"); 
      
       String userName=(String)session.getAttribute("userName");
          
	   boolean bool=Data.getInstance().publishBPMN(Long.parseLong(id),userName);
	   
	   if(bool)
	   {
	      rp="Publish BPMN "+Data.getInstance().getBPMNNameById(Long.parseLong(id))+" Successfully!";
	    
	   }
	   else
	   {
	     rp="Publishing BPMN "+Data.getInstance().getBPMNNameById(Long.parseLong(id))+" failed!";
	   }
	   
	out.print(rp);

 %>
