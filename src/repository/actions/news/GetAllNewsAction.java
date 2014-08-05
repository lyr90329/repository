package repository.actions.news;

import java.util.LinkedList;
import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.NewsTBean;

import repository.actions.BaseAction;
import repository.service.NewsSerivce;

public class GetAllNewsAction extends BaseAction {

	private static final long serialVersionUID = -1502844709655350287L;

	private List<NewsTBean> news;
	private NewsSerivce newsService;

	public String execute() {
		List<NewsTBean> allNews = newsService.getAll();
		this.getPage().setTotalLine(allNews.size());
		news = new LinkedList<NewsTBean>();
		for (int i = getPage().getBeginLine(); i <= getPage().getEndLine(); i++) {
			news.add(allNews.get(i));
		}
		return SUCCESS;
	}

	public List<NewsTBean> getNews() {
		return news;
	}

	public void setNews(List<NewsTBean> news) {
		this.news = news;
	}

	public NewsSerivce getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsSerivce newsService) {
		this.newsService = newsService;
	}

}
