<%@ page language="java" import="java.io.*,config.Config,java.util.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.Data,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*" pageEncoding="GB18030"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String bpmnid=(String)request.getParameter("bpmnid");
String bpmnName=(String)request.getParameter("bpmnName");
bpmnName=bpmnName+".bpmn";
bpmnName="bpmn/"+bpmnName;
out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
  try
  {
     BufferedReader in = new BufferedReader(new FileReader(Config.getPath(bpmnName)));
    String str;    
    while ((str = in.readLine()) != null) {
        out.print(str);
    }
    in.close();

  }
  catch(Exception e)
  {
    e.printStackTrace();
    out.print("Can not open the bpmn file!");
  }
 
%>