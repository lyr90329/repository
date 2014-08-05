package cn.org.act.sdp.bpmnRepository.tool;

import java.util.ArrayList;
import java.util.List;

import cn.org.act.sdp.bpmnRepository.crust.impl.RepositoryConf;
import cn.org.act.sdp.bpmnRepository.entity.TagBean;
import cn.org.act.sdp.bpmnRepository.session.SessionFactory;
import cn.org.act.sdp.bpmnRepository.session.SessionType;
import cn.org.act.sdp.bpmnRepository.session.TagSession;

public class TagTool {
	private TagSession session = null;
	public Integer errorCode;

	/**
	 * @return
	 * @throws Exception
	 */
	private TagSession init() {

		RepositoryConf.poolInit();
		RepositoryConf.managerInit();

		try {
			session = (TagSession) (SessionFactory.openSession(SessionType.Tag));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;

	}

	public int save(String tag, int titleId) {
		TagBean bean = new TagBean();
		bean.setTag(tag);
		return save(bean, titleId);
	}

	public int save(Object o, int titleId) {
		init();
		int id = session.save(o);
		this.errorCode = session.errorCode;
		session.saveRelation(id, titleId);
		session.close();
		return id;
	}

	/**
	 * 
	 * @return return null if empty or error
	 */
	public TagBean getTagById(int id) {
		init();
		TagBean bean = session.getById(id);
		session.close();
		return bean;
	}

	public List<TagBean> getAll() {
		init();
		List<TagBean> list = session.getAll();
		session.close();
		return list;
	}

	public boolean remove(int id) {
		init();
		boolean b = session.remove(id);
		b &= session.removeRelation(id);
		session.close();
		return b;
	}

	public boolean remove(TagBean tag) {
		init();
		boolean b = session.remove(tag);
		b &= session.removeRelation(tag.getId());
		session.close();
		return b;
	}

	public ArrayList<TagBean> getByTitleId(int id) {
		init();
		ArrayList<TagBean> list = session.getByTitleId(id);
		session.close();
		return list;
	}

	public TagBean getByString(String tag) {
		init();
		List<TagBean> list = session.getByProperty("tag", tag);
		TagBean bean;
		if (list != null) {
			bean = list.get(0);
		} else {
			bean = null;
		}
		session.close();
		return bean;
	}
}