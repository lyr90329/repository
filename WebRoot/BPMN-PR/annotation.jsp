<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/lib/tld/c.tld"%>
<%
String path = request.getContextPath() + "/BPMN-PR";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="js/repository.js"></script>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  
  <body>
    <div class="title">
		<h3>
			Annotations
		</h3>
	</div>
	<div class="annotation_list">
		<c:forEach var="annotation" items="${ annotationList }">
			<p>${ annotation.author }: ${ annotation.msg } </p>
		</c:forEach>
	</div>
	<div>
		<form method="post" action="AddAnnotation" class="commentform" onSubmit="return onSubmit(this,'comment')">
			<div>
				<span class="smaller"> <label for="id_comment">
						Your Annotation:
					</label> </span>
				<div class="textarea_wrapper">
					<textarea id="id_comment" rows="5" cols="25" name="comment"></textarea>
				</div>
				<input name="bpmnId" value="${ meta.id }" type="hidden" />
				<input name="titleId" value="${ meta.titleId }" type="hidden" />
				<input value="Post Annotation" type="submit" />
			</div>
		</form>
	</div>
  </body>
</html>
