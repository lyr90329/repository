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
		// ��ʼ�� 
		su.initialize(pageContext);
		// �趨contentDispositionΪnull�Խ�ֹ������Զ����ļ��� 
		//��֤������Ӻ��������ļ��������趨�������ص��ļ���չ��Ϊ 
		//docʱ����������Զ���word��������չ��Ϊpdfʱ�� 
		//���������acrobat�򿪡� 
		su.setContentDisposition(null);
		// �����ļ� 
		su.downloadFile("/j2ws/" + fileName);
		out.clear();  
		out = pageContext.pushBody();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>