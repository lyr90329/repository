package repository.entity;

import java.util.LinkedList;
import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.CommentsTBean;
import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;
import cn.org.act.sdp.repository.query.common.UBean;

public class Pagination {
	/**
	 *  把从数据库中读取的所有service的记录按照每页显示数量和页数返回相应的子集
	 * @param list
	 * @param pageNum
	 *            must be > 0
	 * @param numPerPage
	 *            must be > 0
	 * @return
	 */
	public List<ServiceTBean> splitByPage(List<ServiceTBean> list, int pageNum,
			int numPerPage, int serviceNum) {
		List result = new LinkedList<ServiceTBean>();
		for (int i = (pageNum - 1) * numPerPage; i < (pageNum * numPerPage > serviceNum ? serviceNum
				: pageNum * numPerPage); i++) {
			result.add(list.get(i));
		}
		return result;
	}

	/**
	 * split UBeans searched by xpath into pages
	 * 
	 * @param list
	 * @param pageNum
	 * @param numPerPage
	 * @param beanNum
	 * @return
	 */
	public List<UBean> splitUBeanByPage(List<UBean> list, int pageNum,
			int numPerPage, int beanNum) {
		List result = new LinkedList<UBean>();
		for (int i = (pageNum - 1) * numPerPage; i < (pageNum * numPerPage > beanNum ? beanNum
				: pageNum * numPerPage); i++) {
			result.add(list.get(i));
		}
		return result;
	}

	/**
	 * 
	 * @param list
	 * @param pageNum
	 * @param numPerPage
	 * @param beanNum
	 * @return
	 */
	public List<OperationTBean> splitOperationsByPage(
			List<OperationTBean> list, int pageNum, int numPerPage, int beanNum) {
		List result = new LinkedList<OperationTBean>();
		for (int i = (pageNum - 1) * numPerPage; i < (pageNum * numPerPage > beanNum ? beanNum
				: pageNum * numPerPage); i++) {
			result.add(list.get(i));
		}
		return result;
	}

	/**
	 * 
	 * @param list
	 * @param pageNum
	 * @param numPerPage
	 * @param beanNum
	 * @return
	 */
	public List<CommentsTBean> splitCommentsByPage(List<CommentsTBean> list,
			int pageNum, int numPerPage, int beanNum) {
		List result = new LinkedList<CommentsTBean>();
		for (int i = (pageNum - 1) * numPerPage; i < (pageNum * numPerPage > beanNum ? beanNum
				: pageNum * numPerPage); i++) {
			result.add(list.get(i));
		}
		return result;
	}
}
