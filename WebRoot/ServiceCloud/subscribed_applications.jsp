<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*,db.entity.*,constant.MenuItems"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:useBean id="list" scope="request" class="java.util.ArrayList" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>SubscribedApplications</title>
		<meta content="text/html; charset=ISO-8859-1"
			http-equiv="content-type" />
		<link rel="stylesheet" href="style.css" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/service_cloud.js"></script>
	</head>
	<body>
		<div id="container">
			<jsp:include page="header.jsp" />
			<div class="divider">
			</div>
			<div class="list center">
				<ul>
					<c:forEach var="app" items="${list}">
						<li>
							<div>
								<span class="service-name">
									<label>Id: </label><a href="#">${app.id}</a>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<label>Pages Amount: </label><a href="#">${app.pagesAmount}</a>
								</span>
								<span class="author">
									<label>Access Page: </label>
									<a href="#">
										<c:choose>
											<c:when test="${app.accessPage == null || app.accessPage == ''}">
												<span style="color: grey">No URL ...</span>
											</c:when>
											<c:otherwise>
												${app.accessPage }
											</c:otherwise>
										</c:choose>
									</a>
								</span>
								<span class="options">
									<form action="ApplicationDeploy?id=${app.id }" method="get">
										<input type="button" value="deploy" />
									</form>
								</span>
							</div>
							<br />
							<br />
							<div class="context">
								<c:choose>
									<c:when
										test="${app.description == null || app.description == ''}">
										<span style="color: grey">No description ...</span>
									</c:when>
									<c:otherwise>
									${app.description }
								</c:otherwise>
								</c:choose>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="divider">
			</div>
			<div id="footer">
				<p>
					Copyright &copy; 2007 Your Website. All rights reserved. Design by
					<a href="http://www.firstlightwebdesign.com">First Light</a>.
				</p>
			</div>
		</div>
	</body>
</html>
