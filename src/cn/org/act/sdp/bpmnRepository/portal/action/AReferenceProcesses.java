package cn.org.act.sdp.bpmnRepository.portal.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.constants.ZeroIDDomain;
import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.config.Config;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import cn.org.act.sdp.bpmnRepository.tool.DomainTool;
import cn.org.act.sdp.bpmnRepository.tool.MetaTool;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class AReferenceProcesses extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AReferenceProcesses() {
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
		String idStr = request.getParameter("id");
		if (idStr == null) {
			error(request, response, Message.MSG_NO_DOMAIN_ID);
			return;
		}
		Integer id;
		try {
			id = Integer.valueOf(idStr);
		} catch (NumberFormatException e) {
			error(request, response, Message.MSG_WRONG_DOMAIN_ID);
			return;
		}
		DomainBean domain;
		if (id != 0) {
			DomainTool domainDao = new DomainTool();
			domain = domainDao.getDomainById(id);
		} else {
			domain = new ZeroIDDomain();
		}
		TitleTool titleTool = new TitleTool();
		List<TitleBean> titleList = titleTool.getByDomainId(domain.getId());
		// read out the metadata and bpmn modle
		MetaTool metaTool = new MetaTool();
		BpmnTool bpmnTool = new BpmnTool();
		MetaBean meta;
		if (titleList != null) {
			for (TitleBean bean : titleList) {
				metaTool.getLatestByTitle(bean);
				meta = bean.getMetas().get("v0");
				if (meta != null) {
					bpmnTool.getByMeta(meta);
				}
			}
			request.setAttribute("titleList", titleList);
		}
		request.setAttribute("domain", domain);
		// the relative path
		request.setAttribute("flexImgPath", Config.imgFolderPath);
		request.getRequestDispatcher("a_reference_processes.jsp").forward(
				request, response);
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
