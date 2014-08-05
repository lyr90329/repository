package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.bpmnRepository.config.Config;
import cn.org.act.sdp.bpmnRepository.constants.Message;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import cn.org.act.sdp.bpmnRepository.tool.MetaTool;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class KeywordsSearch extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public KeywordsSearch() {
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
		String inputKeys = request.getParameter("keys");
		if (inputKeys == null || inputKeys.equals("")) {
			Return.error(request, response, Message.MSG_NO_KEYS);
			return;
		}
		inputKeys = inputKeys.replaceAll("\\s+", " ");
		TitleTool tool = new TitleTool();
		MetaTool metaTool = new MetaTool();
		BpmnTool bpmnTool = new BpmnTool();
		List<TitleBean> list = tool.search(inputKeys);
		if (list != null) {
			for (TitleBean title : list) {
				metaTool.getLatestByTitle(title);
				bpmnTool.getByMeta(title.getLatestVersionMeta());
			}
		}
		request.setAttribute("keywords", inputKeys);
		request.setAttribute("titleList", list);
		request.setAttribute("flexImgFolderPath", Config
				.getPath(Config.imgFolderPath));
		request.getRequestDispatcher("search_result.jsp").forward(request,
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
