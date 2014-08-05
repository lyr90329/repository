package repository.actions.news;

import java.util.List;

import repository.actions.BaseAction;
import repository.service.NewsSerivce;

import cn.org.act.sdp.repository.cleavage.entity.NewsTBean;

public class GetNewsAction extends BaseAction {
	private static final long serialVersionUID = -3706422105686067296L;
	private int num; // number of news get
	private NewsSerivce newsService;
	private List<NewsTBean> news;

	public String execute() throws Exception {
		news = newsService.getLatest(num);
		return SUCCESS;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public NewsSerivce getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsSerivce newsService) {
		this.newsService = newsService;
	}

	public List<NewsTBean> getNews() {
		return news;
	}

	public void setNews(List<NewsTBean> news) {
		this.news = news;
	}

}
