package cn.sdp.Service4all.data.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.hbase.coprocessor.CoprocessorHost.Environment;

import cn.sdp.Service4all.data.DataServiceUtil;
import cn.sdp.Service4all.data.cassandra.CassandraConfigFile;
import cn.sdp.Service4all.data.cassandra.SysCassandraDAO;

public class DownloadCassandraServiceClient extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DownloadCassandraServiceClient() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("dataUserName");
		String passwd = request.getParameter("dataUserPasswd");
		String confirmPasswd = request.getParameter("confirmDataUserPasswd");
		if (!passwd.equals(confirmPasswd))
		{
			response.sendRedirect("dataServiceError.jsp");
		}
		else
		{
			SysCassandraDAO dao = SysCassandraDAO.getInstance();
			int code = dao.createCassandraUser(userName, passwd);
			if (code == 0)
			{
				CassandraConfigFile config = dao.getCassandraConfig(userName);
				String path = DataServiceUtil.WEBROOT_PATH+"/DataService/ds_"+userName;
				File f = new File(path);
				if (!f.exists())
					f.mkdir();
				path += "/cassandra.properties";
				FileWriter writer = new FileWriter(new File(path));
				writer.write("server-address = "+config.getServer()+System.getProperty("line.separator"));
				writer.write("keyspace-name = "+config.getKeyspace()+System.getProperty("line.separator"));
				writer.write("user-name = "+config.getUserName()+System.getProperty("line.separator"));
				writer.write("password = "+config.getPassword()+System.getProperty("line.separator"));
				writer.close();
				response.setContentType("text/html;charset=GB2312");
				request.setAttribute("userName", userName);
				request.setAttribute("passwd", passwd);
				request.setAttribute("filePath", request.getContextPath()+"/DataService/ds_"+userName+"/cassandra.properties");
				request.setAttribute("clientPath", request.getContextPath()+"/DataService/cassandra-service.jar");
				RequestDispatcher dispatcher=request.getRequestDispatcher("/ServiceCloud/DownloadCassandraDataService.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
