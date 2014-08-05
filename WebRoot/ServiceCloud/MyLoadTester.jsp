<%@ page language="java" pageEncoding="GBK"
	import="java.util.*,config.Config,db.dao.*,db.entity.*,constant.MenuItems,repository.loadTester.*"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	String name=(String)session.getAttribute("userName");
	if(name==null||name=="")
	{
	   out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	   return ;
    }
    QueryApplianceClient query = new QueryApplianceClient(Config.getApplianceForLoadTest());
	ArrayList<String> appliances = query.queryAppliance();
	String wsdlURL=(String)request.getParameter("wsdlURL");
	String type=(String)request.getParameter("type");
	String serviceid=(String)request.getParameter("serviceid");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">

	<head profile="">
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<title>MyLoadTester</title>
		<link rel="stylesheet" href="../css/style1.css" type="text/css"
			media="screen" />
		<link rel="stylesheet" href="../css/tabs.css" type="text/css" />

		<script
			src="http://api.map.baidu.com/api?v=1.5&ak=2ec523fe70b7ab261079cf043986cc30"
			type="text/javascript"></script>
		<!--<script
			src="http://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>

		-->
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/service_cloud.js"></script>
		<!--  	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>-->
		<script type="text/javascript"><!--//--><![CDATA[//><!--
	sfHover = function() 
	{
		var sfEls = document.getElementById("nav").getElementsByTagName("LI");
		for (var i=0; i<sfEls.length; i++) 
		{
			sfEls[i].onmouseover=function() 
			{
				this.className+=" sfhover";
			}
			sfEls[i].onmouseout=function() 
			{
				this.className=this.className.replace(new RegExp(" sfhover\\b"), "");
			}
		}
	}
	if (window.attachEvent) window.attachEvent("onload", sfHover);
	
	$(
		function(){
			$("#navsubmenu>a").hover(
				function(){
					var childMenu = $(this).next('ul');
					childMenu.show();
				}
			)
		}
	)
    //--><!]]></script>

		
	</head>
	<body onload="initialize()">

	<div id="mapsk"></div>
		<div id="header">
			<div id="header_left">
				<img src="../images/servicefoundry.png" width="180px" height="120px" />
			</div>
			<div id="header_right">
				<script src="../js/date.js" type="text/javascript"></script>
			</div>
		</div>
    <div id="navbar">
		<div id="navigation">
            <div id="nav_left">
  				<ul id="nav">
  				<% UserDao dao = new UserDao();
				List<User> userList=dao.findByProperty("userName",name);
				User user=userList.get(0);
				List<String> items = new ArrayList();
				int n = user.getConf();
				 %>
				 	<li><a href="<%= MenuItems.addrs[0] %>"><font size=2><%=MenuItems.items[0] %></font></a></li>
				 	<li id="navsubmenu">
				 		<a href="#"><font size=2>Deployments</font></a>
				 		<ul>
				 <% 
				 n >>= 1;
				 for(int i=1;i<4;i++){ 
				 	if(n % 2 == 1){
				 %>
				 			<li><a href="<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
				 <% }
				n >>= 1;
				}%>
						</ul>
					</li>
					<li id="navsubmenu" class="current_page_item">
						<a href="#"><font size=2>Tools</font></a>
						<ul>
					<% for(int i=4;i<9;i++){
						if(n % 2 == 1){
					 %>
				 			<li><a href="<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
				 	<% }
				n >>= 1;
				}%>
						</ul>
					</li>
					<li id="navsubmenu">
						<a href="#"><font size=2>Resources</font></a>
						<ul>
					<% for(int i=9;i<11;i++){
						if(n % 2 == 1){
					 %>
				 			<li><a href="<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
					 <% }
				n >>= 1;
				}%>
						</ul>
					</li>
					<li id="navsubmenu">
						<a href="#"><font size=2>DataServices</font></a>
						<ul>
					<% for(int i=11;i<14;i++){
						if(n % 2 == 1){
					 %>
				 			<li><a href="<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
					 <% }
				n >>= 1;
				}%>
						</ul>
					</li>
				 	<li><a href="menu_conf.jsp">>></a></li>
                </ul>
            </div>
            <div id="nav_right"></div>
            <div class="clear"></div>
        </div>
	</div> 


		<div id="tab_header">
			<ul id="primary">
				<li>
					<span>Node Selection</span>
					<ul id="secondary">
						<li></li>
					</ul>
				</li>
				<li>
					<a href="TesterConfig.jsp">Test Config</a>
				</li>
				<li>
					<a href="TesterMonitor.jsp">Test Monitor</a>
				</li>
				<li>
					<a href="TesterResult.jsp">Test Results</a>
				</li>
			</ul>
		</div>
		<div id="main">
			<div id="contents">
				<h2>
					Select the test nodes you want
				</h2>

				<div id="map_canvas" style="width: 850px; height: 600px"></div>
				<div 
					style="width: 250px; height: 600px; position: absolute; top: 320px; left: 910px">
					<input style="width: 80px; font-family: verdana" type="button"
						value="Submit" onclick="submit()" />
					<h2>
						The Selected Nodes:
					</h2>
					<div id="selected_nodes">
					</div>
				</div>

			</div>
		</div>

		<div id="content">
			<div id="postarea">

			</div>
		</div>

		 content end here 


		<div class="clear"></div>
		<div id="footer_bg">
			<div id="footer">
				<div id="footer_center" align="center">
					<center>
						Copyright &copy; 2010
						<a href="http://wp.loreleiwebdesign.com" title="Wordpress Theme">
							ServiceFoundry</a> -- Designed by BUAA-ACT-SDP.
					</center>
					<br />
					Website powered by
					<a href="http://wordpress.org">WordPress</a> and
					<a href="http://topwpthemes.com/stylio">Stylio wordpress theme</a>
					designed by TopTut.com & TopWPThemes.com.
					<br />
					Visit WebHostingFan.com for the latest news on
					<a href="http://www.webhostingfan.com">web hosting</a> and
					<a href="http://www.webhostingfan.com/category/cms/">cms review</a>.
				</div>
				<div class="clear"></div>
			</div>
		</div>
	<script type="text/javascript">
   
        var num=<%=appliances.size()%>;
        var map = new BMap.Map("map_canvas"); 
		var point = new BMap.Point(110.404, 30.915);  // 创建点坐标  
	    map.centerAndZoom(point, 4);                 // 初始化地图，设置中心点坐标和地图级别 
	    map.enableScrollWheelZoom(); 
    	var ipList=[];
   		var laList=[120.38256,120.15335,121.4406,120.05962,120.36925,120.16897,120.42543,120.34955,103.73333,106.71667,113.00000,118.78333,123.38333,112.53333,114.31667];
    	var lgList=[36.06661,30.26825,31.34272,30.30991,36.08580,30.39582,36.07588,30.39020,36.03333,26.56667,28.21667,32.05000,41.80000,37.86667,30.51667];
 		var markersArray = [];
    	var selectedIP=[];

      <%for(int j=0; j<appliances.size(); j++){%>
    	ipList.push('<%=appliances.get(j)%>');
      <%}%>
		

	
  	function initialize() 
  	{
  		 
	  	for(var i=0; i<num ; i++)
	  	{
        	userip(ipList[i],i);
	   	    }
	   	    }
               
 function userip(ip,i){
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
	    var keyword = address;
	   	var localSearch = new BMap.LocalSearch(map);
		localSearch.enableAutoViewport();
　　	   localSearch.setSearchCompleteCallback(function (searchResult) {
　　　    var poi = searchResult.getPoi(0);
	   var point = new BMap.Point(laList[i], lgList[i]);
　　　    var marker = new BMap.Marker(point);        // 创建标注    
 
       marker.addEventListener("click", function(e){    
 	   		    var nodelist_div=document.getElementById("selected_nodes");
		    nodelist_div.innerHTML+='Test Node '+i+'<br><br>';
		    selectedIP.push(ipList[i]);
	   })
       
	   map.addOverlay(marker);
　　});
　　localSearch.search(keyword);              
                }
  
 //        	function attachSecretMessage(marker, number)
//	  	{ 
	  
//		  	var infowindow = new google.maps.InfoWindow( 
//		      { content: ipList[number], 
//		        size: new google.maps.Size(50,50) 
//		      });
//		  	google.maps.event.addListener(marker, 'click', function() {
//		    infowindow.open(map,marker);
		    
//		    var nodelist_div=document.getElementById("selected_nodes");
//		    nodelist_div.innerHTML+=ipList[number]+'<br><br>';
//		    selectedIP.push(ipList[number]);
//		  }); 
//		}
  
  	function submit()
	{
		var para='';
		var serviceid="<%=serviceid%>";
		var wsdlURL="<%=wsdlURL%>";
		var type="<%=type%>";
		for(var j=0; j<selectedIP.length;j++)
		{
			para+=selectedIP[j]+';';
		}
		if('inner'==type)
			window.location='/repository/ServiceCloud/TesterConfig.jsp?para='+para+'&serviceid='+serviceid+'&wsdlURL='+wsdlURL+'&type='+type;
		else
			window.location='/repository/ServiceCloud/TesterConfig.jsp?para='+para+'&serviceid='+serviceid+'&wsdlURL='+wsdlURL+'&type=external';
	}

</script>
	</body>
</html>