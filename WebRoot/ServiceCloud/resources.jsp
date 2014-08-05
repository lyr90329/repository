<%@ page language="java" pageEncoding="GBK" import="java.util.*,config.Config,repository.loadTester.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String name = (String)session.getAttribute("userName");
	QueryApplianceClient query = new QueryApplianceClient(Config.getApplianceQueryUrl());
	//QueryApplianceClient query = new QueryApplianceClient("http://219.143.213.95:7001/appengine/appliancequery/");
	ArrayList<Host> hosts = query.queryHost();
	//for(int i=0;i<hosts.size();i++){
	//	String id=hosts.get(i).getId();
	//	String replace_id=id.replace(id.substring(id.indexOf("//")+2,id.lastIndexOf(".")),"219.143.213");
	//	hosts.get(i).setId(replace_id);
	//}
	String date=hosts.get(0).getTimestamp();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<meta http-equiv="content-type" content="text/html; charset=GBK" />
		<title>Service Cloud Platform</title>
		<link href="../css/firstpage_style.css" rel="stylesheet"
			type="text/css" media="screen" />
		<script
			src="http://api.map.baidu.com/api?v=1.5&ak=2ec523fe70b7ab261079cf043986cc30"
			type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/service_cloud.js"></script>

		<style type="text/css">
/* CSS Document */
a {
	color: #c75f3e;
}

#idle {
	background-color: #AFEEEE;
	width: 15px;
	height: 15px;
	display: inline;
}

#normal {
	background-color: #FFED64;
	width: 15px;
	height: 15px;
	display: inline;
}

#busy {
	background-color: #EE5D7F;
	width: 15px;
	height: 15px;
	display: inline;
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
	font-size: 11px;
	padding: 6px 6px 6px 18px;
	color: #4f6b72;
	min-height: 25px;
	text-align: center;
	height: 35px;
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

th.wide1 {
	width: 50%
}

th.wide2 {
	width: 5%
}
</style>

	</head>
<body onload="initialize();">
<div id="wrapper">
	<div id="header">
		<!--<div id="logo">
			<h1><a href="#">Service Cloud Platform</a></h1>
			<p> designed by  BUAA-ACT</p>
		</div>
	--></div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li><a href="/repository">Home</a></li>
			<li><a href="/servicexchange">ServiceXchange</a></li>
			<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
			<li class="current_page_item"><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
			<li><a href="/repository/MonitorCenter/index.jsp">Monitor Center</a></li>
			<li><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
			<li><a href="/repository/testuser/createAccount.jsp" target="_blank">Join Now</a></li>
			<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="page">
	<div id="page-bgtop">
	<div id="page-bgbtm">
		<div id="resource">
			<h2 class="title" style="display:block;height:100px;line-height:100px;"><img src="../images/appengine.png" width="100px" height="100px" style="float:left;"/><a href="#" style="display:block;float: left;">Service Cloud Platform--Resource Map</a></h2><b>Date:<%=date%></b>
			<br/>
				<div id="map_canvas" style="width:940px; height:600px"></div>
			<br/>
			<br/>
			<h2 class="title"><a href="#">Service Cloud Platform--Resources List</a></h2><br/><b>Date:<%=date%></b>&nbsp;&nbsp;&nbsp;&nbsp;
			Idle:<div id="idle">&nbsp;&nbsp;&nbsp;&nbsp;</div>  &nbsp;&nbsp;&nbsp;&nbsp;Normal:<div id="normal">&nbsp;&nbsp;&nbsp;&nbsp;</div> &nbsp;&nbsp;&nbsp;&nbsp;Busy:<div id="busy">&nbsp;&nbsp;&nbsp;&nbsp;</div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="http://112.124.46.148:8080/Monitor/">CloudMwMan</a>
			<br/>
			<table id="mytable" cellspacing="0" > 
				<caption> </caption> 
				<% 
				for(int j=0;j<hosts.size();j++){
				if(Float.parseFloat(hosts.get(j).getCpu())<=30.0){
				%>
				<tr>
					<td class="alt1">Host主机-<%=j%></td> 
				  	<td class="alt1">CPU: <%=hosts.get(j).getCpu()%>%</td> 
				    <td class="alt1">Memory: <%=hosts.get(j).getMemory() %>M</td> 
				</tr>
				<%}
				  else if(Float.parseFloat(hosts.get(j).getCpu())>30.0 && Float.parseFloat(hosts.get(j).getCpu())<90.0){ 
				 %>
				 <tr>
					<td class="alt2">Host主机-<%=j%></td> 
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
				 <% 
				 }
				 	ArrayList<SubAppliance> subAppliances=hosts.get(j).getHost();
				 	for(int i=0;i<subAppliances.size();i++){
				 %>
				 <tr>
				 	<td class="spec">容器</td> 
				    <td >Id: <%=subAppliances.get(i).getId() %></td> 
				    <td>Type: <%=subAppliances.get(i).getType() %></td>
				 </tr> 
				 <%}%>
				 <%}%>
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
<script type="text/javascript">
	
	var ipList=[];
	var hostsdesc='';
	<%for(int k=0;k<hosts.size();k++){%>
		hostsdesc+='CPU:';
		hostsdesc+=<%=hosts.get(k).getCpu()%>;
		hostsdesc+='%, Memory:';
		hostsdesc+=<%=hosts.get(k).getMemory()%>;
		hostsdesc+='MB;';
		ipList.push('<%=hosts.get(k).getId()%>');
	<%}%>
	hostsdesc=hostsdesc.substr(0,hostsdesc.length-1);
	var hostinfos=[];
	hostinfos=hostsdesc.split(";");
//	alert(hostinfos);
	var num=<%=hosts.size()%>;
//	num=5;
    var lats=[120.38256,120.15335,120.35406,120.05962,120.36925,120.16897,120.42543,120.34955,103.73333,106.71667,113.00000,118.78333,123.38333,112.53333,114.31667]
    var lgts=[36.06661,30.26825,36.07272,30.30991,36.08580,30.39582,36.07588,30.39020,36.03333,26.56667,28.21667,32.05000,41.80000,37.86667,30.51667]
     var map = new BMap.Map("map_canvas"); 
		var point = new BMap.Point(110.404, 30.915);  // 创建点坐标  
	    map.centerAndZoom(point, 4);                 // 初始化地图，设置中心点坐标和地图级别 
	    map.enableScrollWheelZoom(); 
	function initialize() 
  	{
	  	for(var i=0; i<num ; i++)
	  	{
	  	
 userip(ipList[i],i);
	  	}
	  	}
	  	 function userip(ip,i){

         ip=ip.substr(12);	  	 
   $.getScript("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip="+ip, function(){
   var prov=remote_ip_info["province"];
             var city=remote_ip_info["city"];
            if(prov==null||city==null)
            {
            var thisAddess='北京';
            jsShow(thisAddess,ip,i);
            }
            else{
             var thisAddess=prov+city
            
             jsShow(thisAddess,ip,i)
           }
        }
    );     
}


  function jsShow(address,ip,i){
  tempinfo=hostinfos[i];
	    var keyword = address;
	   	var localSearch = new BMap.LocalSearch(map);
		localSearch.enableAutoViewport();
　　	   localSearch.setSearchCompleteCallback(function (searchResult) {
　　　    var poi = searchResult.getPoi(0);
	   var point = new BMap.Point(poi.point.lng, poi.point.lat);
　　　    var marker = new BMap.Marker(point);        // 创建标注    
       marker.addEventListener("click", function(e){    
 	   		   var opts = {    
 		width : 250,     // 信息窗口宽度    
 		height: 70,     // 信息窗口高度    
 		title : tempinfo+"<br/>"+"<a href='resources_detail.jsp?id="+i+"'>Host-"+i+"    More</a>"  // 信息窗口标题   
		}    
       	var infoWindow = new BMap.InfoWindow("",opts);  // 创建信息窗口对象    
		map.openInfoWindow(infoWindow, point);      // 打开信息窗口
	   })
       
	   map.addOverlay(marker);
　　});
　　localSearch.search(keyword);  
          
                }
	
  
	</script>
</body>
</html>
