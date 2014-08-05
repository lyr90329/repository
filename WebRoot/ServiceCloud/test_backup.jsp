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
	String myBpmnId=(String)request.getParameter("MyBpmnId");
	System.out.println("myBpmnId:"+myBpmnId);
	BpmnDao dao=new BpmnDao();
	Bpmn bpmn=dao.findById(Integer.parseInt(bpmnId));
	String bpmnName=bpmn.getBpmnName();
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
		para4="<%=myBpmnId%>";
        
        $.ajax({
		url:"/repository/ServiceCloud/BpmnOperate",
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
					var html="";
					html += "<form action='BpmnOperate' method='post'>";	
					html +="<input type='hidden' value='"+data.argNumber+"' id='argno' name='argno'></input><table align='center'>";
					var type="";
					var name="";
					for(var i = 0;i < data.argNumber ; i++){
						html=html+"<tr><th><label>"+"&nbsp;&nbsp;&nbsp;&nbsp;"+eval("data.argName"+i)+":"+eval("data.argType"+i)+":&nbsp;"+"</label></th><td>"+"<input type='text' value='' id='arg"+i+"' name='arg"+i+"'></input></td><td></td></tr>";
						type=type+eval("data.argType"+i)+",";
						name=name+eval("data.argName"+i)+",";
					}
					html=html+"<tr><th></th><td><input type=\"button\" value=\"submit\" onclick=\"bpmnOperateImplement(this,"+para3+","+data.argNumber+",'"+name+"','"+type+"','"+para4+"')\"></input>&nbsp;&nbsp;&nbsp;&nbsp";
					html=html+"<input type='button' value='monitor' id='s1' onclick='monitor()'></input></td><td><div id='message'></div></td></tr></table>";
					html += "</form>";	//´æ´¢html
					var title=document.getElementById("title");
					title.innerHTML="<%=bpmnName%>";
					var para=document.getElementById("paraForm");
					para.innerHTML=html;
				}
			});
    }
	
	
	function bpmnOperateImplement(e,bpmnId,argno,name,type,myBpmnId)
	{
		var msg=document.getElementById("message");
		msg.innerHTML="<b>&nbsp;&nbsp;&nbsp;&nbsp;BPMNEngines are being prepared  , please wait...</b>";
		
		s_data.action = $(e).attr("value");
		//alert("action:"+s_data.action);
		s_data.userName = "<%=userName %>";
	
		var argvalue="";
		for(i=0;i<argno;i++){
			argvalue=argvalue+document.getElementById("arg"+i).value+",";
		}
		$.ajax({
			url:"/repository/ServiceCloud/BpmnOperate",
			data: {"action":s_data.action,"bpmnId":bpmnId,"userName":s_data.userName,"argno":argno,"argvalue":argvalue,"name":name,"type":type,"myBpmnId":myBpmnId},
			dataType: "json",
			error: function(XMLHttpRequest, textStatus, errorThrown){
					alert("operate failed");
				},
			success: function(data, textStatus){
					s_data.jobId = data.jobId;
					msg.innerHTML="<b>&nbsp;&nbsp;&nbsp;&nbsp;"+data.value+"</b>";
				}
		});
	}


function monitor(){
	$.ajax({
		url:"/repository/ServiceCloud/GetJobId",
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
						//alert('/repository/ServiceCloud/monitor.jsp?jobID='+data.jobId+'&bpmnID='+<%=bpmnId %>);
						var msg=document.getElementById("message");
						msg.innerHTML="<b>&nbsp;&nbsp;&nbsp;&nbsp;The composite service is running , please wait...</b>";
	
						iframe.src='/repository/ServiceCloud/monitor.jsp?jobID='+data.jobId+'&bpmnID='+<%=bpmnId %>;
						//window.open('/repository/ServiceCloud/monitor.jsp?jobID='+data.jobId+'&bpmnID='+<%=bpmnId %>);
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
					UserDao userDao = new UserDao();
					List<User> userList=userDao.findByProperty("userName",userName);
					User user=userList.get(0);
					List<String> items = new ArrayList();
					int n = user.getConf();
					for(int i = 0 ; i < MenuItems.items.length && n > 0 ; i++){
					if(n % 2 == 1) {
				%>
						<li><a href="ServiceCloud/<%= MenuItems.addrs[i] %>"><font size=2><%=MenuItems.items[i] %></font></a></li>
				<%
						}
					n >>= 1;
					}
	 			%>
	 			<li><a href="ServiceCloud/menu_conf.jsp">>></a></li>
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
			<div class="title" id="title">BPMN</div>
			<div class="content">
				<form action="BpmnOperate" method="post" id="paraForm" name="paraForm">
				
				</form>
			</div>
		</div>
		<div class="data" id="data" userName="<%=userName %>"></div>
		<iframe   name=iframe1   id=iframe1   src= "/repository/ServiceCloud/monitor.jsp" width=1000 height=600 style="display :none"> </iframe> 
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
