<%@ page language="java" import="db.dao.Data,java.util.*,db.dao.*" pageEncoding="gb2312"%>
<%
       String rp = "";
       String bpmnid=(String)request.getParameter("bpmnid"); 
       String userName=(String)session.getAttribute("userName");
       BpmnDao bpmnDao=new BpmnDao();
       int num=bpmnDao.deleteUploadBpmn(Integer.parseInt(bpmnid));
	   
	   if(num==1)
	   {
	      rp="Removed Bpmn Successfully!";
	    
	   }
	   else
	   {
	     rp="Removing Bpmn failed!";
	   }
	   
	out.print(rp);
	response.setHeader("Refresh","0;URL=./MyBpmnEngine.jsp");
 %>
