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
    
    <title>Graph</title>
    <s:head theme="ajax"/>

	<%@ include file="../common/imports.html" %>
	
	<link rel="stylesheet" type="text/css" href="service/css/service_detials.css" media="screen" />
	
	
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
					<span id="current" class="menu_botton"><a href="service/ServiceGraph.action?serviceId=<s:property value="serviceId" />">Connectability</a></span>
					<span class="menu_botton"><a href="service/GetServiceInput.action?serviceId=<s:property value="serviceId" />">Try it</a></span>
					<span class="menu_botton"><a href="service/GetQos.action?serviceId=<s:property value="serviceId" />">Preference QoS</a></span>
					<span class="menu_botton_last"><a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />">Comments</a></span>
				</div>				
				<div class="inner_div">
					<s:iterator value="foreOperations" id="operation" status="st">
				  		<div class="lines">
				  			<div style="margin-left:10px;margin-top:10px;margin-bottom:5px;"><strong>From Service: </strong><a href="<%=basePath %>servicesInfo/ServiceOverView.action?serviceId=<s:property value="serviceId" />"><s:property value="serviceName"/></a></div>
							<div style="margin-left:10px;margin-top:5px;margin-bottom:5px;">Operation Name: <a href="<%=basePath %>servicesInfo/ServiceOperations.action?serviceId=<s:property value="serviceId" />&operationId=<s:property value="operationId" />"><s:property value="operationName"/></a></div>
							<div style="margin-left:10px;margin-top:5px;margin-bottom:5px;">Fault: <s:property value="fault"/></div>
							<div style="margin-left:10px;margin-top:5px;margin-bottom:10px;">Description: <s:property value="description"/></div>
				  		</div>
				  	</s:iterator>
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
