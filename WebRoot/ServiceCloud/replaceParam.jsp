<%@ page language="java" import="java.util.*,cn.org.act.sdp.wsdl.WebServiceRunner,db.dao.*,db.entity.*,com.eviware.soapui.impl.wsdl.*,repository.atomicServices.*" pageEncoding="gb2312"%>
<%@ page import="parser.*"%>
<%
	
    String rp = "";
    String para=(String)request.getParameter("para");
    String soaptype=(String)request.getParameter("soaptype");
    String wsdlURL=(String)request.getParameter("wsdlURL");
    String operation=(String)request.getParameter("operation");       
	String soapContent = (String)request.getParameter("soapContent");
	String valueList = (String)request.getParameter("paramList");
	String type=(String)request.getParameter("type");
	String serviceid=(String)request.getParameter("serviceid");
	String[] values = valueList.split(",");
	soapContent = ParameterParse.replaceValues(soapContent, values, '?');
	
	System.out.println("替换参数后的报文："+soapContent);
	
	response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
	String newLoc = "TesterConfig.jsp?soapContent="+soapContent+"&operation="+operation+"&soaptype="+soaptype+"&wsdlURL="+wsdlURL+"&para="+para+"&type="+type+"&serviceid="+serviceid;
	response.setHeader("Location",newLoc);
 %>