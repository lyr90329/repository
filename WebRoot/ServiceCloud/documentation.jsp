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
</head>
<body>
<div id="wrapper">
	<div id="header">
		<!--<div id="logo">
			<h1><a name="pagetop">Service Cloud Platform</a></h1>
			<p> designed by  BUAA-ACT</p>
		</div>
	--></div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li><a href="/repository">Home</a></li>
			<li><a href="/servicexchange">ServiceXchange</a></li>
			<li><a href="/repository/ServiceCloud/MyServiceContainer.jsp">ServiceFoundry</a></li>
			<li><a href="/repository/ServiceCloud/resources.jsp">Service-oriented App Engine</a></li>
			<li><a href="/repository/ServiceCloud/Toolkits.jsp">Toolkits</a></li>
			<li class="current_page_item"><a href="/repository/ServiceCloud/documentation.jsp">Documentation</a></li>
                        <li><a href="/repository/ServiceCloud/publications.jsp">Publications</a></li>
			<li><%if(name!=null) {%><a href="/repository/user/LogoutUser.action">Logout</a><%} else {%><a href="/repository/testuser/login.jsp">Sign In</a><%} %></li>
		</ul>
	</div>
	<!-- end #menu -->
	<div id="quickstart">
	<div id="quickstart-bgtop">
	<div id="quickstart-bgbtm">
		<div id="quickstart-content">
			<div class="post">
				<h3 class="title"><a href="#006">Resources for Users</a></h3>
				<p class="meta"><span class="date"><a name="000">Deployment4BPMN.jar</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>Overview:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BPMN_Deploy.jar is a eclipse sdk plugin to deploy .bpmn file into our SCP .With this plugin , registed developers can easily and conveniently deploy a .bpmn file into a bpmn engine in our SCP without any code or other redundant steps.</li>
						<li>
							<b>User Guide:</b><br/>
							<ul style="list-style:none;">
								<li><b>1)</b>	Copy this jar file into eclipse/plugin folder.</li>
								<li><b>2)</b>	Right click a .bpmn file,choose BPMN Deploy option.</li>
								<li><b>3)</b>	Input your username which is registed in Service Cloud platform and the corresponding password in the dialoge box.</li>
								<li><b>4)</b>	Submit to the platform and you’ll get the BPMN ID after the file is successfully deployed.</li>							
							</ul>
						</li>
						<li><b>Sample:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This figure shows the dialoge to input necessary information while you deploying a bpmn file.<br/><img src="images/Deployment4BPMN.jpg" width="269" height="184" style="border:1px solid"/></li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="001">Deployment4WebService.jar</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>Overview:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;WebServiceDeploy.jar is a eclipse sdk plugin to deploy web service package(usually a .aar file) into our SCP .With this plugin , registed developers can easily and conveniently deploy a web service into several service containers in our SCP by simply configuration without any code or other redundant steps.</li>
						<li>
							<b>User Guide:</b><br/>
							<ul style="list-style:none;">
								<li><b>1)</b>	Copy this jar file into eclipse/plugin folder.</li>
								<li><b>2)</b>	Right click a .aar file,choose Web Service Deploy option.</li>
								<li><b>3)</b>	Input your expected reliability and your username which is registed in Service Cloud platform and the corresponding password in the dialoge box.</li>
								<li><b>4)</b>	Submit to the platform and you’ll get the service ID after the file is successfully deployed.</li>							
							</ul>
						</li>
						<li><b>Sample:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This figure shows the dialoge to input necessary information while you deploying atomic web service.<br/><img src="images/Deployment4WebService.jpg" width="272" height="213" style="border:1px solid"/></li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="002">AppEngine.jar</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>Overview:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AppEngine.jar intergrates the main APIs our app engine provides,including web service deployment, undeployment ,invoking and bpmn deployment, undeployment, composite service invoking API. With these APIs, developers can deploy, undeploy and invoke atomic services or composite services by adding a few codes into their original java codes. </li>
						<li>
							<b>Interfaces & Samples:</b><br/>
							<ul style="list-style:none;">
								<li><b>1) Login</b>	
									<ul style="list-style:none;">
										<li><b>a)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String login(String userName, String password)<br/>Before using any other interfaces in AppEngine.jar, users should login first with this method.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>userName:user name</li>
												<li>password: the corresponding password</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">Login.login("tom","tompasswd");</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>b)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String logout(String userName)<br/>After invoking the needed interfaces, user logouts with this method.</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">Login.logout("tom");</p></li>
											</ul>
										</li>
									</ul>
								</li>
								<li><b>2) ExposedeInterfaces</b>	
									<ul style="list-style:none;">
										<li><b>a)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DeployFeedback webServiceDeploy(String availability, File serviceArchive)<br/>Deploys an atomic web service.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>availability: service availability the user expects (a float number between 0 and 1)</li>
												<li>serviceArchive: atomic servcie package to be deployed ,usually appears as an aar file</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">DeployFeedback deployFeed = ExposedInterfaces.webServiceDeploy("0.7", new File("aar/ConvertMoney.aar"));</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>b)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DeployFeedback webServiceDeploy(File serviceArchive)<br/>Deploys an atomic web service with a fixed service availability of 0.9.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>serviceArchive: atomic servcie package to be deployed ,usually an aar file</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">DeployFeedback deployFeed = ExposedInterfaces.webServiceDeploy(new File("aar/ConvertMoney.aar"));</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>c)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;UndeployFeedback  webServiceUndeploy(String id)<br/>Undeploys an atomic service deployed in our platform.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>id: the unique identification of a service in SCP</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">UndeployFeedback  undeployFeed = ExposedInterfaces.webServiceUndeploy("WS_1112");</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>d)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;OMElement webServiceInvoke(File file)<br/>Invokes an atomic web service deployed in SCP.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>file：a xml style file to invoke a web service in SCP</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">ExposedInterfaces.webServiceInvoke( new File("testData/invoke.xml"));</p></li>
												<li><p style="font-style:italic">Einvoke.xml:</p><br/><img src="images/soap1.jpg" width="730" height="400" style="border:1px solid"/></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>e)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;OMElement webServiceInvoke(OMElement paraList, String id)<br/>Invokes an atomic web service with parameter list and service id.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>paraList: necessary parameters to invoke an atomic service	(paraList is an OMElement that contains all the child nodes of soap:Body shown in invoke.xml)</li>
												<li>id: the unique identification of a service in SCP</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">ExposedInterfaces.webServiceInvoke(paraList, “WS_1647”);</p></li>
												<li><p style="font-style:italic">Where paraList is:</p><br/><img src="images/soap2.jpg" width="500" height="79" style="border:1px solid"/></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>f)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DeployFeedback  bpmnDeploy(File bpmn)<br/>Deploys a composite web service into SCP.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>bpmn: a xml style .bpmn file to deploy</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">ExposedInterfaces.bpmnDeploy(new File("bpmn/exchange.bpmn"));</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>g)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DeployFeedback  bpmnDeploy(String name,File bpmn)<br/>Deploys a composite web service into SCP.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>name: composite web service name</li>
												<li>bpmn: a xml style .bpmn file to deploy</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">ExposedInterfaces.bpmnDeploy(new File(“exchange”,"bpmn/exchange.bpmn"));</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>h)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;UndeployFeedback  bpmnUndeploy(String sn, String sId)<br/>Undeploys a composite web service from SCP.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>sn: composite web service name</li>
												<li>sId: the unique identification of a composite web service in SCP</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">UndeployFeedback undeployFeed = ExposedInterfaces.bpmnUndeploy("eStore2", "BPMN_105");</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>i)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;OMElement bpmnInvoke(String serviceName, String serviceId, List<Parameter4WS> ps)<br/>Invokes a composite web service in SCP.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>serviceName: composite web service name</li>
												<li>serviceId: the unique identification of a composite web service in SCP</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">	private void bpmnInvoke(String serviceName, String id) throws Exception {<br/>&nbsp;&nbsp;&nbsp;&nbsp;List<Parameter4WS> ps = new ArrayList<Parameter4WS>();<br/>&nbsp;&nbsp;&nbsp;&nbsp;Parameter4WS ca = new Parameter4WS();<br/>&nbsp;&nbsp;&nbsp;&nbsp;ca.setParamName("ca");<br/>&nbsp;&nbsp;&nbsp;&nbsp;ca.setParamType("String");<br/>&nbsp;&nbsp;&nbsp;&nbsp;ca.setParamValue("RMB");<br/>&nbsp;&nbsp;&nbsp;&nbsp;ps.add(ca);<br/>&nbsp;&nbsp;&nbsp;&nbsp;Parameter4WS cb = new Parameter4WS();<br/>&nbsp;&nbsp;&nbsp;&nbsp;cb.setParamName("cb");<br/>&nbsp;&nbsp;&nbsp;&nbsp;cb.setParamType("String");<br/>&nbsp;&nbsp;&nbsp;&nbsp;cb.setParamValue("ISK");<br/>&nbsp;&nbsp;&nbsp;&nbsp;ps.add(cb);<br/>&nbsp;&nbsp;&nbsp;&nbsp;Parameter4WS mount = new Parameter4WS();<br/>&nbsp;&nbsp;&nbsp;&nbsp;mount.setParamName("mount");<br/>&nbsp;&nbsp;&nbsp;&nbsp;mount.setParamType("String");<br/>&nbsp;&nbsp;&nbsp;&nbsp;mount.setParamValue("100");<br/>&nbsp;&nbsp;&nbsp;&nbsp;ps.add(mount);<br/>&nbsp;&nbsp;&nbsp;&nbsp;OMElement response=ExposedInterfaces.bpmnInvoke(serviceName, id,ps);<br/>}<br/>	public static void main(String args[]) throws Exception {<br/>&nbsp;&nbsp;&nbsp;&nbsp;try {<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println("Login  "+Login.login("gdl","gdl"));<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;new APISample().bpmnInvoke("exchange","BPMN_100");<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.print("Logout  "+Login.logout("gdl"));<br/>&nbsp;&nbsp;&nbsp;&nbsp;} catch (Exception e) {<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;e.printStackTrace();<br/>&nbsp;&nbsp;&nbsp;&nbsp;}<br/>}</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>j)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;List<Parameter> bpmnMonitor(String id)<br/>Querys the execution information of a composite web service.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>id: the job id of a running composite web service process.</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">List&lt;Parameter&gt; list=ExposedInterfaces.bpmnMonitor("34");</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>k)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;WebAppDeployFeedback webAppDeploy(File file)<br/>Deploys a web application into web servers in SCP.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>file: a web application package,usually appears as a war file.</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">WebAppDeployFeedback  webAppDeployFeedback = ExposedInterfaces.webAppDeploy (new File("testData/JSPPage.war"));</p></li>
											</ul>
										</li>
									</ul>
									<ul style="list-style:none;">
										<li><b>l)</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;WebAppUndeployFeedback  webAppUndeploy(String serviceID)<br/>Removes a web application from SCP.</li>
										<li><b>Parameters:</b>
											<ul style="list-style:none;">
												<li>serviceID: the unique identification of a web application deployed in SCP.</li>
											</ul>
										</li>
										<li><b>Sample:</b>
											<ul style="list-style:none;">
												<li><p style="font-style:italic">WebAppUndeployFeedback webappUndeployFeedback = ExposedInterfaces.webApp Undeploy("APP_2082");</p></li>
											</ul>
										</li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="003">WebServiceDebugger.jar</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>Overview:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;WebServiceDebugger.jar is tool, implemented on basis of log4j, to help you debug your service deployed in the platform. Due to the characteristics of the cloud platform, it is always hard to know why your service runs incorrectly and locate the errors of your web service. And it is also hard to get access to the logs you printed in your service. Using the WebServiceDebugger.jar will help you do such a thing. It records the debugging information. If your web service gets an unexpected result, you can get all the information you logged presented to you.</li>
						<li>
							<b>Interface:</b><br/>
							<ul style="list-style:none;">
								<li>public void out(int level, Obeject message)</li>
								<li>The method logs out what you want to print.</li>
								<li>Parameter level:<br/><img src="images/WSDebugger.jpg" width="633" height="240"/></li>
								<li>Parameter message: message is the information you want to log out.</li>
							</ul>
						</li>
						<li><b>Sample:</b>
							<ul style="list-style:none;">
								<li><p style="font-style:italic">WebServiceDebugger debugger = new WebServiceDebugger();<br/>int num1=0;<br/>int num2=1;<br/>debugger.out(WebServiceDebugger.INFO, "Input is "+num1+","+num2);</p></li>
								<li>And, what is to be pointed out is that the debugger is only effective in web service running in our platform.</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="004">HBaseClient.jar</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><b>Overview:</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This document describes the detail use of HBase Client and main methods the client jar provides.This guide describes the uses of HBase service provided by the Service Cloud Platform. It leads you through creating a table, inserting rows, deleting data and some other operations via the HBase client jar.</li>
						<li>
							<b>Interface:</b><br/>
							<ul style="list-style:none;">
								<li>Here is a table for main methods we provided in HBaseClient.jar</li>
								<li><img src="images/hbaseinterface.jpg" width="632" height="525"/></li>
							</ul>
						</li>
						<li><b>Sample:</b>
							<ul style="list-style:none;">
								<li><b>Initialization: </b>What you need to do is getting the HBase service URL and putting it into the source like:</li>
								<li><p style="font-style:italic">String url = "http://192.168.3.223:8080/axis2/services/HBaseService";<br/>HBaseClient client = HBaseClient.getInstance(url);</p></li>
								<li>After create an instance of HBase client, you can use the 16 methods below now.</li>
								<li><b>Creating a table: </b></li>
								<li><p style="font-style:italic">client.describeTable("tom", "table");</p></li>
								<li><b>Dropping a table: </b></li>
								<li><p style="font-style:italic">client.dropTable ("tom", "table");</p></li>
								<li><b>Modifying a table: </b></li>
								<li>If you want to modify the IsReadOnly property of the table to true:<p style="font-style:italic">client.modifyTable("tom", "table", TableProperty.IS_READ_ONLY , Boolean.TRUE.toString());</p></li>
								<li><b>Adding a family: </b></li>
								<li>For example, add a family whose name is “family” to table “table”:<p style="font-style:italic">client.addFamily("tom", "table", "family");</p></li>
								<li><b>Deleting a family: </b></li>
								<li>For example, delete a family whose name is “family” to table “table”:<p style="font-style:italic"> client.deleteFamily("tom", "table", "family");</p></li>
								<li><b>Modifying a family: </b></li>
								<li>If you want to modify the MaxVersion property of the family “family” to 3:<p style="font-style:italic">client.modifyFamily("tom", "table", "family", FamilyProperty.MAX_VERSION, Integer.toString(3));</p></li>
								<li><b>Inserting data: </b></li>
								<li>For common data, for example, insert data “value” into table “table”, row “row”, column “column”:<p style="font-style:italic">client.insertData("tom", "table", "row", "column", "value");</p></li>
								<li>If you want to record the timestamp:<p style="font-style:italic">client.insertDataWithTs("tom", "table", "row", "column", "value",Long.parseLong(timestamp));</p></li>
								<li><b>Deleting data: </b></li>
								<li><p style="font-style:italic">client.deleteData("tom", "table", "row", "column");</p></li>
								<li>If you want to appoint the timestamp:<p style="font-style:italic">client.deleteData("tom", "table", "row", "column", Long.parseLong(timestamp));</p></li>
								<li>If you want to appoint the version instead of the timestamp:<p style="font-style:italic">client.deleteData("tom", "table", "row", "column", Integer.parseInt(version));</p></li>
								<li><b>Describing a table: </b></li>
								<li><p style="font-style:italic">client.describeTable("tom", "table");</p></li>
								<li><b>Getting data: </b></li>
								<li>If you want to get data in a row:<p style="font-style:italic">client.getRowData("tom", "table", "row");</p></li>
								<li>If you want to get data in a specified row and a specified column:<p style="font-style:italic">client.getValue("tom", "table", "row", "column");</p></li>
								<li>If you want to get data in a specified row and a specified column together with a specified timestamp:<p style="font-style:italic">client.getValueWithTs("tom", "table", "row", "column", Long.parseLong(timstamp));</p></li>
								<li>If you want to get data in a specified row and a specified column together with a specified version:<p style="font-style:italic">client.getValueWithVersion("tom", "table", "row", "column", Integer.parseInt(version));</p></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="post">
				<p class="meta"><span class="date"><a name="006">Resource for Users</a></span></p>
				<div style="clear: both;">&nbsp;</div>
				<div class="entry">
					<ul>
						<li><a href="/repository/ServiceCloud/download/download_doc1.jsp">云服务平台用户使用手册</a></li>
						<li><a href="/repository/ServiceCloud/download/download_doc2.jsp">云服务平台应用程序接口及说明</a></li>
					</ul>
				</div>
			</div>
			
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
