package cn.org.act.sdp.bpmnRepository.portal.servlet.editor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.config.Config;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import cn.org.act.sdp.bpmnRepository.tool.DomainTool;
import cn.org.act.sdp.bpmnRepository.tool.MetaTool;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class GetTitles extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetTitles() {
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
		String idStr = request.getParameter("domainId");
		if (idStr == null) {
			Return.print(response, Message.MSG_NO_DOMAIN_ID);
			return;
		}
		Integer id;
		try {
			id = Integer.valueOf(idStr);
		} catch (NumberFormatException e) {
			Return.print(response, Message.MSG_WRONG_DOMAIN_ID);
			return;
		}
		DomainTool domainDao = new DomainTool();
		DomainBean domain = domainDao.getDomainById(id);
		if (domain == null) {
			Return.print(response, Message.MSG_NO_DOMAIN_BY_ID);
			return;
		}
		TitleTool titleTool = new TitleTool();
		List<TitleBean> titleList = titleTool.getByDomainId(domain.getId());
		StringBuffer xml = new StringBuffer("<root>");
		if (titleList != null) {
			for (TitleBean bean : titleList) {
				xml.append("<title name=\"" + bean.getTitle() + "\" " + "id=\""
						+ bean.getId() + "\" />");
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
