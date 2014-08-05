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
		<title>SubscribedServices</title>
		<meta content="text/html; charset=ISO-8859-1"
			http-equiv="content-type" />
		<link rel="stylesheet" href="style.css" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="js/service_cloud.js"></script>
		<script type="text/javascript" src="js/subscribed_services.js"></script>
	</head>
	<body>
		<div id="container">
			<jsp:include page="header.jsp" />
			<div class="divider">
			</div>
			<div class="list center">
				<ul>
					<c:if test="${ fn:length(list)>0 }">
						<c:forEach var="i" begin="0" end="${fn:length(list)-1}">
							<li>
								<div>
									<span class="service-name"> <label>
											Name:
										</label><a href="#">${list[i].name}</a> </span>
									<span class="author"> <label>
											URL:
										</label> <a href="#"> <c:choose>
												<c:when test="${list[i].url == null || list[i].url == ''}">
													<span style="color: grey">No URL ...</span>
												</c:when>
												<c:otherwise>
												${list[i].url }
											</c:otherwise>
											</c:choose> </a> </span>
									<span class="options">
										<form action="ServiceToContainer?id=${list[i].id }"
											method="get">
											<input type="button" value="to Container"
												serviceId="${list[i].id }"
												onclick="serviceToContainer(this)" />
										</form> </span>
								</div>
								<br />
								<br />
								<div class="context">
									<c:choose>
										<c:when
											test="${list[i].description == null || list[i].description == ''}">
											<span style="color: grey">No description ...</span>
										</c:when>
										<c:otherwise>
									${list[i].description }
								</c:otherwise>
									</c:choose>
								</div>
							</li>
						</c:forEach>
					</c:if>
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
