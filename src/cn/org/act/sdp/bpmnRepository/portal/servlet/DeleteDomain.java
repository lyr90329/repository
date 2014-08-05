package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.DomainTool;

public class DeleteDomain extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DeleteDomain() {
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
		String domainIdStr = request.getParameter("domainId");
		if (domainIdStr == null) {
			Return.print(response, Message.MSG_NO_DOMAIN_ID);
			return;
		}
		Integer domainId;
		try {
			domainId = Integer.valueOf(domainIdStr);
		} catch (NumberFormatException e) {
			Return.error(request, response, Message.MSG_WRONG_DOMAIN_ID);
			return;
		}
		DomainTool tool = new DomainTool();
		DomainBean domain = tool.getDomainById(domainId);
		if (domain == null) {
			Return.error(request, response, Message.MSG_NO_DOMAIN_BY_ID);
			return;
		}
		tool.remove(domain);
		Return.print(response, Message.MSG_DELETE_DOMAIN_OK);
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

}
