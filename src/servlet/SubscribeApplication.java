package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Return;
import db.dao.ApplicationDao;
import db.dao.ServiceTrialDao;
import db.entity.Application;
import db.entity.ServiceTrial;
import db.entity.User_N;

public class SubscribeApplication extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SubscribeApplication() {
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

		ApplicationDao appDao = new ApplicationDao();

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		Application application = appDao.findById(id);

		int result = appDao.subscribe(id, userName, application.getName());
		if (result < 0) {
			Return.print(response, "subscribe error");
			return;
		} else if (result == 0) {

			Return.print(response, "application " + application.getName()
					+ " has been subscribed successfully!");
			return;
		}
		Return.print(response, "Subscribing application "
				+ application.getName() + " done!");
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
