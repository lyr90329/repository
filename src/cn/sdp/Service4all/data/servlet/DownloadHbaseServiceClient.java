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

public class DownloadHbaseServiceClient extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DownloadHbaseServiceClient() {
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
				response.setContentType("text/html;charset=GB2312");
				request.setAttribute("userName", userName);
				request.setAttribute("passwd", passwd);
				request.setAttribute("filePath", request.getContextPath()+"/DataService/hbase-site.xml");
				request.setAttribute("clientPath", request.getContextPath()+"/DataService/hbase-service.jar");
				RequestDispatcher dispatcher=request.getRequestDispatcher("/ServiceCloud/DownloadHbaseDataService.jsp");
				dispatcher.forward(request, response);
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
