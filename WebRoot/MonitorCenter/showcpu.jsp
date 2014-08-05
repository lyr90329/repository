<%@ page language="java" import="java.util.*,monitor.bean.*,monitor.util.*" pageEncoding="GBK"%>
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
MonitorInfo host=parser.parse();
if(host==null){
	out.print("<script>alert('No host information');window.location.href='/repository/index.jsp';</script>");
	return;
}
String reqhostId=request.getParameter("hostId");
String reqtomcatId=request.getParameter("tomcatId");
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
			setInterval(auto,2000);
			function auto(){
				var img=$("#showcpu");
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
			<span class="user">administrator</span>
		</div>
		<div id="content">
			 <div id="content_nav">
			 <ul>
			 	<li>
			 		<a href="javascript:;" class="menuhref"><img src="img/arrowdown.png" alt=">"/> Monitor Info</a>
			 		<ul>
			 			<%if(host!=null){
						for(HostInfo hInfo:host.getHost()){
						int hostId=hInfo.getId();
			 			%>
			 			<li>
							<a href="javascript:;" class="menuhref"><img src="img/arrowdown.png" alt=">"/> Host:<%=hostId %></a>
							<ul class="<%=reqhostId==null&&hostId!=host.getFirstHostInfo().getId()||reqhostId!=null&&Integer.parseInt(reqhostId)!=hostId?"hide":"" %>">
								<li>
									<a href="general.jsp?hostId=<%=hostId %>">General</a>
								</li>
								<li>
									<a href="showcpu.jsp?hostId=<%=hostId %>" class="<%=reqhostId==null&&hostId==host.getFirstHostInfo().getId()||reqhostId!=null&&reqtomcatId==null&&reqappId==null&&hostId==Integer.valueOf(reqhostId)?"current":"" %>">Cpu</a>
								</li>
								<li>
									<a href="showmemory.jsp?hostId=<%=hostId %>">Memory</a>
								</li>
								<li>
									<a href="showNet.jsp?hostId=<%=hostId %>">Network</a>
								</li>
								<li>
									<a href="showdisk.jsp?hostId=<%=hostId %>">Disk</a>
								</li>
						<%List<TomcatInfo> tomcatList=hInfo.getTomcat();
						if(tomcatList!=null)
						for(Iterator<TomcatInfo> tomcatIt=tomcatList.iterator();tomcatIt.hasNext();){ 
							TomcatInfo tomcatInfo=tomcatIt.next();
							int tomcatId=tomcatInfo.getPort();
						%>
							
						<li>
							<a href="javascript:;" class="menuhref"><img src="<%=reqtomcatId!=null&&hostId==Integer.valueOf(reqhostId)&&tomcatId==Integer.valueOf(reqtomcatId)?"img/arrowdown.png":"img/arrowright.png" %>" alt=">"/> Tomcat:<%=tomcatId %></a>
							<ul class="<%=reqtomcatId!=null&&Integer.valueOf(reqhostId)==hostId&&Integer.valueOf(reqhostId)==hostId&&Integer.valueOf(reqtomcatId)==tomcatId?"":"hide" %>">
								<li>
									<a href="general.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>">General</a>
								</li>
								<li>
									<a href="showcpu.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>" class="<%=reqtomcatId!=null&&reqappId==null&&Integer.valueOf(reqhostId)==hostId&&tomcatId==Integer.valueOf(reqtomcatId)?"current":"" %>">Cpu</a>
								</li>
								<li>
									<a href="showmemory.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>">Momory</a>
								</li>
								<%List<AppInfo> appList=tomcatInfo.getApps();
								if(appList!=null)
								for(Iterator<AppInfo> appIt=appList.iterator();appIt.hasNext();){ 
									AppInfo appInfo=appIt.next();
									String appId=appInfo.getName();
								%>
								<li>
									<a href="javascript:;" class="menuhref"><img src="<%=appId.equals(reqappId)&&hostId==Integer.valueOf(reqhostId)&&tomcatId==Integer.valueOf(reqtomcatId)?"img/arrowdown.png":"img/arrowright.png" %>" alt=">"/> <%=appId %></a>
									<ul class="<%=reqappId!=null&&Integer.valueOf(reqhostId)==hostId&&Integer.valueOf(reqtomcatId)==tomcatId&&reqappId.equals(appId)?"":"hide" %>">
										<li>
											<a href="general.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>&appId=<%=appId %>">General</a>
										</li>
										<li>
											<a href="showcpu.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>&appId=<%=appId %>" class="<%=reqappId!=null&&Integer.valueOf(reqhostId)==hostId&&tomcatId==Integer.valueOf(reqtomcatId)&&appId.equals(reqappId)?"current":"" %>">Cpu</a>
										</li>
										<li>
											<a href="showthroughput.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>&appId=<%=appId %>">Throughput</a>
										</li>
										<li>
											<a href="showresponsetime.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>&appId=<%=appId %>">Response Time</a>
										</li>
										<li>
											<a href="showinvokeresult.jsp?hostId=<%=hostId %>&tomcatId=<%=tomcatId %>&appId=<%=appId %>">Invoke Result</a>
										</li>
									</ul>
								</li>
								<%} %>
							</ul>
						</li>
						<%} %>
			 		</ul>
			 	</li>
			 	<%}} %>
			 </ul>
			 </ul>
			</div>
			<div class="content_data">
			 <%if(reqtomcatId==null&&reqappId==null){ 
			 	HostInfo hInfo=host.getHostInfoById(Integer.valueOf(reqhostId));
			 %>
				<div class="content_data_title">
					Host <%=reqhostId==null?host.getFirstHostInfo().getId():reqhostId %> CPU
				</div>
				<div class="content_data_table">
					<table>
						<tr>
							<td>
								<img id="showcpu" src="GetCpuInfo?hostId=<%=reqhostId %>&time=1"/>
							</td>
						</tr>
					</table>
				</div>
			<%}else if(reqappId==null){ 
				TomcatInfo tInfo=host.getHostInfoById(Integer.valueOf(reqhostId)).getTomcatInfoById(Integer.valueOf(reqtomcatId));				
				if(tInfo!=null)
			%>
				<div class="content_data_title">
					Tomcat <%=reqtomcatId %> CPU
				</div>	
				<div class="content_data_table">
					<table>
						<tr>
							<td>
								<img id="showcpu" src="GetCpuInfo?hostId=<%=reqhostId %>&tomcatId=<%=reqtomcatId %>&time=1"/>
							</td>
						</tr>
					</table>
				</div>
			<%}else{ 
				AppInfo aInfo=host.getHostInfoById(Integer.valueOf(reqhostId)).getTomcatInfoById(Integer.valueOf(reqtomcatId)).getAppInfoById(reqappId);
				if(aInfo!=null)
			%>
				<div class="content_data_title">
					<%=reqappId %> CPU
				</div>	
				<div class="content_data_table">
					<table>
						<tr>
							<td>
								<img id="showcpu" src="GetCpuInfo?hostId=<%=reqhostId %>&tomcatId=<%=reqtomcatId %>&appId=<%=reqappId %>&time=1"/>
							</td>
						</tr>
					</table>
				</div>
			<%} %>
			</div>
			<div class="clear"></div>
		</div>
    </div>
    <div id="footer">
		<p>Copyright (c) Software Design and Productivity Group
		The Institute of Advanced Computing Technology, Beijing, China.</p>
	</div>
	<!-- end #footer -->
  </body>
</html>
