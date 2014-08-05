package cn.org.act.sdp.bpmnRepository.tool;

import java.util.ArrayList;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.CorrelativityBean;
import cn.org.act.sdp.bpmnRepository.entity.GraphModelBean;
import cn.org.act.sdp.bpmnRepository.search.model.Correlativity;
import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;
import cn.org.act.sdp.bpmnRepository.session.GraphModelSession;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;

public class GraphModelTool {
	private GraphModelSession session = null;

	/**
	 * @return
	 * @throws Exception
	 */
	private GraphModelSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (GraphModelSession) (SessionFactory
					.openSession(SessionType.Graph));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return session;

	}

	public void save(int BPMNID, DLGraph model) {
		GraphModelBean bean = new GraphModelBean();
		bean.setBpmnID(BPMNID);
		bean.setModelStream(model);
		init();
		session.save(bean);
	}

	public DLGraph getByBPMNID(String BPMNID) {
		init();
		return session.getByBPMNID(BPMNID);
	}

	public ArrayList<GraphModelBean> getAllModel() {
		init();
		return session.getAllModel();
	}

	/**
	 * �����ϵ�����ڼ��㣬ʵ��ֻ��һ��hasmap
	 * 
	 * @param corr
	 */
	public void save(Correlativity corr) {
		init();
		session.saveCorrela(corr);
	}

	/**
	 * ���id���һ����ϵ��
	 * 
	 * @param id
	 * @return
	 */
	public CorrelativityBean getByCorrID(int id) {
		init();
		return session.getbyCorrID(id);
	}

}
