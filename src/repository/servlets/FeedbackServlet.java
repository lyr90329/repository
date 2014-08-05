package repository.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.config.SqlStatementManager;
import cn.org.act.sdp.repository.cleavage.entity.FeedBackTBean;
import cn.org.act.sdp.repository.cleavage.session.FeedBackSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;

import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class FeedbackServlet extends GenericServlet {

	private FeedBackSession fdsession;
	private FeedBackTBean fdbean;

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (httpRequest.getParameter("correctness") != null
				&& httpRequest.getParameter("extime") != null
				&& httpRequest.getParameter("resptime") != null
				&& httpRequest.getParameter("price") != null
				&& httpRequest.getParameter("usability") != null
				&& httpRequest.getParameter("reliability") != null
				&& httpRequest.getParameter("stability") != null
				&& httpRequest.getParameter("compatibility") != null
				&& httpRequest.getParameter("serviceID") != null
				&& httpRequest.getParameter("jobID") != null) {

			RepositoryConf.poolInit();
			SqlManagerConf.init();
			SqlStatementManager manager = SqlStatementManager.singleInstance();

			fdbean = new FeedBackTBean();
			try {
				fdsession = (FeedBackSession) (SessionFactory
						.openSession(SessionType.FeedBack));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fdbean.setCompatibilty(Integer.parseInt(httpRequest
					.getParameter("compatibility")));
			fdbean.setCorrectness(Integer.parseInt(httpRequest
					.getParameter("correctness")));
			fdbean.setExecuteTime(Integer.parseInt(httpRequest
					.getParameter("extime")));

			fdbean
					.setJobId(Integer.parseInt(httpRequest
							.getParameter("jobID")));
			fdbean
					.setPrice(Integer.parseInt(httpRequest
							.getParameter("price")));
			fdbean.setReliability(Integer.parseInt(httpRequest
					.getParameter("reliability")));
			fdbean.setReputation(Integer.parseInt(httpRequest
					.getParameter("usability")));
			fdbean.setRespondVelocity(Integer.parseInt(httpRequest
					.getParameter("resptime")));

			fdbean.setServiceId(Integer.parseInt(httpRequest
					.getParameter("serviceID")));
			fdbean.setStability(Integer.parseInt(httpRequest
					.getParameter("stability")));

			fdbean.setTimestamp(new Date());
			String userName = (String) httpRequest.getSession().getValue(
					"userName");
			if (userName != null && !userName.equals("")) {
				fdbean.setLogIn(1);
				fdbean.setUserName(userName);
			} else
				fdbean.setLogIn(0);
			boolean success = fdsession.save(fdbean);

			httpRequest.setAttribute("valid", 1);
			httpRequest.setAttribute("surRes", fdbean);
			// UserFeedbackClient user = new UserFeedbackClient();
			// System.out.println(user.userAddIntoDB(fdbean));

		} else {
			httpRequest.setAttribute("valid", -1);
		}
		RequestDispatcher dispatcher = httpRequest
				.getRequestDispatcher("/credit/survey.jsp");
		dispatcher.forward(httpRequest, httpResponse);
	}
}
