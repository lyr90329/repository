package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import cn.org.act.sdp.repository.cloud.entity.ServiceLogTBean;
import cn.org.act.sdp.repository.cloud.session.ServiceLogSession;
import cn.org.act.sdp.repository.cloud.session.SessionFactory;
import cn.org.act.sdp.repository.cloud.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;

import repository.service.ServiceLogService;

public class ServiceLogServiceImpl implements ServiceLogService {

	public ServiceLogServiceImpl() {
		RepositoryConf.managerInit();
		RepositoryConf.poolInit();
	}

	@Override
	public List get(String name) {

		ServiceLogSession serviceLogSession = null;
		List<ServiceLogTBean> list = new LinkedList<ServiceLogTBean>();

		try {
			serviceLogSession = (ServiceLogSession) SessionFactory
					.openSession(SessionType.serviceLog);
			list = serviceLogSession.get(name);
			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return list;
		} finally {
			if (serviceLogSession != null)
				serviceLogSession.close();
		}
	}

	@Override
	public List getAll() {
		ServiceLogSession serviceLogSession = null;
		List<ServiceLogTBean> list = new LinkedList<ServiceLogTBean>();

		try {
			serviceLogSession = (ServiceLogSession) SessionFactory
					.openSession(SessionType.serviceLog);
			list = serviceLogSession.getAll();
			return list;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return list;
		} finally {
			if (serviceLogSession != null)
				serviceLogSession.close();
		}
	}

	@Override
	public boolean save(Object o) {
		ServiceLogSession serviceLogSession = null;

		try {
			serviceLogSession = (ServiceLogSession) SessionFactory
					.openSession(SessionType.serviceLog);
			serviceLogSession.save(o);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (serviceLogSession != null)
				serviceLogSession.close();
		}
	}

}
