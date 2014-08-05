<%@ page language="java" pageEncoding="GBK" import="java.util.*,db.dao.TestResultDao,db.entity.TestResult;" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	String name=(String)session.getAttribute("userName");
	System.out.println("saveTestResult.jsp --> userName:"+name);
	if(name==null||name=="")
	{
	   out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	   return ;
    }
    String success=(String)request.getParameter("success");
    String fail=(String)request.getParameter("fail");
    String average=(String)request.getParameter("average");
    
    String strategy=(String)request.getParameter("strategy");
    String testNum=(String)request.getParameter("testNum");
    String type=(String)request.getParameter("type");
    String timeout=(String)request.getParameter("timeout");
    String url=(String)request.getParameter("url");
    String operation=(String)request.getParameter("operation");
    String serviceName=(String)request.getParameter("serviceName");
    
	TestResultDao dao =new TestResultDao();
	TestResult result=null;
	if(name!=null && !name.equals(""))
	{
		result=new TestResult(name, success, fail, average);
		result.setServiceName(serviceName);
		result.setStrategy(strategy);
		result.setTestNum(testNum);
		result.setTestOperation(operation);
		result.setTimeout(timeout);
		result.setType(type);
		result.setUrl(url);
		int rs=dao.save(result);
		if(rs== -1)
			out.print("error");
		else
			out.print("success");
	}
	else
		out.print("error");
%>