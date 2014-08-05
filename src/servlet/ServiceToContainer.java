package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Return;
import db.dao.ServiceDao_N;
import db.dao.ServiceTrialDao;
import db.entity.Service_N;
import db.entity.ServiceTrial;
import db.entity.User_N;

public class ServiceToContainer extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ServiceToContainer() {
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
			Return.print(response, " no id");
			return;
		}
		int id;
		id = Integer.parseInt(idStr);

		ServiceDao_N serviceDao = new ServiceDao_N();
		Service_N service = serviceDao.findById(id);

		ServiceTrial serviceTrial = new ServiceTrial();
		User_N user = (User_N) request.getSession().getAttribute("user");
		if (user == null) {
			user = new User_N();
			user.setName("user1");
		}
		serviceTrial.setUserName(user.getName());
		serviceTrial.setServiceId(id);
		serviceTrial.setServiceName(service.getName());
		serviceTrial.setDeployStatus("false");
		serviceTrial.setRunStatus("false");
		ServiceTrialDao trialDao = new ServiceTrialDao();
		int result = trialDao.save(serviceTrial);
		if (result < 0) {
			Return.print(response, "save error");
			return;
		} else if (result == 0) {
			Return.print(response, "service \"" + service.getName()
					+ "\" existed");
			return;
		}
		Return.print(response, "Add service \"" + service.getName()
				+ "\" to container done!");
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
