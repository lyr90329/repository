<%@ page language="java" pageEncoding="ISO-8859-1" 
import="java.util.*,db.dao.UserDao,db.entity.User,constant.MenuItems"%>
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
		<title><%=name %>Cloud</title>
		<meta content="text/html; charset=ISO-8859-1"
			http-equiv="content-type" />
		<link rel="stylesheet" href="css/style.css" type="text/css" />
	</head>
	<body>
			<div id="header">&nbsp; 
				

				<h1>
					My Cloud					
				</h1>	
				  	
				
			</div>
			<div id="menu">
				<ul>
<%
UserDao dao = new UserDao();
List<User> list=dao.findByProperty("userName",name);
User user=list.get(0);
List<String> items = new ArrayList();
int n = user.getConf();
for(int i = 0 ; i < MenuItems.items.length && n > 0 ; i++){
	if(n % 2 == 1) {
%>
					<li>
						<a href="<%= MenuItems.addrs[i] %>"><%=MenuItems.items[i] %></a>
						
					</li>
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
	</body>
</html>
