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
 su.downloadFile("/ServiceCloud/download/docs/conference/2013/Fenglin_Zhang-Optimizing_Paxos_with_Request_Exchangeability_for_Highly_Available_Web_Services.pdf"); 
%>