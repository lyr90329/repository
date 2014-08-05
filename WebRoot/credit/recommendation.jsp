<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Recommendation for you</title>
    <s:head theme="ajax"/>

	<!-- css样式 -->
	<link rel="stylesheet" type="text/css" href="css/logo.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="common/tags.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/style.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="css/style++.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="credit/css/recommendation.css" media="screen" />
	
	
	<!-- 菜单js函数 -->
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="js/menu.js" type="text/javascript"></script>
	
	<!-- ajax导入 -->
	<script src="common/news.js" type="text/javascript"></script>
	<script src="common/tags.js" type="text/javascript"></script>
	<script src="credit/js/rec.js" type="text/javascript"></script>
	
	<!-- dtree导入 -->
	<link rel="stylesheet" type="text/css" href="dtree/dtree.css" media="screen" />
	<script src="dtree/dtree.js" type="text/javascript"></script>
	
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
			<div class="rec_title"><strong><s:property value="#session.userName" />, Welcome to SDP web service portal.</strong></div>
			<div class="rec_outer">
				<div class="rec_header">Today's recommendations for you</div>
				<div class="rec_content">
					<div class="rec_line">
						<div class="rec_text">Tell us more about your likes and dislikes by rating services you have an opinion about. The more we know about your interests, the more we can do to improve your recommendations. Learn more.</div>
					</div>
					<div class="rec_line">
						<div class="rec_text">Click here to <strong><a href="#">see all recommendations.</a></strong></div>
					</div>
					<div class="rec_line">
						<div class="rec_text">
							<form name="rec_form" action="search/SearchBeans.action" method="post">
								<strong>Search for items to rate:</strong>
								<input name="searchType"type="hidden" value="rec_search"/>
								<input name="searchText"type="text" size="40"/>
								<input name="rec_button" src="credit/images/go.gif" alt="Go" height="18" type="image" />
							</form>
						</div>
					</div>
					<table cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" align="right"><img src="credit/images/step1.gif" width="12" height="12"></td>
							<td width="200">
								<div class="rec_line2" >
									<div class="rec_text">Use the search box above to find your favorite books, movies, albums, artists, authors and brands.</div>
									</br><img src="credit/images/navig.gif" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="right"><img src="credit/images/step2.gif" width="12" height="12"></td>
							<td>
								<div class="rec_line2" >
									<div class="rec_text">Tell us what you think of the items we return for your search by rating the item or telling us you already own them.</div>
									</br><img src="credit/images/navig.gif" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="right"><img src="credit/images/step3.gif" width="12" height="12"></td>
							<td>
								<div class="rec_line2" >
									<div class="rec_text">Repeat until the Recommendations you find in Your Amazon.com reflect your tastes and interests.</div>
								</div>
							</td>
						</tr>
					</table>
					</br></br></br></br>
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
