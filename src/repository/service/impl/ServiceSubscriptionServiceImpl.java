package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import cn.org.act.sdp.repository.cloud.entity.ServiceLogTBean;
import cn.org.act.sdp.repository.cloud.entity.ServiceSubscriptionTBean;
import cn.org.act.sdp.repository.cloud.session.ServiceLogSession;
import cn.org.act.sdp.repository.cloud.session.ServiceSubscriptionSession;
import cn.org.act.sdp.repository.cloud.session.SessionFactory;
import cn.org.act.sdp.repository.cloud.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

import repository.service.ServiceSubscriptionService;

public class ServiceSubscriptionServiceImpl implements
		ServiceSubscriptionService {

	public ServiceSubscriptionServiceImpl() {
		RepositoryConf.managerInit();
		RepositoryConf.poolInit();
	}

	@Override
	public boolean delete(String userName, long serviceId) {
		ServiceSubscriptionSession serviceSubscriptionSession = null;
		Object[] parameters = new Object[2];
		parameters[0] = userName;
		parameters[1] = new Long(serviceId);

		try {
			serviceSubscriptionSession = (ServiceSubscriptionSession) SessionFactory
					.openSession(SessionType.serviceSubscription);
			return serviceSubscriptionSession.delete(parameters);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (serviceSubscriptionSession != null)
				serviceSubscriptionSession.close();
		}
	}

	@Override
	public Object get(String userName, long serviceId) {
		ServiceSubscriptionSession serviceSubscriptionSession = null;
		Object[] parameters = new Object[2];
		parameters[0] = userName;
		parameters[1] = new Long(serviceId);

		try {
			serviceSubscriptionSession = (ServiceSubscriptionSession) SessionFactory
					.openSession(SessionType.serviceSubscription);
			return serviceSubscriptionSession.get(parameters);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return null;
		} finally {
			if (serviceSubscriptionSession != null)
				serviceSubscriptionSession.close();
		}
	}

	@Override
	public List getAll() {
		ServiceSubscriptionSession serviceSubscriptionSession = null;
		List<ServiceSubscriptionTBean> list = null;

		try {
			serviceSubscriptionSession = (ServiceSubscriptionSession) SessionFactory
					.openSession(SessionType.serviceSubscription);
			list = serviceSubscriptionSession.getAll();
			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return list;
		} finally {
			if (serviceSubscriptionSession != null)
				serviceSubscriptionSession.close();
		}
	}

	@Override
	public boolean save(Object o) {
		ServiceSubscriptionSession serviceSubscriptionSession = null;

		try {
			serviceSubscriptionSession = (ServiceSubscriptionSession) SessionFactory
					.openSession(SessionType.serviceSubscription);
			return serviceSubscriptionSession.save(o);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (serviceSubscriptionSession != null)
				serviceSubscriptionSession.close();
		}
	}

	@Deprecated
	@Override
	public boolean update(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ServiceSubscriptionTBean> getByName(String userName) {
		ServiceSubscriptionSession serviceSubscriptionSession = null;
		List<ServiceSubscriptionTBean> list = null;

		try {
			serviceSubscriptionSession = (ServiceSubscriptionSession) SessionFactory
					.openSession(SessionType.serviceSubscription);
			list = serviceSubscriptionSession.getByName(userName);
			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return list;
		} finally {
			if (serviceSubscriptionSession != null)
				serviceSubscriptionSession.close();
		}
	}

}
