package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.org.act.sdp.bpmnRepository.entity.TagBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.TagTool;

public class AddTag extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddTag() {
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
			// check parameters
			String idStr = request.getParameter("titleId");
			if (idStr == null) {
				json.put("ret", false);
				json.put("errorMsg", Message.MSG_NO_TITLE_ID);
				Return.print(response, json.toString());
				return;
			}
			Integer titleId;
			try {
				titleId = Integer.valueOf(idStr);
			} catch (NumberFormatException e) {
				json.put("ret", false);
				json.put("errMsg", Message.MSG_WRONG_TITLE_ID);
				Return.print(response, json.toString());
				return;
			}

			// get tags
			String tagStr = request.getParameter("tags");
			tagStr = tagStr.trim();
			String[] tags = tagStr.split("\\s+");
			// save tags
			TagTool tagTool = new TagTool();
			for (String tag : tags) {
				if (tagTool.save(tag, titleId) <= 0) {
					json.put("ret", false);
					json.put("errMsg", Message.MSG_SAVE_ERROR);
					Return.print(response, json.toString());
					return;
				}
			}
			json.put("ret", true);
			// return all tags of the title
			List<TagBean> list = tagTool.getByTitleId(titleId);
			json.put("tags", list);
			Return.print(response, json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
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
