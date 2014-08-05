<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isLoginStr = (String)request.getSession().getAttribute("userName");
int isLogin;
if(isLoginStr!=null && !isLoginStr.equals(""))
	isLogin = 1;
else
	isLogin = 0;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Survey</title>
    <s:head theme="ajax"/>

	<!-- css样式 -->
	<link rel="stylesheet" type="text/css" href="css/logo.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/style.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/style++.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="js/menu.js" type="text/javascript"></script>
	
	<!-- ajax导入 -->
	<script src="common/news.js" type="text/javascript"></script>
	<script src="common/tags.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="common/tags.css" media="screen" />
	
	
	<!-- dtree导入 -->
	<link rel="stylesheet" type="text/css" href="dtree/dtree.css" media="screen" />
	<script src="dtree/dtree.js" type="text/javascript"></script>
	
	<!-- User Feedback -->
	<script type="text/javascript" src="common/swfobject.js"></script>
	<script type="text/javascript">
		swfobject.embedSWF("service/open-flash-chart.swf", "feedback_chart", "500", "250",
			"9.0.0", "expressInstall.swf", {
				"data-file" : "FeedbackRaderJSONServelet?serviceId=<%=request.getParameter("serviceID")%>"
			});
	</script>
	
  </head>

  <body>

<!-- Logo图片 -->
<div class="logo"></div>

<!-- 顶部菜单 -->
<div id="menu">
	<ul class="nav current-services">
		<%@ include file="../common/menu.html" %>
	</ul>
</div>

<div class="corner">
	<div class="corner_top"></div>
	<div class="corner_middle">
	<table border="0" cellspacing="0" cellpadding="0"><tr><td>
		<!-- 左边 -->
		<%@ include file="../common/left.jsp" %>
		<!-- 中间 -->
		<div class="middle_div">
			<!-- 搜索部分 -->
			<%@ include file="../common/search.jsp" %>
			<!-- 正文部分 -->
			<div class="outer_div">
				<div class="outer_title"><strong>User Survey</strong></div>
				
				
				
				
				
				
				
				
				<form name="surForm" action="survey/submitSurvey" method="post">
			<input type="hidden" name="serviceID" value="${param.serviceID}" />
			<input type="hidden" name="jobID" value="${param.jobID}" }/>
			<input type="hidden" name="isLogIn" value="<%=isLogin %>">
			<table width="100%" border="1" align="center" style="border-collapse:collapse;width:500px;margin-top:20px; ">
				<tr align="center">
					<td>
						Evaluation Parameters
					</td>
					<c:forEach var="columnNum" begin="1" end="5">
						<td>
							<c:forEach var="starNum" begin="1" end="${columnNum}">
								<img src="credit/images/art1.gif">
							</c:forEach>
						</td>
					</c:forEach>
				</tr>


				<c:forEach var="rowNum" begin="1" end="8">
					<c:choose>
						<c:when test="${rowNum==1}">
							<tr align="center">
								<td>
									Correctness
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="correctness" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="correctness" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
						<c:when test="${rowNum==2}">
							<tr align="center">
								<td>
									Execute Speed
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="extime" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="extime" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
						<c:when test="${rowNum==3}">
							<tr align="center">
								<td>
									Respond Speed
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="resptime" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="resptime" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
						<c:when test="${rowNum==4}">
							<tr align="center">
								<td>
									Cost-effective
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="price" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="price" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
						<c:when test="${rowNum==5}">
							<tr align="center">
								<td>
									Practicability
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="usability" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="usability" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
						<c:when test="${rowNum==6}">
							<tr align="center">
								<td>
									Reliability
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="reliability" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="reliability" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
						<c:when test="${rowNum==7}">
							<tr align="center">
								<td>
									Stability
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="stability" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="stability" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
						<c:when test="${rowNum==8}">
							<tr align="center">
								<td>
									Compatibility
								</td>
								<c:forEach var="columnNum" begin="1" end="5">
									<c:if test="${columnNum == 3}">
										<td>
											<input type="radio" name="compatibility" value="${columnNum}"
												checked="checked" />
										</td>
									</c:if>
									<c:if test="${columnNum != 3}">
										<td>
											<input type="radio" name="compatibility" value="${columnNum}" />
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:when>
					</c:choose>
				</c:forEach>
			</table>
			<br>
			<center>
				<input type="submit" value="Submit" />
			</center>
		</form>
		<p></p>
		<c:if test="${valid==-1}">
			<p>
				填写信息不完整，请重新填写！
			</p>
		</c:if>
		<div style="text-align:center;margin:0 auto;">
			<div id="feedback_chart"></div>
		</div>
				
				
				
				
				
				
				
				
				
			</div>
		</div>
		<!-- 右边 -->
		<jsp:include page="../common/right.jsp"></jsp:include>
	</td></tr></table>
	</div>
	<%@ include file="../common/footer.jsp" %>
</div>

  </body>
</html>
