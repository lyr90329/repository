<%@ page language="java" pageEncoding="GBK" import="servlet.DeploymentProxyClient,java.util.*,db.dao.*,db.entity.*,db.entity.Bpmn,constant.MenuItems,constant.MenuItems,config.Config,cn.org.act.sdp.serviceCloud.servicesAPI.ExposedInterfaces,cn.org.act.sdp.serviceCloud.feedback.DeployFeedback"%>
<%@ page import="cn.org.act.sdp.serviceCloud.servicesAPI.Resolution"%>

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
<title>SubscribedBPMN</title>
<link rel="stylesheet" href="../css/style1.css" type="text/css" media="screen" />

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/bpmn_cloud.js"></script>
	<script type="text/javascript" src="js/my_bpmn_engine.js"></script>
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
  				<% UserDao userDao = new UserDao();
				List<User> userList=userDao.findByProperty("userName",name);
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
    		<div id="top_search" style="height:40px;padding-top:15px">
    				<form action="bpmnupload" method=post enctype="multipart/form-data">
　　						<input style="font-family:verdana" type=file size="30" value="upload your own Web Service" name="s" onfocus="if (this.value == 'upload your own Web Service') {this.value = '';}" onblur="if (this.value == '') {this.value = 'upload your own Web Service';}">
　　						<input type=submit value=commit name=submit id="searchbut">
　					</form>
            </div>
	<%
	BpmnDao bpmnDao=new BpmnDao();
	String userName=(String)session.getAttribute("userName");
	List<Bpmn> list=bpmnDao.findByProperty("userName",userName);
	Bpmn bpmn;
	DeployFeedback feedback =null;

	if(list != null){
		for(int i=0;i < list.size(); i++){
			bpmn = list.get(i);
			BpmnDao dao = new BpmnDao();
			Bpmn trial;
			trial = dao.findByBPMNIdAndUserName(bpmn.getBpmnId(), userName);
			if (trial.getDeployStatus().equals("true")) 
			{					
				System.out.println("BPMN "+trial.getBpmnName()+" has been deployed.");
			} else 
			{		
				try
				{
					String bpmnName=trial.getBpmnName()+".bpmn";
		            bpmnName="bpmn/"+userName+"/"+bpmnName;
		            Resolution resolution=new Resolution();
		            resolution.resolve(); 
		            feedback = ExposedInterfaces.bpmnDeploy(trial.getBpmnName(),new java.io.File(Config.getPath(bpmnName)));
		            System.out.println("feedback:"+feedback.isSuccessful()+"   bpmnid:"+feedback.getServiceID()+"    bpmnName:"+feedback.getServiceName());
				} catch (Exception e) {
					e.printStackTrace();
					}
			}
	%>
		<div class="excrept_post">
			<div class="excrept_in">
    		<!-- Thumbnail from Custom Field, Post first image or default thumbnail -->
				       
				<div class="the_excrept"><font size="3" face="Verdana">BpmnName:</font>
            		<font size="2" ><a href="#" name="bpmnName"><%=bpmn.getBpmnName() %></a></font>
            	</div>
            	<div class="the_excrept"><font size="3" face="Verdana">Bpmn  ID:</font>
            		<font size="2" ><a href="#" name="bpmnName"><%=feedback.getServiceID()%></a></font>
            	</div>
            	<div class="the_excrept"><font size="3" face="Verdana">Description:</font>
            		<font size="2" ><a href="/repository/bpmn/<%=userName%>/<%=bpmn.getBpmnName()%>.bpmn" target="_blank">BPMN file</a></font>
            	</div>
        		<div class="clear"></div> 
				 <!-- <table width="600px"><tr><td width="200px" align="right"><a href="javascript:publish(<%=bpmn.getBpmnId() %>);">Publish</a></td><td width="200px" align="center"></td><td align="left"><a href="javascript:edit(<%=bpmn.getBpmnId() %>);">Edit</a></td></tr></table> -->		
        		<div class="excrept_data">
            		
            		<div class="excrept_right">
            		<div class="options">
            		<form action="BpmnOperate" method="post" onsubmit="return bpmnOperate()">
				    	<input type="hidden" name="bpmnId" value="<%=bpmn.getBpmnId()%>" ></input>
				    	<input type="hidden" name="MyBpmnId" value="<%=feedback.getServiceID()%>" ></input>
						<input style="width:80px;font-family:verdana" type="button" value="Use Now" id="implementid" onclick="showDialog(this)"></input>
						<input style="width:80px;font-family:verdana" type="button" value="Remove" id="removeid" onclick="removeMyBPMN('<%=bpmn.getBpmnId()%>','<%=feedback.getServiceID()%>')"></input>
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
				


            
        <div class="widget"><h2>Use Now</h2>
			<ul class='xoxo blogroll'>
				<li><p><b>use this BPMN file to test its feasibility</b></p></li>
				<li><p><b>then you should fill some parameters into a form, after that, you can observe the executing process with a online monitor</b></p></li>
				
			</ul>
		</div>
		
		<div class="widget"><h2>Remove</h2>			
			<ul>
				<li><p><b>you can remove the BPMN file from the the BpmnEngine</b></p></li> 
				
			</ul>
		</div>
		
		<div class="widget"><h2>Publish</h2>		
			<ul>
				<li><p><b>publish the fixed Bpmn file</b></p> </li>
				
			</ul>
		</div> 
		
		<div class="widget"><h2>Edit</h2>		
			<ul>
				<li><p><b>you can edit the "BPMN file" with a online editing tool</b></p> </li>
				
			</ul>
		</div> 
		
		
		<div class="widget"><h2>Description</h2>		
			<ul>
				<li><p><b>you can click the "BPMN file" to see the details of the BPMN file</b></p> </li>
				
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
			<div class="title">BPMN</div>
			<div class="content">
				<form action="BpmnOperate" method="post">
				</form>
			</div>
		</div>
		<div class="data" id="data" userName="<%=name %>"></div>
		
</body>
</html>