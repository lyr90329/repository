package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import repository.service.TagServiceService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.TagServiceTBean;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.cleavage.session.TagServiceSession;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class TagServiceServiceImpl implements TagServiceService {

	@Override
	public List<TagServiceTBean> getByServiceId(Long serviceId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagServiceSession session = null;

		List<TagServiceTBean> beans = new LinkedList<TagServiceTBean>();
		try {
			session = (TagServiceSession) SessionFactory
					.openSession(SessionType.TagService);
			beans = session.getByServiceId(serviceId);
			return beans;
		} catch (Exception e) {
			e.printStackTrace();
			return beans;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<TagServiceTBean> getByTagName(String tagName) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagServiceSession session = null;

		List<TagServiceTBean> beans = new LinkedList<TagServiceTBean>();
		try {
			session = (TagServiceSession) SessionFactory
					.openSession(SessionType.TagService);
			beans = session.getByTagName(tagName);
			return beans;
		} catch (Exception e) {
			e.printStackTrace();
			return beans;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean isExist(String tagName, long serviceId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagServiceSession session = null;

		TagServiceTBean bean = null;
		try {
			session = (TagServiceSession) SessionFactory
					.openSession(SessionType.TagService);
			bean = (TagServiceTBean) session.get(tagName, serviceId);
			if (bean == null)
				return false;
			else
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean save(String tagName, Long serviceId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagServiceSession session = null;

		TagServiceTBean bean = new TagServiceTBean();
		bean.setTagName(tagName);
		bean.setServiceId(serviceId);
		try {
			session = (TagServiceSession) SessionFactory
					.openSession(SessionType.TagService);
			return session.save(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
