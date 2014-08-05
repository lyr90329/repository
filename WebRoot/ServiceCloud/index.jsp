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
<title>Service4All</title>
<link href="/repository/css/new_firstpage_style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="bigbox">
	<div id="topnav">
    	<ul>
        	<li class="blank1">&nbsp;</li>
            <li class="logo"><a href="/repository">Service4All</a></li>
            <li><a href="/servicexchange">ServiceXchange</a></li>
            <li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
            <li><a href="/repository/ServiceCloud/resources.jsp">AppEngine</a></li>
            <li class="blank2"><%if(name==null) {%>&nbsp;<%} else { out.print(name); }%></li>
        	<li class="small"><a href="/repository/ServiceCloud/News.jsp">News</a></li>
            <li class="small"><a href="/repository/ServiceCloud/documentation.jsp">Docs</a></li>
            <li class="small"><a href="/repository/ServiceCloud/Toolkits.jsp">Download</a></li>
            <li class="small"><a href="/repository/ServiceCloud/About.jsp">About</a></li>
            <li class="small"><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
        </ul>
    </div>
    <div id="topic"></div>
    <div id="stepbox">
    	<ul>
        	<li class="a1"><a href="/servicexchange"><img src="/repository/images/tinow.jpg" /></a></li>
            <li class="a2"><a href="/repository/ServiceCloud/MyServiceContainer.jsp"><img src="/repository/images/tinow.jpg" /></a></li>
            <li class="a3"><a href="/repository/ServiceCloud/resources.jsp"><img src="/repository/images/tinow.jpg" /></a></li>
        </ul>
    </div>
    <div id="dwarea">
    	<p><a href="/repository/ServiceCloud/Toolkits.jsp">Download Toolkits</a><a href="/repository/ServiceCloud/documentation.jsp">Read documents</a></p>
    </div>
    <dl id="newsbox">
    	<dt>News</dt>
        <dd><span>2013-9-10</span><a href="#">The new website will be unveiled.</a></dd>
        <dd><span>2013-9-10</span><a href="#">Welcome!Freshman!Warmly welcome to the Institude of Advanced Computing Technology</a></dd>
        <dd><span>2013-9-10</span><a href="#">Apple expected to launch iPhone 5S,lower-priced iPhone 5C todqy</a></dd>
    </dl>
    <ul id="infobox">
    	<li class="a1"><a href="/repository/ServiceCloud/quickStart.jsp">Quick Start</a></li>
        <li class="a2"><a href="/repository/ServiceCloud/What_is_new.jsp">What is New</a></li>
        <li class="a3"><a href="/repository/ServiceCloud/publications.jsp">Scientific&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br />Publications</a></li>
    </ul>
    <div id="footer">
    	Copyright (C) The Institute of Advanced Computing Technology,Beihang University,Beijing,China.<br />¾©ICP±¸13036076ºÅ
    </div>
</div>
</body>
</html>
