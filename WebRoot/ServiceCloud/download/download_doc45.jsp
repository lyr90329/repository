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
 su.downloadFile("/ServiceCloud/download/docs/periodical/2012/Hailong_Sun.Achieving_Dependable_Composite_Services_Through_Two-level_Redundancy.pdf"); 
%>