<%@ page language="java" pageEncoding="GBK" 
import="java.util.*,db.dao.*,db.entity.*,constant.MenuItems,constant.MenuItems"%>
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

<title>SubscribedServices</title>

<link rel="stylesheet" href="../css/style1.css" type="text/css" media="screen" />

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
</head>

<body>
    <div id="header">
    <!--<div id="topnav"> 
        <div id="topnav_right">
            <script src="../js/date.js" type="text/javascript"></script>
        </div>
    	<div class="clear"></div>
    </div>
--><!-- End Topnav -->

        <div id="header_left">
           <img src="../images/servicefoundry.png" width="180px" height="120px"/>
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
					<li id="navsubmenu">
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
					<li id="navsubmenu" class="current_page_item">
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

<div id="content">
    <div id="postarea">
    
<%
ServiceDao serviceDao=new ServiceDao();
String userName=(String)session.getAttribute("userName");
List<Service> list=serviceDao.findByProperty("userName",userName);
Service service;
if( null!=list){
	for(int i=0;i < list.size(); i++){
		service = list.get(i);
%>
		<div class="excrept_post">
			<div class="excrept_in">
    		<!-- Thumbnail from Custom Field, Post first image or default thumbnail -->
				       
				<div class="the_excrept"><font size="3" face="Verdana">Service Name:</font>
            		<font size="2" ><a href="#" name="serviceName"><%=service.getServiceName() %></a></font>
            	</div>
            	<div class="the_excrept"><font size="3" face="Verdana">Description:</font>
            		<font size="2" ><a href="<%=service.getWsdlurl() %>" target="_blank">WSDL file</a></font>
            	</div>
        		<div class="clear"></div> 

        		<div class="excrept_data">
            		
            		<div class="excrept_right">
            		<div class="options">
            		<form action="ServiceOperate" method="post" onsubmit="return serviceOperate()">
				    	<input type="hidden" name="serviceId" value="<%=service.getServiceId()%>" ></input>
                		<input style="width:80px;font-family:verdana" type="button" value="Invoke" id="implement" onclick="useService(<%=service.getServiceId()%>,'<%=service.getWsdlurl()%>','<%=service.getServiceName()%>')"></input>
                		<input style="width:80px;font-family:verdana" type="button" value="Comment" onclick="commentService(<%=service.getServiceId() %>)"></input>
                		<input style="width:80px;font-family:verdana" type="button" value="Remove" id="remove" onclick="removeService(<%=service.getServiceId()%>,'<%=service.getServiceName()%>')"></input>
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
				
		 <div class="widget"><h2>Subscribe</h2>
			<ul class='xoxo blogroll'>
				<li><p><b>you can search for web services in</b></p></li>
				<li><p><b><a href="#"  onclick="window.open('/servicexchange')">serviceXchange</a><b></p></li>
			</ul>
		</div>

            
        <div class="widget"><h2>Invoke</h2>
			<ul class='xoxo blogroll'>
				<li><p><b>you can invoke the selected service </b></p></li>
				<li><p><b>then you can enter the needed parameter to test this service</b></p></li>
				<li><p><b>the system may return the result of your test</b></p></li>
			</ul>
		</div>
		
		<div class="widget"><h2>Remove</h2>			
			<ul>
				<li><p><b>you can remove the service from the this service container</b></p></li> 
				
			</ul>
		</div>
		
		<div class="widget"><h2>Description</h2>		
			<ul>
				<li><p><b>you can click the "WSDL file" to see the details of the WSDL file</b></p> </li>
				
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
				<form action="ServiceOperate" method="post">
				</form>
			</div>
		</div>
		<div class="data" id="data" userName="<%=userName %>"></div>
</body>
</html>