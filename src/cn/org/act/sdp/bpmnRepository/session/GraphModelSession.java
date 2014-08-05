package cn.org.act.sdp.bpmnRepository.session;

import java.io.ObjectInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import cn.org.act.sdp.bpmnRepository.config.Constants;
import cn.org.act.sdp.bpmnRepository.config.SqlHelper;
import cn.org.act.sdp.bpmnRepository.config.SqlStatementManager;
import cn.org.act.sdp.bpmnRepository.entity.CorrelativityBean;
import cn.org.act.sdp.bpmnRepository.entity.GraphModelBean;
import cn.org.act.sdp.bpmnRepository.search.model.Correlativity;
import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class GraphModelSession extends DBSession {
	@Override
	/**
	 * ����һ��bpmn�ļ���ͼģ��
	 */
	public int save(Object o) {
		// return super.save(o);
		GraphModelBean bean = (GraphModelBean) o;
		ArrayList<String> paras = new ArrayList<String>();
		// paras.add(Long.toString(bean.getID()));
		paras.add(Integer.toString(bean.getBpmnID()));
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.GRAPH_TABLE, Constants.SAVE_OPERATION), paras);
		System.out.println(cmdText);
		try {
			// cmd = conn.createStatement();
			cmd = conn.prepareStatement(cmdText);
			((PreparedStatement) cmd).setObject(1, bean.getModelStream());
			if (((PreparedStatement) cmd).executeUpdate() == 0) {
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
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}

		return 0;
	}

	/**
	 * ��bpmnIDΪ���룬�õ���Ӧ���Ѿ������õ�ģ��,ΪDLGraph����
	 * 
	 * @param bpmnid
	 * @return
	 */
	public DLGraph getByBPMNID(String bpmnid) {
		DLGraph graph = null;
		ArrayList<String> paras = new ArrayList<String>();
		// paras.add(Long.toString(bean.getID()));
		paras.add(bpmnid);
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.GRAPH_TABLE, Constants.GET_BY_ID_OPERATION), paras);
		System.out.println(cmdText);
		try {
			cmd = conn.createStatement();
			result = cmd.executeQuery(cmdText);
			while (result.next()) {// �����ϣ�����ʵ���ϣ�ģ�ͺ�������һ��һ�Ĺ�ϵ
				byte[] o = (byte[]) result.getObject("Model");
				// result.getObject(arg0, arg1)
				ObjectInputStream ois = new ObjectInputStream(
						new ByteInputStream(o, o.length));
				graph = (DLGraph) ois.readObject();
				ois.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			return graph;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			return graph;
		} finally {
			try {
				cmd.close();
				result.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return graph;
	}

	public ArrayList<GraphModelBean> getAllModel() {
		DLGraph graph = null;
		GraphModelBean bean = null;
		ArrayList<GraphModelBean> graphs = new ArrayList<GraphModelBean>();
		ArrayList<String> paras = new ArrayList<String>();
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.GRAPH_TABLE, Constants.GETALL_OPERATION), paras);
		System.out.println(cmdText);
		try {
			cmd = conn.createStatement();
			result = cmd.executeQuery(cmdText);
			while (result.next()) {// �����ϣ�����ʵ���ϣ�ģ�ͺ�������һ��һ�Ĺ�ϵ
				bean = new GraphModelBean();
				byte[] o = (byte[]) result.getObject("Model");
				// result.getObject(arg0, arg1)
				ObjectInputStream ois = new ObjectInputStream(
						new ByteInputStream(o, o.length));
				graph = (DLGraph) ois.readObject();
				ois.close();
				bean.setBpmnID(result.getInt("BPMNID"));
				bean.setModelStream(graph);
				graphs.add(bean);
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			return graphs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			return graphs;
		} finally {
			try {
				cmd.close();
				result.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return graphs;
	}

	public void saveCorrela(Correlativity corr) {
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.CORRELATIVITY, Constants.SAVE_OPERATION), null);
		System.out.println(cmdText);
		try {
			// cmd = conn.createStatement();
			cmd = conn.prepareStatement(cmdText);
			((PreparedStatement) cmd).setObject(1, corr.getC());
			// ((PreparedStatement) cmd).setObject(1, corr, Types.BLOB);
			if (((PreparedStatement) cmd).executeUpdate() == 0) {
				return;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			// return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			// return false;
		} finally {
			try {
				cmd.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
	}

	public CorrelativityBean getbyCorrID(int id) {
		CorrelativityBean corr = new CorrelativityBean();
		corr.setId(id);
		ArrayList<String> paras = new ArrayList<String>();
		// paras.add(Long.toString(bean.getID()));
		paras.add(String.valueOf(id));
		SqlStatementManager manager = SqlStatementManager.singleInstance();

		String cmdText = SqlHelper.getFinalSql(manager.getSqlStatement(
				Constants.CORRELATIVITY, Constants.GET_BY_ID_OPERATION), paras);
		System.out.println(cmdText);
		try {
			cmd = conn.createStatement();
			result = cmd.executeQuery(cmdText);
			while (result.next()) {// �����ϣ�����ʵ���ϣ�ģ�ͺ�������һ��һ�Ĺ�ϵ
				byte[] o = (byte[]) result.getObject("Model");
				// result.getObject(arg0, arg1)
				ObjectInputStream ois = new ObjectInputStream(
						new ByteInputStream(o, o.length));
				HashMap<String, Float> c = (HashMap<String, Float>) ois
						.readObject();
				corr.getCorr().setC(c);
				ois.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			sqle.printStackTrace(System.err);
			return corr;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(System.err);
			return corr;
		} finally {
			try {
				cmd.close();
				result.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace(System.out);
			}
		}
		return corr;
	}

}
