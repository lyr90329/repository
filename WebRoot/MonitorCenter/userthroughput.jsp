name<%@ page language="java" import="java.util.*,monitor.bean.*,monitor.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String name=(String)session.getAttribute("userName");
	
	if(name==null||name=="")
	{
	  out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	  return ;
    }	
DocParser parser=new DocParser();
UserInfo userInfo=parser.parseUser(name);
if(userInfo==null){
	out.print("<script>alert('No user information');window.location.href='/repository/index.jsp';</script>");
	return;
}
String reqappId=request.getParameter("appId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Service Cloud Platform</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript">
		$().ready(function(){
			$("a.menuhref").click(function(){
				var ul=$(this).next("ul");
				var img=$(this).children("img");
				ul.toggle(200);
				if(img.attr("src")=="img/arrowright.png")
					img.attr("src","img/arrowdown.png");
				else
					img.attr("src","img/arrowright.png");
			});
			setInterval(auto,30000);
			function auto(){
				var img=$("#show");
				var src=img.attr("src");
				src=src.substring(0,src.lastIndexOf("="))+"="+Math.random();
				img.attr("src",src);
			}
		});
		window.onbeforeunload=function(){
			var JscrollPos;
			if (typeof window.pageYOffset != 'undefined') {
   				JscrollPos = window.pageYOffset;
			}
			else if (typeof document.compatMode != 'undefined' &&
    			document.compatMode != 'BackCompat') {
   				JscrollPos = document.documentElement.scrollTop;
			}
			else if (typeof document.body != 'undefined') {
   				JscrollPos = document.body.scrollTop;
			}
			document.cookie='scrollTop='+JscrollPos;
		}
		window.onload=function()
		{
			var arr;
			if(arr=document.cookie.match(/scrollTop=([^;]+)(;|$)/))
				document.documentElement.scrollTop=parseInt(arr[1]);
			document.body.scrollTop=parseInt(arr[1]);
		}
	</script>

  </head>
  
  <body>
    <div id="wrapper">
    	<div id="header">
			<div id="logo">
				<h1><a name="pagetop">Service Cloud Platform</a></h1>
				<p> designed by  BUAA-ACT</p>
			</div>
		</div>
		<!-- end #header -->
		<div id="menu">
			<ul>
				<li><a href="/repository">Home</a></li>
				<li><a href="/servicexchange">ServiceXchange</a></li>
				<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
				<li><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
				<li class="current_page_item"><a href="/repository/MonitorCenter/index.jsp">Monitor Center</a></li>
				<li><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
				<li><a href="/repository/testuser/createAccount.jsp" target="_blank">Join Now</a></li>
				<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
			</ul>
		</div>
		<!-- end #menu -->
		<div id="content_title">
			<span class="title">Monitor Resources</span>
			<span class="user"><%=userInfo.getName() %></span>
		</div>
		<div id="content">
			 <div id="content_nav">
			 <ul>
			 	<li>
			 		<a href="javascript:;" class="menuhref"><img src="img/arrowdown.png" alt=">"/> Monitor Info</a>
			 		<ul>
			 			<li>
			 				<a href="usercpu.jsp">Cpu</a>
			 			</li>
			 			<%for(Iterator<AppInfo> appIt=userInfo.getApps().iterator();appIt.hasNext();){
			 				AppInfo aInfo=appIt.next();
			 				String appId=aInfo.getName();
			 			 %>
			 			 <li>
			 			 	<a href="javascript:;" class="menuhref"><img src="<%=appId.equals(reqappId)?"img/arrowdown.png":"img/arrowright.png" %>" alt=">"/> <%=appId %></a>
			 				<ul class="<%=appId.equals(reqappId)?"":"hide" %>">
			 					<li>
									<a href="usercpu.jsp?appId=<%=appId %>">Cpu</a>
								</li>
								<li>
									<a href="userthroughput.jsp?appId=<%=appId %>" class="<%=appId.equals(reqappId)?"current":"" %>">Throughput</a>
								</li>
								<li>
									<a href="userresponsetime.jsp?appId=<%=appId %>">Response Time</a>
								</li>
								<li>
									<a href="userinvokeresult.jsp?appId=<%=appId %>">Invoke Result</a>
								</li>
			 				</ul>
			 			 </li>
			 			 <%} %>
			 		</ul>
			 	</li>
			 </ul>
			 </div>
			 <div class="content_data">
				<div class="content_data_title">
					<%=reqappId%> Throughput
					<em>refresh per 
					<select>
						<option>5</option>
						<option>10</option>
						<option>30</option>
						<option>60</option>
					</select>
					s</em>
				</div>
				<div class="content_data_table">
					<table>
						<tr>
							<td>
								<img id="show" src="GetUserThroughputInfo?appId=<%=reqappId %>&time=1" />
							</td>
						</tr>
					</table>
				</div>
			 </div>
			 <div class="clear"></div>
		</div>
		<div id="footer">
		<p>Copyright (c) Software Design and Productivity Group
		The Institute of Advanced Computing Technology, Beijing, China.</p>
		</div>
	</div>
  </body>
</html>
