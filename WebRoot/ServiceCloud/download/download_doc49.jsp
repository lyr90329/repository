<%@ page contentType="text/html;charset=gb2312" 
import="com.jspsmart.upload.*" %><%
  // �½�һ��SmartUpload���� 
 SmartUpload su = new SmartUpload(); 
  // ��ʼ�� 
 su.initialize(pageContext); 
  // �趨contentDispositionΪnull�Խ�ֹ������Զ����ļ��� 
  //��֤������Ӻ��������ļ��������趨�������ص��ļ���չ��Ϊ 
  //docʱ����������Զ���word��������չ��Ϊpdfʱ�� 
  //���������acrobat�򿪡� 
 su.setContentDisposition(null); 
  // �����ļ� 
 su.downloadFile("/ServiceCloud/download/docs/periodical/2009/AutoSyn_A_new_approach_to_automated_synthesis_of_composite_web_services_with_correctness_guarantee.pdf"); 
%>