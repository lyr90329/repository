package repository.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import jofc2.model.Chart;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.LineChart;
import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.FeedBackTBean;
import cn.org.act.sdp.repository.cleavage.entity.OperationQosTBean;
import cn.org.act.sdp.repository.cleavage.session.FeedBackSession;
import cn.org.act.sdp.repository.cleavage.session.OperationQosSession;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

/**
 * Servlet implementation class ResponseTimeJSONServelet
 */
public class FeedbackRaderJSONServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FeedBackSession fbs;

	/**
	 * Default constructor.
	 * 
	 * @throws Exception
	 */
	public FeedbackRaderJSONServelet() throws Exception {
		RepositoryConf.managerInit();
		RepositoryConf.poolInit();
		SqlManagerConf.init();
		fbs = (FeedBackSession) cn.org.act.sdp.repository.cleavage.session.SessionFactory
				.openSession(cn.org.act.sdp.repository.cleavage.session.SessionType.FeedBack);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String serviceId = (String) request.getParameter("serviceId");
		if (serviceId == null)
			return;
		// List<Number> values = new ArrayList<Number>();
		double values[] = new double[8];
		List datas = fbs.get(new String[] { "1", serviceId });
		int i;
		for (i = 0; i < datas.size(); i++) {
			FeedBackTBean data = (FeedBackTBean) datas.get(i);
			values[0] += data.getCorrectness();
			values[1] += data.getExecuteTime();
			values[2] += data.getRespondVelocity();
			values[3] += data.getPrice();
			values[4] += data.getReputation();
			values[5] += data.getReliability();
			values[6] += data.getStability();
			values[7] += data.getCompatibilty();
		}
		String valuesString = "";
		String title = "";
		int max;
		if (i != 0) {// 有评价数据
			for (int j = 0; j < values.length; j++) {
				values[j] /= i;
			}
			int j;
			for (j = 0; j < values.length - 1; j++) {
				valuesString += values[j] + ",";
			}
			valuesString += values[j];
			title = datas.size()+" evaluations";
			max = 5;
		} else {// 没有评价数据
			title = "No Evaluation Data";
			valuesString = "0,0,0,0,0,0,0,0";
			max = 1;
		}

		String spokeLabelsString = "\"Correctness\",\"Execute Time\",\"Respond Velocity\",\"Cost-effective\",\"Reputation\",\"Reliability\",\"Stability\",\"Compatibility\"";
		String string = "{ \"elements\": [ { \"type\": \"area\", \"width\": 1, \"dot-style\": { \"type\": \"hollow-dot\", \"colour\": \"#45909F\", \"dot-size\": 4 }, \"colour\": \"#45909F\", \"fill\": \"#45909F\", \"fill-alpha\": 0.4, \"loop\": true, \"values\": [ "
				+ valuesString
				+ " ] } ], \"title\": { \"text\": \""
				+ title
				+ "\" }, \"radar_axis\": { \"max\": "
				+ max
				+ ", \"colour\": \"#EFD1EF\", \"grid_colour\": \"#EFD1EF\", \"labels\": { \"labels\": [ \"0\", \"1\", \"2\", \"3\", \"4\", \"5\" ], \"colour\": \"#9F819F\" } , \"spoke-labels\": { \"labels\": [ "
				+ spokeLabelsString
				+ " ],  \"colour\": \"#9F819F\" } }, \"tooltip\": { \"mouse\": 1 }, \"bg_colour\": \"#DFFFEC\" }";
		response.getWriter().write(string);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
