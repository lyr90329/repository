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
    
    <title>Services</title>
    <s:head theme="ajax"/>

	<%@ include file="../common/imports.html" %>
	
	<link rel="stylesheet" type="text/css" href="service/css/services.css" media="screen" />
	<script src="service/js/special.js" type="text/javascript"></script>
	
	<!-- 提交表单 -->
	<script type="text/javascript">
		function submitCluster(){
			var form = document.getElementById('clusterSelect');
			form.submit();
		}
		function addTags(serviceId){
			var url = 'tags/ServiceTags.action';
		    var params = {
		        serviceId:serviceId
		    };
		    jQuery.post(url, params, callbackServiceTags, 'json');
		}
		
		function callbackServiceTags(data){
			var tags = data.tags;
			var serviceId = data.serviceId;
			$("#tags"+serviceId).append("Tags: ");
			for(i in tags){
				$("#tags"+serviceId).append("<a href=\"service/ServicesByTag.action?tagName="+tags[i].name+"\">"+tags[i].name+"</a>&nbsp;&nbsp;");
			}
			if(data.flag==true)
				$("#tags"+serviceId).append("...");
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
	<table border="0"><tr><td>
		<!-- 左边 -->
		<%@ include file="../common/left.jsp" %>
		<!-- 中间 -->
		<div class="middle_div">
			<!-- 搜索部分 -->
			<%@ include file="../common/search.jsp" %>
			<!-- 正文部分 -->
			<div class="outer_div">
				<div class="outer_title"><strong>Services</strong></div>
				<!-- 上面的页面跳转 -->
				<div class="inner_title">
					<table width="100%"><tr>
						<td>
							<s:if test="categories!=null">
								<form action="cluster/ServicesByCluster.action" id="clusterSelect">
					  				<select name="cId" onchange="submitCluster();">
							 			<s:iterator id="category" value="categories" status="st">
							 				<option value="<s:property value="clusterId" />" <s:if test="clusterId == cId"> selected</s:if> >category <s:property value="#st.getIndex()+1" />  (<s:property value="size" />)</option>
							 			</s:iterator>
						 			</select>
						 		</form>
							</s:if>
						</td>
						<td align="right">
							<s:if test="page.pageIndex>1">
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=first&page_index=<s:property value="page.pageIndex"/>">first</a>&nbsp;|&nbsp;
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=priv&page_index=<s:property value="page.pageIndex"/>">previous</a>
							</s:if>
							<s:if test="page.pageIndex>1 && page.pageCount>page.pageIndex">&nbsp;|&nbsp;</s:if>
							<s:if test="page.pageCount>page.pageIndex">
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=next&page_index=<s:property value="page.pageIndex"/>">next</a>&nbsp;|&nbsp;
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=last&page_index=<s:property value="page.pageIndex"/>">last</a>&nbsp;
							</s:if>
						</td>
					</tr></table>
				</div>
				<!-- service列表 -->
				<div class="inner_div">
					<s:iterator value="services" id="service" status="st">
					  	<div class="lines">
					  		<table width="100%" border="0"><tr>
						  		<td align="left" width="400">
						  			<table width="100%">
						  				<tr>
						  					<td width="285"><div>Service Name: <a href="service/ServiceOverView.action?serviceId=<s:property value="id" />"><s:property value="name"/></a></div></td>
						  					<td><div>By: <s:property value="userName"/></div></td>
						  				</tr>
						  				<tr>
						  					<td colspan="2"><div>Description: <s:property value="description"/></div></td>
						  				</tr>
						  				<tr>
						  					<td colspan="2">
						  						<div id="tags<s:property value="id" />"></div>
						  						<script type="text/javascript">addTags(<s:property value="id" />);</script>
						  					</td>
						  				</tr>
						  			</table>
								</td>
								<td align="right">
									<s:if test="#session.userName!=null">
										<div class="operation"><a href="credit/survey.jsp?serviceID=<s:property value="id"/>&jobID=<s:property value="jobId" />">Rank</a></div>
									</s:if>
									<s:if test="#session.userName==userName">
										<div class="operation"><a href="service/ServicePreUpdate.action?serviceId=<s:property value="id"/>">Update</a></div>
									</s:if>
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
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=first&page_index=<s:property value="page.pageIndex"/>">first</a>&nbsp;|&nbsp;
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=priv&page_index=<s:property value="page.pageIndex"/>">previous</a>
							</s:if>
							<s:if test="page.pageIndex>1 && page.pageCount>page.pageIndex">&nbsp;|&nbsp;</s:if>
							<s:if test="page.pageCount>page.pageIndex">
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=next&page_index=<s:property value="page.pageIndex"/>">next</a>&nbsp;|&nbsp;
								<a href="cluster/ServicesByCluster.action?cId=<s:property value="cId" />&page_mode=last&page_index=<s:property value="page.pageIndex"/>">last</a>&nbsp;
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
