<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*"%>
<%
	String path = request.getContextPath() + "/BPMNRepository/BPMN-PR";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:useBean id="domainList" scope="request" class="java.util.ArrayList" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<base href="<%=basePath%>" />
		<title>BPMN Repository</title>
		<meta http-equiv="content-type" content="text/html;charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="css/style.css"
			media="screen" />
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/repository.js"></script>
		<script type="text/javascript" src="js/reference_processes.js"></script>
	</head>
	<body>
		<jsp:include page="search.html"></jsp:include>
		<div class="header">
			<h1>
				Business Process Repository (BPR)
			</h1>
			<p>
				... ...
			</p>
			<ul class="tablist">
				<li>
					<a>Tutorials</a>
				</li>
				<li>
					<a class="current" href="${ path }ReferenceProcesses">ReferenceProcesses</a>
				</li>
			</ul>
		</div>
		<div class="content">
			<div class="path">
				<ul>
					<li>
						<a>Home</a>
					</li>
					<li>
						&nbsp;&gt;&gt;&nbsp;
						<a href="ReferenceProcesses">Reference Processes</a>
					</li>
				</ul>
			</div>
			<div class="right">
				<a id="right_panel_drawerhandle"
					class="drawerhandle drawerhandle_right" title="Collapse panel"></a>
			</div>
			<div class="reference_process_list left">
				<ul>
					<c:forEach var="domain" items="${domainList}">
						<li>
							<h2>
								<a href="AReferenceProcesses?id=${ domain.id }">${
									domain.name }</a>
							</h2>
							<span class="meta">Latest modified Date: ${
								domain.modifiedTime }</span>
							<div class="metamenu">
								<button class="trash_icon" title="Move to trash" onclick="removeDomain(${ domain.id })"></button>
							</div>
						</li>
					</c:forEach>
					<li>
						<h2>
							<a href="AReferenceProcesses?id=0" >Processes Of No Domain</a>
						</h2>
					</li>

					<li class="add_new_item">
						<h2>
							<a onclick="return showForm()"> Start a new domain </a>
						</h2>
						<p>
							Start a new domain to work on a specific topic together with the
							group members.
						</p>
						<div>
							<form method="post" action="NewDomain" style="display: none"
								id="form" onSubmit="return onSubmit(this,'domain')">
								<label>
									Title:(less than 45)
								</label>
								<input type="text" maxlength="45" name="domain" />
								<input type="submit" value="Create" class="submit" />
							</form>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="footer">
			<p>
				&copy; 2007 Internet Hosting, Design: Luka Cvrk -
				<a href="#"
					title="Information Architecture and Web Design">Solucija</a>
			</p>
			<p>
				<a href="#">RSS Feed</a> &middot;
				<a href="#">Contact</a> &middot;
				<a href="#">Accessibility</a> &middot;
				<a href="#">Products</a> &middot;
				<a href="#">Disclaimer</a> &middot;
				<a href="#">CSS</a>
				and
				<a href="#">XHTML</a>
				<br />
			</p>
		</div>
	</body>
</html>
