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
    
    <title>Update</title>
    <s:head theme="ajax"/>
	
	<%@ include file="../common/imports.html" %>
	
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
				<div class="outer_title"><strong>Service Update</strong></div>
				
				<form action="service/ServiceUpdate.action" method="post" >
	    			<input type="hidden" name="serviceId" value="<%=request.getParameter("serviceId") %>" />
	    			<table align="center">
	    				<tr>
	    					<td><div style="font:12px;">wsdl url:</div></td>
	    					<td><input type="text" name="wsdl_url" size="50" value="<s:property value="wsdl_url" />"></td>
	    				</tr>
	    				<tr>
	    					<td valign="top"><div style="font:12px;">description:</div></td>
	    					<td><textarea cols="40" rows="5" name="description"><s:property value="description" /></textarea></td>
	    				</tr>
	    				<tr>
	    					<td colspan="2"><div align="right"><input type="submit" name="submit" value="Update"></div></td>
	    				</tr>
	
	    			</table>
    			</form>
				
				<div class="inner_div">
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
