<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath() + "/BPMN-PR";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title>Error</title>
		<link rel="stylesheet" type="text/css" href="css/style.css"
			media="screen" />
	</head>
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<div style="text-align: center">
			<h1 style="color: red">
				Error
			</h1>
			<br></br>
			<br></br>
			<p>${ msg }
		</div>
	</body>
</html>