<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*,db.entity.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Applications</title>
		<meta content="text/html; charset=ISO-8859-1"
			http-equiv="content-type" />
		<link rel="stylesheet" type="text/css" href="../css/logo.css" media="screen" />

	<link rel="stylesheet" type="text/css" href="../css/menu.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="../user/css/user.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="../user/css/account.css" media="screen" />
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/service_cloud.js"></script>
		<script type="text/javascript" src="js/all_applications.js"></script>
	</head>
	 <body>
   <table align="center" border="0" width="890px" height="76px" background="images/apphead.jpg">
<tr>
<td width="150px"><br></td><td width="400px"><table border="0" width="400px" height="70px">
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td></tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td></tr>
<tr>
<td align="center" width="100px"><a href="/repository" >Home</a></td>
<td align="center" width="100px"><a href="/repository/service/GetAllServiceInfo.action" >ServiceXchange</a></td>
<td align="center" width="100px"><a href="/repository/ServiceCloud/tutorials.jsp" >My Cloud</a></td>
<td align="center" width="100px"><a href="/repository/ServiceCloud/AllApplications" >Applications</a></td></tr>
</table></td>
<td><table border="0"  width="340px" height="70px">
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>&nbsp;</td></tr>
<tr>
<td width="40px">&nbsp;</td>
<td width="100px" align="right"><a href="/repository/testuser/login.jsp" target="_blank">Sign In</a></td>
<td width="40px" align="center">or</td>
<td align="left"><a href="/repository/user/createAccount.jsp" target="_blank">Join Now</a></td></tr>
<tr>
<%
	String name=null;
//	name=request.getParameter("userName");
     name = (String)session.getAttribute("userName");
	 
	%>
<td><% if(name!=null) out.print("Welcome");%></td>
<td>
 
	
	<%if(name!=null) out.print(name);%></td>
<td>&nbsp;</td>
<td><%if(name!=null) {%><a href="user/LogoutUser.action">logout</a><%} %></td></tr>
</table>
</td>
</tr>

</table>
	<table align="center" border="0" width="890px" align="center">
	<tr>
<td height="10px"></td>
</tr>
<tr>
<td><table style="background-color: rgb(255, 255, 255);border: 1px solid rgb(195, 204, 213);" width="890px">
<tr >
<td align="left"><FONT size="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Applications:</FONT></td>
</tr>
</table>
</td>
</tr>
<% int flag=0; %>
<c:forEach var="app" items="${list}">
<%flag=flag+1; %>
<tr>
<td><table  width="890px" <% if(flag%2==0){out.print("style=\"background-color: rgb(235, 244, 253);border: 1px solid rgb(215, 224, 233);\"");}else{out.print("style=\"background-color: rgb(255, 255, 255);border: 1px solid rgb(215, 224, 233); \"");} %>>
<tr>
<td width="200px" ><label>Application:</label></td>
<td width="400px">${app.name}</td>
<td  width="150px">&nbsp;</td>
<td>&nbsp;</td></tr>
<tr>
<td ><label>Description:</label></td>
<td>${app.description}</td>
<td>&nbsp;</td>
<td>
<form action="ApplicationDeploy?id=${app.id }" method="get">
										<input type="button" value="Subscribe" appId="${app.id }" onclick="subscribe_application(this)"/>
									</form>
									</td>
</tr>
<tr>
<td ><label>Address:</label></td>
<td><a href="${app.accessPage}" target="_blank">${app.accessPage}</a></td>
<td>&nbsp;</td>
<td>&nbsp;</td></tr>
</table></td>
</tr>
</c:forEach>
<tr>
<td>&nbsp;</td>
</tr>
</table>
<div class="bottom"><p>CopyRight@Software Design and Productivity Group</p>
The Institute of Advanced Computing Technology, Beijing, China. </div> 
  </body>	
	
</html>
