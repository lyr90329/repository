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
import cn.org.act.sdp.bpmnRepository.entity.AnnotationBean;

public class AnnotationSession extends DBSession {
	private final String TABLE = "pr_annotation";
	private final String TABLEDOC = TABLE + ".";
	public int errorCode;

	/**
	 * return id, if failed return 0;
	 */
	public int save(Object o) {
		AnnotationBean bean = (AnnotationBean) o;

		SqlStatementManager manager = SqlStatementManager.singleInstance();
		// get the sql from config file
		String cmdText = manager.getSqlStatement(Constants.ANNOTATION_TABLE,
				Constants.SAVE_OPERATION);
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setString(1, bean.getMsg());
			// ps.setInt(2, bean.getUserId());
			ps.setInt(2, 4);
			ps.setTimestamp(3, new Timestamp(bean.getTime().getTime()));
			ps.setInt(4, bean.getBpmnId());
			ps.setInt(5, bean.getElementId());
			if (ps.execute()) {
				ResultSet rs = ps.getResultSet();
				if (rs.next()) {
					return rs.getInt(1);
				} else
					return 0;
			} else {
				return 0;
			}
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
	private ArrayList<AnnotationBean> getListByResultSet(ResultSet rs) {
		ArrayList<AnnotationBean> list = new ArrayList<AnnotationBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				AnnotationBean bean = new AnnotationBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setUserId(rs.getInt(TABLEDOC + "userId"));
				bean.setTime(rs.getTimestamp(TABLEDOC + "time"));
				bean.setMsg(rs.getString(TABLEDOC + "msg"));
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

	private ArrayList<AnnotationBean> getBpmnAndElementAnnotationListByResultSet(
			ResultSet rs) {
		ArrayList<AnnotationBean> list = new ArrayList<AnnotationBean>();
		try {
			if (!rs.next()) {
				return null;
			}
			do {
				AnnotationBean bean = new AnnotationBean();
				bean.setId(rs.getInt(TABLEDOC + "id"));
				bean.setUserId(rs.getInt(TABLEDOC + "userId"));
				bean.setTime(rs.getTimestamp(TABLEDOC + "time"));
				bean.setMsg(rs.getString(TABLEDOC + "msg"));
				bean.setBpmnId(rs.getInt("pr_r_bpmn_element_annotation."
						+ "bpmnId"));
				bean.setElementId(rs.getInt("pr_r_bpmn_element_annotation."
						+ "elementId"));
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
	public ArrayList<AnnotationBean> getAll() {
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
	public AnnotationBean getById(Integer id) {
		List<AnnotationBean> list = getByProperty("id", id);
		if (list == null || list.size() < 1)
			return null;
		else
			return (AnnotationBean) list.get(0);
	}

	public List<AnnotationBean> getByProperty(String property, Object value) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		paras.add(TABLE);
		paras.add(property);
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.COMMON_TABLE, Constants.GET_BY_PROPERTY_OPERATION),
				paras);
		List<AnnotationBean> list;
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

	/**
	 * 
	 * @param bpmnId
	 * @param elementId
	 *            if no element id, then set elementId=0
	 * @return
	 */
	public List<AnnotationBean> getByBpmnIdAndElementId(int bpmnId,
			int elementId) {
		ArrayList<String> paras = new ArrayList<String>();
		// set table name and property name
		SqlStatementManager manager = SqlStatementManager.singleInstance();
		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.ANNOTATION_TABLE,
				Constants.GET_BPMN_ID_AND_ELEMENT_ID_OPERATION), paras);
		List<AnnotationBean> list;
		try {
			cmd = conn.prepareStatement(cmdText);
			PreparedStatement ps = (PreparedStatement) cmd;
			ps.setInt(1, bpmnId);
			ps.setInt(2, elementId);
			result = ps.executeQuery();
			list = this.getBpmnAndElementAnnotationListByResultSet(result);
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
