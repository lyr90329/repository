<%@ page language="java" pageEncoding="GBK" 
import="java.util.*,db.dao.*,db.entity.*,constant.MenuItems,java.net.InetAddress,java.net.UnknownHostException"%>
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
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US">

<head profile="">
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />

<title>MyServiceContainer</title>

<link rel="stylesheet" href="../css/style1.css" type="text/css" media="screen" />

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/app_cloud.js"></script>
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
            <h1><a href="#">My  Cloud</a></h1>
            <p></p>
        </div>
        <div id="header_right">

           <!-- <div id="top_search">
                 <form id="searchform" method="get" action="/index.php">
                <input type="text" value="Search This Site..." name="s" id="topsearch" onfocus="if (this.value == 'Search This Site...') {this.value = '';}" onblur="if (this.value == '') {this.value = 'Search This Site...';}" />
                <input type="submit" id="searchbut" value="GO" /></form> 
            </div> -->
        </div>
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
                <li>
 						<a href="menu_conf.jsp">>></a>
 				</li>
                </ul>
            </div>
            <div id="nav_right"></div>
            <div class="clear"></div>
        </div>
    </div>
    
    <!-- Header End Here -->        

<div id="content">
    <div id="postarea">
    
<%

AppDao appDao=new AppDao();
String userName=(String)session.getAttribute("userName");
List<App> list=appDao.findByProperty("userName",userName);
App app;
if(list != null){
	for(int i=0;i < list.size(); i++){
		app = list.get(i);
%>
<%
InetAddress inet = InetAddress.getLocalHost();
%>	
		<div class="excrept_post">
			<div class="excrept_in">
    		<!-- Thumbnail from Custom Field, Post first image or default thumbnail -->
				       
				<div class="the_excrept"><font size="3" face="Verdana">AppName:</font>
            		<font size="2" ><a href="#" name="appName"><%=app.getAppName() %></a></font>
            	</div>
            	<div class="the_excrept"><font size="3" face="Verdana">DeployStatus:</font>
            		<font size="2" ><a name="deployStatus"><%=app.getDeployStatus() %></a></font>
            	</div>
        		<div class="clear"></div> 

        		<div class="excrept_data">
            		
            		<div class="excrept_right">
            		<div class="options">
								<form action="AppOperate" method="post" onsubmit="return appOperate()">
				    				<input type="hidden" name="appId" value="<%=app.getAppId()%>" ></input>
									 
									<input style="width:100px" type="button" value="deploy" onclick="deploy(this)"></input>
									<input style="width:100px" type="button" value="undeploy" onclick="undeploy(this)"></input>
									<input style="width:100px" type="button" value="implement" onclick="window.open('http://<%=inet.getHostAddress()%>:8080/<%=app.getAppName()%>')"></input>
								</form>
							</div>
            		</div>
        			<div class="clear"></div>
        		</div>
    		</div>
		</div> 
    
<%
	}//for
}//if
 %>
 
 
 <!-- content end here -->
    
    <div class="pagenavigation">
		<div class="navleft"></div>
		<div class="navright"></div>
		<div class="clear"></div>
    </div>

	</div>

    <div id="sidebar"><!-- Sidebar Start Here -->
    	<div id="sidebar_in"><br/>
				


            
        <div class="widget"><h2>(un)Deploy</h2>
			<ul class='xoxo blogroll'>
				<li><p><b>待写！</b></p></li>
				<li><p><b>待写！</b></p></li>
				<li><p><b>待写！</b></p></li>
			</ul>
		</div>
		
		<div class="widget"><h2>Implement</h2>			
			<ul>
				<li><p><b>待写！</b></p></li> 
				
			</ul>
		</div>
		
		<div class="widget"><h2>DeployStatus</h2>		
			<ul>
				<li><p><b>待写！</b></p> </li>
				
			</ul>
		</div>                
		</div>
	</div><!-- Sidebar End Here -->
</div>
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
	
	
	<div id="veil"></div>
		<div id="implementDialog" class="hidden dialog">
			<div class="title">Implement</div>
			<div class="content">
				<form action="AppOperate" method="post">
				</form>
			</div>
		</div>
		<div class="data" id="data" userName="<%=userName %>"></div>
</body>
</html>