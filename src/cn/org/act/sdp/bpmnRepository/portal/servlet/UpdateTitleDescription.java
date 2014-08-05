package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class UpdateTitleDescription extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateTitleDescription() {
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
		try {
			JSONObject json = new JSONObject();
			json.put("ret", false);
			String titleIdStr = request.getParameter("titleId");
			if (titleIdStr == null) {
				json.put("msg", Message.MSG_NO_TITLE_ID);
				Return.print(response, json.toString());
				return;
			}
			Integer titleId = Integer.valueOf(titleIdStr);
			String description = request.getParameter("description");

			TitleTool tool = new TitleTool();
			TitleBean title = tool.getById(titleId);
			if (title == null) {
				json.put("msg", Message.MSG_NO_TITLE_BY_ID);
				Return.print(response, json.toString());
				return;
			}
			title.setDescription(description);
			if (tool.updateDescription(title)) {
				json.put("ret", true);
				json.put("description", description);
				Return.print(response, json.toString());
				return;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Return.print(response, "{ret:false,msg:'json error'}");
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
