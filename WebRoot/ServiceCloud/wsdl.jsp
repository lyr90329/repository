<%@ page language="java" import="java.io.*,java.util.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.Data,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*" pageEncoding="GB18030"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serviceid=(String)request.getParameter("serviceid");
ServiceTBean stbean=Data.getInstance().getwebservicebyId(Long.parseLong(serviceid));
String wsdl=stbean.getWsdlUrl();
System.out.println("URLÊÇ£º"+wsdl);
if(wsdl.startsWith("http"))
{
   out.print("<script>window.location.href='"+wsdl+"';</script>");
}
else 
{
  try
  {
     BufferedReader in = new BufferedReader(new FileReader(wsdl));
    String str;    
    while ((str = in.readLine()) != null) {
        out.print(str);
    }
    in.close();

  }
  catch(Exception e)
  {
    e.printStackTrace();
    out.print("Can not open the wsdl file!");
  }
} 
%>