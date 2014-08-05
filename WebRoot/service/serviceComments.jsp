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
    
    <title>Comments</title>
    <s:head theme="ajax"/>
	
	<%@ include file="../common/imports.html" %>
	
	<link rel="stylesheet" type="text/css" href="service/css/service_detials.css" media="screen" />
	<script src="service/js/special.js" type="text/javascript"></script>
	
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
				<div class="outer_title"><strong>Web Service Details: <s:property value="serviceBean.name"/></strong></div>
				<!-- 服务菜单 -->
				<div class="inner_menu">
					<span class="menu_botton"><a href="service/ServiceOverView.action?serviceId=<s:property value="serviceId"/>">Overview</a></span>
					<span class="menu_botton"><a href="service/ServiceOperations.action?serviceId=<s:property value="serviceId" />">Details</a></span>
					<span class="menu_botton"><a href="service/ServiceGraph.action?serviceId=<s:property value="serviceId" />">Connectability</a></span>
					<span class="menu_botton"><a href="service/GetServiceInput.action?serviceId=<s:property value="serviceId" />">Try it</a></span>
					<span class="menu_botton"><a href="service/GetQos.action?serviceId=<s:property value="serviceId" />">Preference QoS</a></span>
					<span id="current" class="menu_botton_last"><a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />">Comments</a></span>
				</div>
				<!-- 用户评论部分 -->
				<div class="inner_div">
					<s:iterator value="comments" status="st">
						<div class="comments">
							<table width="100%"><tr>
								<td>
									<span class="user">
										<s:if test="fromUser==-1"></s:if>
										<s:elseif test="fromUser!=null"><s:property value="fromUser"/>: &nbsp;</s:elseif>
									</span>
									<span><s:property value="comment"/></span>
								
								</td>
								<td align="right">
									<input type="hidden" name="id" value="<s:property value="id"/>" />
									<span class="appraise"><input name="up" type="image" src="service/img/thumbs_up_gray.gif" title="up">&nbsp;<span><s:property value="up"/></span></span>
									<span class="appraise"><input name="down" type="image" src="service/img/thumbs_down_gray.gif" title="down">&nbsp;<span><s:property value="down"/></span></span>
								</td>
							</tr></table>
						</div>
					</s:iterator>
					<div class="comments_page">
						<table width="100%"><tr>
							<td><s:property value="page.totalLine" /> Comments</td>
							<td align="right">
								<s:if test="page.pageIndex>1">
									<a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />&page_mode=first&page_index=<s:property value="page.pageIndex"/>">first</a>&nbsp;|&nbsp;
									<a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />&page_mode=priv&page_index=<s:property value="page.pageIndex"/>">previous</a>
								</s:if>
								<s:if test="page.pageIndex>1 && page.pageCount>page.pageIndex">&nbsp;|&nbsp;</s:if>
								<s:if test="page.pageCount>page.pageIndex">
									<a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />&page_mode=next&page_index=<s:property value="page.pageIndex"/>">next</a>&nbsp;|&nbsp;
									<a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />&page_mode=last&page_index=<s:property value="page.pageIndex"/>">last</a>&nbsp;
								</s:if>
							</td>
						</tr></table>
					</div>
					<div>
					 	<form action="comments/SaveComments.action" method="post">
							<input type="hidden" name="serviceId" value="<s:property value="serviceId" />" />
							<div class="add_comments"><textarea cols="50" rows="5" name="comment" /></textarea></div>
							<div class="add_comments"><input type="image" src="service/img/save_button.gif" /></div>
						</form>
					</div>
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
