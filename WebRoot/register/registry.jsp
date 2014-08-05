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
    
    <title>Registry</title>
    <s:head theme="ajax"/>

	<%@ include file="../common/imports.html" %>
	
	<link rel="stylesheet" type="text/css" href="register/css/registry.css" media="screen" />
	<script src="register/js/validateRegistry.js" type="text/javascript"></script>
	
  </head>
  
  <body>

<!-- Logo图片 -->
<div class="logo"></div>

<!-- 顶部菜单 -->
<div id="menu">
	<ul class="nav current-register">
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
				<div class="outer_title"><strong>Registry</strong></div>
				<div class="msg"><strong><s:property value="msg" /></strong></div>
				</br>
				<form action="register/RegisterService.action" method="post" name="regService" onsubmit="return validateRegistry(this)">
					<table width="100%">
						<tr>
							<td width="20%"><div class="registerLabel" align="right">Name:</div></td>
							<td>
								<div><input type="text" name="name" /><span style="color:red;">&nbsp;(&nbsp;*&nbsp;)</span></div>
								<div class="warning"></div>
							</td>
						</tr>
						<tr>
							<td><div class="registerLabel" align="right">Url:</div></td>
							<td>
								<div><input type="text" name="url" /><span style="color:red;">&nbsp;(&nbsp;*&nbsp;)</span></div>
								<div class="warning"></div>
							</td>
						</tr>
						<tr>
							<td><div class="registerLabel" align="right">WsdlUrl:</div></td>
							<td>
								<div><input type="text" name="wsdlUrl" /><span style="color:red;">&nbsp;(&nbsp;*&nbsp;)</span></div>
								<div class="warning"></div>	
							</td>
						</tr>
						<tr>
							<td><div class="registerLabel" align="right">Description:</div></td>
							<td><div><textarea name="description" rows="5" cols="40"></textarea></div></td>
						</tr>
						<tr>
							<td><div class="registerLabel" align="right">Additional Info:</div></td>
							<td><div><textarea name="additionalInfo" rows="5" cols="40"></textarea></div></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><div><input type="submit" value="register" /></div></td>
						</tr>
					</table>
				</form>
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
