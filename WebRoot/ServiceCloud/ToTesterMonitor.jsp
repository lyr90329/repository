<%@ page language="java" pageEncoding="GBK" import="java.util.*,db.dao.*,config.Config,db.entity.*,constant.MenuItems,repository.loadTester.*,org.apache.axiom.om.OMElement,java.io.File;" %>
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
    String soaptype=(String)request.getParameter("soaptype");
    String soap=(String)request.getParameter("soap");
	String operation=(String)request.getParameter("operation");
	String obj_url=(String)request.getParameter("obj_url");
	String test_num=(String)request.getParameter("test_num");
	String strategy=(String)request.getParameter("strategy");
	String accuracy=(String)request.getParameter("accuracy");
	String timeout=(String)request.getParameter("timeout");
	String value=(String)request.getParameter("value");
	String num=(String)request.getParameter("num");
	String beginnum=(String)request.getParameter("beginnum");
	String endnum=(String)request.getParameter("endnum");
	String stepnum=(String)request.getParameter("stepnum");
	String para=(String)request.getParameter("para");
	String type=(String)request.getParameter("type");
	String serviceid=(String)request.getParameter("serviceid");
	String serviceName="";
	if(obj_url!=null)
		serviceName=obj_url.substring(obj_url.lastIndexOf("/")+1,obj_url.length()-5);
	
	para=para.substring(0,para.length()-1);
	String[] paralist=para.split(";");
	System.out.println("my type:"+type+" accuracy:"+accuracy+" value:"+value+" timeout:"+timeout+" num:"+num+" beginnum:"+beginnum+" endnum:"+endnum+" stepnum:"+stepnum);
	LoadTesterClient client=new LoadTesterClient(strategy,test_num);
		
	client.setAccuracy(accuracy);
	client.setExcept_body(value);
	client.setTimeout(timeout);
	client.setNum(Integer.valueOf(num));
	
	client.setStartNum(Integer.valueOf(beginnum));
	
	client.setEndNum(Integer.valueOf(endnum));
	
	client.setStep(Integer.valueOf(stepnum));
	//System.out.println("注意这里！！！stepnum="+stepnum);
	//client.setEndNum(Integer.valueOf(endnum));
	//client.setStep(Integer.valueOf(stepnum));

	obj_url=obj_url.substring(0,obj_url.length()-5);
	
	ArrayList<String> iplist=new ArrayList<String>();
	for(String ip:paralist)
	{
		System.out.println("测试结果IP："+ip);
		iplist.add(ip);
	}
	
	try 
	{
		String req_type="external"; 
		OMElement insoap=client.String2OMElement(soap,"utf-8");
		System.out.println("String2OMElement -> insoap："+insoap);
		System.out.println("first child："+insoap.getFirstElement());
		if("inner".equals(type))
		{
			insoap=client.toInnerRequestSoap(soap, serviceid, operation, name);
			req_type="inner_atom";
			System.out.println("wrapped insoap:"+insoap);
		}

		OMElement rs= client.invoke(iplist, req_type, obj_url, operation, insoap, Config.getCloudTestUrl()+"TestJobDeployment/"); 
		System.out.println("调用返回结果："+rs);
		String isSuccessful="";
		String jobId="";
		
		if(rs!=null)
		{
			Iterator iter=rs.getChildren();
			while(iter.hasNext())
			{
				OMElement elem= (OMElement)iter.next();
				if(elem.getLocalName().equals("issuccessful"))
				{
					isSuccessful=elem.getText();
				}
				else if(elem.getLocalName().equals("jobId"))
				{
					jobId=elem.getText();
				}
				else
					System.out.println(elem.getText());
			}
		}
		
		if("true".equals(isSuccessful))
		{
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			String newLoc = "TesterMonitor.jsp?jobId="+jobId+"&strategy="+strategy+"&testNum="+test_num+"&type="+type+"&timeout="+timeout+"&url="+obj_url+"&operation="+operation;
			if(serviceName!=null && !"".equals(serviceName))
				newLoc+=("&serviceName="+serviceName);
			response.setHeader("Location",newLoc);
		}
		else if("false".equals(isSuccessful))
		{
			System.out.println("调用失败！！！");
		}
		
	} catch (Exception e) {
		System.out.println("there are something wrong!");
		e.printStackTrace();
	}
	
	
	
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
		<li><span>Test Monitor</span>
			<ul id="secondary">
				<li></li>
			</ul>
		</li>
		<li><a href="TesterResult.jsp">Test Results</a></li>
	</ul>
	</div>
	<div id="main">
		<div id="contents">
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