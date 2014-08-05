package cn.org.act.sdp.bpmnRepository.tool;

import java.util.Date;
import java.util.List;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.DomainBean;
import cn.org.act.sdp.bpmnRepository.session.DomainSession;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;

public class DomainTool {
	private DomainSession session = null;
	public Integer errorCode;

	/**
	 * @return
	 * @throws Exception
	 */
	private DomainSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (DomainSession) (SessionFactory
					.openSession(SessionType.Domain));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;

	}

	public int save(String description, Date createdTime, Date modifiedTime,
			String name) {
		DomainBean bean = new DomainBean();
		bean.setDescription(description);
		bean.setCreatedTime(createdTime);
		bean.setModifiedTime(modifiedTime);
		bean.setName(name);
		return save(bean);
	}

	public int save(Object o) {
		init();
		int id = session.save(o);
		this.errorCode = session.errorCode;
		session.close();
		return id;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public DomainBean getDomainById(int id) {
		init();
		DomainBean bean = session.getById(id);
		session.close();
		return bean;
	}

	public boolean update(Object o) {
		init();
		boolean b = session.update(o);
		session.close();
		return b;
	}

	public List<DomainBean> getAll() {
		init();
		List<DomainBean> list = session.getAll();
		session.close();
		return list;
	}

	public boolean remove(int id) {
		init();
		boolean b = session.remove(id);
		session.close();
		return b;
	}

	public boolean remove(DomainBean domain) {
		init();
		boolean b = session.remove(domain);
		session.close();
		return b;
	}
}
