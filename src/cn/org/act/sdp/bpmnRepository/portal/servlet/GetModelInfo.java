package cn.org.act.sdp.bpmnRepository.portal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import cn.org.act.sdp.bpmnRepository.tool.MetaTool;
import cn.org.act.sdp.bpmnRepository.tool.TitleTool;

public class GetModelInfo extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetModelInfo() {
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
			String type = request.getParameter("type");
			// default value
			if (type == null) {
				type = "bpmn";
			}
			String titleIdStr = request.getParameter("titleId");
			if (titleIdStr == null) {
				json.put("ret", false);
				print(response, json.toString());
				return;
			}
			Integer titleId = Integer.valueOf(titleIdStr);
			String versionStr = request.getParameter("version");
			Integer version = 0;
			if (versionStr != null) {
				version = Integer.valueOf(versionStr);
			}

			// read database
			Map map = getModelInfo(titleId, version, type);
			if (map == null) {
				json.put("ret", false);
				print(response, json.toString());
				return;
			} else {
				json.put("ret", true);
				json.put("modelData", map);
				print(response, json.toString());
				return;
			}
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

	private void print(HttpServletResponse response, String msg)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}

	/**
	 * get a map including file location bpmnId,version,type,latest,modifyTime
	 * of the specified model by id ,version and type.
	 * 
	 * @param id
	 *            titleId
	 * @param v
	 *            version,0 represent latest version
	 * @param type
	 *            "bpmn","diagram","flex"
	 * @return
	 */
	private Map<String, Object> getModelInfo(int id, int v, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		TitleTool titleTool = new TitleTool();
		MetaTool metaTool = new MetaTool();
		BpmnTool bpmnTool = new BpmnTool();
		TitleBean title;
		title = titleTool.getById(id);
		metaTool.getVersionByTitle(title, v);
		MetaBean meta = title.getMetas().get("v" + v);
		if (meta == null) {
			System.out.println("no model of this version in title");
			return null;
		}
		bpmnTool.getByMeta(meta);
		BpmnBean bpmn = meta.getBpmn();
		if (bpmn == null) {
			System.out.println("no model of this version in title");
			return null;
		}
		map.put("location", bpmn.getFlexLocation());
		map.put("bpmnId", bpmn.getId());
		map.put("titleId", id);
		map.put("version", v);
		map.put("type", type);
		map.put("latest", title.getLatest());
		map.put("modifyTime", meta.getModDate().toLocaleString());
		return map;
		// extend to other types
		// if(type.equals("bpmn")){
		// }
	}

}
