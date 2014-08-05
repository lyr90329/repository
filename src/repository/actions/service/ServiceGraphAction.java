package repository.actions.service;

import java.util.LinkedList;
import java.util.List;

import repository.actions.BaseAction;
import repository.entity.RelatedOperation;
import repository.service.OperationService;
import repository.service.ServiceGraphService;
import repository.service.ServiceService;

import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.cleavage.session.RelatedType;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServiceGraphAction extends BaseAction {

	private static final long serialVersionUID = 7998693526548464811L;

	private long serviceId;
	private long operationId;
	private ServiceTBean serviceBean;
	private OperationTBean operatonBean;
	private List<OperationTBean> operations;
	private List<RelatedOperation> foreOperations;
	private List<RelatedOperation> afterOperations;
	private ServiceService serviceService;
	private OperationService operationService;
	private ServiceGraphService serviceGraphService;

	public String execute() throws Exception {
		RelatedOperation relatedOperation;
		OperationTBean tempOperation;
		ServiceTBean tempService;
		foreOperations = new LinkedList<RelatedOperation>();
		afterOperations = new LinkedList<RelatedOperation>();
		serviceBean = serviceService.getById(new Long(1), serviceId);
		operations = operationService.getByServiceId(new Long(1), serviceBean
				.getId());
		if (operations.size() > 0 && operationId <= 0) {
			operationId = operations.get(0).getId();
			operatonBean = operations.get(0);
		}
		List<Long> foreOperationIds = serviceGraphService
				.getRelatedOperationIds(operationId, RelatedType.fore);
		List<Long> afterOperationIds = serviceGraphService
				.getRelatedOperationIds(operationId, RelatedType.after);
		if (foreOperationIds != null) {
			for (int i = 0; i < foreOperationIds.size(); i++) {
				relatedOperation = new RelatedOperation();
				tempOperation = operationService.getById(new Long(1),
						foreOperationIds.get(i));
				tempService = serviceService.getById(new Long(1), tempOperation
						.getServiceId());
				relatedOperation.setServiceId(tempService.getId());
				relatedOperation.setServiceName(tempService.getName());
				relatedOperation.setOperationId(tempOperation.getId());
				relatedOperation.setOperationName(tempOperation.getName());
				relatedOperation.setDescription(tempOperation.getDescription());
				relatedOperation.setFault(tempOperation.getFault());
				foreOperations.add(relatedOperation);
			}
		}
		if (afterOperationIds != null) {
			for (int i = 0; i < afterOperationIds.size(); i++) {
				relatedOperation = new RelatedOperation();
				tempOperation = operationService.getById(new Long(1),
						afterOperationIds.get(i));
				tempService = serviceService.getById(new Long(1), tempOperation
						.getServiceId());
				relatedOperation.setServiceId(tempService.getId());
				relatedOperation.setServiceName(tempService.getName());
				relatedOperation.setOperationId(tempOperation.getId());
				relatedOperation.setOperationName(tempOperation.getName());
				relatedOperation.setDescription(tempOperation.getDescription());
				relatedOperation.setFault(tempOperation.getFault());
				afterOperations.add(relatedOperation);
			}
		}
		System.out
				.println(foreOperations.size() + " " + afterOperations.size());
		return SUCCESS;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public long getOperationId() {
		return operationId;
	}

	public void setOperationId(long operationId) {
		this.operationId = operationId;
	}

	public ServiceTBean getServiceBean() {
		return serviceBean;
	}

	public void setServiceBean(ServiceTBean serviceBean) {
		this.serviceBean = serviceBean;
	}

	public OperationTBean getOperatonBean() {
		return operatonBean;
	}

	public void setOperatonBean(OperationTBean operatonBean) {
		this.operatonBean = operatonBean;
	}

	public List<OperationTBean> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationTBean> operations) {
		this.operations = operations;
	}

	public List<RelatedOperation> getForeOperations() {
		return foreOperations;
	}

	public void setForeOperations(List<RelatedOperation> foreOperations) {
		this.foreOperations = foreOperations;
	}

	public List<RelatedOperation> getAfterOperations() {
		return afterOperations;
	}

	public void setAfterOperations(List<RelatedOperation> afterOperations) {
		this.afterOperations = afterOperations;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public OperationService getOperationService() {
		return operationService;
	}

	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	public ServiceGraphService getServiceGraphService() {
		return serviceGraphService;
	}

	public void setServiceGraphService(ServiceGraphService serviceGraphService) {
		this.serviceGraphService = serviceGraphService;
	}

}
