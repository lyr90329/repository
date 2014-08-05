<%@ page language="java" pageEncoding="GBK" import="java.util.*,config.Config,db.dao.*,db.entity.*,constant.MenuItems,repository.loadTester.*,org.apache.axiom.om.OMElement,org.apache.axis2.AxisFault;" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	//String name=(String)session.getAttribute("userName");
	String name="user1";
	if(name==null||name=="")
	{
	   out.print("<script>alert('You hava not logined!');window.location.href='/repository/testuser/login.jsp';</script>");
	   return ;
    }
    String jobId=(String)request.getParameter("jobId");
	TesterMonitorClient client =new TesterMonitorClient();
		try {
		System.out.println("ceshi:"+jobId);
			MonitorResponse monitorResponse=client.invoke(jobId, Config.getCloudTestUrl()+"TestJobMonitor/");
			System.out.println("AverageTime:"+monitorResponse.getAverageTime());
			System.out.println("Status:"+monitorResponse.getStatus());
			System.out.println("SubId:"+monitorResponse.getSubId());
			System.out.println("SuccessListNum:"+monitorResponse.getSuccessList().size());
			System.out.println("FailListNum:"+monitorResponse.getFailList().size());
			out.print(" ;"+monitorResponse.getStatus()+";"+monitorResponse.getSuccessList().size()+";"+monitorResponse.getFailList().size()+";"+monitorResponse.getAverageTime()+";"+monitorResponse.getSubId()+"; ");
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	
%>
