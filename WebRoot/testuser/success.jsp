<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Success</title>
 
  </head>
  
  <body>
    <%
	String name=null;
//	name=request.getParameter("userName");
     name = (String)session.getAttribute("userName");
	if(name!=null) 
	%>
	
	Welcome,<%=name%>!
  </body>
</html>
