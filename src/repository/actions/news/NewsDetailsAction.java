package repository.actions.news;

import cn.org.act.sdp.repository.cleavage.entity.NewsTBean;
import repository.actions.BaseAction;
import repository.service.NewsSerivce;

public class NewsDetailsAction extends BaseAction {

	private static final long serialVersionUID = -7455644690627807935L;

	private long id;
	private NewsTBean newsBean;
	private NewsSerivce newsService;

	public String execute() {
		if (id > 0)
			newsBean = newsService.get(id);
		return SUCCESS;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public NewsTBean getNewsBean() {
		return newsBean;
	}

	public void setNewsBean(NewsTBean newsBean) {
		this.newsBean = newsBean;
	}

	public NewsSerivce getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsSerivce newsService) {
		this.newsService = newsService;
	}

}
