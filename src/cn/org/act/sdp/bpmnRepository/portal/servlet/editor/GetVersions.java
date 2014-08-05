package cn.org.act.sdp.bpmnRepository.portal.servlet.editor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.entity.TagBean;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.config.Config;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import cn.org.act.sdp.bpmnRepository.tool.DomainTool;
import cn.org.act.sdp.bpmnRepository.tool.MetaTool;
import cn.org.act.sdp.bpmnRepository.tool.TagTool;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class GetVersions extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetVersions() {
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
		// check parameters
		String idStr = request.getParameter("titleId");
		if (idStr == null) {
			Return.print(response, Message.MSG_NO_TITLE_ID);
			return;
		}
		Integer id;
		try {
			id = Integer.valueOf(idStr);
		} catch (NumberFormatException e) {
			Return.print(response, Message.MSG_WRONG_TITLE_ID);
			return;
		}

		// initialize the database tools
		TitleTool titleTool = new TitleTool();
		// get the title bean
		TitleBean title = titleTool.getById(id);
		if (title == null) {
			Return.print(response, Message.MSG_NO_BPMN_BY_ID);
			return;
		}
		MetaTool metaTool = new MetaTool();
		List<MetaBean> metaList = metaTool.getAllVersionsByTitleId(id);
		StringBuffer xml = new StringBuffer("<root>");
		if (metaList != null) {
			for (MetaBean bean : metaList) {
				xml.append("<version version=\"" + bean.getVersion() + "\" "
						+ "id=\"" + bean.getId() + "\" />");
			}
		}
		xml.append("</root>");
		Return.print(response, xml.toString());
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
