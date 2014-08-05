package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import config.Config;
import repository.atomicServices.ManageServiceClient;
import db.dao.ServiceDao;
import db.entity.UploadService;

public class UploadWsdl extends HttpServlet {

	private String basePath="C:/uploadServices"; // 上传文件的目录
	private ServiceDao dao=new ServiceDao(); 

	public void init() throws ServletException 
	{
		if(System.getProperty("os.name").contains("Linux"))
			basePath="/home/sdp/uploadServices";
		if(!new File(basePath).isDirectory())
			new File(basePath).mkdirs();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			 String absolutePath="";
			 String serviceLevel="";
			 String file="";
			 String fileName="";
			 HttpSession session=request.getSession();
			 String userName=(String)session.getAttribute("userName");
			 System.out.println(userName);
			 
	         request.setCharacterEncoding("GBK");//防止中文名乱码
	         int sizeThreshold=1024*6; //缓存区大小
	         File repository = new File(basePath); //缓存区目录
	         long sizeMax = 1024 * 1024 * 100;//设置文件的大小为100M
	         final String allowExtNames = "rar,txt,bpmn,xml,wsdl,aar";
	         DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	         diskFileItemFactory.setRepository(repository);
	         diskFileItemFactory.setSizeThreshold(sizeThreshold);
	         ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItemFactory);
	         servletFileUpload.setSizeMax(sizeMax);
	         List<FileItem> fileItems = null;
	         try{
	        	 
	             fileItems = servletFileUpload.parseRequest(request);
	             
	             for(FileItem fileItem:fileItems)
	             {
	            	 if(fileItem.isFormField())
	            	 {
	            		 String fieldName = fileItem.getFieldName();  //获得简单域的值  
	            		 String fieldValue = fileItem.getString("UTF-8");
	            		 if("level".equals(fieldName))
	            		 {
	            			 if("low".equals(fieldValue))
	            			 {
	            				 serviceLevel="0.2";
	            				 System.out.println("级别：low");
	            			 }
	            			 else if("middle".equals(fieldValue))
	            			 {
	            				 serviceLevel="0.9";
	            				 System.out.println("级别：middle");
	            			 }
	            			 else if("high".equals(fieldValue))
	            			 {
	            				 serviceLevel="0.99";
	            				 System.out.println("级别：high");
	            			 }
	            			 else
	            				 System.out.println("+++++输入级别出错！+++++");
	            		 }
	            	 }
	            	 else
	            	 {
	            		 String filePath = fileItem.getName();
		                 System.out.println("filePath:"+filePath);
		                 if(filePath==null || filePath.trim().length()==0)
		                     continue;
		                 file=filePath.substring(filePath.lastIndexOf(File.separator)+1);
		                 System.out.println("file:"+file);
		                 fileName=filePath.substring(filePath.lastIndexOf(File.separator)+1,filePath.lastIndexOf("."));
		                 System.out.println("fileName:"+fileName);
		                 String extName=filePath.substring(filePath.lastIndexOf(".")+1);
		                 if(allowExtNames.indexOf(extName)!=-1)
		                 {
		                     try 
		                     {
		                         fileItem.write(new File(basePath+File.separator+file));
		                         absolutePath=basePath+File.separator+file;
		                         System.out.println("服务上传的路径是："+absolutePath);
		                     
		                     } catch (Exception e) 
		                     {
		                         e.printStackTrace();
		                     }
		                 }
		                 else
		                 {
		                     throw new FileUploadException("file type is not allowed");
		                 }
	            	 }
	             }
	             
	             //开始进行部署
	             
            	 ManageServiceClient msc1=new ManageServiceClient(Config.getWebServiceDeployUrl());//部署反部署组件所在的位置。220是服务器
	             ArrayList<String> address=new ArrayList<String>();
	             address=msc1.deployRemoteService(serviceLevel, new File(absolutePath), userName);
	             String falseInfo="";
	             for(int i=0;i<address.size();i++)
	             {
	            	 System.out.println("打印："+address.get(i));
	            	 if(address.get(0).equals("false"))
	            	 {
	            		 falseInfo=address.get(1);
	            		 break;
	            	 }
	             }
	             String deployId=address.get(0);
	             String isSuccessful=address.get(1);
	             String sandboxInfo="";
	             System.out.println("结果："+isSuccessful);
	             if("true".equals(isSuccessful))
	             {
	            	 dao.saveUploadService(new UploadService(userName,fileName,basePath+File.separator+userName+"&"+file,"","",deployId));
	            	 request.getRequestDispatcher("MyServiceContainer.jsp").forward(request, response);
	             }
	            	 
	             else if("false".equals(deployId))
	             {
	            	 sandboxInfo=address.get(1);
	            	 System.out.println("sandbox信息："+sandboxInfo);
	            	 if(!"".equals(sandboxInfo))
	            		 dao.updateSandBoxInfo(sandboxInfo);
	            	 request.getRequestDispatcher("ServiceDeployException.jsp").forward(request, response);
	             }
	            	 
	             File deleteFile = new File(absolutePath);//删除上传的文件
	             if (deleteFile.exists())
	            	 deleteFile.delete();
	             System.out.println("上传文件已删除！");
	         }
	         catch(FileUploadException e1)
	         {
	             e1.printStackTrace();
	             System.out.println("连接出现问题！！！！！！！！！！！！！");
	         }  
	         
	}
}