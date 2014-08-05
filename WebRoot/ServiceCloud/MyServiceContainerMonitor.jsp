<%@ page language="java" pageEncoding="ISO-8859-1" 
import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String serviceId=request.getParameter("serviceId");
	System.out.println("serviceId"+serviceId);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Service Cloud</title>
		<meta content="text/html; charset=ISO-8859-1"
			http-equiv="content-type" />
		<link rel="stylesheet" href="css/style.css" type="text/css" />
	</head>
	<body>
		<div id="container">
			<jsp:include page="header.jsp" />
			
			<div id="footer">
				<p>
					Copyright &copy;<%=serviceId%> 2009 Your Website. All rights reserved. Design by SDP.
					
				</p>
			</div>
		</div>
	</body>
</html>
