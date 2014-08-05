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
<link href="/repository/css/firstpage_style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="wrapper">
	<div id="header">
	</div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li><a href="/repository">Home</a></li>
			<li class="current_page_item"><a href="/repository">About</a></li>
			<li><a href="/servicexchange">ServiceXchange</a></li>
			<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
			<li><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
			<li><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
                        <li><a href="/repository/ServiceCloud/publications.jsp">Publications</a></li>
			<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="page">
	<div id="page-bgtop">
	<div id="page-bgbtm">
		<div id="content">
			<div class="post">
				<h2 class="title"><a href="#">About the Service Cloud Platform</a></h2>
				<p class="meta"><span class="date">Platform Architecture -- SaaS、PaaS、IaaS</span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<p>As shown in the picture,the architecture of Cloud Computing Platform refers to three types of cloud computing applications to illustrate the three-tier structure of the overall structure of the system. PaaS layer is the Service-oriented AppEngine--services software application engine, it provides a software deployment and service-oriented environment to SaaS layer. Service software application engine is somewhat similar to Google App Engine, but GAE only supplies deployment and operational support for Java and Python applications, does not support service-oriented applications. Basically, as long as the completion of services software development, and user deploy the services by interface which is provided by the AppEngine, AppEngine will schedule service container and composite services engine dynamically, and deploy the services to the appropriate compute nodes. After successful deployment, the user can call the service by the execute interfaces. The whole process of deployment and call is transparent to the users, users do not have to care about the specific implementation details and the physical environment. SaaS layer includes ServiceXchange--services resource sharing platform and ServiceFoundry--personal online development environment. Users do not need to install any program. Using the web services in ServiceXchange and development tools provided by ServiceFoundry, you can develop a complex combination of services, applications, and deploy the combination services to the Service-oriented AppEngine--service software applications engine, then you can operate and monitor them by the tools supplied by our platform.</p>
					<br/><img src="images/architecture.jpg" width="600px" ></img>
				</div>
			</div>
			<div class="post">
				<h2 class="title"><a href="#">What's the features?</a></h2>
				<p class="meta"><span class="date">Instant Development、Transparent Deployment、Trustworthy Running、Online Evolution</span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<p>Cloud-computing platform based on three modules, we expect to minimize the burden of the service-oriented software developers and improve their development efficiency, finally implements instant development, deployment and instant instant operation in the service-oriented software development.</p>
					<p><b>(1)Instant Development:</b> As the development tools and web services which used in the development of services combination are available through the browser, so once demand determined, development work can begin immediately.</p>
					<p><b>(2)Transparent Deployment:</b> Users don't need to care about which physical node they will use,and the platform will allocate the running environment which they need automatically. After the service software development, developers can directly use the online deployment tool in the development environment (or use the interfaces provided by the applications engine) to deploy software, and service software application engines will dynamically allocate web service container and composite service engines at once, the service software will be transferred to the appropriate node to complete the deployment.</p>
					<p><b>(3)Trustworthy Running:</b> The platform provides ensuring measures such as Multi-copy mechanism, Fault Tolerance, Error Recovery and Security Isolation to promise that the service softwares deployed in the platform have high reliability and won't disturb others. As long as the success of service software deployment, users can use the online implementation tools in the development environment (or use the interface provided by the services software application engine) to run the software services immediately.</p>
					<p><b>(4)Online Evolution:</b> The service softwares deployed in the repository platform have the ability of adaptive evolution. When the environment or the users' demand changes, the service softwares or applications will change their own states automatically.</p>
				</div>
			</div>
			<div class="post">
				<h2 class="title"><a href="#">Why Service4all?</a></h2>
				<p class="meta"><span class="date">For Developers , For Testers , For Users</span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<p><b>For Developers:</b> When looking for atomic services， developers can get help from ServiceXchange such as serching services by keyword, using service cluster to locate the service you needed quickly and finding out the service quality by QoS monitor.Our repository platform also support the developers by providing debug tools, HBase storage service， reliable and flexible deployment environment for atomic service.As for service composition，our platform provide composite service modeling and orchestration tools as well as a hosting environment for developers.</p>
					<p><b>For Testers:</b> With the platform of repository, developers can carry out load test and cloud test which can simulate the multi-points test more realisticly by using geographically distributed nodes.The developers can also view the process execution states and intermediate results of the composite service by using monitor tools in the platform.</p>
					<p><b>For Users:</b> The users can access the atomic services,composite services and web applications in the platform, and integrate them to their own applications to enhance application functions.</p>
					<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/forall.jpg" width="450px" ></img>
				</div>
			</div>
			<div class="post">
				<h2 class="title"><a href="#">Pillars in Our Platform</a></h2>
				<p class="meta"><span class="date">ServiceXchange、ServiceFoundry、Service-oriented AppEngine(SAE)</span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<p><b>ServiceXchange</b>--Service resource sharing platform collect the useful web services resources to supply atomic services for ServiceFoundry (or local development tool set). Its main functions: (1) service resources aggregation; (2) service relationship mining; (3) trusted services guarantee.</p>
					<p><b>ServiceFoundry</b>--Personal online development environment consists of a series of online tools which support service-oriented software development, so online users can access through a browser. It consists of the deployment of atomic services, business process modeling, composite service establishment, composite service deployment and composite service testing to fully support composite service development, deployment and running. The key issues of ServiceFoundry include: (1) personalized online development environment. (2) complete on-line development tools support.</p>
					<p><b>Service-oriented AppEngine(SAE)</b>--Service software application engine is a set of middleware suite which installed in the physical cluster environment and followed the model of platform as a service. It   exposes the functional of external interfaces through communication protocols such as SOAP and HTTP, and dynamically schedules cluster computing resources to meet the user's request of software deployment and execution. The AppEngine can be logically divided into three parts: AppEngine business layer, device layer and external software interface layer.</p>
				</div>
			</div>
		<div style="clear: both;">&nbsp;</div>
		</div>
		<!-- end #content -->
		<div id="sidebar">
			<ul>
				<li>
					<div id="search" >
						<a href="/repository/ServiceCloud/quickStart.jsp"><img src="/repository/ServiceCloud/images/quickstart.png"/></a>
						<div style="clear: both;">&nbsp;</div>
					</div>
				</li>
				<li>
					<h3><% if(name!=null) {out.print("Welcome! "); out.print(name);} else {out.print("Please Login!");}%></h3>
					<div style="clear: both;">&nbsp;</div><br/>
					<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Thank you for using our platform, you can refer to our manual in the section of Other Documents if you use our platform for the first time. You can also download resources such as jar package to use our tools in your own project.</p>
				</li>
				<li>
					<h2>Toolkits for Developers</h2>
					<ul>
						<li><a href="/repository/ServiceCloud/documentation.jsp">Deployment4BPMN.jar</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp">Deployment4WebService.jar</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp">AppEngine.jar</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp">WebServiceDebugger.jar</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp">HBaseClient.jar</a></li>
					</ul>
				</li>
				<li>
					<h2>Publications</h2>
					<ul>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="支持动态演化的组合服务执行引擎的设计与实现">支持动态演化的组合服务执行引擎的设计...</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="基于服务组合的可信软件动态演化研究">基于服务组合的可信软件动态演化研究</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="一种具有正确性保证的组合Web服务自动合成方法">一种具有正确性保证的组合Web服务自动...</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="MM面向最终用户的Mashup移动应用开发环境">MM面向最终用户的Mashup移动应用开发...</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="支持面向服务软件开发与运行的云平台">支持面向服务软件开发与运行的云平台</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="SOARWare A Service Oriented Software Production and Running Environment">SOARWare A Service Oriented Software...</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="Early Experience of Building a Cloud Platform for Service Oriented Software Development">Early Experience of Building a Cloud...</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="CAMAN12-A Novel QoS-based Two-stage Replica Placement Algorithm for Web Service">CAMAN12-A Novel QoS-based Two-stage.</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="Distributed Microkernel-based Component Dynamically Loading Mechanism">Distributed Microkernel-based Component</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="SAPF An On-demand and Trustworthy Middleware Provisioning Framework for A Service Cloud Platform">SAPF An On-demand and Trustworthy...</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005" title="Towards Building a Trustworthy Service Container In Cloud Environment">Towards Building a Trustworthy Service...</a></li>
					</ul>
				</li>
				<li>
					<h2>Resources for Users</h2>
					<ul>
						<li><a href="/repository/ServiceCloud/documentation.jsp#005">云服务平台用户使用手册</a></li>
						<li><a href="/repository/ServiceCloud/documentation.jsp#006">云服务平台应用程序接口及说明</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!-- end #sidebar -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	</div>
	</div>
	<!-- end #page -->
</div>
	<div id="footer">
		<p>Copyright (c) Software Design and Productivity Group
		The Institute of Advanced Computing Technology, Beijing, China.</p>
	</div>
	<!-- end #footer -->
</body>
</html>
