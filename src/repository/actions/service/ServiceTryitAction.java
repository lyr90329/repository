package repository.actions.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;

import repository.actions.BaseAction;
import repository.service.OperationService;
import repository.service.ServiceService;
import tryit_Operation.ServiceDetail;

public class ServiceTryitAction extends BaseAction {

	private static final long serialVersionUID = -4172188660220809998L;

	private long serviceId;
	private long operationId;
	private String input;

	private ServiceTBean serviceBean;
	private OperationTBean operationBean;
	private List<OperationTBean> operations;

	private ArrayList inputParaList;
	private List outputParaList;
	private List resultList;

	private ServiceDetail serviceDetail;
	private ServiceService serviceService;
	private OperationService operationService;

	public String getServiceInput() {
		serviceBean = serviceService.getById(new Long(1), serviceId);
		operations = operationService.getByServiceId(new Long(1), serviceId);
		if (operationId < 1) {
			if (operations.size() <= 0) //不存在Operation
				return SUCCESS;
			operationId = operations.get(0).getId();
		}
		operationBean = operationService.getById(new Long(1), operationId);
		// String wsdlUrl =
		// "http://192.168.3.252:8080/axis2/services/myFirstWS?wsdl";
		// String operationName = "add";
		try {
			serviceDetail.getserviceDetail(serviceBean.getWsdlUrl());
			// serviceDetail.getWsdlAddress();
			inputParaList = (ArrayList) serviceDetail
					.getInputParamrters(operationBean.getName());
		} catch (Exception ex) {
			inputParaList = null;
			return SUCCESS;
		}
		return SUCCESS;
	}

	public String getServiceOutput() {
		Map<String, String> inputMap = new HashMap<String, String>();
		if (input != null && !input.equals("")) {
			String[] inputs = input.split(",");
			for (int i = 0; i < inputs.length; i++) {
				String[] keyValue = inputs[i].split(":");
				inputMap.put(keyValue[0], keyValue[1]);
			}
		}
		serviceBean = serviceService.getById(new Long(1), serviceId);
		operationBean = operationService.getById(new Long(1), operationId);
		// String wsdlUrl =
		// "http://192.168.3.252:8080/axis2/services/myFirstWS?wsdl";
		// String operationName = "add";
		try {
			serviceDetail.getserviceDetail(serviceBean.getWsdlUrl());
			serviceDetail.getWsdlAddress();
			inputParaList = (ArrayList) serviceDetail
					.getInputParamrters(operationBean.getName());
			outputParaList = serviceDetail.getOutputParamrters(operationBean
					.getName());
			Iterator iter = inputMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				serviceDetail.setParametersValue(inputParaList, (String) entry
						.getKey(), (String) entry.getValue());
			}
			resultList = serviceDetail.runWebService(inputParaList,
					outputParaList, operationBean.getName());
		} catch (Exception ex) {
			inputParaList = null;
			resultList = null;
			return SUCCESS;
		}
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

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public ServiceTBean getServiceBean() {
		return serviceBean;
	}

	public void setServiceBean(ServiceTBean serviceBean) {
		this.serviceBean = serviceBean;
	}

	public OperationTBean getOperationBean() {
		return operationBean;
	}

	public void setOperationBean(OperationTBean operationBean) {
		this.operationBean = operationBean;
	}

	public List<OperationTBean> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationTBean> operations) {
		this.operations = operations;
	}

	public ArrayList getInputParaList() {
		return inputParaList;
	}

	public void setInputParaList(ArrayList inputParaList) {
		this.inputParaList = inputParaList;
	}

	public List getOutputParaList() {
		return outputParaList;
	}

	public void setOutputParaList(List outputParaList) {
		this.outputParaList = outputParaList;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public ServiceDetail getServiceDetail() {
		return serviceDetail;
	}

	public void setServiceDetail(ServiceDetail serviceDetail) {
		this.serviceDetail = serviceDetail;
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
