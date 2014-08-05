<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath() + "/BPMN-PR";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="/WEB-INF/lib/tld/c.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/styles.css">

	<style type="text/css">
#login_info {
	position: absolute;
	top: 5px;
	right: 5px;

}

#login_info a{
	color: #000000;
	text-decoration: underline;
}

#login_info a:hover{
	color: #999999;
}
	</style>
  </head>
  
  <body>
    <div class="header">
    	<div class="logo">
    	<h1>
			Business Process Repository (BPR)
		</h1>
    	</div>
			<ul class="tablist">
				<li>
					<a href="../service/GetAllServiceInfo.action" >Home</a>
				</li>
				<li>
					<a class="current" href="${ path }ReferenceProcesses">ReferenceProcesses</a>
				</li>
			</ul>
	</div>
	<div id="login_info">
		<c:choose>
			<c:when test="${ sessionScope.user == null }">
				<a href="login_register.jsp">sign in</a>
			</c:when>
			<c:otherwise>
				<span style="color: white">${ sessionScope.user.name }</span> | <a href="SignOut">sign out</a>
			</c:otherwise>
		</c:choose>
	</div>
  </body>
</html>
