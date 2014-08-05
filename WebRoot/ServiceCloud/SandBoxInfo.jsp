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

<title>MyServiceContainer</title>

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

<div id="content">
    <div id="postarea">
    <%
    	ServiceDao sandbox_dao=new ServiceDao();
    	String info=sandbox_dao.getSandBoxInfo();
    	String [] infos=info.split("(\n)");
    %>
	<!-- sandbox info -->
		<div class="excrept_post">
			<div class="excrept_in">
    			<div class="the_excrept"><font size="5" face="Verdana" color="black">SandBox Info:</font>
            		<font size="3" color="black">the info when deploying your Web Service</font>
            	</div>
            	<div class="the_excrept">
            		<%for(int i=0;i<infos.length;i++)
            		{
            		%>
            		<font size="3" face="Verdana" color="red">[Line&nbsp;<%=i%>]:&nbsp;</font><font size="3" face="Verdana" color="green"><%=infos[i]%><br/></font><br/>
            		<%
            			if((i%4)==3)
            			{%>
            				<hr noshade size=4>
            			<%
            			}
            		} %>
            	</div>
        		<div class="clear"></div>
    		</div>
		</div> 
 	<!-- sandbox info end here -->
    
    <div class="pagenavigation">
		<div class="navleft"></div>
		<div class="navright"></div>
		<div class="clear"></div>
    </div>

	</div>

    <div id="sidebar"><!-- Sidebar Start Here -->
    	<div id="sidebar_in"><br/>
				


            
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
</body>
</html>