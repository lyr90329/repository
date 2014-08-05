<%@ page language="java" pageEncoding="ISO-8859-1"
	import="java.util.*"%>
<%
	String path = request.getContextPath() + "/BPMN-PR";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- String action -->
<!-- int titleId -->
<!-- int bpmnId -->
<!-- saved from url=(0014)about:internet -->
<html lang="en">

<!-- 
Smart developers always View Source. 

This application was built using Adobe Flex, an open source framework
for building rich Internet applications that get delivered via the
Flash Player or to desktops via Adobe AIR. 

Learn more about Flex at http://flex.org 
// -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body { margin: 0px; overflow:hidden }
</style>

</head>

<body scroll="no">
  	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="BpmnEditor" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="BpmnEditor.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#869ca7" />
			<param name="allowScriptAccess" value="sameDomain" />
			<embed src="<%=basePath %>flex/BpmnEditor.swf" quality="high" bgcolor="#869ca7"
				width="100%" height="100%" name="BpmnEditor" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer"
				flashVars="action=${ action }&titleId=${ titleId }&bpmnId=${ bpmnId }&name=${ bpmnName }&address=<%=basePath %>">
			</embed>
	</object>

</body>
</html>
