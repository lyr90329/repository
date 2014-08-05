package repository.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import repository.entity.HotTag;
import repository.service.TagService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.TagTBean;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.cleavage.session.TagSession;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class TagServiceImpl implements TagService {

	@Override
	public List<TagTBean> getAll() {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagSession session = null;

		List<TagTBean> tags = new LinkedList<TagTBean>();
		try {
			session = (TagSession) SessionFactory.openSession(SessionType.Tag);
			tags = session.getAll();
			return tags;
		} catch (Exception e) {
			e.printStackTrace();
			return tags;
		} finally {
			if (session != null)
				session.close();

		}
	}

	@Override
	public TagTBean getByName(String tagName) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagSession session = null;

		TagTBean tagBean = null;
		try {
			session = (TagSession) SessionFactory.openSession(SessionType.Tag);
			tagBean = (TagTBean) session.get(tagName);
			return tagBean;
		} catch (Exception e) {
			e.printStackTrace();
			return tagBean;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public Map<String, HotTag> getHotTags(int size) {
		if (size <= 0)
			return new HashMap(0);
		List<TagTBean> tags = getAll();
		size = size > tags.size() ? tags.size() : size;
		int divider = (size - 1) / 10 + 1;
		int caculator = 10;
		Map<String, HotTag> hotTagMap = new HashMap<String, HotTag>(size);
		HotTag ht;
		for (int i = 0; i < size; i++) {
			if (i % divider == 0)
				caculator--;
			ht = new HotTag();
			ht.setTagName(tags.get(i).getName());
			ht.setTimes(tags.get(i).getTimes());
			ht.setTitle(tags.get(i).getTimes() + " times used");
			ht.setGlobalTagWeight(caculator);
			hotTagMap.put(ht.getTagName(), ht);
		}
		return hotTagMap;
	}

	@Override
	public boolean isExist(String tagName) {
		List<TagTBean> allTags = getAll();
		for (int i = 0; i < allTags.size(); i++) {
			if (allTags.get(i).getName().equals(tagName))
				return true;
		}
		return false;
	}

	@Override
	public boolean save(String tagName) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagSession session = null;

		TagTBean bean = new TagTBean();
		bean.setName(tagName);
		bean.setTimes(1);
		try {
			session = (TagSession) SessionFactory.openSession(SessionType.Tag);
			return session.save(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean update(String tagName) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		TagSession session = null;

		TagTBean tagBean = null;
		try {
			session = (TagSession) SessionFactory.openSession(SessionType.Tag);
			return session.update(tagName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
