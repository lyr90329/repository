<%@ page language="java" import="db.dao.J2WSDao,java.util.*"
	pageEncoding="gb2312"%>
<%
	String fileName = (String) request.getParameter("fileName");
	String userName = (String) request.getParameter("userName");

	int result = new J2WSDao().delete(userName, fileName);

	String rp = "";

	if (result < 0) {
		rp = "Remove Service " + fileName + " failed!";
	} else {
		rp = "Remove Service " + fileName + " Successfully!";
	}

	out.print(rp);
	response.setHeader("Refresh", "1;URL=../J2WS.jsp");
%>
