package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.NewsTBean;

/**
 * 新闻定义的借口
 * @author Mandula
 *
 */
public interface NewsSerivce {
	
	/**
	 * 获取所有的新闻
	 * @return 新闻集合
	 */
	public List<NewsTBean> getAll();
	
	/**
	 * 按照给定的参数获取最近新闻
	 * @param num 新闻数量
	 * @return 新闻集合
	 */
	public List<NewsTBean> getLatest(int num);
	
	/**
	 * 获取一个NewsTBean
	 * @param title
	 * @return
	 */
	public NewsTBean get(String title);
	public NewsTBean get(long id);
}
