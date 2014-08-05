<%@ page language="java" pageEncoding="GBK" 
import="java.util.*,db.dao.*,db.entity.*,db.entity.Bpmn,constant.MenuItems,constant.MenuItems,config.Config"%>
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Tutorials</title>
		<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type" />
		<link rel="stylesheet" href="../css/style1.css" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/service_cloud.js"></script>
		<script type="text/javascript">
			function extend(h2){
				var flag = h2.firstChild;
				var content = h2.nextSibling;
				while(content.nodeType != 1){
					content = content.nextSibling;
				}
				if(content.style.display == "none"){
					content.style.display = "block";
					flag.innerHTML = "-";
				}else {
					content.style.display = "none";
					flag.innerHTML = "+";
				}
			}
		</script>
	</head>
	<body>
	<div id="header">
    <div id="topnav"> 
        <div id="topnav_right">
            <script src="../js/date.js" type="text/javascript"></script>
        </div>
    <div class="clear"></div>
    </div>
<!-- End Topnav ##########################################-->

        <div id="header_left">
            <h1><a href="#">My  Cloud</a></h1>
            <p>¸öÐÔ»¯ÔÚÏß¿Õ¼ä</p>
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
				UserDao userDao = new UserDao();
				List<User> userList=userDao.findByProperty("userName",name);
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
 						<a href="menu_conf.jsp">>>></a>
 				</li>
                </ul>
            </div>
            <div id="nav_right">
                <a href="#" target="_blank"> RSS</a>
				<a href="#" target="_blank"><img style="vertical-align:middle" src="../images/rss.png" /></a>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    
    
    <div id="content">
    <div id="container">
			
			<div class="divider">
			</div>
			<div class="tutorial center">
				<div class="contents">
					<div id="toctitle">
						<h2>Contents</h2>
					</div>
						<ul>
							<li class="toclevel-1"><a href="#Introduction"><span class="">1</span> <span class="toctext">Introduction</span></a></li>
							<li class=""><a href="#Handlers"><span class="">4</span> <span class="toctext">Handlers</span></a>
								<ul>
									<li class=""><a href="#Hello_World_Handler"><span class="">4.1</span> <span class="toctext">Hello World Handler</span></a></li>
								</ul>
							</li>
						</ul>
				</div>
				
				<a name="Introduction" ></a>
				<h3 class="title" onclick="javascript:extend(this)"><span class="extend">-</span> Introduction </h3>
				<div>
					<p>This tutorial takes you step-by-step from the simplest Jetty server instantiation, to running multiple web applications with standards-based deployment descriptors.</p>
				</div>
				<div class="divider2"></div>

				<a name="Handlers" ></a>
				<h3 class="title" onclick="javascript:extend(this)"><span class="extend">-</span> Handlers </h3>
				<div>
					<p>This tutorial takes you step-by-step from the simplest Jetty server instantiation, to running multiple web applications with standards-based deployment descriptors.</p>
				</div>
				<div class="divider2"></div>
			</div>		
			
		</div>
	</div>
	
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
