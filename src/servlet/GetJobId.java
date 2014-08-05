package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import db.dao.BpmnDao;
import db.entity.Bpmn;

public class GetJobId extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetJobId() {
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

		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		String bpmnIdStr = request.getParameter("bpmnId");
		int bpmnId = Integer.valueOf(bpmnIdStr);
		String userName = request.getParameter("userName"); // get entity from
		// database
		BpmnDao dao = new BpmnDao();
		Bpmn trial;
		System.out.println("GetJobId: bpmnId=" + bpmnId + "  userName="
				+ userName + "  action=" + action);
		trial = dao.findRunningByBPMNIdAndUserName(bpmnId, userName);
		JSONObject data = new JSONObject();
		try {
			data.put("action", action);
			data.put("userName", userName);
			data.put("bpmnId", bpmnId);
			data.put("bpmnName", trial.getBpmnName());
			data.put("ret", false);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (action.equals("getJobId")) {
			System.out.println("action==getJobId");
			String jobId = String.valueOf(trial.getJobId());
			System.out.println("获取到正在执行的JobId：" + jobId);
			try {
				data.put("jobId", jobId);
				data.put("ret", true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		out.print(data.toString());
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
