package cn.sdp.act.appengine.monitor;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataBaseUtils {

	private static Log logger = LogFactory.getLog(DataBaseUtils.class);

	private static Connection con;

	public static void init() throws Exception {
		String url=null;
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(DataBaseUtils.class.getResource(
					"dbconfig.properties").getFile()));
			url= prop.getProperty("url");
			String user = prop.getProperty("user");
			String pwd = prop.getProperty("password");
			logger.info("Initiate the connection to the database "+url);
			Class.forName(prop.getProperty("driverClass"));
			con = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			logger.warn("Can't get the connection to the database "+url);
			throw e;
		}
	}

	public static void close() throws SQLException {
		if (con != null) {
			con.close();
		}
	}

	/**
	 * query the job by its id
	 * 
	 * @param jobId
	 * @return
	 */
	public static Job getJobById(String jobId) {

		logger.info("Query the target job by its id : " + jobId);
		Statement st = null;
		try {
			st = con.createStatement();
			String querySql = "select * from job where jobId = '" + jobId + "'";
			ResultSet rs = st.executeQuery(querySql);

			if (rs == null) {
				logger
						.warn("Can't get result set after query the database for the job: "
								+ jobId);
				return null;
			}

			// get the job data from statement
			while (rs.next()) {
				Job job = new Job();
				job.setJobId(jobId);

				String serviceName = rs.getString("serviceName");
				String serviceId = rs.getString("serviceId");
				Long tl = rs.getLong("time_long");

				job.setServiceName(serviceName);
				job.setServiceId(serviceId);
				if (tl != null) {
					job.setTimestamp(new Date(tl));
				}

				return job;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't query the target job by its id: " + jobId);
			return null;

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger
							.warn("Can't close the statement for the query by its id :"
									+ jobId);

				}
			}
		}

		return null;
	}

	public static List<MonitorRecord> getMonitorRecordByJobId(String jobId) {

		logger.info("Query the monitor record for the job: " + jobId);

		Statement st = null;
		try {

			st = con.createStatement();
			String querySql = "select * from monitor_record where jobId = '"
					+ jobId + "'";
			ResultSet rs = st.executeQuery(querySql);

			int count = 0;
			List<MonitorRecord> records = new ArrayList<MonitorRecord>();
			while (rs.next()) {

				MonitorRecord record = new MonitorRecord();

				String nodeId = rs.getString("nodeId");
				boolean status = rs.getBoolean("nodeStatus");
				String desp = rs.getString("nodeDesp");
				boolean isError = rs.getBoolean("isError");
				Long time_long = rs.getLong("time_long");

				record.setNodeId(nodeId);
				record.setNodeStatus(status);
				record.setNodeDesp(desp);
				record.setError(isError);
				if (time_long != null) {
					record.setTimestamp(new Date(time_long));
				}

				// query the parameter list for the node
				List<Parameter> ps = queryParameters(jobId, nodeId);
				record.setPs(ps);

				count++;
				records.add(record);

			}
			logger.info("Get " + records.size() + "records for monitor!");
			return records;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't query the target job monitor record by its id: "
					+ jobId);
			return null;
		}

	}

	/**
	 * 
	 * @param jobId
	 * @param nodeId
	 * @return
	 */
	private static List<Parameter> queryParameters(String jobId, String nodeId) {

		logger.info("Query the job monitor record for the job( " + jobId
				+ " ) and the node( " + nodeId + " )");

		Statement st = null;
		try {

			st = con.createStatement();

			String querySql = "select * from monitor_parameter where jobId='"
					+ jobId + "' and nodeId='" + nodeId + "'";

			ResultSet rs = st.executeQuery(querySql);
			if (rs == null) {
				logger.warn("Can't query the monitor record for the job( "
						+ jobId + " ) and the node( " + nodeId + " )");
				return null;
			}

			List<Parameter> ps = new ArrayList<Parameter>();
			while (rs.next()) {
				// get the parameter message
				Parameter p = new Parameter();
				String paraName = rs.getString("parameterName");
				String paraValue = rs.getString("parameterValue");
				String paraType = rs.getString("parameterType");

				p.setParameterType(paraType);
				p.setParameterName(paraName);
				p.setParameterValue(paraValue);

				ps.add(p);

			}

			return ps;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info("Can's query parameters : " + e.getMessage());
		}

		return null;
	}

	// public MonitorRecord getLatestRecordByJobId(String jobId){
	//		
	// }

	/**
	 * if no finished, return null
	 * 
	 * @param jobId
	 * @return
	 */
	public static ResultRecord getResultRecordByJobId(String jobId) {
		logger.info("Query the execute result for the job: " + jobId);

		Statement st = null;
		try {

			st = con.createStatement();
			String querySql = "select * from execute_result where jobId = '"
					+ jobId + "'";
			ResultSet rs = st.executeQuery(querySql);

			int count = 0;

			while (rs.next()) {

				ResultRecord record = new ResultRecord();

				boolean status = rs.getBoolean("isSuccessful");
				String desp = rs.getString("desp");

				Long time_long = rs.getLong("time_long");

				record.setSuccessful(status);
				record.setDesp(desp);

				if (time_long != null) {
					record.setTimestamp(new Date(time_long));
				}

				// query the parameter list for the node
				List<Parameter> ps = getResultParameters(jobId);
				record.setPs(ps);

				count++;

				return record;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.warn("Can't query the target job monitor record by its id: "
					+ jobId, e);

		}
		return null;
	}

	public static List<Parameter> getResultParameters(String jobId) {
		logger
				.info("Query the job execute result for the job( " + jobId
						+ " )");

		Statement st = null;
		try {

			st = con.createStatement();

			String querySql = "select * from result_parameter where jobId='"
					+ jobId + "'";

			ResultSet rs = st.executeQuery(querySql);
			if (rs == null) {
				logger.warn("Can't query the monitor record for the job( "
						+ jobId + " )");
				return null;
			}

			List<Parameter> ps = new ArrayList<Parameter>();
			while (rs.next()) {
				// get the parameter message
				Parameter p = new Parameter();
				String paraName = rs.getString("parameterName");
				String paraValue = rs.getString("parameterValue");
				String paraType = rs.getString("parameterType");

				p.setParameterType(paraType);
				p.setParameterName(paraName);
				p.setParameterValue(paraValue);

				ps.add(p);
			}

			return ps;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.info("Can's query execute result parameters : "
					+ e.getMessage());
		}

		return null;
	}
}
