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
    
    <title>Tryit</title>
    <s:head theme="ajax"/>
	
	<%@ include file="../common/imports.html" %>
	
	<link rel="stylesheet" type="text/css" href="service/css/service_detials.css" media="screen" />
	
	<script type="text/javascript">
	function submitOperation(){
		var form = document.getElementById('operationSelect');
		form.submit();
	}
	$(document).ready(function(){
		$(".hidden").hide();
		$("#invoke").click(invoke);
	});
	function invoke(){
		var paras = new Array();
		$("#inputParas").find("input@[type=text]").each(function(i){
			var keyValue = new Array();
			keyValue[0] = $(this).attr("name");
			keyValue[1] = $(this).attr("value");
			paras[i] = keyValue.join(":");
		});
		var input = paras.join(",");
		var url = 'service/GetServiceOutput.action';
		var params = {
			serviceId:$("#inputParas").find("input@[name=serviceId]").attr("value"),
			operationId:$("#inputParas").find("input@[name=operationId]").attr("value"),
			input:input
		};
		jQuery.post(url, params, callBackOutput, 'json');
	}
	function callBackOutput(data){
		$(".hidden").show();
		$("#outputParas").empty();
		$("#outputParas").addClass("lines");
		resultList = data.resultList;
		$("#outputParas").append("<table width=\"100%\">");
		for(i in resultList){
			$("#outputParas").find("table").append("<tr><td width=\"30%\" align=\"right\">Parameter:&nbsp;&nbsp;&nbsp;&nbsp;</td><td>"+resultList[i].name+"("+resultList[i].type+"):"+resultList[i].value+"</td></tr>");
		}
	}
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
				<div class="outer_title"><strong>Web Service Details: <s:property value="serviceBean.name"/></strong></div>
				<!-- 服务菜单 -->
				<div class="inner_menu">
					<span class="menu_botton"><a href="service/ServiceOverView.action?serviceId=<s:property value="serviceId"/>">Overview</a></span>
					<span class="menu_botton"><a href="service/ServiceOperations.action?serviceId=<s:property value="serviceId" />">Details</a></span>
					<span class="menu_botton"><a href="service/ServiceGraph.action?serviceId=<s:property value="serviceId" />">Connectability</a></span>
					<span id="current" class="menu_botton"><a href="service/GetServiceInput.action?serviceId=<s:property value="serviceId" />">Try it</a></span>
					<span class="menu_botton"><a href="service/GetQos.action?serviceId=<s:property value="serviceId" />">Preference QoS</a></span>
					<span class="menu_botton_last"><a href="comments/GetComments.action?serviceId=<s:property value="serviceId" />">Comments</a></span>
				</div>
				<div class="inner_div">
					<div class="inner_title">
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<s:if test="inputParaList!=null">
									<td><strong>Input Parameters</strong></td>
									<td align="right">
										<form action="service/GetServiceInput.action" id="operationSelect">
								 			<input type="hidden" name="serviceId" value="<s:property value="serviceId" />" />
									 		<select name="operationId" onchange="submitOperation();">
									 			<s:iterator value="operations" status="st">
									 				<option value="<s:property value="id" />" <s:if test="operationId == id"> selected</s:if> ><s:property value="name" /></option>
									 			</s:iterator>
								 			</select>
								 		</form>
								 	</td>
								 </s:if>
								 <s:else>
								 	<td align="center">
								 		<strong>This type of service is not supported by the beta version of serviceXchange.</strong>
								 	</td>
								 </s:else>
						 	</tr>
					 	</table>
			 		</div>
			 		<div class="gap_line"></div>
					<div class="lines" id="inputParas">
						<input type="hidden" name="serviceId" value="<s:property value="serviceId" />" />
						<input type="hidden" name="operationId" value="<s:property value="operationId" />" />
						<table width="100%">
							<s:if test="inputParaList!=null">
								<s:iterator value="inputParaList">
									<tr>
										<td width="30%" align="right">Parameter:&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td><s:property value="name"/>&nbsp;<input type="text" name="<s:property value="name"/>" />(<s:property value="type"/>)</td>
									</tr>
								</s:iterator>
								<tr>
									<td align="right" colspan="2"><input id="invoke" type="button" value="invoke">&nbsp;&nbsp;&nbsp;&nbsp;</td>
								</tr>
							</s:if>
						</table>
					</div>
					<div class="hidden">
						<div class="inner_title"><strong>Output Parameters</strong></div>
				 		<div class="gap_line"></div>
			 		</div>
					<div id="outputParas"></div>
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
