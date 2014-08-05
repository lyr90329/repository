package repository.actions.service;

import repository.actions.BaseAction;
import repository.service.ServiceService;

import com.opensymphony.xwork2.ActionContext;

import cn.org.act.sdp.repository.crust.impl.RepositoryConf;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServicePreUpdateAction extends BaseAction {

	private static final long serialVersionUID = 7503725111439649396L;

	private long serviceId;
	private String wsdl_url;
	private String description;
	private ServiceService serviceService;

	public String execute() throws Exception {
		RepositoryConf.managerInit();
		RepositoryConf.poolInit();
		Long jobId = new Long(1);
		ServiceTBean bean = serviceService.getById(jobId, serviceId);
		ActionContext ctx = ActionContext.getContext();
		String currentUser = (String) ctx.getSession().get("userName");
		if (currentUser == null)
			return LOGIN;
		if (currentUser.equals(bean.getUserName())) {
			wsdl_url = bean.getWsdlUrl();
			description = bean.getDescription();
			return SUCCESS;
		} else {
			return ERROR;
		}

	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public String getWsdl_url() {
		return wsdl_url;
	}

	public void setWsdl_url(String wsdl_url) {
		this.wsdl_url = wsdl_url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

}
