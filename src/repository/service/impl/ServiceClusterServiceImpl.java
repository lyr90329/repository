package repository.service.impl;

import java.util.LinkedList;
import java.util.List;

import repository.entity.Category;
import repository.service.ServiceClusterService;
import repository.service.ServiceService;

import cn.org.act.sdp.repository.cleavage.config.SqlManagerConf;
import cn.org.act.sdp.repository.cleavage.entity.ServiceClusterTBean;
import cn.org.act.sdp.repository.cleavage.session.ServiceClusterSession;
import cn.org.act.sdp.repository.cleavage.session.SessionFactory;
import cn.org.act.sdp.repository.cleavage.session.SessionType;
import cn.org.act.sdp.repository.crust.impl.RepositoryConf;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServiceClusterServiceImpl implements ServiceClusterService {

	private ServiceService serviceDao;

	@Override
	public List<Category> getCategory(int clusterNum) {
		List<Category> categories = new LinkedList<Category>(); // Category����
		List<Long> clusters; // ���е�clusterId����
		List<ServiceClusterTBean> serviceClusters;
		List<ServiceTBean> services;

		Category category;
		ServiceTBean service;

		serviceDao = new ServiceServiceImpl();

		clusters = getDistinctClusterId();
		clusterNum = clusters.size() > clusterNum ? clusterNum : clusters
				.size();
		for (int i = 0; i < clusterNum; i++) {
			category = new Category();
			category.setClusterId(clusters.get(i));
			serviceClusters = getServicesByClusterId(clusters.get(i));
			services = new LinkedList<ServiceTBean>();
			for (int j = 0; j < serviceClusters.size(); j++) {
				service = serviceDao.getById(new Long(1), serviceClusters
						.get(j).getserviceId());
				services.add(service);
			}
			category.setServices(services);
			category.setSize(serviceClusters.size());
			categories.add(category);
		}
		return categories;
	}

	@Override
	public List<Category> getSimpleCategory(int clusterNum) {
		List<Category> categories = new LinkedList<Category>(); // Category����
		List<Long> clusters; // ���е�clusterId����

		Category category;

		clusters = getDistinctClusterId();
		clusterNum = clusters.size() > clusterNum ? clusterNum : clusters
				.size();
		for (int i = 0; i < clusterNum; i++) {
			category = new Category();
			category.setClusterId(clusters.get(i));
			category.setSize(getServicesByClusterId(clusters.get(i)).size());
			categories.add(category);
		}
		return categories;
	}

	@Override
	public List<Category> getCategory() {
		int clusterNum = getDistinctClusterId().size();
		return getCategory(clusterNum);
	}

	@Override
	public List getDistinctClusterId() {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceClusterSession session = null;

		List<Long> ids = new LinkedList<Long>();
		try {
			session = (ServiceClusterSession) SessionFactory
					.openSession(SessionType.ServiceCluster);
			ids = session.getDistinctClusterId();
			return ids;
		} catch (Exception e) {
			e.printStackTrace();
			return ids;
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<ServiceClusterTBean> getServicesByClusterId(long clusterId) {
		RepositoryConf.poolInit();
		SqlManagerConf.init();

		ServiceClusterSession session = null;

		List<ServiceClusterTBean> beans = new LinkedList<ServiceClusterTBean>();
		try {
			session = (ServiceClusterSession) SessionFactory
					.openSession(SessionType.ServiceCluster);
			beans = session.getServicesByClusterId(clusterId);
			return beans;
		} catch (Exception e) {
			e.printStackTrace();
			return beans;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
