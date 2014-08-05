<%@ page language="java" pageEncoding="GBK" 

import="java.io.*,repository.loadTester.TesterMonitorClient,org.apache.axiom.om.OMAbstractFactory,org.apache.axis2.addressing.EndpointReference,org.apache.axis2.client.Options,org.apache.axis2.rpc.client.RPCServiceClient,org.apache.axiom.om.OMFactory,org.apache.axis2.AxisFault,java.util.Iterator,java.util.*,db.dao.*,repository.loadTester.*,db.entity.*,constant.MenuItems,repository.loadTester.*,org.apache.axiom.om.OMElement,java.io.File;" %>
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
    String jobId=(String)request.getParameter("jobId");
    String strategy=(String)request.getParameter("strategy");
    String testNum=(String)request.getParameter("testNum");
    String type=(String)request.getParameter("type");
    String timeout=(String)request.getParameter("timeout");
    String url=(String)request.getParameter("url");
    String operation=(String)request.getParameter("operation");
    String serviceName=(String)request.getParameter("serviceName");
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-

transitional.dtd">
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
    
    <script type="text/javascript" src="js/jsapi.js"></script>

        

       
    <script type="text/javascript"><!--
    	google.load('visualization', '1', {packages: ['corechart']});

    	
    	
    	
    	
 --></script>
    
    
       
    <script type="text/javascript">
    	var comboChart;
        var chart;
  
    	
       	function drawVisualization(success_arr, fail_arr) 
       	{
	       	var data_arr=new Array();
	       	var first=['Month', 'Successful', 'Failed'];
	       	data_arr.push(first);
	       	
	       	for(var i=0; i<success_arr.length;i++)
	       	{
	       		var bar_data=new Array();
	       		bar_data.push((i+1).toString(), parseInt(success_arr[i]), parseInt(fail_arr[i]));
	       		data_arr.push(bar_data);
	       	}
	       	
	         var data = google.visualization.arrayToDataTable(data_arr);
	
	           comboChart.draw(data, {
	           title : 'Successful and Failed Request Number',
	           vAxis: {title: "Request Number"},
	           hAxis: {title: "Test Steps"},
	           seriesType: "bars",
	           series: {2: {type: "line"}}
	         });
       }
       
       function drawChart(average_arr) 
       {
	       var data = new google.visualization.DataTable();
	       data.addColumn('string', 'Time');
	       data.addColumn('number', 'ART');
	       data.addRows(average_arr.length);
	       
	       for(var i=0;i<average_arr.length;i++)
	       {
	       		data.setValue(i, 0, (i+1).toString());
	        	data.setValue(i, 1, parseInt(average_arr[i]));
	       }
	      //chart.draw(data, {width: 800, height: 240, title: 'Average Response Time'});
	        chart.draw(data, {width:800,height:240,
	          title : 'Average Response Time',
	          vAxis: {title: "ART(ms)"},
	           hAxis: {title: "Test Steps"},
	         
	         });
      }
		      
      function initVisualization() {
     
         comboChart = new google.visualization.ComboChart(document.getElementById('chart_div1'));
       }
       
       function initChart() {

        chart = new google.visualization.LineChart(document.getElementById('chart_div2'));
        //一定要在initChart后才能调用AjaxSubmit！！！
        
        AjaxSubmit(monitor_para,'getMonitorResult.jsp');
      
      }
       google.setOnLoadCallback(initVisualization);
       google.setOnLoadCallback(initChart);
 
    </script>
    
     
    
     
    <script type="text/javascript">
    var monitor_para="jobId="+"<%=jobId%>";
    var strategy="<%=strategy%>";
    var success=new Array();
    var fail=new Array();
    var average=new Array();
    var old_subId='0';
    var finished='';
   
          var isstop='no';
        function abort(){
        isstop='yes';//“停止”标志
    		var url1="TesterMonitor?jobId="+"<%=jobId%>";
    		var jobid="<%=jobId%>";
            var xmlhttp;
    try{
			xmlhttp = new XMLHttpRequest();
		}
		catch(e)
		{
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

			
		xmlhttp.onreadystatechange=function(){
			if(4==xmlhttp.readyState){
				if(200==xmlhttp.status){
		        	var res =xmlhttp.responseText;
		        	//alert(res.toString());
		        
	        	}
		        else if(0==xmlhttp.status)
		        {
		        var res =xmlhttp.responseText;
		        	alert(res.toString());
		    
		        }
		        else
		        {
		        alert(xmlhttp.status.toString());
		        }
			}            
		}
	

		
	xmlhttp.open("post",url1,true);
		xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
		xmlhttp.send("jobId="+jobid);
		alert('Average Response Time is:'+average[average.length-1]+' ms');//显示平均响应时间
    	}
    
    function AjaxSubmit(para,url)
    {
		var xmlhttp;
       	try{
			xmlhttp = new XMLHttpRequest();
		}
		catch(e)
		{
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange=function(){
			if(4==xmlhttp.readyState){
				if(200==xmlhttp.status){
		        	var res =xmlhttp.responseText;
		        	ParseResponse(res);
	        	}
		        else 
              {
              alert("error!");
              }
			}            
		}
		xmlhttp.open("post",url,true);
		xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
		xmlhttp.send(para);
	}
	
	function again()
	{
	if(isstop=='no')
		AjaxSubmit(monitor_para,'getMonitorResult.jsp');
	}
	
	function ParseResponse(msg)
    {
   
    	var res_array=new Array(7);
    	res_array=msg.split(";");
    	var new_subId=res_array[5];
		
    	if(res_array[1] == 'running')
    	{
    		if(parseInt(old_subId)<parseInt(new_subId))
    		{
    			success.push(res_array[2]);
    			fail.push(res_array[3]);
    			average.push(res_array[4]);
    		}
    		else if(parseInt(old_subId)==parseInt(new_subId))
    		{
    			success[success.length-1]=res_array[2];
    			fail[fail.length-1]=res_array[3];
    			average[average.length-1]=res_array[4];
    		}
    		else
    		{
    			alert('response from server occurred error!');
    		}
    		drawVisualization(success, fail);
    		drawChart(average);
    		setTimeout(again,"2000");
    		
    	}
    	else if(res_array[1] == 'done')
    	{
    		if(parseInt(old_subId)==parseInt(new_subId))
    		{
    			success[success.length-1]=res_array[2];
    			fail[fail.length-1]=res_array[3];
    			average[average.length-1]=res_array[4];
    		}
    		else if(parseInt(old_subId)<parseInt(new_subId))
    		{
    			success.push(res_array[2]);
	    		fail.push(res_array[3]);
	    		average.push(res_array[4]);
    		}
    		else
    		{
    			alert('Error!');
    		}
    		drawVisualization(success, fail);
	    	drawChart(average);
	    	if(strategy='maximal')
	    	{
	    		alert('Average Response Time is:'+average[average.length-1]+' ms');
	    	}
	    	finished= 'true';
    	}
    	else
    	{
    		alert('Error!');
    	}
    	old_subId=new_subId;
	}
	

	
	function saveResult()
	{
		alert('enter save');
		if('true'==finished)
		{
			var testResult="strategy="+"<%=strategy%>"+"&testNum="+"<%=testNum%>"+"&type="+"<%=type%>"+"&timeout="+"<%=timeout%>"+"&url="+"<%=url%>"+"&operation="+"<%=operation%>"+"&serviceName="+"<%=serviceName%>"+"&success=";
			for(var i=0;i<success.length;i++)
			{
				testResult+=success[i];
				testResult+=',';
			}
			testResult=testResult.substring(0,testResult.length-1);
			testResult+='&fail=';
			for(var i=0;i<fail.length;i++)
			{
				testResult+=fail[i];
				testResult+=',';
			}
			testResult=testResult.substring(0,testResult.length-1);
			testResult+='&average=';
			for(var i=0;i<average.length;i++)
			{
				testResult+=average[i];
				testResult+=',';
			}
			testResult=testResult.substring(0,testResult.length-1);
			
			var xmlhttp;
	       	try{
				xmlhttp = new XMLHttpRequest();
			}
			catch(e)
			{
	            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function(){
				if(4==xmlhttp.readyState){
					if(200==xmlhttp.status){
			        	var res =xmlhttp.responseText;
			        	if('error'==res.toString())
			        		alert('save error!');
			        	else if('success'==res.toString())
			        		alert('save success!');
		        	}
			        else
			        {
			        	alert("Save Test Result Error!");
			        }
				}            
			}
			xmlhttp.open("post","saveTestResult.jsp",true);
			xmlhttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
			xmlhttp.send(testResult);
		}
		else
			alert('The Load Test has not finished!');
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
            <h1><a href="#">ServiceFoundry</a></h1>
            <p></p>
        </div>
        <div id="header_right"></div>
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
				 	<li><a href="<%= MenuItems.addrs[0] %>"><font size=2><%=MenuItems.items

[0] %></font></a></li>
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
    
    <!-- Header End Here -->
    
    <!-- Content Begin Here -->        

	<div id="tab_header">
		<ul id="primary">
			<li><a href="MyLoadTester.jsp">Node Selection<br /></a></li>
			<li><a href="TesterConfig.jsp">Test Config</a></li>
			<li><span>Test Monitor</span>
				<ul id="secondary">
					<li></li>
				</ul>
			</li>
			<li><a href="TesterResult.jsp">Test Results</a></li>
		</ul>
	</div>
	<div id="main">
		<div id="contents">
		
			 <!--<form name="Abort_form" action="TestMonitor" method="post">  
			  <input type="hidden" value="<%=jobId %>" name="jobId">
			  <input type = "submit" style="width: 80px ; margin-left: 1000px; margin-top: 50px;font-family:verdana" value = "abort">
            
             </form>  
		
			
			

			--><button style="width: 80px ; margin-left: 1000px; margin-top: 50px;font-family:verdana"  onclick="abort()">Stop</button>
		
			<div id="chart_div1" style="width: 900px; height: 400px;"></div>
			<div id="chart_div2" style="width: 900px; height: 400px; top:20px; left:40px;"></div>
			<div ><button style="width:80px;font-family:verdana" onclick="saveResult()">Save</button></div>
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
       			<center> Copyright &copy; 2010 <a href="http://wp.loreleiwebdesign.com" title="Wordpress Theme"> ServiceFoundry</a> -- Designed by BUAA-ACT-SDP.</center><br/>Website powered by <a href="http://wordpress.org">WordPress</a> and <a href="http://topwpthemes.com/stylio">Stylio wordpress theme</a> designed by TopTut.com & TopWPThemes.com.<br/>Visit WebHostingFan.com for the latest news on <a href="http://www.webhostingfan.com">webhosting</a> and <a href="http://www.webhostingfan.com/category/cms/">cms review</a>.
   			</div>
 			<div class="clear"></div>
		</div>
	</div>
	
</body>
</html>