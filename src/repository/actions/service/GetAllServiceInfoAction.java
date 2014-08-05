package repository.actions.service;

import java.util.LinkedList;
import java.util.List;

import repository.actions.BaseAction;
import repository.service.ServiceService;
import repository.util.ServParaLenUtil;

import cn.org.act.sdp.repository.entity.ServiceTBean;

public class GetAllServiceInfoAction extends BaseAction {

	private static final long serialVersionUID = 538381381309617667L;

	private List<ServiceTBean> services;
	private ServiceService serviceService;

	public String execute() throws Exception {
		List<ServiceTBean> allServices = serviceService.getAll(1);
		getPage().setTotalLine(allServices.size());
		services = new LinkedList<ServiceTBean>();
		for (int i = getPage().getBeginLine(); i <= getPage().getEndLine(); i++) {
			services.add(ServParaLenUtil.cut(allServices.get(i)));
		}
		return SUCCESS;
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
}
