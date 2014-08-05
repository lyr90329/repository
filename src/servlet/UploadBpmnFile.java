﻿package servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import config.Config;
import db.dao.BpmnDao;
import db.entity.UploadBpmn;


public class UploadBpmnFile extends HttpServlet {

	private String basePath = Config.rootPath+"bpmn"; // 上传文件的目录
	private BpmnDao dao=new BpmnDao();
	

	public void init() throws ServletException 
	{
		
		// 文件夹不存在就自动创建：
		if(!new File(basePath).isDirectory())
			new File(basePath).mkdirs();
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	             throws ServletException, IOException {
			 


			 HttpSession session=request.getSession();
			 String userName=(String)session.getAttribute("userName");
			 System.out.println(userName);
			 String uploadPath=basePath+File.separator+userName;
			 System.out.println(uploadPath);
			 if(!new File(uploadPath).isDirectory())
					new File(uploadPath).mkdirs();
			 
			 
	         request.setCharacterEncoding("GBK");//防止中文名乱码
	         int sizeThreshold=1024*6; //缓存区大小
	         File repository = new File(uploadPath); //缓存区目录
	         long sizeMax = 1024 * 1024 * 10;//设置文件的大小为2M
	         final String allowExtNames = "rar,txt,bpmn,xml,wsdl";
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
	            	 
	                
	                 String filePath = fileItem.getName();
	                 if(filePath==null || filePath.trim().length()==0)
	                     continue;
	                 String file=filePath.substring(filePath.lastIndexOf(File.separator)+1);
	                 String fileName=filePath.substring(filePath.lastIndexOf(File.separator)+1,filePath.lastIndexOf("."));
	                 String extName=filePath.substring(filePath.lastIndexOf(".")+1);
	                 if(allowExtNames.indexOf(extName)!=-1)
	                 {
	                     try 
	                     {
	                         fileItem.write(new File(uploadPath+File.separator+file));
	                         System.out.println(uploadPath+File.separator+file);
	                         dao.saveUploadBpmn(new UploadBpmn(userName,fileName,uploadPath+File.separator+file,"false","false"));
	                         
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
	        
	         }catch(FileUploadException e1)
	         {
	             e1.printStackTrace();
	         }
	         request.getRequestDispatcher("MyBpmnEngine.jsp").forward(request, response);
	         
	}
}
