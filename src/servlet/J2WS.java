package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.act.sdp.axis2.tools.util.ArchiverGenerator;
import db.dao.J2WSDao;

public class J2WS extends HttpServlet {
	
	public final String outputPath = "/home/sdp/apache-tomcat-7.0.32/webapps/repository/j2ws/";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String className = null;
//		String outputPath = "C:/uploadServices"; // 上传文件的目录
//		if (System.getProperty("os.name").contains("Linux")) {
////			outputPath = "/home/sdp/j2ws";
//			outputPath = System.getProperty("user.dir");
//		}
		File outputDir = new File(outputPath);
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}

		String basePath = "C:/uploadServices"; // 上传文件的目录
		if (System.getProperty("os.name").contains("Linux")) {
			basePath = "/home/sdp/uploadServices";
		}

		String absolutePath = "";
		String fileName = "";
		String zipFileName = "";

		try {
			final String allowExtNames = "zip";
			File repository = new File(basePath); // 缓存区目录
			long sizeMax = 1024 * 1024 * 20;// 设置文件的大小为20M
			int sizeThreshold = 1024 * 6; // 缓存区大小
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			diskFileItemFactory.setRepository(repository);
			diskFileItemFactory.setSizeThreshold(sizeThreshold);
			ServletFileUpload servletFileUpload = new ServletFileUpload(
					diskFileItemFactory);
			servletFileUpload.setSizeMax(sizeMax);
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);

			// 将上传的文件放至指定路径下
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName(); // 获得简单域的值
					String fieldValue = fileItem.getString("UTF-8");
					if ("className".equals(fieldName)) {
						className = fieldValue;
					}
				} else {
					String filePath = fileItem.getName();
					System.out.println("filePath:" + filePath);
					if (filePath == null || filePath.trim().length() == 0)
						continue;
					zipFileName = filePath.substring(filePath
							.lastIndexOf(File.separator) + 1);
					System.out.println("archiverName:" + zipFileName);
					fileName = filePath.substring(
							filePath.lastIndexOf(File.separator) + 1,
							filePath.lastIndexOf("."));
					System.out.println("fileName:" + fileName);
					String extName = filePath.substring(filePath
							.lastIndexOf(".") + 1);
					if (allowExtNames.indexOf(extName) != -1) {
						try {
							fileItem.write(new File(basePath + File.separator
									+ zipFileName));
							absolutePath = basePath + File.separator
									+ zipFileName;
							System.out.println("absolutePath of upload: "
									+ absolutePath);

						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						throw new FileUploadException(
								"file type is not allowed");

					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		if (className == null || className.trim().equals("")) {
			throw new NullPointerException("className is null!");
		}

		String archiverName = fileName + ".aar";
		String inputDirPath = outputPath;
		// 解压zip文件
		unZip(absolutePath, inputDirPath);
		
		inputDirPath += File.separator + fileName;

		// outputPath:生成服务包的输出地址
		// className:包名.类名
		// archiverName:带".aar"后缀的文件
		// inputDirPath:解压后的路径名
		System.out.println("outputPath=" + outputPath);
		System.out.println("className=" + className);
		System.out.println("archiverName=" + archiverName);
		System.out.println("inputDirPath=" + inputDirPath);
		
		String osType = "windows";
		if (System.getProperty("os.name").contains("Linux")){
			osType = "linux";
		}
//		System.out.println("-----------------------\n"+outputPath);
//		System.out.println(className);
//		System.out.println(archiverName);
//		System.out.println(inputDirPath);
//		System.out.println(osType+"\n-----------------------");
		ArchiverGenerator.generateArchiver(outputPath, className, archiverName,
				inputDirPath, osType);
		String userName = (String) request.getSession().getAttribute("userName");
		new J2WSDao().addWebservice(userName, fileName);
		// 删除该zip文件
		// file.delete();
		// 删除解压后的文件夹
		// new File(inputDirPath).delete();

		try {
			this.getServletContext()
					.getRequestDispatcher("/ServiceCloud/J2WS.jsp")
					.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	private void unZip(String zipFilePath, String unZipDirPath) {
		try {
			File file = new File(zipFilePath);
			if (file.exists()) {
				ZipFile zipFile = new ZipFile(file);
				// 创建本zip文件解压目录
				File unzipFile = new File(unZipDirPath);
				if (unzipFile.exists()) {
					unzipFile.delete();
				}
				unzipFile.mkdir();
				// 得到zip文件条目枚举对象
				Enumeration zipEnum = zipFile.entries();
				// 定义输入输出流对象
				InputStream input = null;
				OutputStream output = null;
				// 循环读取条目
				// System.out.println("name\t\t\tsize\t\t\tcompressedSize\t\t\tisDirectory");
				while (zipEnum.hasMoreElements()) {
					// 得到当前条目
					ZipEntry entry = (ZipEntry) zipEnum.nextElement();
					String entryName = new String(entry.getName().getBytes(
							"ISO8859_1"));
					// System.out.println(entryName + "\t\t\t" + entry.getSize()
					// + "\t\t\t" + entry.getCompressedSize()
					// + "\t\t\t\t\t\t\t" + entry.isDirectory());

					// 若当前条目为目录则创建
					if (entry.isDirectory())
						new File(unzipFile.getAbsolutePath() + File.separator
								+ entryName).mkdir();
					else { // 若当前条目为文件则解压到相应目录
						input = zipFile.getInputStream(entry);
						output = new FileOutputStream(new File(
								unzipFile.getAbsolutePath() + File.separator
										+ entryName));
						byte[] buffer = new byte[1024 * 8];
						int readLen = 0;
						while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
							output.write(buffer, 0, readLen);
						}
						input.close();
						output.flush();
						output.close();
					}
				}

			} else {

			}
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new J2WS().unZip("C:/Users/DELL/Desktop/Hello.zip",
				"C:/Users/DELL/Desktop");
	}
}
