package repository.AppStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public abstract class DBconnect {
	private final static String CONFIG_FILE_PATH = "db.dbconfig.properties";
	private final static String CONFIG_FILENAME = "db.dbconfig.properties";
	private final static String CONFIG_DRIVERCLASS = "driverClass";
	private final static String CONFIG_USER = "user";
	private final static String CONFIG_PASSWORD = "password";
	private final static String CONFIG_URL = "url";

	private Connection conn = null;
	private ResultSet resultSet = null;
	private int updateCount;

	/**
	 * 获取数据库连�?
	 */
	public DBconnect() {
		try {
			conn = this.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �?启事�?
	 */
	public void beginTransaction() {
		if (conn != null) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 提交事务
	 */
	public void commitTransaction() {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭连接
	 */
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * execute any statement
	 * 
	 * @param sql
	 * @return true if the first result is a ResultSet object; false if it is an
	 *         update count or there are no results
	 */
	public boolean execute(String sql) {
		try {
			Statement sta = conn.createStatement();
			boolean bool = sta.execute(sql);
			if (bool) {
				setResultSet(sta.getResultSet());
			} else {
				setUpdateCount(sta.getUpdateCount());
			}
			return bool;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 执行�?个不带参数的sql,返回RS
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql) {
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 执行更新的sql语句
	 * 
	 * @param sql
	 * @return 0：失�? -1：抛出异�? 其他正数：执行的行号
	 */
	public int executeUpdate(String sql) {

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 查询出所有记�?
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findAll(String sql) {
		ResultSet rs = executeQuery(sql);
		List list = this.getListByResultSet(rs);
		return list;
	}

	/**
	 * 按照id查出唯一记录
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object findById(String sql) {
		ResultSet rs = executeQuery(sql);
		List list = this.getListByResultSet(rs);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 建立数据库连�?
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() {

		Properties p = new Properties();
		InputStream ips = null;
		try {
			// String path =
			// Thread.currentThread().getContextClassLoader().getResource(
			// CONFIG_FILENAME).toString();
			String path = this.getClass().getResource("dbconfig.properties")
					.getFile();
			ips = new FileInputStream(path);
			p.load(ips);
			String driverClass = p.getProperty(CONFIG_DRIVERCLASS);
			String user = p.getProperty(CONFIG_USER);
			String url = p.getProperty(CONFIG_URL);
			String password = p.getProperty(CONFIG_PASSWORD);
			ips.close();
			Class.forName(driverClass);
			DriverManager.setLoginTimeout(3000);
			return DriverManager.getConnection(url, user, password);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get the result in form of List from ResultSet
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getList() {
		return getListByResultSet(resultSet);
	}

	/**
	 * 从记录集中解析出对象的List
	 * 
	 * @param rs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected abstract List getListByResultSet(ResultSet rs);

	/**
	 * 获得�?个PreparedStatement对象，以便插入IN参数
	 * 
	 * @param sql
	 * @return
	 */
	public PreparedStatement getPreparedStatement(String sql) {

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			return ps;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public int getUpdateCount() {
		return updateCount;
	}

	/**
	 * 回滚事务
	 */
	public void rollbackTransaction() {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保存�?个对�?
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 *             0：失�? -1：抛出异�? 其他正数：执行的行号
	 */
	public int save(String sql, Object obj) throws SQLException {
		try {
			PreparedStatement ps = getPreparedStatement(sql);
			this.setSavePreparedStatement(ps, obj);
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	/**
	 * 按语句把对象中的信息插入IN参数
	 * 
	 * @param ps
	 * @param obj
	 * @throws SQLException
	 */
	protected abstract void setSavePreparedStatement(PreparedStatement ps,
			Object obj) throws SQLException;

	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
}
