package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import repository.service.NewsSerivce;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.NewsTBean;
import cn.org.act.sdp.repository.cleavage.session.NewsSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

public class NewsServiceImpl implements NewsSerivce {

	@Override
	public List<NewsTBean> getAll() {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		NewsSession session = null;

		List<NewsTBean> news = new LinkedList<NewsTBean>();

		try {
			session = (NewsSession) SessionFactory
					.openSession(SessionType.News);
			news = session.getAll();
			return news;
		} catch (Exception e) {
			e.printStackTrace();
			return news;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<NewsTBean> getLatest(int num) {
		List<NewsTBean> news = getAll();
		List<NewsTBean> latestNews = new LinkedList<NewsTBean>();

		num = num > news.size() ? news.size() : num;
		for (int i = 0; i < num; i++) {
			latestNews.add(news.get(i));
		}
		return latestNews;
	}

	@Override
	public NewsTBean get(String title) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		NewsSession session = null;

		try {
			session = (NewsSession) SessionFactory
					.openSession(SessionType.News);
			return (NewsTBean) session.get(title);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public NewsTBean get(long id) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		NewsSession session = null;

		try {
			session = (NewsSession) SessionFactory
					.openSession(SessionType.News);
			return (NewsTBean) session.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
