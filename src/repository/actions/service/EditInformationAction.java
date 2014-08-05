package repository.actions.service;

import repository.actions.BaseAction;
import repository.service.ServiceService;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class EditInformationAction extends BaseAction {
	private long serviceId;
	private String additionalInfo;
	private ServiceService serviceService;

	public String execute() throws Exception {
		ServiceTBean serviceBean = serviceService.getById(new Long(1),
				serviceId);
		serviceBean.setAdditionalInfo(additionalInfo);
		serviceService.update(serviceBean);
		return SUCCESS;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

}
