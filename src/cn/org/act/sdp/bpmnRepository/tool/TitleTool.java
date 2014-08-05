package cn.org.act.sdp.bpmnRepository.tool;

import java.util.List;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.TitleBean;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;
import cn.org.act.sdp.bpmnRepository.session.TitleSession;

public class TitleTool {
	private TitleSession session = null;
	public Integer errorCode;

	/**
	 * @return
	 * @throws Exception
	 */
	private TitleSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (TitleSession) (SessionFactory
					.openSession(SessionType.Title));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public int save(Object o) {
		init();
		int id = session.save(o);
		session.close();

		return id;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public TitleBean getById(int id) {
		init();
		TitleBean bean = session.getById(id);
		session.close();
		return bean;
	}

	public boolean update(Object o) {
		init();
		boolean b = session.update(o);
		session.close();
		return b;
	}

	public boolean updateVersions(Object o) {
		init();
		boolean b = session.updateVersions(o);
		session.close();
		return b;
	}

	public List<TitleBean> getAll() {
		init();
		List<TitleBean> list = session.getAll();
		session.close();
		return list;
	}

	public List<TitleBean> getByDomainId(int domainId) {
		init();
		List<TitleBean> list = session.getByDomainId(domainId);
		session.close();
		return list;
	}

	public boolean remove(int id) {
		init();
		boolean b = session.remove(id);
		session.close();
		return b;
	}

	public boolean remove(TitleBean title) {
		return this.remove(title.getId());
	}

	public boolean updateDescription(TitleBean title) {
		init();
		boolean b = session.updateProperty("id", String.valueOf(title.getId()),
				"description", title.getDescription());
		session.close();
		return b;
	}

	public List<TitleBean> search(String keys) {
		init();
		List<TitleBean> list = session.search(keys);
		session.close();
		return list;
	}

	public List<TitleBean> getByTagId(int tagId) {
		init();
		List<TitleBean> list = session.getByTagId(tagId);
		session.close();
		return list;
	}
}
