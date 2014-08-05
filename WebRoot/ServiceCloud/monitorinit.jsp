<%@ page language="java" import="java.net.InetAddress,java.util.*" pageEncoding="gb2312"%>
<jsp:directive.page import="config.Config;"/>
<%
       String rp = "";
      
       String server="http://"+request.getServerName() + ":" + request.getServerPort();
	   
	   
	   
		    rp="<return status=\"true\">";
			rp=rp+"<monitor>"+Config.getflexmonitor()+"</monitor>";
			rp=rp+"<monitorinfo>"+Config.getflexmonitorinfo()+"</monitorinfo>";			
			rp=rp+"</return>";
			   
	   
//	     rp="<return status=\"false\"><errinfo>Some Exceptions occur when accessing database</errinfo></return>";
	  
	   
	out.print(rp);

 %>
