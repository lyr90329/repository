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
    
    <title>Login</title>
    
	<link rel="stylesheet" type="text/css" href="user/css/user.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/account.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
		
  </head>
  
  <body>

<div class="outer">
	<div class="outer_title">
		<strong>Login</strong>
	</div>
	<div class="account_login">
		<div class="inner_title"><strong>Fill in your information</strong></div>
		<div class="login_container">
			<form action="/repository/user/LoginUser.action" method="post">
				<table width="100%">
					<tr>
						<td colspan="2"><div class="error_msg"><s:property value="msg" /></div></td>
					</tr>
					<tr>
						<td width="200" align="right"><div class="account_name">UserName：</div></td>
						<td>
							<span class="account_content"><input type="text" name="userName" /></span>
							<span class="account_info" style="width:100px;"></span>
						</td>
					</tr>
					<tr>
						<td align="right"><div class="account_name">Password：</div></td>
						<td>
							<span class="account_content"><input type="password" name="password" /></span>
							<span class="account_info"></span>
						</td>
					</tr>
				</table>
				<div class="control_botton">
					<input type="image" src="user/img/login_button.gif">
				</div>
			</form>
		</div>
		<div class="prompt_message">
			<div class="message_link"><a href="testuser/createAccount.jsp">Create a new account</a></div>
		</div>
	</div>
	
</div>
<!--  
<div class="logonbottom" align="center">The Institute of Advanced Computing Technology, Beijing, China. Email: <a href="mailto:servicexchange@act.buaa.edu.cn">servicexchange@act.buaa.edu.cn</a></div>
-->
  </body>
</html>
