package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.ServiceKeywordTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;

/**
 * Service定义的接口
 * 
 * @author Administrator
 * 
 */
public interface ServiceService {

	public List<ServiceTBean> getAll(long jobId);

	public ServiceTBean getByUrl(String url, Long jobId);

	public ServiceTBean getById(Long jobId, Long id);

	public boolean update(ServiceTBean serviceBean);

	/**
	 *  通过一组ServiceKeywordTBean提供的serviceId获取一组ServiceTBean
	 * @param jobId
	 * @param serviceKeywordBeans
	 * @return
	 */
	public List<ServiceTBean> getById(Long jobId,
			List<ServiceKeywordTBean> serviceKeywordBeans);

}
