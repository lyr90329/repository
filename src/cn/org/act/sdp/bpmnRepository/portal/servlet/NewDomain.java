package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.tool.DomainTool;

public class NewDomain extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public NewDomain() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("domain");
		if (title == null) {
			error(request, response, Message.MSG_NO_DOMAIN_TITLE);
			return;
		}
		DomainBean domain = new DomainBean();
		domain.setName(title);
		Date date = new Date();
		domain.setCreatedTime(date);
		domain.setModifiedTime(date);
		DomainTool dao = new DomainTool();
		if (dao.save(domain) <= 0) {
			error(request, response, Message.MSG_START_NEW_DOAMIN_FAILED);
			return;
		} else
			response.sendRedirect("ReferenceProcesses");
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	private void error(HttpServletRequest request,
			HttpServletResponse response, String msg) {
		request.setAttribute("msg", msg);
		try {
			request.getRequestDispatcher("error.jsp")
					.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
