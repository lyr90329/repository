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
		<script type="text/javascript">
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
			function val(){
				var menu_conf = 0;
				var n = 1;
				var form = document.getElementById("menu_conf_form");
				var checkboxes = form.elements["checkbox"];
				for(var i=0; i < checkboxes.length; i++){
					if(checkboxes[i].checked == true) menu_conf |= n;
					n <<= 1;
				}
				menu_conf |= n;
				form.elements["menu_conf"].value = menu_conf;
				
				return true;
			}
		</script>
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
					<% for(int i=4;i<8;i++){
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
					<% for(int i=8;i<10;i++){
						if(n % 2 == 1){
					 %>
				 			<li><a href="<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
					 <% }
				n >>= 1;
				}%>
						</ul>
					</li>
				 	<li class="current_page_item"><a href="menu_conf.jsp">>></a></li>
                </ul>
            </div>
            <div id="nav_right"></div>
            <div class="clear"></div>
        </div>
	</div> 
	<%
	String userName=(String)session.getAttribute("userName");    
    User loginUser=dao.findByUserName(userName);
	int flag = loginUser.getConf();
	%>
    <div id="content">
    	<div id="container">
			<div class="center"><h1>Configure Your Menu Items</h1></div>
			<form id="menu_conf_form" class="menu_conf_form" 
					action="menu_conf" method="post" onsubmit="val()">     
	     	<div class="class_1">
	     		<h2>Business Process Based Service Composition</h2>
	     		<img src="../images/line.jpg" alt=""/>
	     		<div class="flow">
					<div>Business Modeling</div>
					<p title="BPIDELite is an online development tool for Business Modeling."><input type="checkbox" name="checkbox" 
							<%=MenuItems.editable[4]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[4] %></p><% flag >>=1; %>
	     			<p title="Your BPMN files on ServiceFoundry."><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[9]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[9] %></p><% flag >>=1; %>
	     		</div>
	     		<div class="flow">
					<div>Service Discovery</div>
	     			<p title="Your Atomic Services on ServiceFoundry."><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[8]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[8] %></p><% flag >>=1; %>
	     			<p title="You can search for services in ServiceXchange."><a href="/servicexchange">ServiceXchange</a></p>
	     		</div>
	     		<div class="flow">
					<div>Atomic-service Development</div>
	     			<p title="Deploy atomic services in MyServiceContainer."><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[1]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[1] %></p><% flag >>=1; %>
	     			<p title="It's a local toolset for atomic service development."><a href="#">J2WS</a></p>
	     		</div>
	     		<div class="flow">
					<div>Atomic-service Test</div>
	     			<p title="Test the performance of atomic services."><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[6]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[6] %></p><% flag >>=1; %>
	     		</div>
	     		<div class="flow">
					<div>Combined-service Orchestration</div>
	     			<p title="Orchestration:Integrate the Business Model and atomic services to generate Combined services."><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[4]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[4] %></p><% flag >>=1; %>
	     		</div>
	     		<div class="flow">
					<div>Combined-service Test</div>
	     			<p title="Deploy and test your BPMN."><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[2]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[2] %></p><% flag >>=1; %>
	     		</div>
	     	</div>
	     	<div class="class_2">
	     		<h2>End-User-Oriented Web Integration</h2>
	     		<div class="flow">
					<div>MashUp Application Development</div>
	     			<p><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[5]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[5] %></p><% flag >>=1; %>
	     		</div>
	     		<div class="flow">
					<div>MashUp In Mobile Terminal</div>
	     			<p><a href="#">Client</a></p>
	     		</div>
	     	</div>
	     	
	     	<div class="class_3">
	     		<h2>Java Application Hosting</h2>
	     		<div class="flow">
					<div>Web Application Hosting</div>
	     			<p><input type="checkbox" name="checkbox"
							<%=MenuItems.editable[3]?"":"disabled"%> 
							<%=(flag%2==1)?"checked":"" %> /><%=MenuItems.items[3] %></p><% flag >>=1; %>
	     		</div>
	     	</div>
	     	<div class="class_4">
	     		<input type="hidden" name="menu_conf" value="0" />
				<div class="divider2"></div>
	     		<input value="submit" type="submit" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input value="reset" type="reset" />
	     	</div>
	     	</form>
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
		</div>
	</div>
	</body>
</html>
