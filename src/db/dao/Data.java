package db.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import cn.org.act.sdp.bpmnRepository.tool.cloud.BpmnPublish;
import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.cleavage.session.OperationSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.entity.ServiceTBean;
import cn.org.act.sdp.repository.session.ServiceSession;

import com.mysql.jdbc.Driver;

import config.Config;

public class Data {

	private static Data data = null;

	public Data(String driverName, String userName, String userPasswd,
			String pathurl) {

		// �����ַ�

		String url = pathurl + "?user=" + userName + "&password=" + userPasswd;

		try {

			Class.forName(driverName).newInstance();

			connection = DriverManager.getConnection(url);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Connection connection = null;

	public static Data getInstance() {
		if (null == data) {
			try {
				String driverName;
				String userName;
				String userPasswd;
				String pathurl;
				Properties properties = new Properties();
				String urlPath = Data.class.getResource("dbconfig.properties")
						.getFile();
				File file = new File(urlPath);
				properties.load(new FileInputStream(file));
				userName = properties.getProperty("user", null);
				userPasswd = properties.getProperty("password", null);
				driverName = properties.getProperty("driverClass", null);
				pathurl = properties.getProperty("url", null);
				// release the properties file
				properties = null;

				data = new Data(driverName, userName, userPasswd, pathurl);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return data;

	}

	private void closeConnection() {
		if (connection != null) {
			try {

				connection.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public static void close() {

		if (data != null) {
			data.closeConnection();
			data = null;

		}
	}

	public void testConnection() {
		// ����

		String tableName = "systemuser";

		Statement statement;

		ResultSet rs;

		try {

			statement = connection.createStatement();

			String sql = "SELECT * FROM " + tableName;

			rs = statement.executeQuery(sql);

			// �����ݽ���

			ResultSetMetaData rmeta = rs.getMetaData();

			// ȷ����ݼ����������ֶ���

			int numColumns = rmeta.getColumnCount();

			System.out.println(numColumns);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long createPath(long parentid, String pathname, long type,
			String userName) // type=0���ļ��У�type=1,bpmn�ļ�
	{
		String tableName = "cloud_path";

		Statement statement = null;

		long id = 0;

		try {

			statement = connection.createStatement();

			id = (long) (Math.random() * 100000000);

			String sql = "insert into " + tableName
					+ " (id,pathname,userName,type) values(" + id + ",'"
					+ pathname + "','" + userName + "'," + type + ")";

			statement.execute(sql);

			sql = "insert into  cloud_pathrelation(id,child) values("
					+ parentid + "," + id + ")";

			statement.execute(sql);

			if (type == 1) {
				this.savaBPMN(id, userName, "", "", "");
			}
		} catch (Exception e) {
			e.printStackTrace();

			return -1;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return id;
	}

	public List getPath(long parentid, String userName) // type=0���ļ��У�type=1,bpmn�ļ�
	{
		String tableName = "cloud_pathrelation";

		Statement statement = null;

		List list = new LinkedList();

		ResultSet rs = null;

		Path path = null;
		try {

			statement = connection.createStatement();

			String sql = "select cloud_path.id,cloud_path.pathname,cloud_path.type from cloud_pathrelation,cloud_path where cloud_pathrelation.id="
					+ parentid
					+ " and cloud_path.userName='"
					+ userName
					+ "' and cloud_pathrelation.child=cloud_path.id";

			rs = statement.executeQuery(sql);

			while (rs.next()) {
				path = new Path();
				path.setId(rs.getLong("id"));
				path.setPathname(rs.getString("pathname"));
				path.setType(rs.getLong("type"));
				list.add(path);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;

	}

	public boolean savaBPMN(long id, String userName, String bpmnfile,
			String diagramfile, String diagramflex) {
		String tableName = "cloud_bpmn";

		Statement statement = null;

		ResultSet rs = null;

		long bpmnid = id;

		try {

			statement = connection.createStatement();

			String sql = "select * from cloud_bpmn where bpmnid=" + id;

			rs = statement.executeQuery(sql);

			if (!rs.next()) {
				String value = bpmnid + ",'" + userName + "','" + bpmnfile
						+ "','" + diagramfile + "','" + diagramflex + "'";

				sql = "insert into "
						+ tableName
						+ " (bpmnid,userName,bpmnfile,diagramfile,diagramflex,time,state,addition) values("
						+ value + ",'',0,''" + ")";

				statement.execute(sql);
			} else {
				sql = "update cloud_bpmn set bpmnfile='" + bpmnfile
						+ "',diagramfile='" + diagramfile + "',diagramflex='"
						+ diagramflex + "' where bpmnid=" + bpmnid;

				statement.execute(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public Bpmn getBPMN(long bpmnid) {
		String tableName = "cloud_bpmn";

		Statement statement = null;

		ResultSet rs = null;

		Bpmn bpmn = new Bpmn();

		try {

			statement = connection.createStatement();

			String sql = "select * from " + tableName + " where bpmnid="
					+ bpmnid;

			rs = statement.executeQuery(sql);

			if (!rs.next()) {
				return null;
			} else {
				bpmn.setBpmnid(bpmnid);
				bpmn.setBpmnfile(rs.getString("bpmnfile"));
				bpmn.setDiagramflex(rs.getString("diagramflex"));
				bpmn.setDiagramfile(rs.getString("diagramfile"));
				return bpmn;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public boolean removeBPMN(long bpmnid) {
		String tableName = "cloud_bpmn";

		Statement statement = null;

		ResultSet rs = null;

		try {

			statement = connection.createStatement();

			String sql = "delete from " + tableName + " where bpmnid=" + bpmnid;

			statement.execute(sql);

			sql = "delete from cloud_path where id=" + bpmnid;

			statement.execute(sql);

			sql = "delete from cloud_pathrelation where child=" + bpmnid;

			statement.execute(sql);

			sql = "select * from cloud_pathrelation where id=" + bpmnid;

			rs = statement.executeQuery(sql);

			while (rs.next()) {

				long childid = rs.getLong("child");
				boolean bool = removeBPMN(childid);
				if (!bool) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;

	}

	public String getBPMNNameById(long id) {
		String tableName = "cloud_path";

		Statement statement = null;

		ResultSet rs = null;

		long bpmnid = id;

		try {

			statement = connection.createStatement();

			String sql = "select * from cloud_path where id=" + id;

			rs = statement.executeQuery(sql);

			if (rs.next()) {
				return rs.getString("pathname");
			}

		} catch (Exception e) {
			e.printStackTrace();

			return "";
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	public String getBPMNContentById(long id) {
		String tableName = "cloud_bpmn";

		Statement statement = null;

		ResultSet rs = null;

		long bpmnid = id;

		try {

			statement = connection.createStatement();

			String sql = "select * from cloud_bpmn where bpmnid=" + id;

			rs = statement.executeQuery(sql);

			if (rs.next()) {
				return rs.getString("bpmnfile");
			}

		} catch (Exception e) {
			e.printStackTrace();

			return "";
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	public boolean publishBPMN(long id, String userName) {
		String tableName = "cloud_bpmn";

		Statement statement = null;

		ResultSet rs = null;

		long bpmnid = id;

		try {

			statement = connection.createStatement();

			String sql = "select * from cloud_bpmn where bpmnid=" + id;

			rs = statement.executeQuery(sql);

			if (rs.next()) {
				Bpmn bpmn = new Bpmn();
				bpmn.setBpmnfile(rs.getString("bpmnfile"));
				bpmn.setDiagramflex(rs.getString("diagramflex"));
				bpmn.setDiagramfile(rs.getString("diagramfile"));
				String bpmnname = this.getBPMNNameById(id);

				new BpmnPublish().publish(bpmn.getBpmnfile(), bpmn
						.getDiagramflex(), bpmn.getDiagramfile(), bpmnname,
						userName);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public boolean subscribeService(String serviceid, String servicename,
			String url, String userName) {
		String tableName = "cloud_servicetrial";

		Statement statement = null;

		ResultSet rs = null;

		try {

			statement = connection.createStatement();

			String sql = "select * from cloud_servicetrial where serviceId="
					+ serviceid + " and userName='" + userName + "'";

			rs = statement.executeQuery(sql);

			if (!rs.next()) {
				sql = "insert into "
						+ tableName
						+ " (serviceId,userName,serviceName,wsdlurl,deployStatus,runStatus) values("
						+ serviceid + ",'" + userName + "','" + servicename
						+ "','" + url + "','false','false')";
				statement.execute(sql);
			}
			// ���ص�
			// end
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public boolean validateServiceById(String id) {
		Statement statement = null;

		ResultSet rs = null;

		try {

			statement = connection.createStatement();

			String sql = "select * from cloud_servicetrial where serviceId="
					+ id;

			rs = statement.executeQuery(sql);

			if (rs.next()) {
				return true;
			}
			// ���ص�
			// end
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public boolean subscribe(long id, String userName, String bpmnname) {
		String tableName = "cloud_bpmntrial";
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			String sql = "select * from cloud_bpmntrial where bpmnId=" + id;
			rs = statement.executeQuery(sql);
			if (!rs.next()) {
				System.out.println("--->insert new bpmn into cloud_bpmntrial");
				sql = "insert into "
						+ tableName
						+ " (bpmnId,userName,bpmnName,deployStatus,runStatus) values("
						+ id + ",'" + userName + "','" + bpmnname
						+ "','false','false')";
				statement.execute(sql);
			} else // else if (exist) update
			{
				System.out.println("--->update bpmn file");
				sql = "update "
						+ tableName
						+ " set userName='"
						+ userName
						+ "',bpmnName='"
						+ bpmnname
						+ "',deployStatus='false',runStatus='false' where bpmnId= "
						+ id;
				statement.execute(sql);
			}
			String path = Config.getPath("bpmn/" + userName);
			if (!new File(path).exists())
				new File(path).mkdirs();
			String bpmnContent = this.getBPMNContentById(id);
			String bpmnName = Config.getPath("bpmn/" + userName + "/"
					+ bpmnname + ".bpmn");
			System.out.println("bpmn file name:" + bpmnName);
			System.out.println("bpmn file content:" + bpmnContent);
			File bpmnFile = new File(bpmnName);
			if (bpmnFile.exists()) {
				boolean deleted = bpmnFile.delete();
				if (deleted) {
					System.out.println("deleted old bpmn file!");
					boolean created = bpmnFile.createNewFile();
					if (created)
						System.out.println("create new bpmn file success!");
					else
						System.out.println("create new bpmn file fail!");
				}
			} else {
				System.out.println("bpmn file doesn't exist!");
				boolean created = bpmnFile.createNewFile();
				if (created)
					System.out.println("create new bpmn file success!");
				else
					System.out.println("create new bpmn file fail!");
			}
			FileWriter filewriter = new FileWriter(bpmnName);
			filewriter.write(bpmnContent);
			filewriter.flush();
			filewriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean unSubscribe(long id, String userName) {
		String tableName = "cloud_bpmntrial";
		Statement statement = null;
		long bpmnid = id;
		try {
			statement = connection.createStatement();
			String sql = "delete from cloud_bpmntrial where bpmnId=" + id
					+ " and userName='" + userName + "'";
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean removeService(long id, String userName) {
		String tableName = "cloud_servicetrial";
		Statement statement = null;
		long bpmnid = id;
		try {
			statement = connection.createStatement();
			String sql = "delete from cloud_servicetrial where serviceId=" + id
					+ " and userName='" + userName + "'";
			statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public List getOperationsbyServiceId(long id) {

		List oplist = null;
		int i;
		try {
			String[] paras = new String[2];
			OperationSession session = (OperationSession) (SessionFactory
					.openSession(SessionType.Operation));
			paras[0] = "1";
			paras[1] = "" + id;
			oplist = session.getByServiceId(paras);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return oplist;
	}

	public ServiceTBean getwebservicebyId(long id) {
		ServiceTBean stbean = null;
		try {
			String[] paras = new String[2];
			ServiceSession ss = (ServiceSession) (cn.org.act.sdp.repository.session.SessionFactory
					.openSession(cn.org.act.sdp.repository.session.SessionType.Service));
			String[] paras1 = new String[2];
			paras1[0] = "1";
			paras1[1] = Long.toString(id);
			stbean = (ServiceTBean) ss.getById(paras1);
			ss.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return stbean;
	}

	public static void main(String[] args) {
		Data data = Data.getInstance();

		// List list=data.getPath(-1, "user1");
		//
		// if(list!=null)
		// {
		// for(int i=0;i<list.size();i++)
		// {
		// Path path=(Path)list.get(i);
		// System.out.println(path.getId()+path.getPathname()+path.getType());
		// }
		// }

		// data.savaBPMN(38118554, "user1", "", "", "");
		data.publishBPMN(162635, "user1");

		// System.out.println(data.getBPMN(38118554).getBpmnfile());
	}
}
