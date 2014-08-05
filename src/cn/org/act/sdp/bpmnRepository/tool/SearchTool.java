package cn.org.act.sdp.bpmnRepository.tool;

import java.util.ArrayList;
import java.util.Iterator;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.GraphModelBean;
import cn.org.act.sdp.bpmnRepository.search.algorithms.ExactMatch;
import cn.org.act.sdp.bpmnRepository.search.algorithms.InexactMatch;
import cn.org.act.sdp.bpmnRepository.search.model.Correlativity;
import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;
import cn.org.act.sdp.bpmnRepository.session.MetaSession;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;

public class SearchTool {
	private float threshold = 0;
	private MetaSession metaSession = null;

	/**
	 * @return
	 * @throws Exception
	 */
	private MetaSession metaSessionInit() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			metaSession = (MetaSession) (SessionFactory
					.openSession(SessionType.Meta));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return metaSession;

	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	/**
	 * ������Ƭ�ν��в��ҵĽӿ�
	 * 
	 * @param queryURL
	 * @return
	 */
	// public ArrayList<BpmnBean> searchBySegment(String queryURL){
	// GraphModelTool tool=new GraphModelTool();
	// Correlativity corr=tool.getByCorrID(5).getCorr();
	// InexactMatch match=new InexactMatch(corr);
	// BPMNReader reader=new BPMNReader();
	// try {
	// reader.readGraph(new FileInputStream(new File(queryURL)));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// ArrayList<GraphModelBean> models=tool.getAllModel();
	// ArrayList<GraphModelBean> result=new ArrayList<GraphModelBean>();
	// for (Iterator iter = models.iterator(); iter.hasNext();) {
	// GraphModelBean element = (GraphModelBean) iter.next();
	// if(match.match(element.getModelStream(), null)>threshold)
	// result.add(element);
	// }
	// return getResult(result);
	// }
	/**
	 * ����ʹ��ԭʼ��sql��䣬����description�ֶν���sql��ѯ
	 * 
	 * @param key
	 * @return
	 */
	// public ArrayList<BpmnBean> searchByKeyWord(String key) {
	// // ArrayList<> models=tool.getAllModel();
	// metaSessionInit();
	// return metaSession.getBpmnByKey(key);
	// }
	// public ArrayList<BpmnBean> searchByHierarchical(String query){
	// return null;
	// }
	// private ArrayList<BpmnBean> getResult(ArrayList<GraphModelBean> models){
	// BpmnTool tool=new BpmnTool();
	// ArrayList<BpmnBean> result=new ArrayList<BpmnBean>();
	// for(GraphModelBean bean: models){
	// result.add(tool.getBpmnByID(String.valueOf(bean.getBpmnID())));
	// }
	// return result;
	// }
	/**
	 * ���������Ϊ��ϲ��Խ���
	 * 
	 * @param query
	 * @return
	 */
	public ArrayList<GraphModelBean> searchBySegmentInexact(DLGraph query) {
		GraphModelTool tool = new GraphModelTool();
		Correlativity corr = tool.getByCorrID(5).getCorr();
		InexactMatch match = new InexactMatch(corr);
		// BPMNReader reader=new BPMNReader();
		// try {
		// reader.readGraph(new FileInputStream(new File(queryURL)));
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		ArrayList<GraphModelBean> models = tool.getAllModel();
		ArrayList<GraphModelBean> result = new ArrayList<GraphModelBean>();
		for (Iterator iter = models.iterator(); iter.hasNext();) {
			GraphModelBean element = (GraphModelBean) iter.next();
			float ma = match.match(element.getModelStream(), query);
			if (ma > threshold) {
				System.out.println(ma + "    "
						+ element.getModelStream().getName());
				result.add(element);
			}
		}
		// return getResult(result);
		return result;
	}

	/**
	 * ���������Ϊ��ϲ��Խ���
	 * 
	 * @param query
	 * @return
	 */
	public ArrayList<GraphModelBean> searchBySegmentExact(DLGraph query) {
		GraphModelTool tool = new GraphModelTool();
		Correlativity corr = tool.getByCorrID(5).getCorr();
		ExactMatch match = new ExactMatch();
		// BPMNReader reader=new BPMNReader();
		// try {
		// reader.readGraph(new FileInputStream(new File(queryURL)));
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		ArrayList<GraphModelBean> models = tool.getAllModel();
		ArrayList<GraphModelBean> result = new ArrayList<GraphModelBean>();
		for (Iterator iter = models.iterator(); iter.hasNext();) {
			GraphModelBean element = (GraphModelBean) iter.next();
			float ma = match.match(element.getModelStream(), query);
			if (ma > threshold) {
				System.out.println(ma + "    "
						+ element.getModelStream().getName());
				result.add(element);
			}
		}
		// return getResult(result);
		return result;
		// return null;
	}
}
