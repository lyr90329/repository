<%@ page language="java" pageEncoding="GBK" import="java.util.*,db.dao.*,db.entity.*,constant.MenuItems;" %>
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
    TestResultDao testDao =new TestResultDao();
    List<TestResult> list=testDao.findByUserName(name);
%>

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
    
    <!--[if lt IE 7]>
        <script defer type="text/javascript" src="js/pngfix.js"></script>
    <style type="text/css">
        *html .excrept_in {height: 1%;}
    </style>
    <![endif]-->
    
    <style type="text/css"> 
		/* CSS Document */ 
		
		a { 
		    color: #c75f3e; 
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
		    min-height:30px;
		    text-align:center;
		} 
		
		td.alt { 
			border-top: 0px; 
		    background: #F2FAF5; 
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
        <div id="header_right">

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
    
    <!-- Header End Here -->
    
    <!-- Content Begin Here -->        

	<div id="tab_header">
	<ul id="primary">
		<li><a href="MyLoadTester.jsp">Node Selection<br /></a></li>
		<li><a href="TesterConfig.jsp">Test Config</a></li>
		<li><a href="TesterMonitor.jsp">Test Monitor</a></li>
		<li><span>Test Results</span>
			<ul id="secondary">
				<li></li>
			</ul>
		</li>
	</ul>
	</div>
	<div id="main">
		<div id="contents">
			<table id="mytable" cellspacing="0" > 
				<caption> </caption> 
				  <tr> 
				    <th scope="col" >Service Name</th> 
				    <th scope="col" >Test Operation</th> 
				    <th scope="col" >Service URL</th> 
				    <th scope="col" >Single/Multiple</th> 
				    <th scope="col" >Inner/External</th> 
					<th scope="col" >Test Strategy</th> 
					<th scope="col" >Time Out</th>
					<th scope="col" >Chart Details</th> 
					<th scope="col" >Download</th>
				  </tr> 
				<% 
					for(int j=0;j<list.size();j++){ 
					String detail="ShowDetail.jsp?success="+list.get(j).getSuccess()+"&fail="+list.get(j).getFail()+"&average="+list.get(j).getAverage();
					String download="DownloadResult.jsp?success="+list.get(j).getSuccess()+"&fail="+list.get(j).getFail()+"&average="+list.get(j).getAverage();
					if(j%2==0){
				%>
				  <tr>
				    <td class="spec"><%=list.get(j).getServiceName() %></td> 
				    <td><%=list.get(j).getTestOperation() %></td> 
				    <td><%=list.get(j).getUrl() %></td> 
				    <td><%=list.get(j).getTestNum() %></td> 
				  	<td><%=list.get(j).getType() %></td>
				  	<td><%=list.get(j).getStrategy() %></td>
				  	<td><%=list.get(j).getTimeout() %></td>
				    <td><a href="<%=detail%>">view</a></td> 
				    <td><a href="<%=download%>">download</a></td> 
				  </tr> 
				 <% }
					else if(j%2==1){
				 %>
				  <tr>
				  	<td class="alt"><%=list.get(j).getServiceName() %></td> 
				    <td class="alt"><%=list.get(j).getTestOperation() %></td> 
				    <td class="alt"><%=list.get(j).getUrl() %></td> 
				    <td class="alt"><%=list.get(j).getTestNum() %></td> 
				   	<td class="alt"><%=list.get(j).getType() %></td>
				   	<td class="alt"><%=list.get(j).getStrategy() %></td>
				   	<td class="alt"><%=list.get(j).getTimeout() %></td>
				    <td class="alt"><a href="<%=detail%>">view</a></td> 
				    <td class="alt"><a href="<%=download%>">download</a></td> 
				  </tr>
				  <%}} %>
			</table> 
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