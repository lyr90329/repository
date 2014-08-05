package repository.actions.service;

import java.util.LinkedList;
import java.util.List;

import repository.actions.BaseAction;
import repository.service.ServiceService;
import repository.service.TagServiceService;
import repository.util.ServParaLenUtil;

import cn.org.act.sdp.repository.cleavage.entity.TagServiceTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServicesByTagAction extends BaseAction {

	private static final long serialVersionUID = -6697893959883486791L;

	private String tagName;
	private List<ServiceTBean> services;
	private TagServiceService tagServiceService;
	private ServiceService serviceService;

	public String execute() throws Exception {
		Long jobId = new Long(1);
		List<TagServiceTBean> list = tagServiceService.getByTagName(tagName);
		List<ServiceTBean> serviceByTag = new LinkedList<ServiceTBean>();
		for (int i = 0; i < list.size(); i++) {
			serviceByTag.add(serviceService.getById(jobId, list.get(i)
					.getServiceId()));
		}
		this.getPage().setTotalLine(serviceByTag.size());
		services = new LinkedList<ServiceTBean>();
		for (int i = getPage().getBeginLine(); i <= getPage().getEndLine(); i++) {
			services.add(ServParaLenUtil.cut(serviceByTag.get(i)));
		}
		return SUCCESS;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<ServiceTBean> getServices() {
		return services;
	}

	public void setServices(List<ServiceTBean> services) {
		this.services = services;
	}

	public TagServiceService getTagServiceService() {
		return tagServiceService;
	}

	public void setTagServiceService(TagServiceService tagServiceService) {
		this.tagServiceService = tagServiceService;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

}
