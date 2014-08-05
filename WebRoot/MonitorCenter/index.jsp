<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String name=(String)session.getAttribute("userName");
	if(name==null||name=="")
	{
	  out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	  return ;
    }
    else if(name.equals("user1")){
      out.print("<script>window.location.href='/repository/MonitorCenter/general.jsp';</script>");
	  return ;
    }
    else{
      out.print("<script>window.location.href='/repository/MonitorCenter/usercpu.jsp';</script>");
	  return ;
    }
%>