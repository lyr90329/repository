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
 su.downloadFile("/ServiceCloud/download/docs/conference/2013/Wei_Yuan-Towards_Efficient_Deployment_of_Cloud_Applications_Through_Dynamic_Reverse_Proxy_Optimization.pdf"); 
%>