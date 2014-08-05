package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

/**
 * Add a new business process theme
 * 
 * @author nevor
 * 
 */
public class NewTitle extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public NewTitle() {
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
		// check domain id
		String temp = request.getParameter("domainId");
		if (temp == null) {
			error(request, response, Message.MSG_NO_DOMAIN_ID);
			return;
		}
		Integer domainId = Integer.valueOf(temp);
		// check model name
		temp = request.getParameter("title");
		if (temp == null || temp.equals("")) {
			error(request, response, Message.MSG_NO_MODEL_NAME);
			return;
		}
		TitleBean title = new TitleBean();
		title.setDomainId(domainId);
		title.setTitle(temp);
		title.setLatest(0);
		title.setVersions(0);
		TitleTool tool = new TitleTool();
		if (tool.save(title) <= 0) {
			error(request, response, Message.MSG_SAVE_ERROR);
			return;
		}

		response.sendRedirect("AReferenceProcesses?id=" + domainId);
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
