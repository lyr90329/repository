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
	<title>jQuery treeView</title>
	
	
	<script src="js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script src="category/jquery.treeview.js" type="text/javascript"></script>
	<link rel="stylesheet" href="category/jquery.treeview.css" />
	<link rel="stylesheet" href="category/screen.css" />
	<script type="text/javascript">
	$(document).ready(function(){
		//first example
		var url = 'cluster/CategoryTree.action';
	    var params = {
	        clusterNum:10
	    };
	    jQuery.post(url, params, callbackCategory, 'json');
	});
	function callbackCategory(data){
		var categories = data.categories;
		var counter = 0;
		for(i in categories){
			counter++;
			var services = categories[i].services;
			var category_name = "category "+counter+" ("+categories[i].size+")";
			var category_id = "category"+counter;
			$("#category").append("<li class=\"closed\"><span class=\"folder\">"+category_name+"</span><ul id=\""+category_id+"\"></ul></li>");
			for(j in services){
				var service_name = services[j].name;
				var service_url = "service/ServiceOverView.action?serviceId="+services[j].id;
				$("#category").find("#category"+counter).append("<li><span class=\"file\"><a href=\""+service_url+"\">"+service_name+"</a></span></li>");
			}
		}
		$("#category").treeview();
	}
	</script>
</head>
<body>
	<h4>Category Tree</h4>
	<ul id="category" class="filetree">	</ul>
</body>
</html>
