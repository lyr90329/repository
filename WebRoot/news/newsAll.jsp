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
				<div class="outer_title"><strong>News</strong></div>
				<!-- 上面的页面跳转 -->
				<div class="page_area">
					<table width="100%"><tr>
						<td><strong><s:property value="page.totalLine" /> Found</strong></td>
						<td align="right">
							<s:if test="page.pageIndex>1">
								<a href="news/GetAllNews.action?page_mode=first&page_index=<s:property value="page.pageIndex"/>">first</a>&nbsp;|&nbsp;
								<a href="news/GetAllNews.action?page_mode=priv&page_index=<s:property value="page.pageIndex"/>">previous</a>
							</s:if>
							<s:if test="page.pageIndex>1 && page.pageCount>page.pageIndex">&nbsp;|&nbsp;</s:if>
							<s:if test="page.pageCount>page.pageIndex">
								<a href="news/GetAllNews.action?page_mode=next&page_index=<s:property value="page.pageIndex"/>">next</a>&nbsp;|&nbsp;
								<a href="news/GetAllNews.action?page_mode=last&page_index=<s:property value="page.pageIndex"/>">last</a>&nbsp;
							</s:if>
						</td>
					</tr></table>
				</div>
				<table width="100%">
					<s:iterator value="news">
						<tr>
							<td><div class="list_title"><a href="news/NewsDetails.action?id=<s:property value="id"/>"><s:property value="title"/></a></div></td>
							<td align="right"><div class="list_time"><s:property value="date"/></div></td>
						</tr>
					</s:iterator>
				</table>
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
