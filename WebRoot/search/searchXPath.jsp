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
    
    <title>Search</title>
    <s:head theme="ajax"/>

	<%@ include file="../common/imports.html" %>
	
	<link rel="stylesheet" type="text/css" href="service/css/services.css" media="screen" />
	<script src="service/js/special.js" type="text/javascript"></script>
	
  </head>
  
  <body>

<!-- Logo图片 -->
<div class="logo"></div>

<!-- 顶部菜单 -->
<div id="menu">
	<ul class="nav current-services">
		<li class="services"><a href="service/GetAllServiceInfo.action">Services</a></li>
		<li class="news"><a href="#">News</a></li>
		<li class="consumers"><a href="user/GetUserInfo.action">Consumers</a></li>
		<li class="providers"><a href="BPMN-PR/ReferenceProcesses">Providers</a></li>
		<li class="register"><a href="register/RegisterServicePre.action">Register</a></li>
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
				<div class="outer_title"><strong>Search Results</strong></div>
				<!-- 上面的页面跳转 -->
				<div class="inner_title">
					<table width="100%"><tr>
						<td><strong><s:property value="page.totalLine" /> Found</strong></td>
						<td align="right">
							<s:if test="page.pageIndex>1">
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=first&page_index=<s:property value="page.pageIndex"/>">first</a>&nbsp;|&nbsp;
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=priv&page_index=<s:property value="page.pageIndex"/>">previous</a>
							</s:if>
							<s:if test="page.pageIndex>1 && page.pageCount>page.pageIndex">&nbsp;|&nbsp;</s:if>
							<s:if test="page.pageCount>page.pageIndex">
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=next&page_index=<s:property value="page.pageIndex"/>">next</a>&nbsp;|&nbsp;
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=last&page_index=<s:property value="page.pageIndex"/>">last</a>&nbsp;
							</s:if>
						</td>
					</tr></table>
				</div>
				<!-- service列表 -->
				<div class="inner_div">
					<s:iterator value="uBeanList" id="service" status="st">
					  	<div class="lines">
					  		<table width="100%" border="0"><tr>
						  		<td align="left" width="500">
							  		<div>Service Name: <a href="service/ServiceOverView.action?serviceId=<s:property value="getLongProperty('Id')" />"><s:property value="getStringProperty('Name')"/></a></div>
									<div>By <a href="<s:property value="url"/>"><s:property value="getStringProperty('Url')"/></a></div>
									<div>Description: <s:property value="getStringProperty('Description')"/></div>
								</td>
								<td align="right">
									<s:if test="#session.userName!=null">
										<dir style="margin-right:10px;"><a href="credit/survey.jsp?serviceID=<s:property value="getLongProperty('Id')" />&jobID=<s:property value="getLongProperty('jobId')" />&isLogin=1">Rank</a></dir>
									</s:if>
									<s:else>
										<dir style="margin-right:10px;"><a href="credit/survey.jsp?serviceID=<s:property value="getLongProperty('Id')" />&jobID=<s:property value="getLongProperty('jobId')" />&isLogin=0">Rank</a></dir>
									</s:else>
									<s:if test="#session.userName==getStringProperty('UserName')">
										<dir style="margin-left:5px;margin-right:10px;"><a href="service/ServicePreUpdate.action?serviceId=<s:property value="getLongProperty('Id')" />">Update</a></dir>
									</s:if>
									<s:else>
										<dir class="deadLink" style="margin-left:5px;margin-right:10px;">Update</dir>
									</s:else>
								</td>
							</tr></table>
					  	</div>
					  </s:iterator>
				</div>
				<!-- 下面的页面跳转 -->
				<div class="inner_footer">
					<table width="100%"><tr>
						<td><strong><s:property value="page.totalLine" /> Found</strong></td>
						<td align="right">
							<s:if test="page.pageIndex>1">
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=first&page_index=<s:property value="page.pageIndex"/>">first</a>&nbsp;|&nbsp;
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=priv&page_index=<s:property value="page.pageIndex"/>">previous</a>
							</s:if>
							<s:if test="page.pageIndex>1 && page.pageCount>page.pageIndex">&nbsp;|&nbsp;</s:if>
							<s:if test="page.pageCount>page.pageIndex">
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=next&page_index=<s:property value="page.pageIndex"/>">next</a>&nbsp;|&nbsp;
								<a href="search/SearchBeans.action?searchText=<s:property value="searchText"/>&searchType=<s:property value="searchType"/>&page_mode=last&page_index=<s:property value="page.pageIndex"/>">last</a>&nbsp;
							</s:if>
						</td>
					</tr></table>
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
