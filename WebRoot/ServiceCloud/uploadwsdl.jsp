<%@ page language="java" import="repository.atomicServices.ManageServiceClient,java.io.*,java.util.*,config.Config,db.dao.*,db.entity.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.Data,cn.org.act.sdp.repository.entity.ServiceTBean,com.eviware.soapui.impl.wsdl.*,repository.atomicServices.ManageServiceClient" pageEncoding="GB18030"%>
<body>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serviceid=(String)request.getParameter("serviceid");

ManageServiceClient msc=new ManageServiceClient(Config.getWSNameQueryUrl());	
String wsdlURL=msc.queryWsdlByID(serviceid).get(0);
wsdlURL=wsdlURL.substring(0,wsdlURL.length()-1);
wsdlURL+="?wsdl";
System.out.println("URL�ǣ�"+wsdlURL);
if(wsdlURL.startsWith("http"))
{
   out.print("<script>window.location.href='"+wsdlURL+"';</script>");
}
else 
{
  try
  {
     BufferedReader in = new BufferedReader(new FileReader(wsdlURL));
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

</body>