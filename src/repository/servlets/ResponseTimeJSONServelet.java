package repository.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jofc2.model.Chart;
import jofc2.model.Text;
import jofc2.model.axis.XAxis;
import jofc2.model.axis.YAxis;
import jofc2.model.elements.LineChart;
import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.OperationQosTBean;
import cn.org.act.sdp.repository.cleavage.session.OperationQosSession;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

/**
 * Servlet implementation class ResponseTimeJSONServelet
 */
public class ResponseTimeJSONServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OperationQosSession qs;

	/**
	 * Default constructor.
	 * 
	 * @throws Exception
	 */
	public ResponseTimeJSONServelet() throws Exception {
		RepositoryConf.managerInit();
		RepositoryConf.poolInit();
		SqlManagerConf.init();
		System.out.println("ss");
		qs = (OperationQosSession) cn.org.act.sdp.repository.cleavage.session.SessionFactory
				.openSession(cn.org.act.sdp.repository.cleavage.session.SessionType.OperationQos);
		// System.out.println("ss");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String para = (String) request.getParameter("operationData");
		String[] paras = para.split("_");

		String operId = paras[0];
		String startDate = paras[1];
		String endDate = paras[2];
		if (operId == null)
			return;
		List<Number> values = new ArrayList<Number>();
		List datas = qs.getByDates(new String[] { "1", operId, startDate,
				endDate });
		long maxResponseTime = -1;
		List<String> labels = new ArrayList<String>();
		for (Object data : datas) {
			long time = ((OperationQosTBean) data).getResponseTime();
			values.add(time);
			labels.add(((OperationQosTBean) data).getStartTime().split(" ")[0]);
			if (time > maxResponseTime)
				maxResponseTime = time;
		}
		String title;
		if (maxResponseTime == -1)
			title = "No Response time data";
		else
			title = "Response time data";
		Chart chart = new Chart(title);
		LineChart linechart = new LineChart(LineChart.Style.NORMAL);

		linechart.addValues(values);
		linechart.setText("time/ms");
		linechart.setGradientFill(true);
		linechart.setToggleVisibility();
		linechart.setTooltip("sof");

		YAxis yaxis = new YAxis();
		yaxis.setMax(maxResponseTime * 1.3);
		yaxis.setGridColour("#177261");
		yaxis.setSteps(((int) maxResponseTime / 10));
		chart.setYAxis(yaxis);
		// chart.setYLegend(new Text("ms"));
		XAxis xaxis = new XAxis();
		// xaxis.setMax(6);
		// xaxis.setSteps(1);
		// xaxis.setStroke(15);

		xaxis.setLabels(labels);
		xaxis.setGridColour("#177261");
		chart.setXAxis(xaxis);
		chart.setBackgroundColour("#888888");

		// chart.addElements(
		// .setOutlineColour("#577261").setColour(
		// "#E2D66A").addValues(19, 8, 7, 6, 5, 4, 3, 2, 1))
		// .setBackgroundColour("#FFFFFF");
		chart.addElements(linechart);
		response.getWriter().write(chart.toString());

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
