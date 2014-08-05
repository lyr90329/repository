package cn.org.act.sdp.bpmnRepository.session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DBSession {
	/**
	 * close the connection to the datebase
	 */

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	public int save(Object o) {
		return 0;
	}

	public boolean delete(Object o) {
		return false;
	}

	public boolean clear(long jobId) {
		return false;
	}

	public Object get(Object[] parameters) {
		return null;
	};

	protected Connection conn = null;

	protected Statement cmd = null;

	protected ResultSet result = null;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
