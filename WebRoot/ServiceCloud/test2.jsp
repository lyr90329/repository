<%@ page language="java" import="java.util.*,db.dao.*,db.entity.*,db.entity.Bpmn,constant.MenuItems,constant.MenuItems,config.Config" pageEncoding="GBK"%>
<%@ page import="parser.*"%>
<jsp:directive.page import="cn.org.act.sdp.wsdl.WebServiceRunner;"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
String userName=(String)request.getParameter("userName");
if(userName==null||userName=="")
	{
	   out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	   return ;
    }	


String action=(String)request.getParameter("action"); 
String bpmnId=(String)request.getParameter("bpmnId"); 
BpmnDao dao=new BpmnDao();
UploadBpmn uploadBpmn=dao.findUploadBpmnById(Integer.parseInt(bpmnId));
String bpmnName=uploadBpmn.getBpmnName();

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	<link rel="stylesheet" href="./css/style2.css" type="text/css" media="screen" />
	
	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	
 <script type="text/javascript">
 var s_data = {
	action: "",
	bpmnId: "",
	userName: "",
	jobId: ""
};

    function init()
    {

    	var para1;
    	var para1;
    	var para1;

    	para1="<%=userName %>";
    	para2="<%=action %>";
		para3="<%=bpmnId %>";
		
        
        $.ajax({
		url:"/repository/ServiceCloud/UploadBpmnOperate",
		data: {"action":para2,"bpmnId":para3,"userName":para1},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					
					alert("operate failed");
				},
		success: function(data, textStatus){
		            
		            if(data.ret == false){
		               
		                    alert(data.msg);
		
		                      return;
		             }
		            
					//alert("12");
					
					var html="";
					html += "<form action='UploadBpmnOperate' method='post'>";	
					html +="<input type='hidden' value='"+data.argNumber+"' id='argno' name='argno'></input><table align='center'>";
					var type="";
					var name="";
					for(var i = 0;i < data.argNumber ; i++){
						html=html+"<tr><th><label>"+"&nbsp;&nbsp;&nbsp;&nbsp;"+eval("data.argName"+i)+":"+eval("data.argType"+i)+":&nbsp;"+"</label></th><td>"+"<input type='text' value='' id='arg"+i+"' name='arg"+i+"'></input></td><td></td></tr>";
						type=type+eval("data.argType"+i)+",";
						name=name+eval("data.argName"+i)+",";
					}
					html=html+"<tr><th></th><td><input type=\"button\" value=\"submit\" onclick=\"bpmnOperateImplement(this,"+para3+","+data.argNumber+",'"+name+"','"+type+"')\"></input>&nbsp;&nbsp;&nbsp;&nbsp";
					
					html=html+"<input type='button' value='  monitor  ' disabled id=s1 onclick='monitor()'></input></td><td><div id='message'></div></td></tr></table>";
					
					html += "</form>";	//存储html
					var title=document.getElementById("title");
					title.innerHTML="<%=bpmnName%>";
					//document.getElementById("paraForm").innerHTML=html;
					var para=document.getElementById("paraForm");
					
					para.innerHTML=html;
				}
	 
			});
        
        
    }
	
	
	 function bpmnOperateImplement(e,bpmnId,argno,name,type){
	
	var msg=document.getElementById("message");
	msg.innerHTML="<b>&nbsp;&nbsp;&nbsp;&nbsp;the program is running , please wait...</b>";
	
	s_data.action = $(e).attr("value");
	//alert("action:"+s_data.action);
	s_data.userName = "<%=userName %>";
	
	
	var argvalue="";
	for(i=0;i<argno;i++){
		argvalue=argvalue+document.getElementById("arg"+i).value+",";
	}
	
	$.ajax({
		url:"/repository/ServiceCloud/UploadBpmnOperate",
		data: {"action":s_data.action,"bpmnId":bpmnId,"userName":s_data.userName,"argno":argno,"argvalue":argvalue,"name":name,"type":type},
		dataType: "json",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					s_data.jobId = data.jobId;
					msg.innerHTML="<b>&nbsp;&nbsp;&nbsp;&nbsp;"+data.value+"</b>";
					//alert("data:"+data.value);
					
					
				}
	
	});
}


function monitor(){
	$.ajax({
		url:"/repository/ServiceCloud/GetUpJobId",
		data: {"action":"getJobId","bpmnId":"<%=bpmnId %>","userName":"<%=userName %>"},
		dataType: "json",
		type: "POST",
		error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
		success: function(data, textStatus){
					if(data.jobId == null){
						alert("no job id,wait a miniute");
					}else{
						var iframe=document.getElementById("iframe1");
						iframe.style.display="";
						//alert("data.jobId:"+data.jobId);
						//alert("<%=bpmnId %>");
						iframe.src='/repository/ServiceCloud/monitor.jsp?jobID='+data.jobId+'&bpmnID='+<%=bpmnId %>;
						
					}
				}
	
	});
}

    </script>
  </head>
  
  <body onload="init()">
  	<div id="header">
    <div id="topnav"> 
        <div id="topnav_right">
            <script src="./js/date.js" type="text/javascript"></script>
        </div>
    <div class="clear"></div>
    </div>
<!-- End Topnav -->

    <div id="header_left">
            <h1><a href="#">My  Cloud</a></h1>
            <p>个性化在线空间</p>
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
  				<% UserDao userDao = new UserDao();
				List<User> userList=userDao.findByProperty("userName",userName);
				User user=userList.get(0);
				List<String> items = new ArrayList();
				int n = user.getConf();
				 %>
				 	<li><a href="ServiceCloud/<%= MenuItems.addrs[0] %>"><font size=2><%=MenuItems.items[0] %></font></a></li>
				 	<li id="navsubmenu">
				 		<a href="#"><font size=2>Deployments</font></a>
				 		<ul>
				 <% 
				 n >>= 1;
				 for(int i=1;i<4;i++){ 
				 	if(n % 2 == 1){
				 %>
				 			<li><a href="ServiceCloud/<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
				 <% }
				n >>= 1;
				}%>
						</ul>
					</li>
					<li id="navsubmenu"  class="current_page_item">
						<a href="#"><font size=2>Tools</font></a>
						<ul>
					<% for(int i=4;i<8;i++){
						if(n % 2 == 1){
					 %>
				 			<li><a href="ServiceCloud/<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
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
				 			<li><a href="ServiceCloud/<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
					 <% }
				n >>= 1;
				}%>
						</ul>
					</li>
					<li id="navsubmenu">
						<a href="#"><font size=2>DataServices</font></a>
						<ul>
					<% for(int i=10;i<13;i++){
						if(n % 2 == 1){
					 %>
				 			<li><a href="ServiceCloud/<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
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
	
	
  		<div id="veil"></div>
        <div id="implementDialog" class="dialog">
			<div class="title" id="title"></div>
			<div class="content">
				<form action="BpmnOperate" method="post" id="paraForm" name="paraForm">
				
				</form>
			</div>
		</div>
		<div class="data" id="data" userName="<%=userName %>"></div>
		
		<!-- 
		
		<iframe   name=iframe1   id=iframe1   src= "/repository/ServiceCloud/monitor.jsp" width=1000 height=600 style="display :none"> </iframe> 
		
		 -->
		
	</div>
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
