package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.portal.constants.Message;
import cn.org.act.sdp.bpmnRepository.portal.util.Return;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import cn.org.act.sdp.bpmnRepository.tool.MetaTool;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;
import db.dao.Data;

public class BPMNSubscribe extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BPMNSubscribe() {
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

		JSONObject data = new JSONObject();
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
			Return.error(request, response, Message.MSG_WRONG_DOMAIN_ID);
			return;
		}
		// get bpmn
		BpmnTool tool = new BpmnTool();
		BpmnBean bpmn = tool.getBpmnByID(bpmnId);
		String bpmnName = bpmn.getBPMNName();
		if (bpmn == null) {
			Return.error(request, response, Message.MSG_NO_DOMAIN_ID);
			return;
		}
		tool.getContentByBpmn(bpmn, "flex");
		this.flex = bpmn.getFlexContent();
		tool.getContentByBpmn(bpmn, "bpmn");
		this.bpmn = bpmn.getBpmnContent();
		MetaTool metaTool = new MetaTool();
		MetaBean meta = metaTool.getById(bpmn.getId());

		TitleTool titleTool = new TitleTool();
		TitleBean title = titleTool.getById(meta.getTitleId());

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		this.userName = userName;

		if (updateBpmn(bpmn, meta, title, userName, response)) {
			if (updateTrial(bpmn, meta, title, response)) {
				try {
					data.put("ret", true);
					data.put("bpmnName", bpmnName);
					Return.print(response, data.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}

	}

	private String flex = "";
	private String bpmn = "";
	private String userName = "";
	private long bpmnid = 0;

	private boolean updateTrial(BpmnBean bpmn, MetaBean meta, TitleBean title,
			HttpServletResponse response) throws IOException {
		// database
		Data data = Data.getInstance();
		boolean bool = data.subscribe(this.bpmnid, this.userName, title
				.getTitle());
		if (!bool) {
			Return.print(response, "subscribe failed");
			return false;
		}
		return true;
	}

	private boolean updateBpmn(BpmnBean bpmn, MetaBean meta, TitleBean title,
			String userName, HttpServletResponse response) throws IOException {
		// database
		Data data = Data.getInstance();
		long bpmnid = data.createPath(-1, title.getTitle(), 1, userName);
		this.bpmnid = bpmnid;
		boolean bool = data
				.savaBPMN(bpmnid, userName, this.bpmn, "", this.flex);

		// String dataUrl = "jdbc:mysql://localhost:3306/repository";
		// Connection connection = null;
		// PreparedStatement pstmt = null;
		// try {
		// Class.forName("com.mysql.jdbc.Driver").newInstance();
		// connection = DriverManager.getConnection(dataUrl,"root","sdp123");
		// String sql1 =
		// "insert into cloud_bpmn (bpmnid,userName,diagramflex) values(?,?,?);";
		// // 1
		// pstmt = connection.prepareStatement(sql1);
		// pstmt.setLong(1, bpmnid);
		// pstmt.setString(2, this.userName);
		// pstmt.setString(3, bpmn.getFlexContent());
		// if(pstmt.executeUpdate()<0){
		// Return.print(response, "subscribe failed");
		// return false;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// Return.print(response, "subscribe failed");
		// return false;
		// } finally {
		// try {
		// pstmt.close();
		// connection.close();
		//				
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// Return.print(response, "subscribe failed");
		// return false;
		// }
		// }
		if (!bool) {
			Return.print(response, "subscribe failed");
			return false;
		}

		return true;
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
