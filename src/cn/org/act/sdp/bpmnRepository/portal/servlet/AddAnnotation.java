package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.AnnotationBean;
import cn.org.act.sdp.bpmnRepository.entity.UserBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.LoginCheck;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.AnnotationTool;

public class AddAnnotation extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddAnnotation() {
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
		this.doPost(request, response);
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
		// if(!LoginCheck.isLogin(request, response)){
		// System.out.println("you have not logined in!!!");
		// return;
		// }
		String bpmnIdStr = request.getParameter("bpmnId");
		if (bpmnIdStr == null) {
			Return.error(request, response, Message.MSG_NO_BPMN_ID);
			return;
		}
		int bpmnId;
		try {
			bpmnId = Integer.valueOf(bpmnIdStr);
		} catch (NumberFormatException e) {
			Return.error(request, response, Message.MSG_WRONG_BPMN_ID);
			return;
		}

		String titleIdStr = request.getParameter("titleId");
		if (titleIdStr == null) {
			Return.error(request, response, Message.MSG_NO_TITLE_ID);
			return;
		}
		int titleId;
		try {
			titleId = Integer.valueOf(titleIdStr);
		} catch (NumberFormatException e) {
			Return.error(request, response, Message.MSG_WRONG_BPMN_ID);
			return;
		}

		String comment = request.getParameter("comment");
		if (comment == null) {
			Return.error(request, response, Message.MSG_NO_COMMENT);
			return;
		}
		// UserBean user = (UserBean)request.getSession().getAttribute("user");
		AnnotationBean annotation = new AnnotationBean();
		annotation.setBpmnId(bpmnId);
		annotation.setMsg(comment);
		// annotation.setUserId(user.getId());
		AnnotationTool tool = new AnnotationTool();
		if (tool.save(annotation) <= 0) {
			Return.error(request, response, Message.MSG_SAVE_ERROR);
			return;
		} else {
			response.sendRedirect("AProcess?id=" + titleId);
			return;
		}
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
