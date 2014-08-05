<%@ page language="java" import="db.dao.*,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String id=(String)request.getParameter("id");  
       
//       String userName=(String)session.getAttribute("userName");	   
	   Bpmn bpmn=Data.getInstance().getBPMN(Long.parseLong(id));
	   
	   if(bpmn==null)
	   {
	      rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	    
	   }
	   else
	   {
	     
	     rp="<return status=\"true\"><diagramxml>"+bpmn.getDiagramflex()+"</diagramxml></return>";
	   }
	   
	out.print(rp);

 %>
