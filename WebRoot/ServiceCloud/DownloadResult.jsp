<%@ page language="java" pageEncoding="GBK" import="java.util.*,db.dao.*,db.entity.*,constant.MenuItems;" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	String name=(String)session.getAttribute("userName");
	if(name==null||name=="")
	{
	   out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	   return ;
    }
    String success=(String)request.getParameter("success");
    String fail=(String)request.getParameter("fail");
    String average=(String)request.getParameter("average");
    String[] success_arr=success.split(",");
    String[] fail_arr=fail.split(",");
    String[] average_arr=average.split(",");
    response.setContentType("application/octet-stream");//可以对文件进行另存为操作
	response.addHeader("Content-Disposition","attachment; filename=testresult.txt");
	 
	String result="Load Test Result:\r\n";
	result+="------------------------------------------------------------\r\n";
	for(int i=0;i<success_arr.length;i++)
	{
		result+="Success Number: ";
		result+=success_arr[i]+"  ";
		result+="   Fail Number: ";
		result+=fail_arr[i]+"  ";
		result+="   Average RTT: ";
		result+=average_arr[i]+"  ";
		result+="\r\n";
	}
	out.print(result+"------------------------------------------------------------\r\n");
%>