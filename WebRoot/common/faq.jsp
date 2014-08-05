<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>FAQ</title>

	<!-- css样式 -->
	<link rel="stylesheet" type="text/css" href="css/logo.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/user.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/login.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="js/menu.js" type="text/javascript"></script>
	
	<meta http-equiv="keywords" content="service,introduction">
	<meta http-equiv="description" content="introduction of serviceXchange">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	
		
  </head>
  
  <body>
  
<!-- Logo图片 -->
<div class="logo"></div>

<!-- 顶部菜单 -->
<div id="menu">
	<ul class="nav current-user">
		<%@ include file="../common/menu.html" %>
	</ul>
</div>
		<div class="outer_about">
			<h3>
				How to search a web service?
			</h3>
			<p>
				There are four ways to find a web service in the index page: <I>keyword search</I>, <I>xpath search</I>, <I>service category tree</I> and <I>tag cloud</I>.
			</p>
		</div>
		<div class="outer_about">
			<h3>
				What’s <I>keyword search</I>?
			</h3>
			<p>
				We create an index of keyword to web service by analyze WSDL file. You can find a web service by a word relating to it using <I>keyword search</I>.
			</p>
		</div>
		<div class="outer_about">
			<h3>
				What’s the <I>service category tree</I>?
			</h3>
			<p>
				We clustered the web services by description information in WSDL file. The service category tree is built to show the cluster result. Every category was displayed as a node while the services in the category list as the leaf.
			</p>
		</div>
		<div class="outer_about">
			<h3>
				What’s the <I>tag cloud</I>?
			</h3>
			<p>
				User can label one or several words as tag to a service.  Then every service was associate with tags. The most popular tags was list in the index page and the importance of a tag is shown with font size. By click on a tag you can get all services which were labeled with that tag.
			</p>
		</div>
		<div class="outer_about">
			<h3>
				What information could I get about a web service?
			</h3>
			<ul>
				<li>
					There will be five tabs in the page which you get by click a web service’s name in the index page, named <I>Overview</I>, <I>Details</I>, <I>Connectability</I>, <I>Try It</I> and <I>Comments</I>.
				</li>
				<li>
					The <I>Overview</I> tab lists web service’s summary information including service Id in ServiceExchange, service name, service description, web service url and WSDL url. The descriptions and tags provided by users displayed in the lower half of this tab.
				</li>
				<li>
					The <I>Details</I> tab lists two quality information of a web service: user feedback and service availability. User feedback collect and aggregate multidimensional rating from consumers. Service Availability shows the response time tendency as a broken line, which monitored by ServiceExchange for each operation. You can use the slider control to adjust the time period displayed.
				</li>
				<li>
					The <I>Connectability</I> tab shows the mining result of the web service connection relationship. You could find the Predecessors and Subsequent of a web service. A Predecessor of a web service means former’s  output message matches the latter’s input message, and vice versa, while two message match means they are structure equivalent.
				</li>
				<li>
					The <I>Try It</I> tab contain a interface consists of a input box and a output box ,where you could set the input parameters to invoke a web service’s operation and check the response output.
				</li>
				<li>
					The <I>Comments</I> tab shows the textual comments, with which you could exchange the perception with other consumers.
				</li>
			</ul>
		</div>
		<div class="outer_about">
			<h3>
				What's the <I>web services’ rank</I>?
			</h3>
			<p>
				User could give a multidimensional rating to a web service, from 1 to 5, the higher rating means the better performance.
			</p>
		</div>
		
<div class="bottom">The Institute of Advanced Computing Technology, Beijing, China. Email: <a href="mailto:servicexchange@act.buaa.edu.cn">servicexchange@act.buaa.edu.cn</a></div>

  </body>
</html>
