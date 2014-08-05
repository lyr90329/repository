package cn.org.act.sdp.bpmnRepository.portal.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Action;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class ToModelEditor extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ToModelEditor() {
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
		// get the action
		String action = request.getParameter("action");
		if (action == null) {
			Return.error(request, response, Message.MSG_NO_ACTION);
			return;
		}
		Integer bpmnId = null;
		Integer titleId = null;
		String titleIdStr = request.getParameter("titleId");
		if (titleIdStr == null) {
			Return.error(request, response, Message.MSG_NO_TITLE_ID);
			return;
		}
		try {
			titleId = Integer.valueOf(titleIdStr);
		} catch (NumberFormatException e) {
			Return.error(request, response, Message.MSG_WRONG_TITLE_ID);
			return;
		}
		request.setAttribute("titleId", titleId);
		TitleTool tool = new TitleTool();
		TitleBean title = tool.getById(titleId);
		if (title == null) {
			Return.error(request, response, Message.MSG_NO_TITLE_BY_ID);
			return;
		} else {
			request.setAttribute("bpmnName", title.getTitle());
		}
		if (action.equals(Action.NEW)) {

		} else if (action.equals(Action.MODIFY)) {
			// get the bpmnId
			String bpmnIdStr = request.getParameter("bpmnId");
			if (bpmnIdStr == null) {
				Return.error(request, response, Message.MSG_NO_DOMAIN_ID);
				return;
			}
			try {
				bpmnId = Integer.valueOf(bpmnIdStr);
			} catch (NumberFormatException e) {
				Return.error(request, response, Message.MSG_WRONG_DOMAIN_ID);
				return;
			}
			request.setAttribute("bpmnId", bpmnId);
		} else {
			Return.error(request, response, Message.MSG_UNKNOWN_ACTION);
			return;
		}
		request.setAttribute("action", action);
		request.getRequestDispatcher("flex/BpmnEditor.jsp").forward(request,
				response);

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
