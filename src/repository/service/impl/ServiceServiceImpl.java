package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import repository.service.ServiceService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.ServiceKeywordTBean;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;
import cn.org.act.sdp.repository.entity.ServiceTBean;
import cn.org.act.sdp.repository.session.ServiceSession;
import cn.org.act.sdp.repository.session.SessionFactory;
import cn.org.act.sdp.repository.session.SessionType;

public class ServiceServiceImpl implements ServiceService {

	@Override
	public List<ServiceTBean> getAll(long jobId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceSession session = null;

		List<ServiceTBean> services = new LinkedList<ServiceTBean>();
		try {
			session = (ServiceSession) SessionFactory
					.openSession(SessionType.Service);
			services = (List<ServiceTBean>) session.getAll(jobId);
			return services;
		} catch (OutOfMemoryError o) {
			System.err.println(o.getMessage());
			o.printStackTrace(System.err);
			return services;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return services;
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public ServiceTBean getById(Long jobId, Long id) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceSession session = null;

		ServiceTBean serviceBean = null;
		String[] para = new String[2];
		try {
			session = (ServiceSession) SessionFactory
					.openSession(SessionType.Service);
			para[0] = jobId.toString();
			para[1] = id.toString();
			serviceBean = (ServiceTBean) session.getById(para);
			return serviceBean;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return serviceBean;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<ServiceTBean> getById(Long jobId,
			List<ServiceKeywordTBean> serviceKeywordBeans) {
		List<ServiceTBean> serviceBeans = new LinkedList<ServiceTBean>();
		ServiceTBean serviceBean = null;
		for (int i = 0; i < serviceKeywordBeans.size(); i++) {
			serviceBean = getById(jobId, serviceKeywordBeans.get(i)
					.getServiceId());
			serviceBeans.add(serviceBean);
		}
		return serviceBeans;
	}

	@Override
	public ServiceTBean getByUrl(String url, Long jobId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceSession session = null;

		ServiceTBean serviceBean = null;
		String[] para = new String[2];
		try {
			session = (ServiceSession) SessionFactory
					.openSession(SessionType.Service);
			para[0] = jobId.toString();
			para[1] = url;
			serviceBean = (ServiceTBean) session.get(para);
			return serviceBean;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
			return serviceBean;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean update(ServiceTBean serviceBean) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceSession session = null;

		try {
			session = (ServiceSession) SessionFactory
					.openSession(SessionType.Service);
			return session.update(serviceBean);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
