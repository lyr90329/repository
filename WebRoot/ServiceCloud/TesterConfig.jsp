<%@ page language="java" pageEncoding="GBK" import="java.util.*,db.dao.*,db.entity.*,constant.MenuItems,repository.loadTester.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String serviceid="";
	String para=(String)request.getParameter("para");
	
	String temp_wsdlURL=(String)request.getParameter("wsdlURL");
	String wsdlURL="";
	if(temp_wsdlURL!=null&&(!temp_wsdlURL.equals("null")))
	wsdlURL=temp_wsdlURL;
	
	
	String type=(String)request.getParameter("type");
	if("inner".equals(type))
	{
		serviceid=(String)request.getParameter("serviceid");		
	}
	
	String soaptype=(String)request.getParameter("soaptype");
	
	String temp_operation=(String)request.getParameter("operation");  
	String operation="";
	if(temp_operation!=null)	
    operation=temp_operation;  
    
    String temp_soapContent=(String)request.getParameter("soapContent");
    String soapContent="";
    if(temp_soapContent!=null)
    soapContent=temp_soapContent;
	
	
	System.out.println("-------------------------------------------------------------");
	System.out.println("soaptype:"+soaptype);
	System.out.println("wsdlURL:"+wsdlURL);
	System.out.println("operation:"+operation);
	System.out.println("soapContent:"+soapContent);
	
	String name=(String)session.getAttribute("userName");
	if(name==null||name=="")
	{
	   out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	   return ;
    }	

%><!--
返回时，页面重新载入
--><%response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","No-cache");
response.setDateHeader("Expires", -1);
response.setHeader("Cache-Control", "No-store");%>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">

<head profile="">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />

<title>MyServiceContainer</title>

<link rel="stylesheet" href="../css/style1.css" type="text/css" media="screen" />

<link rel="stylesheet" href="../css/tabs.css" type="text/css" />

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/service_cloud.js"></script>
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
    
    <script type="text/javascript">
    	var strategy_value='static';
    	var test_num_value='single';
    	
    	function select1(obj){
	    	var num_td1=document.getElementById("num_td1");
	    	var beginnum_td1=document.getElementById("beginnum_td1");
	    	var endnum_td1=document.getElementById("endnum_td1");
	    	var stepnum_td1=document.getElementById("stepnum_td1");
	    	
	    	var num_td2=document.getElementById("num_td2");
	    	var beginnum_td2=document.getElementById("beginnum_td2");
	    	var endnum_td2=document.getElementById("endnum_td2");
	    	var stepnum_td2=document.getElementById("stepnum_td2");
	    	
    		num_td1.style.display=""
    		beginnum_td1.style.display="none"
    		endnum_td1.style.display="none"
    		stepnum_td1.style.display="none"
    		
    		num_td2.style.display=""
    		beginnum_td2.style.display="none"
    		endnum_td2.style.display="none"
    		stepnum_td2.style.display="none"
    		
    		strategy_value=obj.value;
    	}
    	
    	function select2(obj){
    		var num_td1=document.getElementById("num_td1");
	    	var beginnum_td1=document.getElementById("beginnum_td1");
	    	var endnum_td1=document.getElementById("endnum_td1");
	    	var stepnum_td1=document.getElementById("stepnum_td1");
	    	
	    	var num_td2=document.getElementById("num_td2");
	    	var beginnum_td2=document.getElementById("beginnum_td2");
	    	var endnum_td2=document.getElementById("endnum_td2");
	    	var stepnum_td2=document.getElementById("stepnum_td2");

    		num_td1.style.display="none"
    		beginnum_td1.style.display=""
    		endnum_td1.style.display=""
    		stepnum_td1.style.display=""
    		
    		num_td2.style.display="none"
    		beginnum_td2.style.display=""
    		endnum_td2.style.display=""
    		stepnum_td2.style.display=""
    		
    		strategy_value=obj.value;
    	}
    	
    	function select3(obj){
    		var num_td1=document.getElementById("num_td1");
	    	var beginnum_td1=document.getElementById("beginnum_td1");
	    	var endnum_td1=document.getElementById("endnum_td1");
	    	var stepnum_td1=document.getElementById("stepnum_td1");
	    	
	    	var num_td2=document.getElementById("num_td2");
	    	var beginnum_td2=document.getElementById("beginnum_td2");
	    	var endnum_td2=document.getElementById("endnum_td2");
	    	var stepnum_td2=document.getElementById("stepnum_td2");
    		
    		num_td1.style.display="none"
    		beginnum_td1.style.display=""
    		endnum_td1.style.display="none"
    		stepnum_td1.style.display=""
    		
    		num_td2.style.display="none"
    		beginnum_td2.style.display=""
    		endnum_td2.style.display="none"
    		stepnum_td2.style.display=""
    		
    		strategy_value=obj.value;
    	}
    	
    	function select_testnum(obj)
    	{
    		test_num_value=obj.value;
    	}
    	
    	function getSoap()
    	{
    		var request_type="<%=type%>";
    		var serviceid="<%=serviceid%>";
    		var wsdl=document.getElementById("wsdl");
    		var url=wsdl.value;
    		if(request_type=='inner')
    		{
    			//alert('inner');
    			window.location="getTestSoap.jsp?WSDL="+"<%=wsdlURL %>"+"&para="+"<%=para%>"+"&type="+request_type+"&serviceid="+serviceid;
    		}
    		else
    		{
    			//alert('external');
				window.location="getTestSoap.jsp?WSDL="+url+"&para="+"<%=para%>"+"&type="+'external';
			}
    	}
    	
    	function submit()
    	{
    		var type="<%=type%>";
    		var soaptype="<%=soaptype%>";
    		var serviceid="<%=serviceid%>";
    		var soap=document.getElementById("soap").value;
    		var operation=document.getElementById("operation").value;
    		var obj_url=document.getElementById("obj_url").value;
    		var accuracy="";
    		var timeout=document.getElementById("timeout").value;
    		var value=document.getElementById("value").value;
    		var num=document.getElementById("num").value;
    		var beginnum=document.getElementById("beginnum").value;
    		if(document.getElementById("beginnum").value=='')
    		beginnum='0';
    		var endnum=document.getElementById("endnum").value;
    		if(document.getElementById("endnum").value=='')
    		endnum='0';
    		var stepnum=document.getElementById("stepnum").value;
    		if(document.getElementById("stepnum").value=='')
    		stepnum='0';
    		//var beginnum=document.getElementById("beginnum").value;
    		//var endnum=document.getElementById("endnum").value;
    		//var stepnum=document.getElementById("stepnum").value;
    		
    		if('inner'==type)
	    		window.location="ToTesterMonitor.jsp?"+"soap="+soap
	    		+"&operation="+operation
	    		+"&obj_url="+obj_url
	    		+"&test_num="+test_num_value
	    		+"&strategy="+strategy_value
	    		+"&accuracy="+accuracy
	    		+"&timeout="+timeout
	    		+"&value="+value
	    		+"&num="+num
	    		+"&beginnum="+beginnum
	    		+"&endnum="+endnum
	    		+"&stepnum="+stepnum
	    		+"&para="+"<%=para%>"
	    		+"&type="+type
	    		+"&serviceid="+serviceid
	    		+"&soaptype="+soaptype;
	    	else
	    		{
	    		type="external";
	    		window.location="ToTesterMonitor.jsp?"+"soap="+soap
	    		+"&operation="+operation
	    		+"&obj_url="+obj_url
	    		+"&test_num="+test_num_value
	    		+"&strategy="+strategy_value
	    		+"&accuracy="+accuracy
	    		+"&timeout="+timeout
	    		+"&value="+value
	    		+"&num="+num
	    		+"&beginnum="+beginnum
	    		+"&endnum="+endnum
	    		+"&stepnum="+stepnum
	    		+"&para="+"<%=para%>"
	    		+"&type="+type;
	    		}	
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
	    <!-- End Topnav -->

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
    
    <!-- Header End Here -->
    
    <!-- Content Begin Here -->        

	<div id="tab_header">
	<ul id="primary">
		<li><a href="MyLoadTester.jsp">Node Selection<br /></a></li>
		<li><span>Test Config</span>
			<ul id="secondary">
				<li></li>
			</ul>
		</li>
		<li><a href="TesterMonitor.jsp">Test Monitor</a></li>
		<li><a href="TesterResult.jsp">Test Results</a></li>
	</ul>
	</div>
	<div id="main">
		<div id="contents">
			<h2>Test Configuration</h2>			
			<p class="note"></p>
			<!-- form table -->
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
	            
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="30" align="right"><b>WSDL URL：</b></td>
	              <td>&nbsp;<input type="text" name="wsdl" id="wsdl" size="35" value="<%=serviceid %>"></input>&nbsp;&nbsp; <input style="width:80px;font-family:verdana" type="button" value="test" onclick="getSoap()" /></td>
	            </tr>
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="30" align="right"><b>Request Soap：</b></td>
	              <td>&nbsp;<textarea  id="soap" name="soap" rows="6" cols="30" ><%=soapContent %></textarea></td>
	            </tr>
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="30" align="right"><b>Test Operation：</b></td>
	              <td>&nbsp;<input type="text" id="operation" name="operation" size="45" value="<%=operation %>" /></td>
	            </tr>
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="30" align="right"><b>Test Object URL：</b></td>
	              <td>&nbsp;<input type="text" id="obj_url" name="obj_url" size="45" value="<%=wsdlURL %>" /></td>
	            </tr>
				<tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="80" align="right"><b>Test Type：</b></td>
	              <td >
	              		<table>
	              		<tr>
	              			<td>&nbsp;&nbsp;&nbsp;&nbsp;Single&nbsp;&nbsp;&nbsp;&nbsp;</td>
	              			<td>&nbsp;&nbsp;&nbsp;&nbsp;Multiple&nbsp;&nbsp;&nbsp;&nbsp;</td>
	              		</tr>
	              		<tr>
	              			<td align="center"><input type="radio" id ="test_num" name="test_num" value="single" checked="checked" onclick="select_testnum(this)"/></td>
	              			<td align="center"><input type="radio" id ="test_num" name="test_num" value="multiple" onclick="select_testnum(this)"/></td>
	              		</tr>
	              		</table>
	              </td>
	            </tr>	            
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="80" align="right"><b>Test Mode：</b></td>
	              <td >
	              		<table>
	              		<tr>
	              			<td>&nbsp;&nbsp;&nbsp;&nbsp;Static&nbsp;&nbsp;&nbsp;&nbsp;</td>
	              			<td>&nbsp;&nbsp;&nbsp;&nbsp;Step&nbsp;&nbsp;&nbsp;&nbsp;</td>
	              			<td>&nbsp;&nbsp;&nbsp;&nbsp;Maximal&nbsp;&nbsp;&nbsp;&nbsp;</td>
	              		</tr>
	              		<tr>
	              			<td align="center"><input type="radio" id="strategy" name="strategy" value="static" checked="checked" onclick="select1(this)"/></td>
	              			<td align="center"><input type="radio" id="strategy" name="strategy" value="step" onclick="select2(this)"/></td>
	              			<td align="center"><input type="radio" id="strategy" name="strategy" value="maximal" onclick="select3(this)"/></td>
	              		</tr>
	              		</table>
	              </td>
	            </tr>
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="30" align="right"><b>Expect Result：</b></td>
	              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Timeout&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="timeout" name="timeout" value="" size="20" /></td>
	            </tr>
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="30" align="right"></td>
	              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; Value&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="value" name="value" value="" size="20" /></td>
	            </tr><!--
	            <tr bgcolor="#E7F3F7" class="text">
	              <td width="200" height="30" align="right"></td>
	              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br /></td>
	            </tr>
	            
	            --><tr bgcolor="#E7F3F7" id="num_tr" >
	              <td width="200" height="30" align="right" id="num_td1"><b>Test Case Number：</b></td>
	              <td id="num_td2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="num" name="num" value="0" size="20" /></td>
	            </tr>
	            <tr bgcolor="#E7F3F7" id="beginnum_tr" >
	              <td width="200" height="30" align="right" id="beginnum_td1" style="display:none"><b>Start Number：</b></td>
	              <td id="beginnum_td2" style="display:none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="beginnum" name="beginnum" value="" size="20" /></td>
	            </tr>
	            <tr bgcolor="#E7F3F7" id="endnum_tr" >
	              <td width="200" height="30" align="right" id="endnum_td1" style="display:none"><b>End Number：</b></td>
	              <td id="endnum_td2" style="display:none">&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" id="endnum" name="endnum" value="" size="20" ></input></td>
	            </tr>
	            <tr bgcolor="#E7F3F7" id="stepnum_tr" >
	              <td width="200" height="30" align="right" id="stepnum_td1" style="display:none"><b>Step Size：</b><br /></td>
	              <td id="stepnum_td2" style="display:none">&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" id="stepnum" name="stepnum" value="" size="20" ></input><br /></td>
	            </tr>
	        	</table>
				<div align="center" style="margin-top:10px"><input style="width:80px;font-family:verdana" type="submit" value="Submit" onclick="submit()"></input></div>
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