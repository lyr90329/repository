package repository.actions.cluster;

import java.util.LinkedList;
import java.util.List;

import repository.actions.BaseAction;
import repository.entity.Category;
import repository.service.ServiceClusterService;
import repository.service.ServiceService;
import repository.util.ServParaLenUtil;

import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServicesByClusterAction extends BaseAction {

	private static final long serialVersionUID = 4793583578113981025L;

	private long cId;
	private List<Category> categories;
	private List<ServiceTBean> services;
	private ServiceService serviceService;
	private ServiceClusterService serviceClusterService;

	public String execute() throws Exception {
		List<ServiceTBean> servicesByCluster = new LinkedList<ServiceTBean>();
		categories = serviceClusterService.getCategory();
		if (cId <= 0) {
			cId = categories.get(0).getClusterId();
		}
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getClusterId() == cId) {
				servicesByCluster = categories.get(i).getServices();
				break;
			}
		}
		this.getPage().setTotalLine(servicesByCluster.size());
		services = new LinkedList<ServiceTBean>();
		for (int i = getPage().getBeginLine(); i <= getPage().getEndLine(); i++) {
			services.add(ServParaLenUtil.cut(servicesByCluster.get(i)));
		}
		return SUCCESS;
	}

	public long getCId() {
		return cId;
	}

	public void setCId(long id) {
		cId = id;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<ServiceTBean> getServices() {
		return services;
	}

	public void setServices(List<ServiceTBean> services) {
		this.services = services;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public ServiceClusterService getServiceClusterService() {
		return serviceClusterService;
	}

	public void setServiceClusterService(
			ServiceClusterService serviceClusterService) {
		this.serviceClusterService = serviceClusterService;
	}
}
