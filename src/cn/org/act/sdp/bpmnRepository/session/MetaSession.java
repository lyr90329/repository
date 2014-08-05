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
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;

public class MetaSession extends DBSession {
	private final String TABLE = "pr_metadata";
	private final String TABLEDOC = TABLE + ".";

	/**
	 * 保存一个BPMN文件的元数据,提供给用户的标记接口
	 */
	public int save(Object o) {
		MetaBean bean = (MetaBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = manager.getSqlStatement(Constants.META_TABLE,
				Constants.SAVE_OPERATION);
		try {
			// conn.setAutoCommit(false);
			Statement sta = conn.createStatement();
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, bean.getId());
			ps.setString(2, bean.getDescription());
			ps.setTimestamp(3, new Timestamp(bean.getCreDate().getTime()));
			ps.setTimestamp(4, new Timestamp(bean.getModDate().getTime()));
			ps.setString(5, bean.getAuthor());
			ps.setInt(6, bean.getVersion());
			ps.setInt(7, bean.getDerive());
			ps.setInt(8, bean.getTitleId());
			if (ps.executeUpdate() == 0) {
				return 0;
			}
			return bean.getId();
			// sta.executeQuery("select LAST_INSERT_ID()"); // get the last
			// insert id
			// conn.commit();
			// ResultSet rs = sta.getResultSet();
			// if (rs.next()) {
			// return rs.getInt(1);
			// } else
			// return 0;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			return 0;
		} finally {
			try {
				// conn.setAutoCommit(true);
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
	private ArrayList<MetaBean> getListByResultSet(ResultSet rs) {
		ArrayList<MetaBean> list = new ArrayList<MetaBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				MetaBean bean = new MetaBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setDescription(rs.getString(TABLEDOC + "description"));
				bean.setCreDate(rs.getTimestamp(TABLEDOC + "creationDate"));
				bean.setModDate(rs.getTimestamp(TABLEDOC + "modifyDate"));
				bean.setAuthor(rs.getString(TABLEDOC + "author"));
				bean.setVersion(rs.getInt(TABLEDOC + "version"));
				bean.setDerive(rs.getInt(TABLEDOC + "derive"));
				bean.setTitleId(rs.getInt(TABLEDOC + "titleId"));
				list.add(bean);
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public boolean update(Object o) {
		MetaBean bean = (MetaBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.META_TABLE,
				Constants.UPDATE_OPERATION);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getDescription());
			ps.setTimestamp(2, new Timestamp(bean.getCreDate().getTime()));
			ps.setTimestamp(3, new Timestamp(bean.getModDate().getTime()));
			ps.setString(4, bean.getAuthor());
			ps.setInt(5, bean.getVersion());
			ps.setInt(6, bean.getDerive());
			ps.setInt(7, bean.getTitleId());
			ps.setInt(8, bean.getId());
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
	public MetaBean getById(Integer id) {
		List<MetaBean> list = getByProperty("id", id);
		if (list == null || list.size() < 1)
			return null;
		else
			return (MetaBean) list.get(0);
	}

	public MetaBean getLatestByTitle(TitleBean title) {
		if (title == null)
			return null;
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.META_TABLE,
				Constants.GET_LATEST_BY_TITLE_ID_OPERATION);
		MetaBean bean;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, title.getId());
			ps.setInt(2, title.getLatest());
			ResultSet rs = ps.executeQuery();
			List<MetaBean> list = getListByResultSet(rs);
			if (list == null)
				return null;
			else
				bean = list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return bean;
	}

	public MetaBean getVersionByTitle(TitleBean title, int version) {
		if (title == null)
			return null;
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.META_TABLE,
				Constants.GET_VERSION_BY_TITLE_ID_OPERATION);
		MetaBean bean;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, title.getId());
			ps.setInt(2, version);
			ResultSet rs = ps.executeQuery();
			List<MetaBean> list = getListByResultSet(rs);
			if (list == null)
				return null;
			else
				bean = list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return bean;
	}

	public List<MetaBean> getByProperty(String property, Object value) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(property);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GET_BY_PROPERTY_OPERATION),
				paras);
		List<MetaBean> list;
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

	public boolean remove(MetaBean meta) {
		return remove(meta.getId());
	}

	public List<MetaBean> getAllVersionsByTitleId(int titleId) {
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.META_TABLE,
				Constants.GET_ALL_VERSIONS_BY_TITLE_ID_OPERATION);
		List<MetaBean> list = null;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, titleId);
			ResultSet rs = ps.executeQuery();
			list = getListByResultSet(rs);
			if (list == null)
				return null;
			else {
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
