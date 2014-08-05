package repository.actions.service;

import java.util.LinkedList;
import java.util.List;

import repository.actions.BaseAction;
import repository.service.ServiceService;
import repository.service.TagService;
import repository.service.TagServiceService;

import cn.org.act.sdp.repository.cleavage.entity.TagServiceTBean;
import cn.org.act.sdp.repository.cleavage.entity.TagTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServiceOverViewAction extends BaseAction {

	private static final long serialVersionUID = -5407405969658255283L;

	private long serviceId;
	private Long newServiceId;
	private ServiceTBean serviceBean;
	private List<TagTBean> tags;
	private ServiceService serviceService;
	private TagService tagService;
	private TagServiceService tagServiceService;

	public String execute() throws Exception {
		if (newServiceId != null)
			serviceId = newServiceId;
		Long jobId = new Long(1);
		serviceBean = serviceService.getById(jobId, serviceId);
		//获取属于此Service的所有TagTBean
		List<TagServiceTBean> tagServiceBeans = tagServiceService
				.getByServiceId(serviceId);
		tags = new LinkedList<TagTBean>();
		TagTBean tagBean = null;
		for (int i = 0; i < tagServiceBeans.size(); i++) {
			tagBean = tagService.getByName(tagServiceBeans.get(i).getTagName());
			tags.add(tagBean);
		}
		return SUCCESS;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public void setNewServiceId(Long newServiceId) {
		this.newServiceId = newServiceId;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public ServiceTBean getServiceBean() {
		return serviceBean;
	}

	public void setServiceBean(ServiceTBean serviceBean) {
		this.serviceBean = serviceBean;
	}

	public List<TagTBean> getTags() {
		return tags;
	}

	public void setTags(List<TagTBean> tags) {
		this.tags = tags;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public TagServiceService getTagServiceService() {
		return tagServiceService;
	}

	public void setTagServiceService(TagServiceService tagServiceService) {
		this.tagServiceService = tagServiceService;
	}

	public long getNewServiceId() {
		return newServiceId;
	}

	public void setNewServiceId(long newServiceId) {
		this.newServiceId = newServiceId;
	}

}
