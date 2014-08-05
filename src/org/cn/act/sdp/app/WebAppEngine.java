package org.cn.act.sdp.app;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.cn.act.sdp.app.AppInfo;
import org.cn.act.sdp.utility.Client;

import config.Config;

public class WebAppEngine extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		String action = request.getParameter("action");
		if("deploy".equals(action)){
			deploy(request, response);
		}else if("undeploy".equals(action)){
			undeploy(request, response);
		}
	}
	
	private void undeploy(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		String serviceID = (String)request.getParameter("appID");
		Client client = new Client(Config.getWebAppUndeployUrl());
		client.undeployWebApp(serviceID, userName);
		
		try {
			this.getServletContext().getRequestDispatcher("/ServiceCloud/MyWebServer.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void deploy(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		String absolutePath="";
//		File file = parseFile(request, response);
		// by tangyu
//		String level = request.getParameter("level");
		String serviceLevel = "0.2";
//		if ("low".equals(level)) {
//			serviceLevel = "0.2";
//			System.out.println("AvailabilityLevel:low");
//		} else if ("middle".equals(level)) {
//			serviceLevel = "0.9";
//			System.out.println("AvailabilityLevel:middle");
//		} else if ("high".equals(level)) {
//			serviceLevel = "0.99";
//			System.out.println("AvailabilityLevel:high");
//		} else{
//			if(level!=null){
//				System.out.println("Error availabilityLevel:"+level);
//			}
//			else{
//				System.out.println("Level is null.");
//			}
//		}
		try {
			String fileName="";
			final String allowExtNames = "rar,txt,bpmn,xml,wsdl,aar,war";
			String basePath="C:/uploadServices"; // 上传文件的目录
			if(System.getProperty("os.name").contains("Linux")){
				basePath="/home/sdp/uploadServices";
			}
			File repository = new File(basePath); //缓存区目录
			long sizeMax = 1024 * 1024 * 100;//设置文件的大小为100M
			int sizeThreshold=1024*6; //缓存区大小
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			diskFileItemFactory.setRepository(repository);
			diskFileItemFactory.setSizeThreshold(sizeThreshold);
			ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItemFactory);
			servletFileUpload.setSizeMax(sizeMax);
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);
			
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName(); // 获得简单域的值
					String fieldValue = fileItem.getString("UTF-8");
					if ("level".equals(fieldName)) {
						if ("low".equals(fieldValue)) {
							serviceLevel = "0.2";
							System.out.println("级别：low");
						} else if ("middle".equals(fieldValue)) {
							serviceLevel = "0.9";
							System.out.println("级别：middle");
						} else if ("high".equals(fieldValue)) {
							serviceLevel = "0.99";
							System.out.println("级别：high");
						} else {

							System.out.println("Error availabilityLevel");

						}
					}
				}
				else {
					String filePath = fileItem.getName();
					System.out.println("filePath:" + filePath);
					if (filePath == null || filePath.trim().length() == 0)
						continue;
					String tmpFile = filePath.substring(filePath
							.lastIndexOf(File.separator) + 1);
					System.out.println("file:" + tmpFile);
					fileName = filePath.substring(
							filePath.lastIndexOf(File.separator) + 1,
							filePath.lastIndexOf("."));
					System.out.println("fileName:" + fileName);
					String extName = filePath.substring(filePath
							.lastIndexOf(".") + 1);
					if (allowExtNames.indexOf(extName) != -1) {
						try {
							fileItem.write(new File(basePath + File.separator
									+ tmpFile));
							absolutePath = basePath + File.separator + tmpFile;
							System.out.println("absolutePath of upload: " + absolutePath);

						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						throw new FileUploadException(
								"file type is not allowed");
					}
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		
		Client client = new Client(Config.getWebAppDeployUrl());
		File file = new File(absolutePath);
		AppInfo info = client.deployWebApp(file, userName, serviceLevel);
		request.setAttribute("app_info", info);
		
		//删除临时文件
		file.delete();
		
		try {
			this.getServletContext().getRequestDispatcher("/ServiceCloud/MyWebServer.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File parseFile(HttpServletRequest request,HttpServletResponse response){
		String path = request.getRealPath("/") + "upload/";
		File dir=new File(path);
		if(!dir.exists())
			dir.mkdir();
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("GBK");

			DiskFileItemFactory factoty = new DiskFileItemFactory();
			factoty.setRepository(new File(request.getRealPath("/") + "upload"));
			
			ServletFileUpload sfu = new ServletFileUpload(factoty);
			List fileList = sfu.parseRequest(request);
			File file;
			FileItem item = (FileItem)fileList.get(0);
			String name = item.getName();
				
			//保存文件
			if(name != null){
				file = new File(path + name);
				item.write(file);
				return file;
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
