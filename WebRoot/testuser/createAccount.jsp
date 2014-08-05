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
    
    <title>Create Account</title>

	<link rel="stylesheet" type="text/css" href="user/css/user.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="user/css/account.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	
	<!-- ajax导入 -->
	<script src="user/js/validateUser.js" type="text/javascript"></script>
	<script type="text/javascript">
		function change(){
			var img=document.getElementById("image");
			img.src="user/VerifCode.action?ts="+new Date().getTime();
		}
	</script>
  </head>
  
  <body>

<div class="outer">
	<div class="outer_title">
		<strong>Create New Account</strong>
	</div>
	<div class="account_login">
		<div class="inner_title"><strong>Fill in your information</strong></div>
		<div class="login_container">
			<form action="user/SaveUser.action" method="post" onsubmit="return signUp(this)">
				<table width="100%">
					<tr>
						<td colspan="2"><div class="error_msg"><s:property value="msg" /></div></td>
					</tr>
					<tr>
						<td width="200" align="right"><div class="account_name">Email：</div></td>
						<td>
							<div>
								<span class="essential">*&nbsp;</span>
								<span class="account_content"><input type="text" name="userName" /></span>
							</div>
							<div class="account_info"></div>
						</td>
					</tr>
					<tr>
						<td align="right"><div class="account_name">Password：</div></td>
						<td>
							<div>
								<span class="essential">*&nbsp;</span>
								<span class="account_content"><input type="password" name="password" /></span>
							</div>
							<div class="account_info"></div>
						</td>
					</tr>
					<tr>
						<td align="right"><div class="account_name">Confirm Password：</div></td>
						<td>
							<div>
								<span class="essential">*&nbsp;</span>
								<span class="account_content"><input type="password" name="password_confirm" /></span>
							</div>
							<div class="account_info"></div>
						</td>
					</tr>
					<tr>
						<td align="right"><div class="account_name">Company：</div></td>
						<td>
							<div>
								<span class="non_essential">&nbsp;&nbsp;</span>
								<span class="account_content"><input type="text" name="company" /></span>
							</div>
							<div class="account_info"></div>
						</td>
					</tr>
					<tr>
						<td align="right"><div class="account_name">Job：</div></td>
						<td>
							<div>
								<span class="non_essential">&nbsp;&nbsp;</span>
								<span class="account_content"><input type="text" name="job" /></span>
							</div>
							<div class="account_info"></div>
						</td>
					</tr>
				</table>
				<div class="inner_title"><strong>Complete Your Registration</strong></div>
				<div class="control_botton">
					<input type="image" src="user/img/register_button.gif"> or <a href="testuser/login.jsp">cancel</a>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- 
<div class="bottom">The Institute of Advanced Computing Technology, Beijing, China. Email: <a href="mailto:servicexchange@act.buaa.edu.cn">servicexchange@act.buaa.edu.cn</a></div>
 -->
  </body>
</html>
