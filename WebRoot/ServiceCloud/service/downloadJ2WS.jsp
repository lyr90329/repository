<%@ page language="java"
	import="db.dao.Data,java.util.*,java.io.File,java.io.FileInputStream,com.jspsmart.upload.*"
	pageEncoding="gb2312"%>
<%
	String userName = (String) session.getAttribute("userName");
	String fileName = (String) request.getParameter("fileName");
	
	if(fileName==null){
		out.print("The fileName is null!");
	}

	try {
		if (!fileName.endsWith(".aar")) {
			fileName += ".aar";
		}
		SmartUpload su = new SmartUpload();
		// 初始化 
		su.initialize(pageContext);
		// 设定contentDisposition为null以禁止浏览器自动打开文件， 
		//保证点击链接后是下载文件。若不设定，则下载的文件扩展名为 
		//doc时，浏览器将自动用word打开它。扩展名为pdf时， 
		//浏览器将用acrobat打开。 
		su.setContentDisposition(null);
		// 下载文件 
		su.downloadFile("/j2ws/" + fileName);
		out.clear();  
		out = pageContext.pushBody();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>