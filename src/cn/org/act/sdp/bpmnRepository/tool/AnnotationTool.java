package cn.org.act.sdp.bpmnRepository.tool;

import java.util.Date;
import java.util.List;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.AnnotationBean;
import cn.org.act.sdp.bpmnRepository.session.AnnotationSession;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;

public class AnnotationTool {
	private AnnotationSession session = null;
	public Integer errorCode;

	/**
	 * @return
	 * @throws Exception
	 */
	private AnnotationSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (AnnotationSession) (SessionFactory
					.openSession(SessionType.Annotation));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;

	}

	public int save(Object o) {
		init();
		int id = session.save(o);
		this.errorCode = session.errorCode;
		session.close();
		return id;
	}

	public List<AnnotationBean> getAll() {
		init();
		List<AnnotationBean> list = session.getAll();
		session.close();
		return list;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public AnnotationBean getById(int id) {
		init();
		AnnotationBean bean = session.getById(id);
		session.close();
		return bean;
	}

	public List<AnnotationBean> getByBpmn(int bpmnId) {
		init();
		List<AnnotationBean> list = session.getByBpmnIdAndElementId(bpmnId, 0);
		session.close();
		return list;
	}

	public List<AnnotationBean> getByElement(int bpmnId, int elementId) {
		init();
		List<AnnotationBean> list = session.getByBpmnIdAndElementId(bpmnId,
				elementId);
		session.close();
		return list;
	}
}
