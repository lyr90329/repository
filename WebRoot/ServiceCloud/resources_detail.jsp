<%@ page language="java" pageEncoding="GBK" import="java.util.*,config.Config,repository.loadTester.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String name = (String)session.getAttribute("userName");
	String id = (String)request.getParameter("id");
	int nodeId=Integer.parseInt(id);
	QueryApplianceClient query = new QueryApplianceClient(Config.getApplianceQueryUrl());
	ArrayList<Host> hosts = query.queryHost();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Service Cloud Platform</title>
	<link href="css/firstpage_style.css" rel="stylesheet" type="text/css" media="screen" />
	<style type="text/css"> 
		/* CSS Document */ 
		
		a { 
		    color: #c75f3e; 
		} 
		
		#idle{
			background-color:#AFEEEE;
			width:15px;
			height:15px;
			display:inline;
		}
		
		#normal{
			background-color:#FFED64;
			width:15px;
			height:15px;
			display:inline;
		}
		
		#busy{
			background-color:#EE5D7F;
			width:15px;
			height:15px;
			display:inline;
		}
		
		#mytable { 
		    width: 100%; 
		    padding: 0; 
		    margin: 0; 
		} 
		
		caption { 
		    padding: 0 0 5px 0; 
		    width: 700px;      
		    text-align: right; 
		} 
		
		th { 
		    border-right: 1px solid #C1DAD7; 
		    border-bottom: 1px solid #C1DAD7; 
		    border-top: 1px solid #C1DAD7; 
		    letter-spacing: 2px; 
		    text-transform: uppercase; 
		    text-align: middle; 
		    padding: 6px 6px 6px 12px; 
		    background: #CAE8EA url(images/bg_header.jpg) no-repeat; 
		} 
		
		th.nobg { 
		    border-top: 0; 
		    border-left: 0; 
		    border-right: 1px solid #C1DAD7; 
		    background: none; 
		} 
		
		td { 
		    border-right: 1px solid #C1DAD7; 
		    border-bottom: 1px solid #C1DAD7; 
		    background: #fff; 
		    font-size:11px; 
		    padding: 6px 6px 6px 18px; 
		    color: #4f6b72; 
		    min-height:25px;
		    text-align:center;
		    height:35px;
		}
		
		td.alt1 {
			border-top: 0px; 
		    background: #AFEEEE; 
		    color: #797268; 
		    padding: 6px 6px 2px 18px; 
		}
		
		td.alt2 {
			border-top: 0px; 
		    background: #FFED64; 
		    color: #797268; 
		    padding: 6px 6px 2px 18px; 
		}
		
		td.alt3 {
			border-top: 0px; 
		    background: #EE5D7F; 
		    color: #797268; 
		    padding: 6px 6px 2px 18px; 
		}
		
		th.spec { 
		    border-left: 1px solid #C1DAD7; 
		    border-top: 0; 
		    background: #fff url(images/bullet1.gif) no-repeat; 
		  
		} 
		
		th.specalt { 
		    border-left: 1px solid #C1DAD7; 
		    border-top: 0; 
		    background: #f5fafa url(images/bullet2.gif) no-repeat; 
		    
		    color: #797268; 
		} 
		th.wide1{
		          width:50%
		       }
		th.wide2{
		          width:5%
		       }
	</style> 
	
	<script type="text/javascript">
	
	</script>
</head>
<body>
<div id="wrapper">
	<div id="header">
		<div id="logo">
			<h1><a href="#">Service Cloud Platform</a></h1>
			<p> designed by  BUAA-ACT</p>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li><a href="/repository">Home</a></li>
			<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
			<li><a href="/repository/service/GetAllServiceInfo.action">ServiceXchange</a></li>
			<li class="current_page_item"><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
			<li><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
			<li><a href="/repository/user/createAccount.jsp" target="_blank">Join Now</a></li>
			<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="page">
	<div id="page-bgtop">
	<div id="page-bgbtm">
		<div id="resource">
			<br/>
			<br/>
			<h2 class="title"><a href="#">Service Cloud Platform--Resources List</a></h2>
			<br/>&nbsp;&nbsp;&nbsp;&nbsp;
			Idle:<div id="idle">&nbsp;&nbsp;&nbsp;&nbsp;</div>  &nbsp;&nbsp;&nbsp;&nbsp;Normal:<div id="normal">&nbsp;&nbsp;&nbsp;&nbsp;</div> &nbsp;&nbsp;&nbsp;&nbsp;Busy:<div id="busy">&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<table id="mytable" cellspacing="0" > 
				<caption> </caption> 
				<% 
				for(int j=0;j<hosts.size();j++){
				if(j==nodeId){
					if(Float.parseFloat(hosts.get(j).getCpu())<=30.0){
				%>
				<tr>
					<td class="alt1">Host主机-<%=j%></td> 
				  	<td class="alt1">CPU: <%=hosts.get(j).getCpu()%>%</td> 
				    <td class="alt1">Memory: <%=hosts.get(j).getMemory() %>M</td> 
				</tr>
				<%}else if(Float.parseFloat(hosts.get(j).getCpu())>30.0 && Float.parseFloat(hosts.get(j).getCpu())<90.0){  %>
				<tr>
					<td class="alt2">Host主机</td> 
				  	<td class="alt2">CPU: <%=hosts.get(j).getCpu()%>%</td> 
				    <td class="alt2">Memory: <%=hosts.get(j).getMemory() %>M</td> 
				</tr>
				<%}
				  else if(Float.parseFloat(hosts.get(j).getCpu())>90.0){
				 %>
				 <tr>
					<td class="alt3">Host主机-<%=j%></td> 
				  	<td class="alt3">CPU: <%=hosts.get(j).getCpu()%>%</td> 
				    <td class="alt3">Memory: <%=hosts.get(j).getMemory() %>M</td> 
				</tr>
				 <% }
				 	ArrayList<SubAppliance> subAppliances=hosts.get(j).getHost();
				 	for(int i=0;i<subAppliances.size();i++){
				 %>
				 <tr>
				 	<td class="spec">容器</td> 
				    <td >Id: <%=subAppliances.get(i).getId() %></td> 
				    <td>Type: <%=subAppliances.get(i).getType() %></td>
				 </tr> 
				 <%}}}%>
			</table> 
		</div>
		<div style="clear: both;">&nbsp;</div>
	</div>
	</div>
	</div>
	<!-- end #page -->
</div>
	<div id="footer">
		<p>Copyright (c) Software Design and Productivity Group
		The Institute of Advanced Computing Technology, Beijing, China.</p>
	</div>
	<!-- end #footer -->
</body>
</html>
