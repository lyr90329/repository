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
    
    <title>News</title>
    <s:head theme="ajax"/>
	
	<%@ include file="../common/imports.html" %>

	<link rel="stylesheet" type="text/css" href="news/css/news.css" media="screen" />
	
  </head>
  
  <body>

<!-- Logo图片 -->
<div class="logo"></div>

<!-- 顶部菜单 -->
<div id="menu">
	<ul class="nav current-news">
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
				<div class="outer_title"><strong><s:property value="newsBean.title" /></strong></div>
				<div class="news_info">
					<table width="100%">
						<tr>
							<td><strong>Author:</strong><a href="mailto:<s:property value="newsBean.userName" />"><s:property value="newsBean.userName" /></a></td>
							<td align="right"><strong>Time:</strong><s:property value="newsBean.date" /></td>
						</tr>
					</table>
				</div>
				<div class="news_content">
					<p>
						<s:property value="newsBean.content"/>
					</p>
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
