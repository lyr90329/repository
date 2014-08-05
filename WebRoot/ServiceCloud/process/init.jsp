<%@ page language="java" import="java.net.InetAddress,java.util.*" pageEncoding="gb2312"%>
<%
       String rp = "";
      
       String server="http://"+request.getServerName() + ":" + request.getServerPort();
	   String createfolder="/repository/ServiceCloud/process/createFolder.jsp";
	   String creatediagram="/repository/ServiceCloud/process/createDiagram.jsp";
	   String remove="/repository/ServiceCloud/process/remove.jsp";
	   String save="/repository/ServiceCloud/process/save.jsp";
	   String getcatalog="/repository/ServiceCloud/process/getCatalogue.jsp";
	   String getprocess="/repository/ServiceCloud/process/getProcess.jsp";
	   String deploy="/repository/ServiceCloud/process/deploy.jsp";
	   String getservice="/repository/ServiceCloud/process/getService.jsp";
	   
		    rp="<return status=\"true\">";
			rp=rp+"<createfolder>"+server+createfolder+"</createfolder>";
			rp=rp+"<creatediagram>"+server+creatediagram+"</creatediagram>";
			rp=rp+"<remove>"+server+remove+"</remove>";
			rp=rp+"<save>"+server+save+"</save>";
			rp=rp+"<getcatalog>"+server+getcatalog+"</getcatalog>";
			rp=rp+"<getprocess>"+server+getprocess+"</getprocess>";
			rp=rp+"<deploy>"+server+deploy+"</deploy>";
			rp=rp+"<getservice>"+server+getservice+"</getservice>";
			rp=rp+"</return>";
			   
	   
//	     rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	  
	   
	out.print(rp);

 %>
