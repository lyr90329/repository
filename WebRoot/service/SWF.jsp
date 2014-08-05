<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript">
	swfobject.embedSWF("open-flash-chart.swf", "my_chart", "550", "200",
			"9.0.0", "expressInstall.swf", {
				"data-file" : "<%=basePath %>ResponseTimeJSONServelet?operationId=16"
			});
</script>

</head>
<body>

<p>Hello World</p>


<div id="my_chart"></div>



</body>


</html>