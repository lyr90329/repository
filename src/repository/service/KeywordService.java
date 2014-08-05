package repository.service;

import cn.org.act.sdp.repository.cleavage.entity.KeywordTBean;

/**
 * 关键字定义接口
 * @author Mandula
 *
 */
public interface KeywordService {
	
	/**
	 * 通过value获取一个KeywordTBean,没有则返回null
	 * @param jobId
	 * @param value
	 * @return
	 */
	public KeywordTBean getByValue(Long jobId,String value);
}
