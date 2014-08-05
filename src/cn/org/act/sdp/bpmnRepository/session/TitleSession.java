package cn.org.act.sdp.bpmnRepository.session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.org.act.sdp.bpmnRepository.config.Constants;
import cn.org.act.sdp.bpmnRepository.config.SqlHelper;
import cn.org.act.sdp.bpmnRepository.config.SqlStatementManager;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;

public class TitleSession extends DBSession {
	private final String TABLE = "pr_title";
	private final String TABLEDOC = TABLE + ".";

	public int errorCode;

	@Override
	public int save(Object o) {
		TitleBean bean = (TitleBean) o;

		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.TITLE_TABLE,
				Constants.SAVE_OPERATION);
		try {
			conn.setAutoCommit(false);
			cmd = conn.prepareStatement(cmdText);
			Statement sta = conn.createStatement();
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getTitle());
			ps.setInt(2, bean.getVersions());
			ps.setInt(3, bean.getLatest());
			ps.setInt(4, bean.getDomainId());
			ps.setString(5, bean.getDescription());
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
	private ArrayList<TitleBean> getListByResultSet(ResultSet rs) {
		ArrayList<TitleBean> list = new ArrayList<TitleBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				TitleBean bean = new TitleBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setDomainId(rs.getInt(TABLEDOC + "domainId"));
				bean.setLatest(rs.getInt(TABLEDOC + "latest"));
				bean.setTitle(rs.getString(TABLEDOC + "title"));
				bean.setVersions(rs.getInt(TABLEDOC + "versions"));
				bean.setDescription(rs.getString(TABLEDOC + "description"));
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
	public ArrayList<TitleBean> getAll() {
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.TITLE_TABLE,
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
		TitleBean bean = (TitleBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.TITLE_TABLE,
				Constants.UPDATE_OPERATION);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getTitle());
			ps.setInt(2, bean.getVersions());
			ps.setInt(3, bean.getLatest());
			ps.setInt(4, bean.getDomainId());
			ps.setString(5, bean.getDescription());
			ps.setInt(6, bean.getId());
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

	public boolean updateProperty(String positionParaName,
			String positionParaValue, String updateParaName,
			String updateParaValue) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(updateParaName);
		paras.add(positionParaName);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = SqlHelper.getFinalSql(
				manager.getSqlStatement(Constants.COMMON_TABLE,
						Constants.UPDATE_BY_PROPERTY_OPERATION), paras);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, updateParaValue);
			ps.setString(2, positionParaValue);
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
	 * update versions and latest
	 * 
	 * @param o
	 * @return
	 */
	public boolean updateVersions(Object o) {
		TitleBean bean = (TitleBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.TITLE_TABLE,
				Constants.UPDATE_VERSIONS_OPERATION);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, bean.getVersions());
			ps.setInt(2, bean.getLatest());
			ps.setInt(3, bean.getId());
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
	public TitleBean getById(Integer id) {
		List<TitleBean> list = getByProperty("id", id);
		if (list == null || list.size() < 1)
			return null;
		else
			return (TitleBean) list.get(0);
	}

	public List<TitleBean> getByProperty(String property, Object value) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(property);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GET_BY_PROPERTY_OPERATION),
				paras);
		List<TitleBean> list;
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
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return list;
	}

	public List<TitleBean> getByDomainId(int domainId) {
		return getByProperty("domainId", new Integer(domainId));
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

	public boolean remove(TitleBean title) {
		return remove(title.getId());
	}

	public List<TitleBean> search(String keys) {
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = manager.getSqlStatement(Constants.TITLE_TABLE,
				Constants.SEARCH_OPERATION);
		List<TitleBean> list = null;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, keys);
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
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return list;
	}

	public List<TitleBean> getByTagId(int tagId) {
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = manager.getSqlStatement(Constants.TITLE_TABLE,
				Constants.GET_BY_TAG_ID_OPERATION);
		List<TitleBean> list = null;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, tagId);
			result = ps.executeQuery();
			list = this.getListByResultSet(result);
			if (list == null) {
				return null;
			} else {
				return list;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace(System.err);
			return null;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		} finally {
			try {
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
	}
}
