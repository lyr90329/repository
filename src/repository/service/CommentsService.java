package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.CommentsTBean;

/**
 * 用户评价接口
 * @author Mandula
 *
 */
public interface CommentsService {

	/**
	 * 保存一个用户评价
	 * @param jobId 
	 * @param serviceId 被评价的serviceId
	 * @param comment 评价的内容
	 * @param fromUser 评价的用户，null的时候数据库相应的值为-1
	 * @return 评价保存是否成功
	 */
	public boolean save(Long jobId,Long serviceId,String comment,String fromUser);
	
	/**
	 * 通过ServiveId获取用户评价集合
	 * @param jobId
	 * @param serviceId
	 * @return 本serviceId相应Service的用户评价集合
	 */
	public List get(Long jobId,Long serviceId);
	
	/**
	 * 通过commentsId获取一个commentTBean
	 * @param id
	 * @return
	 */
	public CommentsTBean get(long id);
	
	/**
	 * 返回所有的用户评价
	 * @param jobId
	 * @return 用户评价集合
	 */
	public List getAll(Long jobId);
	
	/**
	 * 更新一个用户评价
	 * @param o
	 * @return
	 */
	public boolean update(Object o);
}
