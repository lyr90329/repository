<%@ page language="java" pageEncoding="GBK" import="java.util.*,db.dao.*,db.entity.*,constant.MenuItems;" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	//String name=(String)session.getAttribute("userName");
	String name="user1";
	if(name==null||name=="")
	{
	   out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	   return ;
    }
    String success=(String)request.getParameter("success");
    String fail=(String)request.getParameter("fail");
    String average=(String)request.getParameter("average");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">
<head profile="">
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<title>MyServiceContainer</title>
	<link rel="stylesheet" href="../css/style1.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="../css/tabs.css" type="text/css" />
	
	<style type="text/css"> 
		/*样式*/ 
		#consuming_time{ font-size:12px; border:solid 1px #ccc; background-color:#f2f6fb; margin:10px 20px; height:40px; line-height:20px; padding:5px 10px; width:260px; } 
		#TwentyFourHourChart{ width:700px; height:240px; border:solid 1px #B3B3DC; position:relative; top:40px; left:20px; } 
	</style> 
	
	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
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
    //--><!]]></script>
    
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
    	google.load('visualization', '1', {packages: ['corechart']});
    </script>
    
    <script type="text/javascript">
    	var comboChart;
        var chart;
       	function drawVisualization(success_arr, fail_arr) 
       	{
	       	var data_arr=new Array();
	       	var first=['Month', 'Success', 'Fail'];
	       	data_arr.push(first);
	       	
	       	for(var i=0; i<success_arr.length;i++)
	       	{
	       		var bar_data=new Array();
	       		bar_data.push((i+1).toString(), parseInt(success_arr[i]), parseInt(fail_arr[i]));
	       		data_arr.push(bar_data);
	       	}
	       	
	         var data = google.visualization.arrayToDataTable(data_arr);
	
	         comboChart.draw(data, {
	           title : 'Successful and failed Times for Current Requests',
	           vAxis: {title: "Request Times"},
	           hAxis: {title: "Time Sequence"},
	           seriesType: "bars",
	           series: {2: {type: "line"}}
	         });
       }
       
       function drawChart(average_arr) 
       {
	       var data = new google.visualization.DataTable();
	       data.addColumn('string', 'Time');
	       data.addColumn('number', 'RTT');
	       data.addRows(average_arr.length);
	       
	       for(var i=0;i<average_arr.length;i++)
	       {
	       		data.setValue(i, 0, (i+1).toString());
	        	data.setValue(i, 1, parseInt(average_arr[i]));
	       }
	       chart.draw(data, {width: 800, height: 240, title: 'Average RTT Performance'});
      }
		      
      function initVisualization() {
         comboChart = new google.visualization.ComboChart(document.getElementById('chart_div1'));
       }
       
       function initChart() {
        chart = new google.visualization.LineChart(document.getElementById('chart_div2'));
        
        //一定要在initChart后才能调用！！！
        showChart();
      }
      
       google.setOnLoadCallback(initVisualization);
       google.setOnLoadCallback(initChart);
       
    </script>
    
    <script type="text/javascript">
    var success_para="<%=success%>";
    var fail_para="<%=fail%>";
    var average_para="<%=average%>";
    
    var success=new Array();
    var fail=new Array();
    var average=new Array();
	    
    function showChart()
    {
    	success=success_para.split(",");
    	fail=fail_para.split(",");
    	average=average_para.split(",");
    	
    	drawVisualization(success, fail);
    	drawChart(average);
	}
	
    </script>
    
    <!--[if lt IE 7]>
        <script defer type="text/javascript" src="js/pngfix.js"></script>
	    <style type="text/css">
	        *html .excrept_in {height: 1%;}
	    </style>
    <![endif]-->
</head>

<body>
    <div id="header">
	    <div id="topnav"> 
	        <div id="topnav_right">
	            <script src="../js/date.js" type="text/javascript"></script>
	        </div>
	    	<div class="clear"></div>
	    </div>
		=<!-- End Topnav -->

        <div id="header_left">
            <h1><a href="#">My  Cloud</a></h1>
            <p>个性化在线空间</p>
        </div>
        <div id="header_right"></div>
    </div>
    
    <div id="navbar">
        <div id="navigation">
            <div id="nav_left">
  				<ul id="nav">
            	<%
				UserDao dao = new UserDao();
				List<User> userList=dao.findByProperty("userName",name);
				User user=userList.get(0);
				List<String> items = new ArrayList();
				int n = user.getConf();
				for(int i = 0 ; i < MenuItems.items.length && n > 0 ; i++){
				if(n % 2 == 1) {
				%>
					<li><a href="<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
				<%
					}
				n >>= 1;
				}
 				%>
                <li><a href="menu_conf.jsp">>></a></li>
                </ul>
            </div>
            <div id="nav_right"></div>
            <div class="clear"></div>
        </div>
    </div>
    
    <!-- Header End Here -->
    
    <!-- Content Begin Here -->        

	<div id="tab_header">
		<ul id="primary">
			<li><a href="MyLoadTester.jsp">TesterSelect</a></li>
			<li><a href="TesterConfig.jsp">TesterConfig</a></li>
			<li><span>TesterMonitor</span>
				<ul id="secondary">
					<li><a href="../philosophy.html">Our Philosophy</a></li>
				</ul>
			</li>
			<li><a href="TesterResult.jsp">TesterResult</a></li>
		</ul>
	</div>
	<div id="main">
		<div id="contents">
			<h2>About Us</h2>
			<div id="chart_div1" style="width: 900px; height: 400px;"></div>
			<div id="chart_div2" style="width: 900px; height: 400px; top:20px; left:40px;"></div>
		</div>
	</div>

	<div id="content">
    	<div id="postarea">
		
		</div>
	</div>
	
	<!-- content end here -->

    <div class="clear"></div>
	<div id="footer_bg">
		<div id="footer">
    		<div id="footer_center" align="center">
       			<center> Copyright &copy; 2010 <a href="http://wp.loreleiwebdesign.com" title="Wordpress Theme"> ServiceFoundry</a> -- Designed by BUAA-ACT-SDP.</center><br/>
						Website powered by <a href="http://wordpress.org">WordPress</a> and <a href="http://topwpthemes.com/stylio">Stylio wordpress theme</a> designed by TopTut.com & TopWPThemes.com.<br/>
						Visit WebHostingFan.com for the latest news on <a href="http://www.webhostingfan.com">web
						hosting</a> and <a href="http://www.webhostingfan.com/category/cms/">cms review</a>.
   			</div>
 			<div class="clear"></div>
		</div>
	</div>
	
</body>
</html>