<%@ page language="java" pageEncoding="GBK" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String name = (String)session.getAttribute("userName");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Service Cloud Platform</title>
<link href="css/firstpage_style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="wrapper">
	<div id="header">
		<!--<div id="logo">
			<h1><a name="pagetop">Service Cloud Platform</a></h1>
			<p> designed by  BUAA-ACT</p>
		</div>
	--></div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li><a href="/repository">Home</a></li>
			<li><a href="/servicexchange">ServiceXchange</a></li>
			<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
			<li><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
			<li><a href="/repository/ServiceCloud/Toolkits.jsp">Toolkits</a></li>
			<li><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
            <li class="current_page_item"><a href="/repository/ServiceCloud/What_is_new.jsp">What is new</a></li>
			<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="quickstart">
	<div id="quickstart-bgtop">
	<div id="quickstart-bgbtm">
		<div id="quickstart-content">
			<div class="post">
				<h3 class="title"><a href="#">What is new</a></h3>
                <h4>Service4All v2.0</h4>
				<ul>
                     
					<li>Support for Linux.</li>
                    <li>The function of composite web services is added.</li>
                    <li>Multiple replicas for WebApp deployment is available.</li>
                    <li>Some bugs are fixed.</li>
				</ul>
				<div style="clear: both;">&nbsp;</div>
			</div>
			
		</div>	
		<!-- end #content -->
		<div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
        <div style="clear: both;">&nbsp;</div>
	</div>
	</div>
	</div>
	<!-- end #page -->
</div>
	<div id=toTop><a href="#">Top<img src="images/arrow_up.png"/><br/></a></div>
	<div id="footer">
		<p>Copyright (c) Software Design and Productivity Group
		The Institute of Advanced Computing Technology, Beijing, China.</p>
	</div>
	<!-- end #footer -->
</body>
</html>
