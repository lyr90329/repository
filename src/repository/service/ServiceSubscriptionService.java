package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cloud.entity.ServiceSubscriptionTBean;

public interface ServiceSubscriptionService {

	public boolean delete(String userName, long serviceId);

	public Object get(String userName, long serviceId);

	public List<ServiceSubscriptionTBean> getByName(String userName);

	public boolean save(Object o);

	public boolean update(Object o);

	public List getAll();
}
