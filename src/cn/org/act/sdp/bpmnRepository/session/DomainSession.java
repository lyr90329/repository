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

public class DomainSession extends DBSession {
	private final String TABLE = "pr_domain";
	private final String TABLEDOC = TABLE + ".";
	public int errorCode;

	/**
	 * return id, if failed return 0;
	 */
	public int save(Object o) {
		DomainBean bean = (DomainBean) o;

		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.DOMAIN_TABLE,
				Constants.SAVE_OPERATION);
		try {
			conn.setAutoCommit(false);
			Statement sta = conn.createStatement();
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getDescription());
			ps.setTimestamp(2, new Timestamp(bean.getCreatedTime().getTime()));
			ps.setTimestamp(3, new Timestamp(bean.getCreatedTime().getTime()));
			ps.setString(4, bean.getName());
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
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
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
	private ArrayList<DomainBean> getListByResultSet(ResultSet rs) {
		ArrayList<DomainBean> list = new ArrayList<DomainBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				DomainBean bean = new DomainBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setDescription(rs.getString(TABLEDOC + "description"));
				bean.setCreatedTime(rs.getTimestamp(TABLEDOC + "createdTime"));
				bean
						.setModifiedTime(rs.getTimestamp(TABLEDOC
								+ "modifiedTime"));
				bean.setName(rs.getString(TABLEDOC + "name"));
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
	public ArrayList<DomainBean> getAll() {
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.DOMAIN_TABLE,
				Constants.GETALL_OPERATION);
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

	public boolean update(Object o) {
		DomainBean bean = (DomainBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.DOMAIN_TABLE,
				Constants.UPDATE_OPERATION);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getDescription());
			ps.setTimestamp(2, new Timestamp(bean.getCreatedTime().getTime()));
			ps.setTimestamp(3, new Timestamp(bean.getModifiedTime().getTime()));
			ps.setString(4, bean.getName());
			ps.setInt(5, bean.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}

		return true;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public DomainBean getById(Integer id) {
		List<DomainBean> list = getByProperty("id", id);
		if (list == null || list.size() < 1)
			return null;
		else
			return (DomainBean) list.get(0);
	}

	public List<DomainBean> getByProperty(String property, Object value) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(property);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GET_BY_PROPERTY_OPERATION),
				paras);
		List<DomainBean> list;
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

	public boolean remove(int id) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add("id");
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(
				manager.getSqlStatement(Constants.COMMON_TABLE,
						Constants.REMOVE_BY_PROPERTY_OPERATION), paras);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				cmd.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean remove(DomainBean domain) {
		return remove(domain.getId());
	}
}
