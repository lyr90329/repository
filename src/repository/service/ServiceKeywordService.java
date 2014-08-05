package repository.service;

import java.util.List;

public interface ServiceKeywordService {

	/**
	 *  通过keywordId获取ServiceKeywordTBean
	 * @param jobId
	 * @param keywordId
	 * @return
	 */
	public List getByKeywodId(Long jobId, Long keywordId);
}
