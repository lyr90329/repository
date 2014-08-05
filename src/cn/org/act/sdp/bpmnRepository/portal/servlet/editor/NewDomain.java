package cn.org.act.sdp.bpmnRepository.portal.servlet.editor;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
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
		StringBuffer xml = new StringBuffer("<root>");
		String title = request.getParameter("domain");
		if (title == null) {
			xml.append("<ret>false</ret>" + "<errMsg>"
					+ Message.MSG_NO_TITLE_ID + "</errMsg>");
			xml.append("</root>");
			Return.print(response, xml.toString());
			return;
		}
		DomainBean domain = new DomainBean();
		domain.setName(title);
		Date date = new Date();
		domain.setCreatedTime(date);
		domain.setModifiedTime(date);
		DomainTool dao = new DomainTool();
		int id = dao.save(domain);
		if (id <= 0) {
			xml.append("<ret>false<ret>" + "<errMsg>"
					+ Message.MSG_START_NEW_DOAMIN_FAILED + "</errMsg>");
			xml.append("</root>");
			Return.print(response, xml.toString());
			return;
		} else {
			xml.append("<ret>true</ret>" + "<id>" + id + "</id>");
			xml.append("</root>");
			Return.print(response, xml.toString());
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
