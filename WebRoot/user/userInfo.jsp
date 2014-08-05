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
    
    <title>My Account</title>

	<!-- css样式 -->
	<link rel="stylesheet" type="text/css" href="css/logo.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/user.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/userInfo.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="js/menu.js" type="text/javascript"></script>
	
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

<div class="outer"><table><tr><td>
	<div class="outer_title">
		<strong>My Account</strong>
	</div>
	<div class="accountMenu">
		<p><strong>Account Information</strong></p>
		<ul>
			<li><a href="user/GetUserInfo.action">View Account</a></li>
		</ul>
		<p><strong>Account Log</strong></p>
		<ul>
			<li><a href="log/GetServiceLog.action">Service Log</a></li>
			<li><a href="log/GetQueryLog.action">Query Log</a></li>
			<li><a href="log/GetServiceSubscriptionLog.action">Service Subscription Log</a></li>
		</ul>
	</div>
	<div class="accountInfo">
		<div class="inner_title"><strong>Basic Information</strong></div>
		<table width="100%" border="0">
			<tr>
				<td width="150" align="right"><div class="key">User Name:</div></td>
				<td><div class="value"><s:property value="userBean.userName" /></div></td>
			</tr>
			<tr>
				<td align="right"><div class="key">Nationality:</div></td>
				<td><div class="value"><s:property value="userBean.nationality" /></div></td>
			</tr>
			<tr>
				<td align="right"><div class="key">Email:</div></td>
				<td><div class="value"><s:property value="userBean.email" /></div></td>
			</tr>
			<tr>
				<td align="right"><div class="key">Concern:</div></td>
				<td><div class="value"><s:property value="userBean.concern" /></div></td>
			</tr>
		</table>
		<div class="inner_title"><strong>Job Information</strong></div>
		<table width="100%" border="0">
			<tr>
				<td width="150" align="right"><div class="key">Company:</div></td>
				<td><div class="value"><s:property value="userBean.company" /></div></td>
			</tr>
			<tr>
				<td align="right"><div class="key">Job:</div></td>
				<td><div class="value"><s:property value="userBean.job" /></div></td>
			</tr>
		</table>
	</div>
	
</td></tr></table></div>

<div class="bottom">The Institute of Advanced Computing Technology, Beijing, China. Email: <a href="mailto:servicexchange@act.buaa.edu.cn">servicexchange@act.buaa.edu.cn</a></div>

  </body>
</html>
