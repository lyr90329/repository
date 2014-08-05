<%@ page language="java" import="db.dao.Data,java.util.*,cn.org.act.sdp.serviceCloud.servicesAPI.ExposedInterfaces,cn.org.act.sdp.serviceCloud.servicesAPI.Resolution,cn.org.act.sdp.serviceCloud.feedback.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String id=(String)request.getParameter("bpmnid"); 
       String EngineBpmnId=(String)request.getParameter("EngineBpmnId"); 
       String userName=(String)session.getAttribute("userName");
          
	   boolean bool=Data.getInstance().unSubscribe(Long.parseLong(id),userName);
	   System.out.println("rempve.jsp  BpmnName:"+Data.getInstance().getBPMNNameById(Long.parseLong(id)));
	   Resolution resolution=new Resolution();
	   resolution.resolve();
	   UndeployFeedback bpmnUndeploy = ExposedInterfaces.bpmnUndeploy(Data.getInstance().getBPMNNameById(Long.parseLong(id)),EngineBpmnId);
	   
	   if(bool&&bpmnUndeploy.isSuccessful())
	   {
	      rp="Remove BPMN "+Data.getInstance().getBPMNNameById(Long.parseLong(id))+" Successfully!";
	    
	   }
	   else
	   {
	     rp="Removing BPMN "+Data.getInstance().getBPMNNameById(Long.parseLong(id))+" failed!";
	   }
	   
	out.print(rp);

 %>
