package cn.org.act.sdp.bpmnRepository.session;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.org.act.sdp.bpmnRepository.config.Constants;
import cn.org.act.sdp.bpmnRepository.config.SqlHelper;
import cn.org.act.sdp.bpmnRepository.config.SqlStatementManager;
import cn.org.act.sdp.bpmnRepository.constants.FileType;
import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.entity.MetaBean;

public class BpmnSession extends DBSession {
	private final String TABLE = "pr_bpmn";
	private final String TABLEDOC = TABLE + ".";

	/**
	 * save with id
	 */
	public int save(Object o) {
		BpmnBean bean = (BpmnBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = manager.getSqlStatement(Constants.BPMN_TABLE,
				Constants.SAVE_OPERATION);
		try {
			conn.setAutoCommit(false);
			Statement sta = conn.createStatement();
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getBpmnLocation());
			ps.setString(2, bean.getDiagramLocation());
			ps.setString(3, bean.getFlexLocation());
			if (ps.executeUpdate() == 0) {
				return bean.getId();
			}
			sta.executeQuery("select LAST_INSERT_ID()"); // get the last
			conn.commit();
			ResultSet rs = sta.getResultSet();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
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
				conn.setAutoCommit(true);
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
	}

	public boolean update(Object o) {
		BpmnBean bean = (BpmnBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = manager.getSqlStatement(Constants.BPMN_TABLE,
				Constants.UPDATE_OPERATION);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getBpmnLocation());
			ps.setString(2, bean.getDiagramLocation());
			ps.setString(3, bean.getFlexLocation());
			ps.setInt(4, bean.getId());
			if (ps.executeUpdate() == 0) {
				return false;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
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
	 * @param o
	 * @param type
	 *            "bpmn","diagram","flex"
	 * @return
	 */
	public boolean updateContent(Object o, String type) {
		if (o == null) {
			return false;
		}
		String sizeColumnName = this.getSizeColumnName(type);
		String typeColumnName = this.getTypeColumnName(type);
		if (sizeColumnName == null || typeColumnName == null) {
			return false;
		}
		ArrayList<String> paras = new ArrayList<String>();
		paras.add(sizeColumnName);
		paras.add(typeColumnName);
		BpmnBean bean = (BpmnBean) o;
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.BPMN_TABLE, Constants.UPDATE_CONTENT_OPERATION),
				paras);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			if (type.equals(FileType.BPMN)) {
				System.out.println("BPMN");
				ps.setLong(1, bean.getBpmnSize());
				ps.setBinaryStream(2, bean.getBpmnStream(), (int) bean
						.getBpmnSize());
			} else if (type.equals(FileType.DIAGRAM)) {
				System.out.println("DIAGRAM");
				ps.setLong(1, bean.getDiagramSize());
				ps.setBinaryStream(2, bean.getDiagramStream(), (int) bean
						.getDiagramSize());
			} else if (type.equals(FileType.FLEX)) {
				System.out.println("FLEX");
				ps.setLong(1, bean.getFlexSize());
				ps.setBinaryStream(2, bean.getFlexStream(), (int) bean
						.getFlexSize());
			} else if (type.equals(FileType.FLEXIMG)) {
				System.out.println("FLEXIMG");
				System.out.println("FLEXIMG STREAM:");

				ps.setLong(1, bean.getFlexImgSize());
				ps.setBinaryStream(2, bean.getFlexImgStream(), (int) bean
						.getFlexImgSize());
			}
			ps.setInt(3, bean.getId());
			if (ps.executeUpdate() == 0) {
				return false;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
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

	public ArrayList<BpmnBean> getByName(String name) {
		ArrayList<String> paras = new ArrayList<String>();
		ArrayList<BpmnBean> bpmns = new ArrayList<BpmnBean>();
		paras.add(name);
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.BPMN_TABLE, Constants.GET_BY_NAME_OPERATION), paras);
		System.out.println(cmdText);
		try {
			cmd = conn.createStatement();
			result = cmd.executeQuery(cmdText);
			while (result.next()) {
				BpmnBean bpmnBean = new BpmnBean();
				bpmnBean.setId((int) result.getInt(TABLEDOC + "id"));
				bpmnBean.setBpmnLocation(result.getString(TABLEDOC
						+ "bpmnLocation"));
				bpmnBean.setDiagramLocation(result.getString(TABLEDOC
						+ "diagramLocation"));
				bpmnBean.setBPMNName(result.getString(TABLEDOC + "bpmnName"));
				bpmnBean.setDiagramName(result.getString(TABLEDOC
						+ "diagramName"));
				// bpmnBean.setAnotationID(result.getString(6));
				bpmnBean.setBpmnStream(result.getBinaryStream(TABLEDOC
						+ "bpmnContent"));
				bpmnBean.setDiagramStream(result.getBinaryStream(TABLEDOC
						+ "diagramContent"));
				bpmnBean.setFlexStream(result.getBinaryStream(TABLEDOC
						+ "flexContent"));

				bpmns.add(bpmnBean);
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			return bpmns;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			return bpmns;
		} finally {
			try {
				cmd.close();
				result.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return bpmns;
	}

	/**
	 * 
	 * 
	 * @param name
	 * @param version
	 * @return null if not existed
	 */
	// public BpmnBean getByVersion(String name, String version) {
	// BpmnBean bean = null;
	// return bean;
	// }
	public BpmnBean getByID(int id) {
		List<BpmnBean> list = getByProperty("id", new Integer(id));
		if (list == null || list.size() < 1)
			return null;
		else
			return (BpmnBean) list.get(0);
	}

	// public List<BpmnBean> getByDomainId(int id){
	// SqlStatementManager manager = SqlStatementManager.singleInstance();
	// // get the sql from config file
	// String cmdText = manager.getSqlStatement(Constants.MULTI_TABLE,
	// Constants.GET_BY_DOMAIN_ID_OPERATION);
	// try {
	// cmd = conn.prepareStatement(cmdText);
	// PreparedStatement ps = (PreparedStatement) cmd;
	// ps.setInt(1, id);
	// ps.executeQuery();
	// } catch (SQLException sqle) {
	// // this.errorCode = sqle.getErrorCode();
	// System.out.println(sqle.getMessage());
	// sqle.printStackTrace(System.err);
	// return null;
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// e.printStackTrace(System.err);
	// return null;
	// } finally {
	// try {
	// cmd.close();
	// } catch (SQLException e) {
	// System.out.println(e.getMessage());
	// e.printStackTrace(System.out);
	// }
	// }
	// }

	/**
	 * return null if rs is empty
	 * 
	 * @param rs
	 * @return
	 */
	private List<BpmnBean> getListByResultSet(ResultSet rs) {
		ArrayList<BpmnBean> list = new ArrayList<BpmnBean>();
		try {
			rs.beforeFirst();
			if (!rs.next()) {
				return null;
			}
			do {
				BpmnBean bean = new BpmnBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setBpmnLocation(rs.getString(TABLEDOC + "bpmnLocation"));
				bean.setDiagramLocation(rs.getString(TABLEDOC
						+ "diagramLocation"));
				bean.setFlexLocation(rs.getString(TABLEDOC + "flexLocation"));
				bean.setBpmnSize(rs.getLong(TABLEDOC + "bpmnSize"));
				bean.setDiagramSize(rs.getLong(TABLEDOC + "diagramSize"));
				bean.setFlexSize(rs.getLong(TABLEDOC + "flexSize"));
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

	public BpmnBean getByMeta(MetaBean meta) {
		if (meta == null)
			return null;
		List<BpmnBean> list = getByProperty("id", new Integer(meta.getId()));
		if (list == null || list.size() < 1)
			return null;
		else
			return (BpmnBean) list.get(0);
	}

	public List<BpmnBean> getByProperty(String property, Object value) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(property);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GET_BY_PROPERTY_OPERATION),
				paras);
		List<BpmnBean> list;
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

	/**
	 * get bpmn content
	 * 
	 * @param bpmn
	 * @param type
	 *            "bpmn","diagram","flex"
	 * @return
	 */
	public boolean getContentByBpmn(BpmnBean bpmn, String type) {
		if (bpmn == null) {
			return false;
		}
		String columnName = this.getTypeColumnName(type); // get the table
		// column name of
		// this type file
		if (columnName == null) {
			return false;
		}
		ArrayList<String> paras = new ArrayList<String>();
		paras.add(columnName);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.BPMN_TABLE, Constants.GET_CONTENT_OPERATION), paras);
		InputStream is = null;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, bpmn.getId());
			result = ps.executeQuery();
			if (result.next()) {
				is = result.getBinaryStream(columnName);
			} else
				return false;
		} catch (SQLException sqle) {
			sqle.printStackTrace(System.err);
			return false;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return false;
		} finally {
			try {
				cmd.close();
				result.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		if (is == null)
			return false;
		if (type.equals(FileType.BPMN)) {
			bpmn.setBpmnStream(is);
		} else if (type.equals(FileType.DIAGRAM)) {
			bpmn.setDiagramStream(is);
		} else if (type.equals(FileType.FLEX)) {
			bpmn.setFlexStream(is);
		} else if (type.equals(FileType.FLEXIMG)) {
			bpmn.setFlexImgStream(is);
		}
		return true;
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

	public boolean remove(BpmnBean bpmn) {
		return remove(bpmn.getId());
	}

	private String getTypeColumnName(String type) {
		if (type == null) {
			return null;
		} else if (type.equals(FileType.BPMN)) {
			return "bpmnContent";
		} else if (type.equals(FileType.FLEX)) {
			return "flexContent";
		} else if (type.equals(FileType.DIAGRAM)) {
			return "diagramContent";
		} else if (type.equals(FileType.FLEXIMG)) {
			return "flexImg";
		} else {
			return null;
		}
	}

	private String getSizeColumnName(String type) {
		if (type == null) {
			return null;
		} else if (type.equals(FileType.BPMN)) {
			return "bpmnSize";
		} else if (type.equals(FileType.FLEX)) {
			return "flexSize";
		} else if (type.equals(FileType.DIAGRAM)) {
			return "diagramSize";
		} else if (type.equals(FileType.FLEXIMG)) {
			return "flexImgSize";
		} else {
			return null;
		}
	}
}
