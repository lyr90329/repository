<%@ page language="java" pageEncoding="GBK" import="java.util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String name = (String)session.getAttribute("userName");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Service Cloud Platform</title>
<link href="css/firstpage_style.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript">
	function show1(){
		var div=document.getElementById("atomic");
		if(div.style.display=="none"){
			div.style.display="block";
		}
		else div.style.display="none";
	}
	function show2(){
		var div=document.getElementById("composit");
		if(div.style.display=="none"){
			div.style.display="block";
		}
		else div.style.display="none";
	}
</script>
</head>
<body>
<div id="wrapper">
	<div id="header">
		<div id="logo">
			<h1><a name="pagetop">Service Cloud Platform</a></h1>
			<p> designed by  BUAA-ACT</p>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li class="current_page_item"><a href="/repository">Home</a></li>
			<li><a href="/repository/service/GetAllServiceInfo.action">ServiceXchange</a></li>
			<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
			<li><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
			<li><a href="/repository/MonitorCenter/index.jsp">Monitor Center</a></li>
			<li><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
			<li><a href="/repository/testuser/createAccount.jsp" target="_blank">Join Now</a></li>
			<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="quickstart">
	<div id="quickstart-bgtop">
	<div id="quickstart-bgbtm">
		<div id="quickstart-content">
			<div class="post">
				<h3 class="title"><a href="#">Quick Start</a></h3>
				<ul>
					<li><h4 class="date"><a href="#000">Introduction of ServiceCloud</a></h4></li>
					<li><h4 class="date"><a href="#" onclick="show1()">Atomic WebService</a></h4></li>
					<div id="atomic" style="display:none">
						<ul>
							<li><h4 class="date"><a href="#001">Searching WebServices with ServiceExchange</a></h4></li>
							<li><h4 class="date"><a href="#002">Deploying WebServices</a></h4></li>
							<li><h4 class="date"><a href="#003">LoadTesting WebServices</a></h4></li>
						</ul>
					</div>
					<li><h4 class="date"><a href="#" onclick="show2()">Composite Service</a></h4></li>
					<div id="composit" style="display:none">
						<ul>
							<li><h4 class="date"><a href="#004">Creating BPMN Models</a></h4></li>
							<li><h4 class="date"><a href="#005">Creating Composite Service</a></h4></li>
							<li><h4 class="date"><a href="#006">Deploying Composite Service</a></h4></li>
							<li><h4 class="date"><a href="#007">Monitoring Composite Service</a></h4></li>
						</ul>
					</div>
					<li><h4 class="date"><a href="#008">Deploying WebApp</a></h4></li>
					<li><h4 class="date"><a href="#009">Using WebService Debugger</a></h4></li>
					<li><h4 class="date"><a href="#0010">Using HBase</a></h4></li>
				</ul>
				<p class="meta"><span class="date"><a name="000">Introduction of ServiceCloud</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<p>Cloud-computing platform based on three modules, we expect to minimize the burden of the service-oriented software developers and improve their development efficiency, finally implements instant development, deployment and instant instant operation in the service-oriented software development.</p>
					<p>(1)Instant development: As the development tools and web services which used in the development of services combination are available through the browser, so once demand determined, development work can begin immediately.</p>
					<p>(2)Instant deployment: After the service software development, developers can directly use the online deployment tool in the development environment (or use the interfaces provided by the applications engine) to deploy software, and service software application engines will dynamically allocate web service container and composite service engines at once, the service software will be transferred to the appropriate node to complete the deployment.</p>
					<p>(3)Instant operation: As long as the success of service software deployment, users can use the online implementation tools in the development environment (or use the interface provided by the services software application engine) to run the software services immediately.</p>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="001">Searching WebServices with ServiceExchange</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) What is ServiceExchange?</b><br/>Service Exchange is a service repository which gathers the online 3rd part web services and provides convenience for you to retrieve and subscribe them. Pic.1 shows the home page of ServiceExchange.<br/><img src="images/serviceXchange.jpg" width="800" height="585" style="border:1px solid"/></li>
						<li>
							<b>b) How to use it?</b><br/>
							<ul>
								<li><b>i.</b>	Retrieving: First, you should select the ¡°Key word¡± or ¡°XPath¡± radio button, and then type the information in the input box, then the related services will be listed below.</li>
								<li><b>ii.</b>	Viewing details: Choose a certain service in the retrieved list, press the service Name, you¡¯ll open the details¡¯ page, showing the name, wsdl, evaluations, QoS, comments and so on.</li>
								<li><b>iii.</b>	Subscribing: In the details¡¯ page, you can press the ¡°subscribe¡± button in the upper right corner of the page, and then the service will be subscribed. </li>							
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="002">Deploying WebServices</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>1.</b>	Login ServiceFoundry and it would enter the MyServiceContainer view directly or you can select the MyServiceContainer tag to enter the view. </li>
						<li><b>2.</b>	Click browse button to select the service package to be deployed, then select the reliability of this web service to determine the repetition number, finally , click commit button to deploy the service package.</li>
						<li><b>3.</b>	If the deployment is successful ,you would see the deployed service shown in the service list ,then this service can be invoked or load-tested in our platform. Maybe the deploy process goes wrong, then you would get the deployment failed message.</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="003">LoadTesting WebServices</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>1.</b>	Click the tag named MyLoadTester on top of ServiceFoundry page and enter the TesterSelect view. After selecting the testers you want to use, click submit and the page skips to TesterConfig view. </li>
						<li><b>2.</b>	Input the wsdl address of a third-party web service or the service id of a service deployed in our SCP into WSDL URL textbox, click test button to set the input parameters, then Request Soap, Test Operation and Test Object URL textbox would be filled automatically .Select Test Node Number and Test Strategy and fill Expect Result related textboxs (if your expected value is exactly the same with your input value, set Accuracy as 1) and Test Case Number textbox. Click submit to start the test and the page would skip to TesterMonitor view.</li>
						<li><b>3.</b>	In TesterMonitor view, you can monitor the test process by the request times chart and average RTT chart. When the test task is down, you can save the test result and check it in the TestResult view whenever you want.</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="004">Creating BPMN Models</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) Getting Ready:</b> <br/>Before using the BPIDE Lite tool, you need to deploy some atom services in our platform or look for some atom services in the internet because you must use these atom services when binding the operation to work flow. Besides, design the work flow, because our tool can¡¯t design the work flow automatically.</li>
						<li><b>b) Drawing work flow:</b> <br/>
							<ul>
								<li><b>1.</b> Click ¡°Window¡± menu, choose two subitem in ¡°show view¡±, including ¡°FileNavigator¡± and ¡°FigureAttributeNavigator¡± both. Then you have open two windows (¡°Navigator¡± and ¡°Properties¡±).</li>
								<li><b>2.</b> Click ¡°File¡± menu, input the file name, and you will find you have created a new BPMN file.</li>
								<li><b>3.</b> Firstly, a draw panel will appear after you double click the file you have created. Click the ¡°Pool¡± icon in the ¡°Palette¡± on the right of the view, move your mouse to anywhere you want to place the pool component to, then click the left button again, and you will see that the pool has been place there. You can resize the pool to comfit you.</li>
								<li><b>4.</b> Secondly, put a ¡°lane¡± in the graphic view as you have done in the first step. But you must to put the lane into the pool.</li>
								<li><b>5.</b> Now you can put a ¡°start¡± component, an ¡°end¡± component and some ¡°Task¡± component in the ¡°Lane¡± as you have done in the former two steps. Be sure to connect every component to other component as you have designed.<br/><img src="images/bpmncreate.jpg" width="700" height="323" style="border:1px solid"/></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="005">Creating Composite Service</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) Getting Ready:</b> <br/>Before creating composite service you have to create a BPMN file which is shown in the last section, then you just need to bind the atomic service on the components of the BPMN file with our BPIDE Lite.<br/><img src="images/bindbpmn.jpg" width="603" height="483" style="border:1px solid"/></li>
						<li><b>b) Setting Properties:</b> <br/>
							<ul>
								<li><b>1.</b> Choose the ¡°Task¡± component; in the ¡°Properties¡± window, you can change the name of the component in the ¡°name¡± attribute.</li>
								<li><b>2.</b> Generally speaking, choose the type ¡°ServiceTask¡± because we usually use the component as an atom service. Click the ¡°Webservice¡± button, the browser will pop up a window. Choose a service in the window or you are also supposed to fill in the blanks, but we suggest that you¡¯d better choose a service because there is usually no spelling mistake. Click the ¡°OK¡± button. Then the binding has finished.</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="006">Deploying Composite Service</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) Getting Ready:</b> <br/>After binding all the ¡°Task¡± component in the BPMN file which you can refer to the last two sections, you can deploy the composite service</li>
						<li><b>b) Setting Properties:</b> <br/>
							<ul>
								<li><b>1.</b> After binding all the ¡°Task¡± component, please save the file by click the ¡°save¡± icon and choose the ¡°deploy¡± item in ¡°File¡± menu, then your composite service has been deployed in our platform.</li>
								<li><b>2.</b> To execute the composite service, please check the quick start of monitor.<br/><img src="images/deploycompositeservice.jpg" width="396" height="243" style="border:1px solid"/></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="007">Monitoring Composite Service</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) Introduction:</b> <br/>This guide describes the uses of composite service executing monitor. It leads you through executing a composite service and monitoring the flow through our monitor view online. The below content should take no more than ten minutes.</li>
						<li><b>b) Getting Ready:</b> <br/>Before executing the composite service and using the monitor, you need to design and deploy a composite service in our BPIDE Lite tool on line.</li>
						<li><b>c) Using details:</b> <br/>
							<ul>
								<li><b>1.</b> After deploy a composite service in our BPIDE Lite tool on line, click the ¡°MyBPMNEngine¡± menu and you can find the composite service here. Click the ¡°Use Now¡± button and the browser will bring you to the executing view.</li>
								<li><b>2.</b> To execute the composite service, click the ¡°submit¡± button; to see the executing flow of the composite service, click the ¡°monitor¡± button. But notice that if you click the ¡°monitor¡± button too late, you will see a stopped flow because the composite service has already finished executing. However, if you click the ¡°monitor¡± button before click the ¡°submit¡± button, you will get nothing, for you haven¡¯t give the message that you want to execute the composite service.</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="008">Deploying WebApp</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) Creating your own web applications:</b><br/>A web application, usually a web site runs in a web server such as tomcat, apache and so on.<br/>First, you create a new java web project, and realize its business logic;<br/>Second, you should add your project to a .war file. The following two methods will be both helpful.<br/>
							<ul>
								<li><b>i.</b>	Using the command line:<br/>In windows: first, you enter the root directory of your project, e.g. JSPPage is a web project I develop.<br/>Then, type the following command:<br/>jar cvf JSPPage.war  *.*/ </li>
								<li><b>ii.</b>	Using IDE:<br/>In MyEclipse, choose the project, right click the project, choose ¡°export¡±->¡±Java EE¡±->¡±WAR file¡±.</li>
							</ul>
							<br/>You also can use tools such as ANT to add your project into a .war file.
						</li>
						<li><b>b) Deploying your web application:</b><br/>You have two ways to deploy your web application:<br/>
							<ul>
								<li><b>i.</b>	Through ServiceFoundry:<br/>In ServiceFoundry, you open MyWebAppEngine, click the ¡°Browse¡± button to choose the .war file you just created, then click the ¡°commit¡± button to commit your deployment request.<br/>The id and the url will be presented a few seconds later.</li>
								<li><b>ii.</b>	Through appengine.jar:<br/>appengine.jar contains all the APIs for developers. It can be download in the Download Page. The following picture shows you how to use it to deploy a web app in your code.</li>
							</ul>
							<br/><img src="images/webservicedebugger.jpg" width="607" height="82" style="border:1px solid"/>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="009">Using WebService Debugger</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) What is it for?</b><br/>Owing to the characteristics of the system, users can not get access to the lower layer of the platform. It¡¯s getting hard to know which step of our program runs incorrectly. Web Service Debugger is such a tool, helping you handle the problem. It is a log tool, implemented on basis of log4j. It records the debugging information. If your web service gets an unexpected result, you can get all the information you logged presented to you.</li>
						<li>
							<b>b) How to use it?</b><br/>
							<ul>
								<li><b>i.</b>	There is only one method in this tool: out(). The following picture will show you how to use it.<br/><img src="images/webservicedebugger.jpg" width="607" height="82" style="border:1px solid"/></li>
								<li><b>ii.</b>	The first parameter is the level of your log, as log4j. These levels contains:<br/>
									<ul>
										<li><b>DEBUG: </b> The DEBUG Level designates fine-grained informational events that are most useful to debug an application.</li>
										<li><b>INFO: </b> The INFO level designates informational messages that highlight the progress of the application at coarse-grained level.</li>
										<li><b>WARN: </b> The WARN level designates potentially harmful situations.</li>
										<li><b>ERROR: </b> The ERROR level designates error events that might still allow the application to continue running.</li>
										<li><b>FATAL: </b> The FATAL level designates very severe error events that will presumably lead the application to abort.</li>
									</ul>
								</li>
								<li><b>iii.</b>	Subscribing: In the details¡¯ page, you can press the ¡°subscribe¡± button in the upper right corner of the page, and then the service will be subscribed. </li>							
							</ul>
						</li>
						<li><b>c) Get your logs back</b><br/>You can get your logs back through ServiceFoundry. You¡¯ll notice that in the block of your service, there is hyper link ¡°Log Info¡±, click it, after invoking the service, you¡¯ll get the logs back. Deploying WebApp</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="0010">Using HBase</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>a) Introduction:</b> <br/>This guide describes the uses of HBase service provided by the Service Cloud Platform. It leads you through creating a table, inserting rows, deleting data and some other operations via the HBase client. The below content should take no more than ten minutes (not including download time).</li>
						<li><b>b) Getting Ready:</b> <br/>Before using the HBase Service, we have to take care of a little housekeeping. First of all, you need to download HBaseClient.jar from the Service Cloud Platform. Besides, the client needs all the jars in the lib directory of Apache Axis2, so you also need to download an Axis2. Finally, notice that you don¡¯t need to download HBase from apache.org because you are to use our HBase service online.</li>
						<li><b>c) Using service:</b> <br/>After you have already gotten the jars mentioned above, you can import them into the project. There are some methods that we provide for you to handle data in the HBaseClient.jar. There are total 16 methods we have provided for users to handle data in the platform. In each method you must provide your username to distinguish your tables from other user¡¯s tables.</li>
						<li><b>d) Initialization:</b> <br/>What you need to do is getting the HBase service URL and putting it into the source like:<br/><img src="images/hbase.jpg" width="550" height="63" style="border:1px solid"/></li>
					</ul>
				</div>
			</div>
		<div style="clear: both;">&nbsp;</div>
		</div>
		<!-- end #content -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	</div>
	</div>
	<!-- end #page -->
</div>
	<div id=toTop><a href="#">Top<img src="images/arrow_up.png"/><br/></a></div>
	<div id="footer">
		<p>Copyright (c) Software Design and Productivity Group
		The Institute of Advanced Computing Technology, Beijing, China.</p>
	</div>
	<!-- end #footer -->
</body>
</html>
