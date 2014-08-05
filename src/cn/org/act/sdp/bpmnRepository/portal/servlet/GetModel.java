package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import cn.org.act.sdp.bpmnRepository.constants.FileType;
import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;

public class GetModel extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetModel() {
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
		String type = request.getParameter("type");
		// default value
		if (type == null) {
			type = FileType.BPMN;
		}
		String bpmnIdStr = request.getParameter("bpmnId");
		if (bpmnIdStr == null) {
			System.out.println("no bpmn id");
			Return.print(response, Message.MSG_NO_BPMN_ID);
			return;
		}
		Integer bpmnId;
		try {
			bpmnId = Integer.valueOf(bpmnIdStr);
		} catch (NumberFormatException e) {
			Return.error(request, response, Message.MSG_WRONG_BPMN_ID);
			return;
		}
		// String versionStr = request.getParameter("version");
		// Integer version = 0;
		// if (versionStr != null) {
		// version = Integer.valueOf(versionStr);
		// }

		// get filename
		// String filename = getFilename(bpmnId, version, type);
		// if (filename == null) {
		// print(request, response, Message.MSG_NO_MODEL);
		// return;
		// }
		BpmnTool tool = new BpmnTool();
		BpmnBean bpmn = tool.getBpmnByID(bpmnId);
		if (bpmn == null) {
			Return.error(request, response, Message.MSG_NO_DOMAIN_ID);
			return;
		}
		tool.getContentByBpmn(bpmn, type);
		// if (bpmn.getBpmnStream() != null) {
		InputStream is = null;
		if (type.equals(FileType.BPMN)) {
			response.setContentType("text/xml");
			Return.print(response, bpmn.getBpmnContent());
		} else if (type.equals(FileType.FLEX)) {
			response.setContentType("text/xml");
			Return.print(response, bpmn.getFlexContent());
		} else if (type.equals(FileType.DIAGRAM)) {
			response.setContentType("text/xml");
			Return.print(response, bpmn.getDiagramContent());
		} else if (type.equals(FileType.FLEXIMG)) {
			Return.print(response, bpmn.getFlexImg());
		} else {
			Return.error(request, response, Message.MSG_UNKNOWN_TYPE);
			return;
		}
		// }
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

	/**
	 * @return if the bpmn and version existed ,the return String , else return
	 *         null
	 */
	// private String getFilename(int id, int v, String type) {
	// BPMNDao bpmnDao = new BPMNDao();
	// BPMN bpmn = bpmnDao.findById(id);
	// if (v == 0) {
	// if (!bpmn.loadLatestModel())
	// return null;
	// else
	// return bpmn.getLatestModel().getBpmnLocation();
	// } else {
	// if (!bpmn.loadVersionModel(v))
	// return null;
	// else
	// return bpmn.getVersionModel(v).getBpmnLocation();
	// }
	// // extend to other types
	// // if(type.equals("bpmn")){
	// // }
	// }
}
