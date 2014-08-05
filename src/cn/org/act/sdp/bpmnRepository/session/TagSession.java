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
import cn.org.act.sdp.bpmnRepository.entity.TagBean;

public class TagSession extends DBSession {
	private final String TABLE = "pr_tag";
	private final String TABLEDOC = TABLE + ".";
	private final String R_TABLE = "pr_r_tag_title";
	public int errorCode;

	/**
	 * return id, if failed return 0;
	 */
	public int save(Object o) {
		TagBean bean = (TagBean) o;

		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.TAG_TABLE,
				Constants.SAVE_OPERATION);
		try {
			conn.setAutoCommit(false);
			Statement sta = conn.createStatement();
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getTag());
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
				// return the existed tag id
				List<TagBean> list = this.getByProperty("tag", bean.getTag());
				if (list != null) {
					return list.get(0).getId();
				} else {
					return 0;
				}
			} else {
				System.out.println(sqle.getMessage());
				sqle.printStackTrace(System.err);
				return 0;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			return 0;
		} finally {
			try {
				conn.setAutoCommit(true);
				cmd.close();
			} catch (SQLException e) {
				// System.out.println(e.getMessage());
				// e.printStackTrace(System.out);
			}
		}
	}

	public boolean saveRelation(int tagId, int titleId) {
		String cmdText = "insert into pr_r_tag_title(tagId,titleId) values(?,?);";
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, tagId);
			ps.setInt(2, titleId);
			if (ps.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				return true;
			} else {
				e.printStackTrace();
				return false;
			}
		} finally {
			try {
				cmd.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * return null if rs is empty
	 * 
	 * @param rs
	 * @return
	 */
	private ArrayList<TagBean> getListByResultSet(ResultSet rs) {
		ArrayList<TagBean> list = new ArrayList<TagBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				TagBean bean = new TagBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setTag(rs.getString(TABLEDOC + "tag"));
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
	public ArrayList<TagBean> getAll() {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GETALL_OPERATION), paras);
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
	public TagBean getById(Integer id) {
		List<TagBean> list = getByProperty("id", id);
		if (list == null || list.size() < 1)
			return null;
		else
			return (TagBean) list.get(0);
	}

	public List<TagBean> getByProperty(String property, Object value) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(property);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GET_BY_PROPERTY_OPERATION),
				paras);
		List<TagBean> list;
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

	public boolean remove(TagBean bean) {
		return remove(bean.getId());
	}

	/**
	 * remove one tag of the title
	 * 
	 * @param tagId
	 * @param titleId
	 * @return
	 */
	public boolean removeRelation(int tagId, int titleId) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(R_TABLE);
		paras.add("tagId");
		paras.add("titleId");
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.RELATION_TABLE,
				Constants.REMOVE_BY_TWO_PROPERTY_OPERATION), paras);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, tagId);
			ps.setInt(2, titleId);
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

	/**
	 * remove tag relation of all concerned titles
	 * 
	 * @param tagId
	 * @return
	 */
	public boolean removeRelation(int tagId) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(R_TABLE);
		paras.add("tagId");
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(
				manager.getSqlStatement(Constants.COMMON_TABLE,
						Constants.REMOVE_BY_PROPERTY_OPERATION), paras);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, tagId);
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

	public ArrayList<TagBean> getByTitleId(int id) {
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = manager.getSqlStatement(Constants.TAG_TABLE,
				Constants.GET_BY_TITLE_ID_OPERATION);
		ArrayList<TagBean> list = null;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, id);
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
}