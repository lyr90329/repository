package repository.actions.service;

import java.util.Calendar;
import java.util.List;

import repository.actions.BaseAction;
import repository.service.OperationService;
import repository.service.ServiceService;

import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServiceOperationsAction extends BaseAction{

	private static final long serialVersionUID = -3649919885399369404L;

	private long serviceId;
	private long operationId;
	private String regDate;
	private String nowDate;
	private int width;		//拖动按钮的宽度像素
	private ServiceTBean serviceBean;
	private OperationTBean operatonBean;
	private List<OperationTBean> operations;
	private ServiceService serviceService;
	private OperationService operationService;
	
	public String execute()throws Exception {
		Long jobId = new Long(1);
		
		serviceBean = serviceService.getById(jobId, serviceId);
		operations = operationService.getByServiceId(jobId, serviceBean.getId());
		if(operations.size()>0 && operationId<=0){
			operationId = operations.get(0).getId();
			operatonBean = operations.get(0);
		}
		
		regDate = serviceBean.getRegisterDate();  //获取注册时间
		Calendar now = Calendar.getInstance();		//获取现在的时间
		nowDate = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE);
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

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
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
	
}
