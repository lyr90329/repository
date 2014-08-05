<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath() + "/BPMN-PR";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/lib/tld/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <base href="<%=basePath%>" />
    <title>Login & Register</title>
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script type="text/javascript" src="js/repository.js"></script>
	<script type="text/javascript" src="js/register.js"></script>
  </head>
  <body>
  	<jsp:include page="search.html"></jsp:include>
  	<jsp:include page="header.jsp"></jsp:include>
	<center>
		<div style="color: red">${ msg }</div>
		<br /><br />
		<center><h2>Login & Register</h2></center>
		<br/><br/>
		<form action="SignIn" onSubmit="return onSubmit(this,'name')&&onSubmit(this,'password')">
			<label>name</label>
			<input type="text" name="name" />
			<br />
			<label>password</label>
			<input type="password" name="password" />
			<br /><br />
			<input type="submit" value="Login" />
			<input type="button" value="Register" onclick="register(this.parentNode)"/>
		</form>
	</center>
  </body>
</html>
