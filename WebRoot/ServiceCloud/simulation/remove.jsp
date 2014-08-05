<%@ page language="java" import="db.dao.Data,java.util.*,cn.edu.fudan.se.service.MyServiceImplProxy;" pageEncoding="gb2312"%>
<%
       String rp = "";
       String parentid=(String)request.getParameter("id");      
	   boolean bool=Data.getInstance().removeBPMN(Long.parseLong(parentid));
	   
	   String userID=(String)session.getAttribute("userID");
	   String artifactID=(String)session.getAttribute("artifactID");
	   String taskID=(String)session.getAttribute("taskID");
	   
	   MyServiceImplProxy eif = new MyServiceImplProxy();
	   boolean bool2=eif.deleteArtifact(Integer.parseInt(taskID), Integer.parseInt(artifactID), userID);
	   
	   if(!bool)
	   {
	      rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing ServiceProxy!</errinfo></return>";
	   }
	   else
	   {
	     rp="<return status=\"true\"></return>";
	   }
	   
	out.print(rp);

 %>
