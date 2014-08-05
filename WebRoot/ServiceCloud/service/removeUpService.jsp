<%@ page language="java" import="db.dao.Data,java.util.*,config.Config,db.dao.*,repository.atomicServices.ManageServiceClient" pageEncoding="gb2312"%>
<%
       String rp = "";
       String serviceid=(String)request.getParameter("serviceid"); 
       String serviceName=(String)request.getParameter("serviceName");
       String deployid=(String)request.getParameter("deployid");
       String userName=(String)request.getParameter("userName");
       //ServiceDao serviceDao=new ServiceDao();
       //int num=serviceDao.deleteUploadService(Integer.parseInt(serviceid));
	   //System.out.println("被删除的服务："+serviceid+"结果："+num );
	   ManageServiceClient msc=new ManageServiceClient(Config.getWebServiceUndeployUrl());	
	   boolean res=msc.undeploy(deployid, userName);
	   if(res)
	   {
	      rp="Removed Service "+serviceName+" Successfully!";
	   }
	   else
	   {
	     rp="Removing Service "+serviceName+" failed!";
	   }
	   
	out.print(rp);
	response.setHeader("Refresh","1;URL=../MyServiceContainer.jsp");
 %>
