package cn.org.act.sdp.bpmnRepository.session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.org.act.sdp.bpmnRepository.config.Constants;
import cn.org.act.sdp.bpmnRepository.config.SqlHelper;
import cn.org.act.sdp.bpmnRepository.config.SqlStatementManager;
import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.entity.UserBean;

public class UserSession extends DBSession {
	private final String TABLE = "user";
	private final String TABLEDOC = TABLE + ".";
	public int errorCode;

	/**
	 * return id, if failed return 0;
	 */
	public int save(Object o) {
		UserBean bean = (UserBean) o;

		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.USER_TABLE,
				Constants.SAVE_OPERATION);
		try {
			conn.setAutoCommit(false);
			Statement sta = conn.createStatement();
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPwd());
			ps.executeUpdate();
			sta.executeQuery("select LAST_INSERT_ID()"); // get the last
			// insert id
			conn.commit();
			ResultSet rs = sta.getResultSet();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return 0;
		} catch (SQLException sqle) {
			this.errorCode = sqle.getErrorCode();
			if (this.errorCode == 1062) {

			} else {
				System.out.println(sqle.getMessage());
				sqle.printStackTrace(System.err);
			}
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			return 0;
		} finally {
			try {
				conn.setAutoCommit(true);
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
	}

	/**
	 * return null if rs is empty
	 * 
	 * @param rs
	 * @return
	 */
	private ArrayList<UserBean> getListByResultSet(ResultSet rs) {
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				UserBean bean = new UserBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setName(rs.getString(TABLEDOC + "name"));
				bean.setPwd(rs.getString(TABLEDOC + "pwd"));
				list.add(bean);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public ArrayList<UserBean> getAll() {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.COMMON_TABLE,
				Constants.GETALL_OPERATION);
		cmdText = SqlHelper.getFinalSql(cmdText, paras);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ResultSet rs = ps.executeQuery();
			return getListByResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public UserBean getById(Integer id) {
		List<UserBean> list = getByProperty("id", id);
		if (list == null || list.size() < 1)
			return null;
		else
			return (UserBean) list.get(0);
	}

	public List<UserBean> getByProperty(String property, Object value) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(property);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GET_BY_PROPERTY_OPERATION),
				paras);
		List<UserBean> list;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, value.toString());
			result = ps.executeQuery();
			list = this.getListByResultSet(result);
			if (list == null)
				return null;
		} catch (SQLException sqle) {
			sqle.printStackTrace(System.err);
			return null;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		} finally {
			try {
				cmd.close();
				result.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return list;
	}

	public UserBean getByName(String name) {
		List<UserBean> list = this.getByProperty("name", name);
		if (list != null) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
